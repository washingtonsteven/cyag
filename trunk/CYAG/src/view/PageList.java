package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
public class PageList extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JList pageList;
	private AbstractAction aboutAction;
	private JMenuItem jMenuItem1;
	private AbstractAction deleteAction;
	private AbstractAction createAction;
	private AbstractAction editAction;
	private JSeparator buttonPanelSeparator;
	private JTextArea pageInfoText;
	private JButton createButton;
	private JMenuItem helpItem;
	private JMenu aboutMenu;
	private JTextArea aboutText;
	private AbstractAction exitAction;
	private JMenuItem jMenuItem2;
	private AbstractAction okAction;
	private JButton aboutOk;
	private JDialog jDialog1;
	private JMenu fileMenu;
	private JMenuBar mainmenu;
	private JButton deletePage;
	private JButton editButton;
	private JPanel buttonsPanel;
	private JLabel pageListLabel;
	private Tree tree;
	private JFrame frame;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PageList inst = new PageList();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public PageList() {
		super();
		tree = new Tree();
		initGUI();
		frame = this;
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.setTitle("Choose Your Own Adventure Creation Tool :: List of Pages");
			this.setResizable(false);
			this.setVisible(false);
			{
				mainmenu = new JMenuBar();
				setJMenuBar(mainmenu);
				{
					fileMenu = new JMenu();
					mainmenu.add(fileMenu);
					fileMenu.setText("File");
					fileMenu.add(getJMenuItem2());
				}
				{
					aboutMenu = new JMenu();
					mainmenu.add(aboutMenu);
					aboutMenu.setText("About");
					{
						helpItem = new JMenuItem();
						aboutMenu.add(helpItem);
						aboutMenu.add(getJMenuItem1());
						helpItem.setText("Help");
					}
				}
			}
			{
				pageListLabel = new JLabel();
				getContentPane().add(pageListLabel, BorderLayout.NORTH);
				pageListLabel.setText("Select a page below, then click a button at right...");
				pageListLabel.setPreferredSize(new java.awt.Dimension(603, 16));
				pageListLabel.setFont(new java.awt.Font("Tahoma",2,12));
			}
			{
				buttonsPanel = new JPanel();
				getContentPane().add(buttonsPanel, BorderLayout.EAST);
				buttonsPanel.setPreferredSize(new java.awt.Dimension(141, 290));
				{
					editButton = new JButton();
					buttonsPanel.add(editButton);
					editButton.setText("Edit Selected Page");
					editButton.setAction(getEditAction());
				}
				{
					createButton = new JButton();
					buttonsPanel.add(createButton);
					createButton.setText("Create New Page");
					createButton.setAction(getCreateAction());
				}
				{
					deletePage = new JButton();
					buttonsPanel.add(deletePage);
					deletePage.setText("Delete Selected Page");
					deletePage.setAction(getDeleteAction());
				}
				{
					buttonPanelSeparator = new JSeparator();
					buttonsPanel.add(buttonPanelSeparator);
					buttonPanelSeparator.setPreferredSize(new java.awt.Dimension(127, 5));
				}
				{
					pageInfoText = new JTextArea();
					buttonsPanel.add(pageInfoText);
					pageInfoText.setText("Select a page...");
					pageInfoText.setFont(new java.awt.Font("Segoe UI",1,11));
					pageInfoText.setEditable(false);
					pageInfoText.setPreferredSize(new java.awt.Dimension(121, 324));
					pageInfoText.setFocusable(false);
					pageInfoText.setFocusTraversalKeysEnabled(false);
					pageInfoText.setWrapStyleWord(true);
					pageInfoText.setBackground(new java.awt.Color(244,244,244));
					pageInfoText.setLineWrap(true);
				}
			}
			{
				ListModel pageListModel = 
					new DefaultComboBoxModel(tree.getPages().toArray());
				pageList = new JList();
				getContentPane().add(pageList, BorderLayout.CENTER);
				pageList.setModel(pageListModel);
				pageList.setPreferredSize(new java.awt.Dimension(462, 245));
				pageList.setFont(new java.awt.Font("Courier New",0,12));
				pageList.setSelectedIndex(-1);
				pageList.getSelectionModel().addListSelectionListener(new PageListSelectionHandler());
				pageList.setBorder(BorderFactory.createLineBorder(Color.black));

				
			}
			pack();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AbstractAction getEditAction() {
		if(editAction == null) {
			editAction = new AbstractAction("Edit Selected Page", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					if (pageList.getSelectedIndex() < 0) 
						JOptionPane.showMessageDialog(null, "Illegal Selection. Please select a Page.");
					int page = pageList.getSelectedIndex() + 1;
					EditPageView inst = new EditPageView(page, pageList);
					inst.setLocationRelativeTo(null);
					inst.setVisible(true);
				}
			};
		}
		return editAction;
	}
	
	private AbstractAction getCreateAction() {
		if(createAction == null) {
			createAction = new AbstractAction("Create New Page", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					Page p = new Page(tree.getPages().get(tree.getPages().size()-1).getId() + 1);
					tree.addToTree(p);
					tree.writeTree();
					EditPageView inst = new EditPageView(p, pageList);
					inst.setLocationRelativeTo(null);
					inst.setVisible(true);
					pageList.setListData(tree.getPages().toArray());
				}
			};
		}
		return createAction;
	}
	
	private AbstractAction getDeleteAction() {
		if(deleteAction == null) {
			deleteAction = new AbstractAction("Delete Selected Page", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					
					int toRemove = pageList.getSelectedIndex()+1;
					if (toRemove <= 0 || toRemove > tree.getPages().size()) 
						JOptionPane.showMessageDialog(null, "Illegal Selection. Please select a Page.");
					
					int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Page "+toRemove+"?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (n != 0) return;
					
					LinkedList<Page> parents;
					
					if (!(parents = tree.getParents(toRemove)).isEmpty())
					{
						String msg = "The following pages reference the one you tried to delete (Page ";
						msg += toRemove + "): \n\n";
						Iterator<Page> iter = parents.iterator();
						while (iter.hasNext())
						{
							msg += "Page "+iter.next().getId();
							
							if (iter.hasNext()) msg += ", ";
						}
						msg += "\n\nPlease remove all dependencies before deleting this page.";
						
						JOptionPane.showMessageDialog(null, msg);
						return;
					}
					
					tree.removeFromTree(toRemove);
					pageList.setListData(tree.getPages().toArray());
				}
			};
		}
		return deleteAction;
	}
	
	private JMenuItem getJMenuItem1() {
		if(jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("jMenuItem1");
			jMenuItem1.setAction(getAboutAction());
		}
		return jMenuItem1;
	}
	
	private AbstractAction getAboutAction() {
		if(aboutAction == null) {
			aboutAction = new AbstractAction("About", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					getJDialog1().pack();
					getJDialog1().setLocationRelativeTo(null);
					getJDialog1().setVisible(true);
					jDialog1.setSize(426, 340);
				}
			};
		}
		return aboutAction;
	}
	
	private JDialog getJDialog1() {
		if(jDialog1 == null) {
			jDialog1 = new JDialog(this);
			GridBagLayout jDialog1Layout = new GridBagLayout();
			jDialog1Layout.rowWeights = new double[] {0.0, 0.1};
			jDialog1Layout.rowHeights = new int[] {240, 20};
			jDialog1Layout.columnWeights = new double[] {0.1};
			jDialog1Layout.columnWidths = new int[] {7};
			jDialog1.getContentPane().setLayout(jDialog1Layout);
			jDialog1.setTitle("About...");
			jDialog1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			jDialog1.getContentPane().add(getAboutText(), new GridBagConstraints(-1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			jDialog1.getContentPane().add(getAboutOk(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 25), 0, 0));
		}
		return jDialog1;
	}
	
	private JTextArea getAboutText() {
		if(aboutText == null) {
			aboutText = new JTextArea();
			aboutText.setText("Version 0.5.0\n\nThanks for trying this out! \nPlease help out this application and send me any feedback you have.\n\n\nWritten by Steven Washington\nEmail: washington.steven@gmail.com\n\nCopyright © 2009 by Steven Washington. All Rights Reserved.\n\nGUI created with Jigloo.\nSite: http://www.cloudgarden.com/jigloo/\n\nJigloo is Copyright © 2004-2007 by Cloud Garden.com. \nAll Rights Reserved.\n\n");
			aboutText.setBackground(new java.awt.Color(244,244,244));
			aboutText.setFont(new java.awt.Font("Tahoma",0,11));
			aboutText.setPreferredSize(new java.awt.Dimension(370, 266));
			aboutText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED), "Choose Your Adventure Tool", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma",1,14)));
		}
		return aboutText;
	}
	
	private JButton getAboutOk() {
		if(aboutOk == null) {
			aboutOk = new JButton();
			aboutOk.setText("OK");
			aboutOk.setAction(getOkAction());
		}
		return aboutOk;
	}
	
	private AbstractAction getOkAction() {
		if(okAction == null) {
			okAction = new AbstractAction("OK", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					jDialog1.dispose();
				}
			};
		}
		return okAction;
	}
	
	private JMenuItem getJMenuItem2() {
		if(jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("Exit");
			jMenuItem2.setAction(getExitAction());
		}
		return jMenuItem2;
	}
	
	private AbstractAction getExitAction() {
		if(exitAction == null) {
			exitAction = new AbstractAction("Exit", null) {

				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					frame.dispose();
					frame.setVisible(false);
				}
			};
		}
		return exitAction;
	}

	class PageListSelectionHandler implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent lse)
		{
			ListSelectionModel lsm = (ListSelectionModel)lse.getSource();
			
			int index = lsm.getMinSelectionIndex();
			
			if (index < 0)
			{
				pageInfoText.setText("Select a page...");
				return;
			}
			
			Page selectedPage = tree.getPages().get(index);
			
			String info = "Page "+selectedPage.getId()+"\n";
			info += "\""+selectedPage.getTitle()+"\"\n\n";
			info += "Text:\n";
			info += selectedPage.getText().substring(0,Math.min(30,selectedPage.getText().length()));
			if (selectedPage.getText().length() > 30)
				info += "...";
			info += "\n\nExits: ";
			
			LinkedList<Link> exitList = selectedPage.getChoices();
			
			if (exitList.isEmpty())
				info += "<none>";
			else
			{
				Iterator<Link> iter = exitList.iterator();
				
				while (iter.hasNext())
				{
					Link curr = iter.next();
					
					info += curr.getTarget();
					
					if (iter.hasNext())
						info += ", ";	
				}
			}
			
			pageInfoText.setText(info);
		}
		
	}
}
