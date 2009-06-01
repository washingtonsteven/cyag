import javax.swing.SwingUtilities;

import view.PageList;

/**
 * Just a simple class to start CYAG Choose Your Adventure Tool
 * 
 * All it does is start a PageList object. Check there next.
 * 
 * @author Steven Washington (washington.steven@gmail.com)
 *
 */
public class StartCYAG
{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PageList inst = new PageList();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
}
