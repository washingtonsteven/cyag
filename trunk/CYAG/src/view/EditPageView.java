package view;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import model.Link;
import model.Page;
import model.Tree;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class EditPageView extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JList pageList;
	private Page page;
	private Tree tree;
	private LinkedList<JTextField> choiceFields;
	private JLabel choicesLabel;
	private JLabel textLabel;
	private JLabel titleLabel;
	private JLabel titleLable;
	private JScrollPane textAreaScrollPane;
	private JTextArea textArea;
	private JTextField titleField;
	private JButton saveButton;
	private LinkedList<JComboBox> comboBoxes;
	private JPanel choicesPanel;
	private JPanel buttonPanel;
	private JPanel bottomPanel;
	private JPanel textTitlePanel;
	private JButton saveCloseButton;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	/*
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EditPageView inst = new EditPageView();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	public EditPageView()
	{
		this(1, null, null);
	}
	
	public EditPageView(int id, JList list, Tree tree) {
		this(new Page(id,tree.getFolder()), list, tree);
	}
	
	public EditPageView(Page p, JList list, Tree tree)
	{
		super();
		this.pageList = list;
		page = p;
		this.tree = tree;
		choiceFields = new LinkedList<JTextField>();
		comboBoxes = new LinkedList<JComboBox>();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("Choose Your Adventure Tool :: Page "+page.getId());
			{
				bottomPanel = new JPanel();
				getContentPane().add(bottomPanel, BorderLayout.SOUTH);
				BorderLayout choicesPanelLayout = new BorderLayout();
				choicesPanelLayout.setHgap(25);
				choicesPanelLayout.setVgap(25);
				bottomPanel.setLayout(choicesPanelLayout);
				bottomPanel.setPreferredSize(new java.awt.Dimension(747, 241));
				{
					buttonPanel = new JPanel();
					GridBagLayout buttonPanelLayout = new GridBagLayout();
					GridBagConstraints b = new GridBagConstraints();
					buttonPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
					buttonPanelLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7};
					buttonPanelLayout.columnWeights = new double[] {0.1};
					buttonPanelLayout.columnWidths = new int[] {7};
					bottomPanel.add(buttonPanel, BorderLayout.EAST);
					buttonPanel.setLayout(buttonPanelLayout);
					buttonPanel.setPreferredSize(new java.awt.Dimension(233, 187));
					{
						saveButton = new JButton();
						saveButton.setLayout(null);
						b.gridx = 0;
						b.gridy = 4;
						buttonPanel.add(saveButton, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						saveButton.setText("Save & Continue");
						saveButton.addActionListener(getSaveListener());
					}
					{
						saveCloseButton = new JButton();
						buttonPanel.add(saveCloseButton, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						saveCloseButton.setText("Save & Close");
						saveCloseButton.addActionListener(getSaveCloseListener());
					}
				}
				{
					choicesPanel = new JPanel();
					GridBagLayout choicesPanelLayout1 = new GridBagLayout();
					GridBagConstraints c = new GridBagConstraints();
					choicesPanelLayout1.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
					choicesPanelLayout1.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7};
					choicesPanelLayout1.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					choicesPanelLayout1.columnWidths = new int[] {7, 7, 7, 7};
					choicesPanel.setLayout(choicesPanelLayout1);
					bottomPanel.add(choicesPanel, BorderLayout.CENTER);
					choicesPanel.setPreferredSize(new java.awt.Dimension(561, 241));
					//textfields
					{
						LinkedList<Link> choices = page.getChoices();
						Iterator<Link> iter = choices.iterator();
						
						while (iter.hasNext())
						{
							Link curr = iter.next();
							JTextField choiceField = new JTextField();
							choiceField.setBackground(new java.awt.Color(255,255,255));
							
							c.gridx = 0;
							c.gridy = choiceFields.size();
							c.fill = GridBagConstraints.HORIZONTAL;
							c.gridwidth = 3;
							c.insets = new Insets(0,10,5,0);
							choicesPanel.add(choiceField, c);
							choiceField.setText(curr.getText());
							choiceField.setSize(300, 23);
							
							choiceFields.add(choiceField);
							
							addComboBox(choiceFields.size(), c);
						}
					
						if (choices.size() < 7)
						{
							int diff = 7 - choices.size();
							
							for (int i = 1; i <= diff; i++)
							{
								JTextField extraChoiceField = new JTextField();
								extraChoiceField.setBackground(new java.awt.Color(255,255,255));
								
								c.gridx = 0;
								c.gridy = (choices.size() + i) - 1;
								c.fill = GridBagConstraints.HORIZONTAL;
								c.gridwidth = 3;
								c.insets = new Insets(0,10,5,0);
								choicesPanel.add(extraChoiceField,c);
								extraChoiceField.setText("");
								
								choiceFields.add(extraChoiceField);
								
								addComboBox(i + choices.size(), c);
							}
						}
					}
				}
			}
			{
				textTitlePanel = new JPanel();
				GridBagLayout textTitlePanelLayout = new GridBagLayout();
				textTitlePanelLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.1, 0.1, 0.0, 0.0};
				textTitlePanelLayout.rowHeights = new int[] {24, 35, 18, 7, 7, 147, 26};
				textTitlePanelLayout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.1};
				textTitlePanelLayout.columnWidths = new int[] {254, 7, 7, 7};
				textTitlePanel.setLayout(textTitlePanelLayout);
				getContentPane().add(textTitlePanel, BorderLayout.NORTH);
				textTitlePanel.setPreferredSize(new java.awt.Dimension(747, 397));
				{
					titleField = new JTextField();
					textTitlePanel.add(titleField, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 0), 0, 0));
					titleField.setBackground(new java.awt.Color(255,255,255));
					{
						titleLable = new JLabel();
						titleField.add(titleLable);
						titleLable.setText("Title:");
					}
					titleField.setText(page.getTitle());
				}
				{
					textAreaScrollPane = new JScrollPane();
					textTitlePanel.add(textAreaScrollPane, new GridBagConstraints(0, 3, 4, 3, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					{
						textArea = new JTextArea();
						textAreaScrollPane.setViewportView(textArea);
						textArea.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
						textArea.setLineWrap(true);
						textArea.setText(page.getText());
					}
				}
				{
					titleLabel = new JLabel();
					textTitlePanel.add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
					titleLabel.setText("Title:");
					titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
					titleLabel.setFont(new java.awt.Font("Tahoma",1,14));
				}
				{
					textLabel = new JLabel();
					textTitlePanel.add(textLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
					textLabel.setText("Text:");
					textLabel.setFont(new java.awt.Font("Tahoma",1,14));
				}
				{
					choicesLabel = new JLabel();
					textTitlePanel.add(choicesLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
					choicesLabel.setText("Choices/Exits:");
					choicesLabel.setFont(new java.awt.Font("Tahoma",1,14));
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addComboBox(int i, GridBagConstraints c)
	{
		JComboBox choiceBox = new JComboBox();
		choiceBox.addItem("<none>");
		
		LinkedList<Page> pages = tree.getPages();
		Iterator<Page> pageiter = pages.iterator();
		
		while (pageiter.hasNext())
			choiceBox.addItem("Page "+pageiter.next().getId());
		
		c.gridx = 3;
		c.gridy = i-1;
		c.gridwidth = 1;
		c.insets = new Insets(0,5,10,0);
		choicesPanel.add(choiceBox, c);
		
		if (page.getChoice(i) != null)
			choiceBox.setSelectedIndex(page.getChoice(i).getTarget());
		else
			choiceBox.setSelectedIndex(0);
		
		comboBoxes.add(choiceBox);
	}
	
	private ActionListener getSaveListener()
	{
		return new SaveCloseListener(this, false);
	}
	
	private ActionListener getSaveCloseListener()
	{
		return new SaveCloseListener(this, true);
	}
	
	class SaveCloseListener extends JFrame implements ActionListener
	{

		JFrame parent;
		boolean close = false;
		
		public SaveCloseListener(JFrame parent, boolean close)
		{
			super();
			this.parent = parent;
			this.close = close;
		}
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			page.setTitle(titleField.getText());
			page.setText(textArea.getText());
			
			Iterator<JTextField> linktextList = choiceFields.iterator();
			Iterator<JComboBox> targetList = comboBoxes.iterator();
			int i = 1;
			
			while (linktextList.hasNext())
			{
				String linktext = linktextList.next().getText();
				int target = targetList.next().getSelectedIndex();
				
				if (target < 0 || linktext == null || linktext.isEmpty()) continue;
				
				if (target == 0)
				{
					page.removeChoice(page.getChoice(i));
					continue;
				}
				
				if (target != 0 && linktext != null)
					page.addChoice(new Link(target, linktext)); 
				i++;
			}
			
			page.save();
			tree.addToTree(page);
			tree.writeTree();
			
			if (parent != null && close)
			{
				parent.dispose();
				parent.setVisible(false);
			}
			
			if (pageList != null)
				pageList.setListData(tree.getPages().toArray());
		}
	}

}
