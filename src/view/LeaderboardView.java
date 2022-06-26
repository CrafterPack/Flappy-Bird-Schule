/**
 * 
 */
package view;

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
		
		defaultTableModel = new DefaultTableModel(null, column);
		table = new JTable(defaultTableModel);
		scrollPane = new JScrollPane(table);
		closeLeaderBoardButton = new JButton("Back");
		
		scrollPane.setBounds(40, 40, 400, 470);
		add(scrollPane);
		
		closeLeaderBoardButton.setBounds(40, 550, 400, 50);
		closeLeaderBoardButton.addActionListener(al);
		add(closeLeaderBoardButton);
		
		setBounds(0, 0, (int) windowSize.getWidth(), (int) windowSize.getHeight());
		setLayout(null);
		setOpaque(false);
		setVisible(true);
	}
	
	public JButton getCloseLeaderBoardButton() {
		return closeLeaderBoardButton;
	}
	
	public void addRow(Object[] obj) {
		defaultTableModel.addRow(obj);
	}
	
	public void removeRows() {
		if (defaultTableModel.getRowCount() > 0)
			for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--)
				defaultTableModel.removeRow(i);
	}
	
//	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		LeaderboardView lbv = new LeaderboardView(new Dimension(480, 640), null);
//		
//		f.add(lbv);
//		
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setLayout(null);	
//		f.setResizable(false);
//		f.getContentPane().setPreferredSize(new Dimension(480, 640));
//		f.setVisible(true);
//		f.pack();
//	}
}
