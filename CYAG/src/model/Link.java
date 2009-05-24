package model;

/**
 * Definition for a Link, a type of choice for the CYAG system
 * @author Steven Washington (washington.steven@gmail.com)
 *
 */
public class Link
{
	/** The id of the target page */
	private int target;
	/** The link text to be used for this link*/
	private String text;
	
	public Link(int target, String text)
	{
		this.setTarget(target);
		this.setText(text);
	}
	
	public Link(int target)
	{
		this(target, "");
	}

	public void setTarget(int target)
	{
		this.target = target;
	}

	public int getTarget()
	{
		return target;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}
	
	public String toString()
	{
		return "target: "+target+", text: "+text;
	}
	
	public boolean equals(Object o)
	{
		if (!(o instanceof Link)) return false;
		Link l = (Link) o;
		
		return (l.getTarget() == target && l.getText().equals(text));
	}
}
