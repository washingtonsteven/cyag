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
	File pageData;
	
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
	
	public void constructPage(int id, String folder)
	{
		pageData = new File(folder+id+".xml");
		choices = new LinkedList<Link>();
		
		try
		{
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			
			if (pageData.exists())
			{
				doc = db.parse(pageData);
				Element root = doc.getDocumentElement();
				NodeList nodes = root.getChildNodes();
				int nodeLength = nodes.getLength();
				
				this.id = Integer.parseInt(root.getAttribute("id"));
				
				for (int i = 0; i < nodeLength; i++)
				{
					Element curr = (Element) nodes.item(i);
					
					String tagName = curr.getTagName();
					
					if (tagName.equals("title"))
						title = curr.getTextContent();
					else if (tagName.equals("text"))
						text = curr.getTextContent();
					else if (tagName.equals("choice"))
					{
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
						
						if (tgt != -1 && !txt.isEmpty())
							choices.add(new Link(tgt,txt));
					}
				}
			}
			else
			{
				this.id = id;
				doc = db.newDocument();
				Element root = doc.createElement("page");
				root.setAttribute("id",id+"");
				doc.appendChild(root);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void setText(String text)
	{
		this.text = text;
		NodeList tList = doc.getElementsByTagName("text");
		
		if (tList.getLength() > 0)
			((Element) tList.item(0)).setTextContent(text);
		else
		{
			Element textElem = doc.createElement("text");
			textElem.setTextContent(text);
			
			doc.getDocumentElement().appendChild(textElem);
		}
	}

	public String getText()
	{
		return text;
	}

	public void setTitle(String title)
	{
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
	
	public boolean addChoice(Link choice)
	{
		Iterator<Link> iter = choices.iterator();
		
		while (iter.hasNext())
			if (iter.next().equals(choice)) return false;
		
		boolean success = choices.add(choice);
		
		if (!success) return success;
		
		Element choiceElem = doc.createElement("choice");
		
		if (choices.size() <= 1)
			choiceElem.setAttribute("id", "1");
		else
			choiceElem.setAttribute("id", (choices.size()+""));

		Element targetElem = doc.createElement("target");
		targetElem.setTextContent(choice.getTarget()+"");
		Element textElem = doc.createElement("link_text");
		textElem.setTextContent(choice.getText());
		
		choiceElem.appendChild(targetElem);
		choiceElem.appendChild(textElem);
		
		doc.getDocumentElement().appendChild(choiceElem);
		
		return success;
	}
	
	public boolean removeChoice(Link choice)
	{
		boolean success = choices.remove(choice);
		
		if (!success) return success;
		
		NodeList choiceList = doc.getElementsByTagName("choice");
		
		if (choiceList.getLength() <= 0) return false;
		
		success = false;
		for (int i = 0; i < choiceList.getLength(); i++)
		{
			Element curr = (Element) choiceList.item(i);
			NodeList linkChildren = curr.getChildNodes();
			
			if (success)
			{
				if (curr.getAttribute("id").isEmpty()) continue;
				
				int lid = Integer.parseInt(curr.getAttribute("id"));
				
				curr.setAttribute("id",(lid-1)+"");
			}
			
			if (Integer.parseInt(linkChildren.item(0).getTextContent()) == choice.getTarget() && linkChildren.item(1).getTextContent().equals(choice.getText()))
			{
				curr.getParentNode().removeChild(curr);
				success = true;
			}
		}
		
		return false;
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
		if (doc.getChildNodes().getLength() <= 0)
		{
			Element pageElem = doc.createElement("page");
			pageElem.setAttribute("id",id+"");
			
			Element textElem = doc.createElement("text");
			textElem.setTextContent(text);
			
			Element titleElem = doc.createElement("title");
			titleElem.setTextContent(title);
			
			doc.appendChild(pageElem);
			pageElem.appendChild(titleElem);
			pageElem.appendChild(textElem);
			
			Iterator<Link> iter = choices.iterator();
			int i = 1;
			
			while (iter.hasNext())
			{
				Link curr = iter.next();
				
				Element choiceElem = doc.createElement("choice");
				choiceElem.setAttribute("id", i+"");
				
				Element targetElem = doc.createElement("target");
				targetElem.setTextContent(curr.getTarget()+"");
				
				Element linkTextElem = doc.createElement("link_text");
				linkTextElem.setTextContent(curr.getText());
				
				choiceElem.appendChild(targetElem);
				choiceElem.appendChild(linkTextElem);
				pageElem.appendChild(choiceElem);
				
				i++;
			}
		}
		
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
	
	public Link getChoice(int index)
	{
		NodeList choices = doc.getElementsByTagName("choice");
		
		for (int i = 0; i < choices.getLength(); i++)
		{
			Element curr = (Element) choices.item(i);
			
			if (curr.getAttribute("id").equals(index+""))
			{
				NodeList linkChildren = curr.getChildNodes();
				String txt = "";
				int tgt = -1;
				
				for (int j = 0; j < linkChildren.getLength(); j++)
				{
					Element lCurr = (Element) linkChildren.item(j);
					String tagName = lCurr.getTagName();
					
					if (tagName.equals("target"))
					{
						if (!lCurr.getTextContent().isEmpty())
						tgt = Integer.parseInt(lCurr.getTextContent());
					}
					else
						txt = lCurr.getTextContent();
				}
				
				if (tgt != -1 && !txt.isEmpty())
					return new Link(tgt, txt);
			}
		}
		
		return null;
	}
}
