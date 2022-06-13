package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Klasse fuer die allgemeine Benutzeroberflaeche
 *
 * @author Simon Le
 * @version 31.05.2022
 */

public class MainWindow extends JFrame{
	private GameView gameView;
	private MenuView menuView;
	private DeathScreen deathScreen;
	private Dimension windowSize;
	
	private boolean ingame;
	
	public MainWindow(ActionListener al, Rectangle playerDimensions, Rectangle[][] roehrenArrayDimensions, int[] backgroundsPosX) {
		super();
		windowSize = new Dimension(480, 640);
		
		//Instanziieren der JPanels
		menuView = new MenuView(windowSize);
		menuView.addActionListener(al);
		gameView = new GameView(windowSize, playerDimensions, roehrenArrayDimensions, backgroundsPosX);
		deathScreen = new DeathScreen(windowSize);
		deathScreen.addActionListener(al);
		add(menuView);
		add(gameView);
		SwingUtilities.updateComponentTreeUI(this);
		
		/*
		 * Drueckt man oben rechts auf das X, wird das Programm in der Regel nicht komplett geschlossen, 
		 * sondern arbeitet im Hintergrund weiter. Mit EXIT_ON_CLOSE wird das Programm dann vollstaendig
		 * geschlossen
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);	
		setResizable(false);
		getContentPane().setPreferredSize(windowSize);
		setVisible(true);
		pack();
	}
	
	/**
	 * Methode fuer die Game Loop
	 *
	 * @param playerPosY
	 * @param roehrenArrayDimensions
	 * @param backgroundsPosX
	 * @version 31.05.2022
	 */
	public void render(int playerPosY, Rectangle[][] roehrenArrayDimensions, int[] backgroundsPosX, int score) {
		gameView.movePlayer(playerPosY);
		gameView.movePipes(roehrenArrayDimensions);
		gameView.moveBackground(backgroundsPosX);
		if (ingame)
			gameView.updateScore(score);
		
		if(!ingame)
			SwingUtilities.updateComponentTreeUI(this);
	}
	
	public Dimension getWindowSize() {
		return windowSize;
	}
	
	public JButton getStartButton() {
		return menuView.getStartButton();
	}
	
	public JButton getQuitButton() {
		if (ingame)
			return menuView.getQuitButton();
		else
			return deathScreen.getQuitButton();
	}
	
	public JButton getRestartButton() {
		return deathScreen.getRestartButton();
	}
	
	/**
	 * Entfernt das menuView-JPanel und setzt ingame auf true
	 *
	 * @version 31.05.2022
	 */
	public void startGame() {
		remove(menuView);
		requestFocus();
		SwingUtilities.updateComponentTreeUI(this); 
		ingame = true;
	}
	
	public void die(int score) {
		remove(gameView);
		add(deathScreen);
		add(gameView);
		deathScreen.setScore(score);
		gameView.removeScore();
		ingame = false;
		requestFocus();
		SwingUtilities.updateComponentTreeUI(this); 
	}
	
	public void restart() {
		remove(deathScreen);
		requestFocus();
		SwingUtilities.updateComponentTreeUI(this); 
		ingame = true;		
	}
}
