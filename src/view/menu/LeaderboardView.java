/**
 * 
 */
package view.menu;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Klasse, um das Leaderboard anzuzeigen
 *
 * @author Simon Le
 * @version 22.06.2022
 */

public class LeaderboardView extends JPanel{
	
	private String[] column = {"Name", "Highscore"};
	private DefaultTableModel defaultTableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton closeLeaderBoardButton;
	
	public LeaderboardView(Dimension windowSize, ActionListener al) {
		//Instanziieren der Objekte
		defaultTableModel = new DefaultTableModel(null, column);
		table = new JTable(defaultTableModel);
		table.setDefaultEditor(Object.class, null);
		scrollPane = new JScrollPane(table);
		closeLeaderBoardButton = new JButton("Back");
		
		scrollPane.setBounds(40, 40, 400, 470);
		add(scrollPane);
		
		closeLeaderBoardButton.setBounds(40, 550, 400, 50);
		closeLeaderBoardButton.addActionListener(al);
		add(closeLeaderBoardButton);
		
		//JPanel wird richtig eingestellt
		setBounds(0, 0, (int) windowSize.getWidth(), (int) windowSize.getHeight());
		setLayout(null);
		setOpaque(false);
		setVisible(true);
	}
	
	/**
	 * Getter-Methode
	 */
	
	public JButton getCloseLeaderBoardButton() {
		return closeLeaderBoardButton;
	}
	
	/**
	 * fuegt eine neue Zeile (= neuer Spieler) in die Tabelle 
	 *
	 * @param obj
	 * @version 22.06.2022
	 */
	public void addRow(Object[] obj) {
		defaultTableModel.addRow(obj);
	}
	
	/**
	 * loescht alle Zeilen 
	 *
	 * @version 22.06.2022
	 */
	public void removeRows() {
		if (defaultTableModel.getRowCount() > 0)
			for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--)
				defaultTableModel.removeRow(i);
	}
}
