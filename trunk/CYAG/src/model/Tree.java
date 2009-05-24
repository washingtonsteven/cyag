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
	
	public static File getDefaultFile()
	{
		File f = new File("My Adventure");
		if (!f.exists())
			f.mkdir();
		
		return new File("My Adventure"+File.separator+"tree.xml");
	}
	
	/**
	 * Constructs a tree
	 * 
	 * @param filePath The filename of the xml document to be used for this tree. Does not have to exist.
	 */
	public Tree(String filePath)
	{
		treeData = new File(filePath);

		try
		{
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			
			if (!treeData.exists())
			{
				doc = db.newDocument();
				root = doc.createElement("tree");
				doc.appendChild(root);
				
				writeTree();
			}
			else
			{
				doc = db.parse(treeData);
				root = doc.getDocumentElement();
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
		NodeList pages = doc.getElementsByTagName("page");
		
		for (int i = 0; i < pages.getLength(); i++)
		{
			if (((Element) pages.item(i)).getAttribute("id").equals(p.getId()+""))
				return;
		}
		
		Element page = doc.createElement("page");
		page.setAttribute("id", p.getId()+"");
		page.setTextContent(p.getId()+".xml");
		doc.getDocumentElement().appendChild(page);
	}
	
	private void sortElements()
	{
		NodeList pages = doc.getElementsByTagName("page");
		Document sortedDoc = db.newDocument();
		Element sortedRoot = sortedDoc.createElement("tree");
		sortedDoc.appendChild(sortedRoot);
		int numPages = pages.getLength();
		
		for (int i = 1; i <= numPages; i++)
		{
			Element currPage = null;
			for (int j = 0; j < pages.getLength(); j++)
			{
				currPage = (Element) pages.item(j);
				if (Integer.parseInt(currPage.getAttribute("id")) == i)
					break;
			}
			
			if(currPage == null) continue;
			
			Element sortedPage = (Element) sortedDoc.adoptNode(currPage);
			
			sortedRoot.appendChild(sortedPage);
		}
		
		doc = sortedDoc;
	}
	
	/**
	 * Writes the content of the tree out to a file, by default "tree.xml"
	 */
	public void writeTree()
	{
		sortElements();
		try
		{
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
	
	public LinkedList<Page> getPages()
	{
		sortElements();
		LinkedList<Page> pageList = new LinkedList<Page>();
		
		NodeList pages = doc.getElementsByTagName("page");
		
		for (int i = 0; i < pages.getLength(); i++)
		{
			Element curr = (Element) (pages.item(i));
			Page p = getPageByFile(getFolder()+curr.getTextContent(), Integer.parseInt(curr.getAttribute("id")));
			
			if (p != null)
				pageList.add(p);
		}
		
		return pageList;
	}
	
	public boolean removeFromTree(int index)
	{
		NodeList pages = doc.getDocumentElement().getChildNodes();
		
		for (int i = 0; i < pages.getLength(); i++)
		{
			Element curr = (Element) pages.item(i);
			
			if (curr.getAttribute("id").isEmpty()) continue;
			
			int currIndex = Integer.parseInt(curr.getAttribute("id"));
			
			if (currIndex == index)
			{
				doc.getDocumentElement().removeChild(curr);
				(new File(index+".xml")).delete();
				writeTree();
				return true;
			}
		}
		return false;
	}
	
	private Page getPageByFile(String file, int id)
	{
		File pageFile = new File(file);	
		if (!pageFile.exists())
			return null;
		
		Page p = new Page(id, getFolder());
		
		return p;
	}
	
	public LinkedList<Page> getParents(int i)
	{
		LinkedList<Page> pages = getPages();
		LinkedList<Page> parents = new LinkedList<Page>();
		Iterator<Page> iter = pages.iterator();
		
		while (iter.hasNext())
		{
			Page curr = iter.next();
			LinkedList<Link> choices = curr.getChoices();
			Iterator<Link> linkIter = choices.iterator();
			
			while (linkIter.hasNext())
			{
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

	public String getFolder()
	{
		String path = treeData.getAbsolutePath();
		int treeIndex = path.indexOf(treeData.getName());
		path = path.substring(0, treeIndex);
		return path;
	}

	public File getFile()
	{
		return treeData;
	}
}
