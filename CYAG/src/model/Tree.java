package model;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Defines a tree for the CYAG
 * 
 * A tree in CYAG holds a set of pages, and stores the address to the .xml files that define them
 * 
 * @author Steven Washington (washington.steven@gmail.com)
 *
 */
public class Tree
{
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private Element root;
	private File treeData;
	
	public Tree()
	{
		this(getDefaultFile().getAbsolutePath());
	}
	
	/**
	 * Gets the default File for the tree in this project (current folder\My Adventure\tree.xml
	 * 
	 * @return a file that describes currentfolder\My ADventure\tree.xml
	 */
	public static File getDefaultFile()
	{
		//Make the My Adventure Directory
		File f = new File("My Adventure");
		if (!f.exists())
			f.mkdir();
		
		//Make the tree.xml file in My Adventure
		return new File("My Adventure"+File.separator+"tree.xml");
	}
	
	/**
	 * Constructs a tree
	 * 
	 * @param filePath The filename of the xml document to be used for this tree. Does not have to exist.
	 */
	public Tree(String filePath)
	{
		//Set the tree file
		treeData = new File(filePath);

		try
		{
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			
			//if the document doesn't exist
			if (!treeData.exists())
			{
				//create a new one, add the root element <tree>
				doc = db.newDocument();
				root = doc.createElement("tree");
				doc.appendChild(root);
				//write the tree to file
				writeTree();
			}
			else //the document exists
			{
				//parse the existing file
				doc = db.parse(treeData);
				root = doc.getDocumentElement();
				//make sure the pages are in order
				sortElements();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		

		System.err.println("Tree written to: "+getFolder());

	}
	
	/**
	 * Adds Page p to the tree
	 * 
	 * @param p The page to be added to the tree
	 */
	public void addToTree(Page p)
	{
		//Get all pages from file
		NodeList pages = doc.getElementsByTagName("page");
		
		//loop over them, if the page exists, return
		for (int i = 0; i < pages.getLength(); i++)
		{
			if (((Element) pages.item(i)).getAttribute("id").equals(p.getId()+""))
				return;
		}
		
		//Set up the page element
		Element page = doc.createElement("page");
		page.setAttribute("id", p.getId()+"");
		page.setTextContent(p.getId()+".xml");
		//append it to the root
		doc.getDocumentElement().appendChild(page);
	}
	
	private void sortElements()
	{
		//Get all the pages in the doc
		NodeList pages = doc.getElementsByTagName("page");
		
		//Create and set up a new document
		Document sortedDoc = db.newDocument();
		Element sortedRoot = sortedDoc.createElement("tree");
		sortedDoc.appendChild(sortedRoot);
		//Use this to avoid infinite loops as we add elements
		int numPages = pages.getLength();
		
		//loop over all the pages in original doc
		for (int i = 1; i <= numPages; i++)
		{
			Element currPage = null;
			//loop though all the pages looking for the i-th one.
			for (int j = 0; j < pages.getLength(); j++)
			{
				currPage = (Element) pages.item(j);
				//if we find it, break
				if (Integer.parseInt(currPage.getAttribute("id")) == i)
					break;
			}
			
			//make sure it isn't null
			if(currPage == null) continue;
			
			//tell the sorteddoc to adopt the node
			Element sortedPage = (Element) sortedDoc.adoptNode(currPage);
			
			//add the node to the root
			sortedRoot.appendChild(sortedPage);
		}
		
		//Set the current document to the new sorted document
		doc = sortedDoc;
	}
	
	/**
	 * Writes the content of the tree out to a file, by default "tree.xml"
	 */
	public void writeTree()
	{
		//Sort the tree
		sortElements();
		try
		{
			//Write the document to file as is
			TransformerFactory tf = TransformerFactory.newInstance(); 
			Transformer trans = tf.newTransformer(); 
		
			Source src = new DOMSource(doc); 
			Result dest = new StreamResult(new FileWriter(treeData)); 
			trans.transform(src, dest); 
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a list of all the pages
	 * 
	 * @return a Linked List of all the pages in this tree
	 */
	public LinkedList<Page> getPages()
	{
		//sort the pages
		sortElements();
		//Create the empty linked list
		LinkedList<Page> pageList = new LinkedList<Page>();
		
		//Get all the pages from file
		NodeList pages = doc.getElementsByTagName("page");
		
		//Loop over the pages
		for (int i = 0; i < pages.getLength(); i++)
		{
			Element curr = (Element) (pages.item(i));
			//construct the page based on the page element
			Page p = getPageByFile(getFolder()+curr.getTextContent(), Integer.parseInt(curr.getAttribute("id")));
			
			//add the page
			if (p != null)
				pageList.add(p);
		}
		
		return pageList;
	}
	
	/**
	 * Removes a page from the tree
	 * @param index the id of the Page on the tree
	 * @return true if the operation was successful
	 */
	public boolean removeFromTree(int index)
	{
		//Get all the pages
		NodeList pages = doc.getDocumentElement().getChildNodes();
		
		//Loop over the pages
		for (int i = 0; i < pages.getLength(); i++)
		{
			Element curr = (Element) pages.item(i);
			
			if (curr.getAttribute("id").isEmpty()) continue;
			
			int currIndex = Integer.parseInt(curr.getAttribute("id"));
			
			//Check to see if the current page is the one we want
			if (currIndex == index)
			{
				//if so, remove child from the document
				doc.getDocumentElement().removeChild(curr);
				//delete the xml file
				(new File(getFolder()+index+".xml")).delete();
				//rewrite the tree
				writeTree();
				return true;
			}
		}
		return false;
	}
	
	private Page getPageByFile(String file, int id)
	{
		//Make sure the file exists
		File pageFile = new File(file);	
		if (!pageFile.exists())
			return null;
		
		//Create a new page
		Page p = new Page(id, getFolder());
		
		//return the page
		return p;
	}
	
	/**
	 * Gets all the parents of the page with ID i
	 * A "parent" is a page that points at another
	 * 
	 * Ex. If Page 1 had a choice that led to Page 2, Page 1 is a parent of Page 2
	 * 
	 * @param i The id of the page to operate on
	 * @return A LinkedList of Pages that are parents of the Page with ID i
	 */
	public LinkedList<Page> getParents(int i)
	{
		//Get all the pages
		LinkedList<Page> pages = getPages();
		//Set up the LinkedList
		LinkedList<Page> parents = new LinkedList<Page>();
		Iterator<Page> iter = pages.iterator();
		
		//loop over the pages
		while (iter.hasNext())
		{
			Page curr = iter.next();
			LinkedList<Link> choices = curr.getChoices();
			
			//Check the choices
			Iterator<Link> linkIter = choices.iterator();
			
			//Loop through the choices
			while (linkIter.hasNext())
			{
				//If a link's target point to i, it's a parent, add it
				Link lCurr = linkIter.next();
				if (lCurr.getTarget() == i)
				{
					parents.add(curr);
					break;
				}
			}
		}
		
		return parents;
	}

	/**
	 * Gets the folder this Tree's XML file is located
	 * 
	 * @return a String describing the folder where the tree XML file is located
	 */
	public String getFolder()
	{
		//get the path of this Tree's XML
		String path = treeData.getAbsolutePath();
		//Get the index of the name (the part we need to chop of the URI)
		int treeIndex = path.indexOf(treeData.getName());
		//Get the path all the way up until the name
		path = path.substring(0, treeIndex);
		return path;
	}

	public File getFile()
	{
		return treeData;
	}
}
