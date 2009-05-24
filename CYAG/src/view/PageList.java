package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

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
	private JFileChooser fc;
	private AbstractAction aboutAction;
	private AbstractAction deleteAction;
	private AbstractAction createAction;
	private AbstractAction editAction;
	private JSeparator buttonPanelSeparator;
	private JTextArea pageInfoText;
	private JButton createButton;
	private JMenu aboutMenu;
	private JTextArea aboutText;
	private AbstractAction saveAdvAs;
	private JMenuItem saveAdvAsItem;
	private JMenuItem jMenuItem1;
	private JLabel statusLabel;
	private AbstractAction saveAction;
	private JMenuItem saveItem;
	private AbstractAction saveAdventureAction;
	private JMenuItem newAdvItem;
	private AbstractAction openTreeAction;
	private JMenuItem openAdvItem;
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
					fileMenu.add(getNewAdvItem());
					fileMenu.add(getOpenAdvItem());
					fileMenu.add(getSaveItem());
					fileMenu.add(getSaveAdvAsItem());
					fileMenu.add(getJMenuItem2());
				}
				{
					aboutMenu = new JMenu();
					mainmenu.add(aboutMenu);
					aboutMenu.setText("Help");
					aboutMenu.add(getJMenuItem1());
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
				getContentPane().add(getStatusLabel(), BorderLayout.SOUTH);
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
					{
						JOptionPane.showMessageDialog(null, "Illegal Selection. Please select a Page.");
						return;
					}
					int page = pageList.getSelectedIndex();
					Page p = tree.getPages().get(page);
					EditPageView inst = new EditPageView(p, pageList, tree);
					inst.setLocationRelativeTo(null);
					inst.setVisible(true);
					statusLabel.setText("Page edited: Page "+p.getId());
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
					Page p = null;
					if (tree.getPages().isEmpty())
						p = new Page(1, tree.getFolder());
					else
						p = new Page(tree.getPages().get(tree.getPages().size()-1).getId() + 1, tree.getFolder());
					EditPageView inst = new EditPageView(p, pageList, tree);
					inst.setLocationRelativeTo(null);
					inst.setVisible(true);
					statusLabel.setText("New Page created: Page "+p.getId());
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
					if (pageList.getSelectedIndex() < 0 || pageList.getSelectedIndex() > tree.getPages().size()) 
					{
						JOptionPane.showMessageDialog(null, "Illegal Selection. Please select a Page.");
						return;
					}
					int toRemove = tree.getPages().get(pageList.getSelectedIndex()).getId();
						
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
					statusLabel.setText("Deleted Page: Page "+toRemove);
				}
			};
		}
		return deleteAction;
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
			aboutText.setText("Version 0.6.2a\n\nThanks for trying this out! \nPlease help out this application and send me any feedback you have.\n\n\nWritten by Steven Washington\nEmail: washington.steven@gmail.com\n\nCopyright © 2009 by Steven Washington. All Rights Reserved.\n\nGUI created with Jigloo.\nSite: http://www.cloudgarden.com/jigloo/\n\nJigloo is Copyright © 2004-2007 by Cloud Garden.com. \nAll Rights Reserved.\n\n");
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
	
	private JMenuItem getOpenAdvItem() {
		if(openAdvItem == null) {
			openAdvItem = new JMenuItem();
			openAdvItem.setText("openAdvItem");
			openAdvItem.setAction(getOpenTreeAction());
		}
		return openAdvItem;
	}
	
	private AbstractAction getOpenTreeAction() {
		if(openTreeAction == null) {
			openTreeAction = new AbstractAction("Open Adventure...", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					
					fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);
					fc.addChoosableFileFilter(new TreeFileFilter());
					int returnVal = fc.showOpenDialog(frame);

			            if (returnVal == JFileChooser.APPROVE_OPTION) {
			            	
			            	if (!fc.getSelectedFile().isDirectory())
			            	{
			            		JOptionPane.showMessageDialog(null, "Please select a directory");
			            		return;
			            	}
			            	
			                File file = new File(fc.getSelectedFile().getAbsolutePath()+File.separator+"tree.xml");
			                
			                try
			                {
				                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				                DocumentBuilder db = dbf.newDocumentBuilder();
				                Document doc = db.parse(file);
				                
				                String rootTag = doc.getDocumentElement().getTagName();
				                
				                if (!rootTag.equals("tree"))
				                {
				                	JOptionPane.showMessageDialog(null, file.getName()+" is an invalid adventure file");
				                	return;
				                }
			                } catch (Exception e)
			                {
			                	e. printStackTrace();
			                }
			                
			                tree = new Tree(file.getAbsolutePath());
			                pageList.setListData(tree.getPages().toArray());
			                statusLabel.setText("Adventure opened: "+tree.getFile().getAbsolutePath());
			            } else {
			            	JOptionPane.showMessageDialog(null, "Open Cancelled");
			            }
				}
			};
		}
		return openTreeAction;
	}
	
	private JMenuItem getNewAdvItem() {
		if(newAdvItem == null) {
			newAdvItem = new JMenuItem();
			newAdvItem.setText("newAdvItem");
			newAdvItem.setAction(getSaveAdventureAction());
		}
		return newAdvItem;
	}
	
	private AbstractAction getSaveAdventureAction() {
		if(saveAdventureAction == null) {
			saveAdventureAction = new AbstractAction("New Adventure...", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					fc = new JFileChooser();
					fc.setToolTipText("Select a Directory where the new Adventure will be saved.");
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);
					fc.addChoosableFileFilter(new TreeFileFilter());
					int returnVal = fc.showSaveDialog(frame);

			            if (returnVal == JFileChooser.APPROVE_OPTION) {
			                File file = fc.getSelectedFile();
			                
			                if (!file.isDirectory())
			                {
			                	JOptionPane.showMessageDialog(null, "Please select a directory.");
			                	return;
			                }
			                
			                tree = new Tree(file.getAbsolutePath()+File.separator+"tree.xml");
			                
			                pageList.setListData(tree.getPages().toArray());
			                statusLabel.setText("New adventure created: "+tree.getFile().getAbsolutePath());
			            } else {
			            	JOptionPane.showMessageDialog(null, "Save Cancelled");
			            }
					
				}
			};
		}
		return saveAdventureAction;
	}
	
	private JMenuItem getSaveItem() {
		if(saveItem == null) {
			saveItem = new JMenuItem();
			saveItem.setText("saveItem");
			saveItem.setAction(getSaveAction());
		}
		return saveItem;
	}
	
	private AbstractAction getSaveAction() {
		if(saveAction == null) {
			saveAction = new AbstractAction("Save Adventure", null) {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					tree.writeTree();
					statusLabel.setText("Adventure saved to: "+tree.getFile().getAbsolutePath());
				}
			};
		}
		return saveAction;
	}
	
	private JLabel getStatusLabel() {
		if(statusLabel == null) {
			statusLabel = new JLabel();
			statusLabel.setText("Welcome!");
			statusLabel.setFont(new java.awt.Font("Segoe UI",0,10));
			statusLabel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		}
		return statusLabel;
	}
	
	private JMenuItem getJMenuItem1() {
		if(jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setAction(getAboutAction());
		}
		return jMenuItem1;
	}
	
	private JMenuItem getSaveAdvAsItem() {
		if(saveAdvAsItem == null) {
			saveAdvAsItem = new JMenuItem();
			saveAdvAsItem.setText("saveAdvAsItem");
			saveAdvAsItem.setAction(getSaveAdvAs());
		}
		return saveAdvAsItem;
	}
	
	private AbstractAction getSaveAdvAs() {
		if(saveAdvAs == null) {
			saveAdvAs = new AbstractAction("Save Adventure As...", null) {
	
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent evt) {
					fc = new JFileChooser();
					fc.setToolTipText("Select a Directory where the Adventure will be saved.");
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setAcceptAllFileFilterUsed(false);
					fc.addChoosableFileFilter(new TreeFileFilter());
					int returnVal = fc.showSaveDialog(frame);

			            if (returnVal == JFileChooser.APPROVE_OPTION) {
			                File file = fc.getSelectedFile();
			                
			                if (!file.isDirectory())
			                {
			                	JOptionPane.showMessageDialog(null, "Please select a directory.");
			                	return;
			                }
			                
			                tree.saveAs(file.getAbsolutePath());
			                
			                pageList.setListData(tree.getPages().toArray());
			                statusLabel.setText("Adventure saved as: "+tree.getFile().getAbsolutePath());
			            } else {
			            	JOptionPane.showMessageDialog(null, "Save Cancelled");
			            }
					
				}
			};
		}
		return saveAdvAs;
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
	
	class TreeFileFilter extends FileFilter
	{

		@Override
		public boolean accept(File f)
		{
			if (f.isDirectory()) return true;
			
			String fileName = f.getAbsolutePath();
			String ext = fileName.substring(fileName.length()-4, fileName.length());
			
			return ext.equals(".xml");
		}

		@Override
		public String getDescription()
		{
			
			return "Directories";
		}
		
	}
}
