package model;

import java.util.*;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult; 

import org.w3c.dom.*;

/**
 * This class defines a Page in the CYAG Framework
 *
 * A Page has text, a title, an id number, and a set of choices that lead to other pages
 * 
 * TODO: Add functionality for defining a page as the start page
 * 			Or perhaps just have page 1 be the first page always.
 * 
 * @author Steven Washington (washington.steven@gmail.com)
 *
 */
public class Page
{
	/** The id number of this Page */
	private int id;
	/** The set of choices available for this page */
	private LinkedList<Link> choices;
	/** The story text on this page */
	private String text;
	/** The title of this page */
	private String title;
	
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private File pageData;
	
	public Page(int id, String text, String title)
	{
		this (id, true);
		this.setTitle(title);
		this.setText(text);
		choices = new LinkedList<Link>();
	}
	
	public Page(int id, boolean overwrite)
	{
		if (overwrite)
			(new File(id+".xml")).delete();
		
		constructPage(id,"");
	}
	
	public Page (int id, String folder)
	{
		constructPage(id, folder);
	}
	
	/**
	 * Reads the info from "id.xml" into the Page object.
	 * If "id.xml" does not exist, it is created with only a &lt;page&gt; element
	 * 
	 * @param id The id of the Page to be loaded/created
	 * @param folder The folder containing id.xml
	 */
	public void constructPage(int id, String folder)
	{
		//set the file, instantice Linked Lists
		pageData = new File(folder+id+".xml");
		choices = new LinkedList<Link>();
		
		try
		{
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			
			//If the page is there, grab data from it
			if (pageData.exists())
			{
				doc = db.parse(pageData);
				Element root = doc.getDocumentElement();
				NodeList nodes = root.getChildNodes();
				//use this in loop instead of nodes.getLength()
				//As Elements are added, nodes.getLength() gets larger,
				//and causes an infinite loop
				int nodeLength = nodes.getLength();
				
				//Set the id based on the file
				this.id = Integer.parseInt(root.getAttribute("id"));
				
				//loop though the children
				for (int i = 0; i < nodeLength; i++)
				{
					Element curr = (Element) nodes.item(i);
					
					//Get the tag name
					String tagName = curr.getTagName();
					
					if (tagName.equals("title"))
						title = curr.getTextContent();
					else if (tagName.equals("text"))
						text = curr.getTextContent();
					else if (tagName.equals("choice"))
					{
						//Loop to Get the target and link text
						NodeList linkChildren = curr.getChildNodes();
						int linkLength = linkChildren.getLength();
						int tgt = -1;
						String txt = "";
						
						for (int j = 0; j < linkLength; j++)
						{
							Element lCurr = (Element) linkChildren.item(j);
							String lTagName = lCurr.getTagName();
							
							if (lTagName.equals("target"))
							{
								if (!lCurr.getTextContent().isEmpty())
								tgt = Integer.parseInt(lCurr.getTextContent());
							}
							else
								txt = lCurr.getTextContent();
						}
						
						//If we got valid data from the file create the choice
						if (tgt != -1 && !txt.isEmpty())
							choices.add(new Link(tgt,txt));
					}
				}
			}
			else //if the file doesn't exist
			{
				//assign id
				this.id = id;
				
				//Create a new Document
				doc = db.newDocument();
				//Create root element...<page> and the id
				Element root = doc.createElement("page");
				root.setAttribute("id",id+"");
				//add <page> to the document
				doc.appendChild(root);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Setter for the text of this page
	 * 
	 * @param text
	 */
	public void setText(String text)
	{
		this.text = text;
		NodeList tList = doc.getElementsByTagName("text");
		
		if (tList.getLength() > 0) //there should only be one, so grab the one at item(0)
			((Element) tList.item(0)).setTextContent(text);
		else //<text> isn't there...create it
		{
			Element textElem = doc.createElement("text");
			textElem.setTextContent(text);
			
			//append to root
			doc.getDocumentElement().appendChild(textElem);
		}
	}

	public String getText()
	{
		return text;
	}

	/**
	 * Setter for the title field
	 * 
	 * @param title
	 */
	public void setTitle(String title)
	{
		//see setText(String) for comments, it's identical
		this.title = title;
		NodeList tList = doc.getElementsByTagName("title");
		
		if (tList.getLength() > 0)
			((Element) tList.item(0)).setTextContent(title);
		else
		{
			Element textElem = doc.createElement("title");
			textElem.setTextContent(title);
			
			doc.getDocumentElement().appendChild(textElem);
		}
	}

	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Adds a choice to the list of choices
	 * 
	 * @param choice A Link object describing the choice
	 * @return true if the choice was added
	 */
	public boolean addChoice(Link choice)
	{
		Iterator<Link> iter = choices.iterator();
		
		//Make sure there are no duplicates
		while (iter.hasNext())
			if (iter.next().equals(choice)) return false;
		
		//Add it to the internal LinkedList
		boolean success = choices.add(choice);
		
		if (!success) return success;
		
		//Create the choice element
		Element choiceElem = doc.createElement("choice");
		
		//if there are no choices, make this the first one
		if (choices.size() <= 1)
			choiceElem.setAttribute("id", "1");
		else //make this the last one (1 + the id of the last choice)
			choiceElem.setAttribute("id", (choices.size()+""));

		//Create the choice sub elements <target> and <link_text>
		Element targetElem = doc.createElement("target");
		targetElem.setTextContent(choice.getTarget()+"");
		Element textElem = doc.createElement("link_text");
		textElem.setTextContent(choice.getText());
		
		//Add those children to <choice>
		choiceElem.appendChild(targetElem);
		choiceElem.appendChild(textElem);
		
		//Add choice to <page>
		doc.getDocumentElement().appendChild(choiceElem);
		
		return success;
	}
	
	/**
	 * Removes a choice from this page 
	 * 
	 * @param choice the choice to be removed
	 * @return true if the operation was successful
	 */
	public boolean removeChoice(Link choice)
	{
		//Remove it from the internal LinkedList
		boolean success = choices.remove(choice);
		
		if (!success) return success;
		
		//Get all the choices on this page
		NodeList choiceList = doc.getElementsByTagName("choice");
		
		//return false if there are no choices to remove
		if (choiceList.getLength() <= 0) return false;
		
		//Set to false
		success = false;
		//loop through choices
		for (int i = 0; i < choiceList.getLength(); i++)
		{
			Element curr = (Element) choiceList.item(i);
			//Get this choices children, <target> and <link_text>
			NodeList linkChildren = curr.getChildNodes();
			
			//If we already removed a choice, bump up the id of subsequent choices
			if (success)
			{
				//Make sure id is set (should be)
				if (curr.getAttribute("id").isEmpty()) continue;
				
				//Get the id of the current Link
				int lid = Integer.parseInt(curr.getAttribute("id"));
				
				//Set the id to that id minus one
				curr.setAttribute("id",(lid-1)+"");
			}
			
			//If the current element is equal to the given one
			if (new Link(Integer.parseInt(linkChildren.item(0).getTextContent()),linkChildren.item(1).getTextContent()).equals(choice))
			{
				//remove the choice from its parent 
				curr.getParentNode().removeChild(curr);
				//Set to true, so subsequent choices' id can be updated
				success = true;
			}
		}
		
		return success;
	}
	
	/**
	 * Saves a presentation of this Page to disk, in .xml format
	 * 
	 * @return true if the operation succeeded
	 */
	public boolean save()
	{
		try
		{
			saveXML();
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	private boolean saveXML() throws Exception
	{	
		//if the tree is empty
		if (doc.getChildNodes().getLength() <= 0)
		{
			//Set up all the Elements
			Element pageElem = doc.createElement("page");
			pageElem.setAttribute("id",id+"");
			
			Element textElem = doc.createElement("text");
			textElem.setTextContent(text);
			
			Element titleElem = doc.createElement("title");
			titleElem.setTextContent(title);
			
			//Add them to the document
			doc.appendChild(pageElem);
			pageElem.appendChild(titleElem);
			pageElem.appendChild(textElem);
			
			//Loop over the choices
			Iterator<Link> iter = choices.iterator();
			int i = 1;
			
			while (iter.hasNext())
			{
				Link curr = iter.next();
				
				//Create the choice element
				Element choiceElem = doc.createElement("choice");
				choiceElem.setAttribute("id", i+"");
				
				//create the target element
				Element targetElem = doc.createElement("target");
				targetElem.setTextContent(curr.getTarget()+"");
				
				//create the link text element
				Element linkTextElem = doc.createElement("link_text");
				linkTextElem.setTextContent(curr.getText());
				
				//append link text and target to choice
				choiceElem.appendChild(targetElem);
				choiceElem.appendChild(linkTextElem);
				//append choice to root
				pageElem.appendChild(choiceElem);
				
				i++;
			}
		}
		
		//Write the document to file
		TransformerFactory tf = TransformerFactory.newInstance(); 
		Transformer trans = tf.newTransformer(); 

		Source src = new DOMSource(doc); 
		Result dest = new StreamResult(new FileWriter(pageData)); 
		trans.transform(src, dest); 
		
		return true;
	}

	public int getId()
	{
		return id;
	}
	
	public String toString()
	{
		String toReturn = "";
		
		toReturn += id+":";
		toReturn += "    "+text.substring(0, Math.min(text.length(), 45));
		
		if (text.length() > 45)
			toReturn += "...";
		
		return toReturn+"\n";
	}

	public LinkedList<Link> getChoices()
	{
		return choices;
	}
	
	/**
	 * Gets a choice at index (i) based on the XML data
	 * 
	 * @param index
	 * @return a Link describing the choice at index (i)
	 */
	public Link getChoice(int index)
	{
		//Get all choices
		NodeList choices = doc.getElementsByTagName("choice");
		
		//loop through choices
		for (int i = 0; i < choices.getLength(); i++)
		{
			Element curr = (Element) choices.item(i);
			
			//if the current choice is equal to the one given
			if (curr.getAttribute("id").equals(index+""))
			{
				//Get the children of the choice
				NodeList linkChildren = curr.getChildNodes();
				String txt = "";
				int tgt = -1;
				
				//loop over them
				for (int j = 0; j < linkChildren.getLength(); j++)
				{
					//Get the data in the child elements
					Element lCurr = (Element) linkChildren.item(j);
					String tagName = lCurr.getTagName();
					
					if (tagName.equals("target")) //set target
					{
						if (!lCurr.getTextContent().isEmpty())
						tgt = Integer.parseInt(lCurr.getTextContent());
					}
					else //set text
						txt = lCurr.getTextContent();
				}
				
				//if data is valid, return a new link
				if (tgt != -1 && !txt.isEmpty())
					return new Link(tgt, txt);
			}
		}
		
		return null;
	}
}
