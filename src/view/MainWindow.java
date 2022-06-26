package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.ProgramState;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Rectangle;

import model.Tuple;

/**
 * Klasse fuer die allgemeine Benutzeroberflaeche
 *
 * @author Simon Le
 * @version 26.06.2022
 */

public class MainWindow extends JFrame{
	private GameView gameView;
	private MenuView menuView;
	private DeathScreen deathScreen;
	private PauseMenu pauseMenu;
	private Dimension windowSize;
	private LeaderboardView leaderBoard;
	
	//private boolean ingame;
	private ProgramState programstate = ProgramState.InMenu;
	
	public MainWindow(ActionListener al, Rectangle playerDimensions, Rectangle[][] roehrenArrayDimensions, int[] backgroundsPosX, KeyListener kl) {
		super();
		windowSize = new Dimension(480, 640);
		
		//Instanziieren der JPanels
		menuView = new MenuView(windowSize, al);
		gameView = new GameView(windowSize, playerDimensions, roehrenArrayDimensions, backgroundsPosX);
		deathScreen = new DeathScreen(windowSize, al);
		pauseMenu = new PauseMenu(windowSize, al);
		leaderBoard = new LeaderboardView(windowSize, al);
		add(menuView);
		add(gameView);

		addKeyListener(kl);
		menuView.getNameInput().addKeyListener(kl);
		menuView.getNameInput().requestFocus();
		
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
	 * @param score
	 * @version 31.05.2022
	 */
	public void render(int playerPosY, Rectangle[][] roehrenArrayDimensions, int[] backgroundsPosX, int score) {
		
		if (programstate == ProgramState.InMenu || programstate == ProgramState.InGame)
			gameView.moveBackground(backgroundsPosX);
		
		if (programstate == ProgramState.InGame)
			gameView.updateScore(score);
		
		gameView.movePlayer(playerPosY);
		gameView.movePipes(roehrenArrayDimensions);
		
		this.repaint(0, 0, 0, (int) windowSize.getWidth(), (int) windowSize.getHeight());
	}
	
	/**
	 * Methode, um die Icon der Roehren neuzuskalieren, wenn die sich vertikal bewegen oder zurueckgesetzt werden
	 * Diese Methode wird nicht in render() ausgefuehrt, da die Roehren sich nur mit 60ticks bewegen und die Methode daher nur 60 mal pro Sekunde ausgefuehrt werden muss. 
	 * render() hingegen wird mit der FPS ausgefuehrt, also bis zu 1800000 mal pro Sekunde (je nach PC)
	 *
	 * @param i
	 * @param j
	 * @param newWidth
	 * @param newHeight
	 * @version 26.06.2022
	 */
	public void resizePipeIcon(int i, int j, int newWidth, int newHeight) {
		gameView.resizePipeIcon(i, j, newWidth, newHeight);
	}
	
	public Dimension getWindowSize() {
		return windowSize;
	}
	
	public JButton getStartButton() {
		return menuView.getStartButton();
	}
	
	public JButton getQuitButton() {
		if (programstate == ProgramState.InMenu)
			return menuView.getQuitButton();
		else if (programstate == ProgramState.Dead)
			return deathScreen.getQuitButton();
		else
			return pauseMenu.getQuitButton();
	}
	
	public JButton getRestartButton() {
		return deathScreen.getRestartButton();
	}
	
	public JButton getResumeButton() {
		return pauseMenu.getResumeButton();
	}
	
	public JButton getOpenLeaderBoardButton() {
		switch (programstate) {
			case InMenu: 
				return menuView.getOpenLeaderBoardButton();
			case Paused:
				return pauseMenu.getOpenLeaderBoardButton();
			case Dead:
				return deathScreen.getOpenLeaderBoardButton();
			default:
				return null;
		}
	}
	
	public JButton getCloseLeaderBoardButton() {
		return leaderBoard.getCloseLeaderBoardButton();
	}
	
	public String getName() {
		return menuView.getNameInput().getText();
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
		programstate = ProgramState.InGame;
	}
	
	/**
	 * sobald man stirbt, erscheint der Score in der Mitte sowie 2 Buttons zum Neustarten bzw. Schliessen
	 * 
	 * @param score
	 * @param highscore
	 * @version 21.06.2022
	 */
	public void die(int score, int highscore) {
		remove(gameView);
		add(deathScreen);
		add(gameView);
		deathScreen.setScore(score, highscore);
		gameView.removeScore();
		programstate = ProgramState.Dead;
		requestFocus();
		SwingUtilities.updateComponentTreeUI(this); 
	}
	
	/**
	 * Methode, um das Spiel neuzustarten
	 * 
	 * @version 01.06.2022
	 */
	public void restart() {
		remove(deathScreen);
		requestFocus();
		SwingUtilities.updateComponentTreeUI(this); 
		programstate = ProgramState.InGame;		
	}
	
	/**
	 * Methode, um das Spiel zu pausieren
	 * 
	 * @version 15.06.2022
	 */
	public void pause() {
		remove(gameView);
		add(pauseMenu);
		add(gameView);
		programstate = ProgramState.Paused;
		requestFocus();
		SwingUtilities.updateComponentTreeUI(this); 
	}
	
	/**
	 * Methode, um das Spiel fortzufahren
	 * 
	 * @version 15.06.2022
	 */
	public void resume() {
		remove(pauseMenu);
		programstate = ProgramState.InGame;
		requestFocus();
		SwingUtilities.updateComponentTreeUI(this); 
	}
	
	/**
	 * Methode, um das Leaderboard zu oeffnen
	 *
	 * @version 26.06.2022
	 */
	public void openLeaderBoard(ArrayList<Tuple> list) {
		switch(programstate) {
		case InMenu:
			remove(menuView);
			break;
		case Paused:
			remove(pauseMenu);
			break;
		case Dead:
			remove(deathScreen);
			break;
		default:
			break;
		}
		remove(gameView);
		add(leaderBoard);
		add(gameView);
		for (int i = 0; i < list.size(); i++) {
			String n = list.get(i).getName();
			int s = list.get(i).getScore();
			
			Object[] obj = {n, s};
			leaderBoard.addRow(obj);
		}
	}
	
	/**
	 * Methode, um das Leaderboard zu schliessen
	 *
	 * @version 26.06.2022
	 */
	public void closeLeaderBoard() {
		leaderBoard.removeRows();
		
		remove(leaderBoard);
		remove(gameView);
		
		switch(programstate) {
			case InMenu:
				add(menuView);
				break;
			case Paused:
				add(pauseMenu);
				break;
			case Dead:
				add(deathScreen);
				break;
			default:
				break;
		}
		
		add(gameView);
	}
}
