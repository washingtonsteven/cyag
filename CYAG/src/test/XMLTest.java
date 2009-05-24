package test;

import model.Link;
import model.Page;
import model.Tree;

public class XMLTest
{
	public static void main (String [] args)
	{
		Page p = new Page(1, "Begin the story! Choose a path! I'm making this much longer to test stuff and everything. I should just copy paste I guess. I'm silly.", "This is the story...");
		p.addChoice(new Link(2, "Left"));
		p.addChoice(new Link(3, "Right"));
		
		Page p2 = new Page(2, "You have gone left. Huzzah!", "To the left (to the left)");
		Page p3 = new Page(3, "You have gone right. Huzzah!", "You spin my head round, baby, right round");
		
		p.save();
		p2.save();
		p3.save();
		System.err.println("Page XML File(s) Written.");
		
		Tree t = new Tree();
		t.addToTree(p3);
		t.addToTree(p2);
		t.addToTree(p);
		t.addToTree(p2);
		t.writeTree();
		System.err.println("Tree XML File Written.");
		
		System.out.println(t.getPages());
		
		
		System.err.println("Application Ended.");
	}
}
