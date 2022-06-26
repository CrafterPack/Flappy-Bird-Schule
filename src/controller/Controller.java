package controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import model.*;
import view.MainWindow;

/**
 * Controller für die Programmsteuerung
 *
 * @author Simon Le
 * @version 22.06.2022
 */

public class Controller implements ActionListener, KeyListener, Runnable{
	
	private Thread gameThread;
	private Model model;
	private Leaderboard leaderBoard;
	private MainWindow mainWindow;
	
	private ProgramState programstate = ProgramState.InMenu;
	
	public Controller() {
		model = new Model();
		leaderBoard = new Leaderboard();
		mainWindow = new MainWindow(this, model.getPlayerDimensions(), model.getPipeArray(), model.getBackgroundsPosX(), this);
		
		//Game Loop
		gameThread = new Thread(this);
		gameThread.start();
	}

	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == mainWindow.getStartButton()) {
			start();
		}
		else if (ae.getSource() == mainWindow.getQuitButton()) {
			System.exit(0);
		}
		else if (ae.getSource() == mainWindow.getRestartButton()) {
			restart();
		}
		else if (ae.getSource() == mainWindow.getResumeButton()) {
			programstate = ProgramState.InGame;
			mainWindow.resume();
		}
		else if (ae.getSource() == mainWindow.getOpenLeaderBoardButton()) {
			mainWindow.openLeaderBoard(leaderBoard.getList());
		}
		else if (ae.getSource() == mainWindow.getCloseLeaderBoardButton()) {
			mainWindow.closeLeaderBoard();
		}
	}


	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			if (programstate == ProgramState.InGame)
				model.springen();
			else if (programstate == ProgramState.Dead)
				restart();
		}
		else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			if (programstate == ProgramState.InMenu || programstate == ProgramState.Dead) 
				System.exit(0);
			else if (programstate == ProgramState.InGame) {
				programstate = ProgramState.Paused;
				mainWindow.pause();
			}
			else if (programstate == ProgramState.Paused) {
				programstate = ProgramState.InGame;
				mainWindow.resume();
			}
		}
		else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (programstate == ProgramState.InMenu) {
				start();
			}
		}
	}
	
	
	/**
	 * keyPressed() und keyReleased() werden zwar nicht verwendet, 
	 * werden aber bei der Verwendung vom KeyListener gefordert
	 * 
	 * @version 23.05.2022
	 */
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	

	/**
	 * Gameloop
	 * wird verwendet, um das Spiel kontinuierlich upzudaten; Grundlage fuer jedes Spiel
	 * --> render() fuer die View
	 * --> tick() fuer das Model 
	 * wird vorallem spaeter wichtig, wenn man das spiel pausieren will
	 * 
	 * @version 23.05.2022
	 */
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	/**
	 * Game-Loop-Methode fuer das Model
	 * 
	 * @version 21.06.2022
	 */
	public void tick() {
		if(programstate == ProgramState.InMenu || programstate == ProgramState.InGame)
		model.moveBackground();
		if(programstate == ProgramState.InGame) {
			model.tick();
		
			/*
			 * Damit model.getPlayerDimensions nicht zweimal wiederholt werden muss, 
			 * wird pd initialisiert
			 */
			Rectangle pd = model.getPlayerDimensions();   
			if (pd.getY() >= (mainWindow.getWindowSize().getHeight() - pd.getHeight()) || model.isPlayerColliding()) {
				programstate = ProgramState.Dying; // Wenn der Spieler stirbt, fällt er erst von der Bildflaeche herunter
			}
		}
		else if (programstate == ProgramState.Dying) {			
			model.addPlayerGravitation();		
			
			if (model.getPlayerDimensions().getY() > mainWindow.getWindowSize().getHeight()) {
				programstate = ProgramState.Dead;
				
				try {
					leaderBoard.addPlayerToLeaderBoard(model.getScore());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				mainWindow.die(model.getScore(), leaderBoard.getHighScore());
			}
		}
	}
	
	
	/**
	 * Game-Loop-Methode fuer die View
	 * 
	 * @version 23.05.2022
	 */
	public void render() {
		mainWindow.render((int) model.getPlayerDimensions().getY(), model.getPipeArray(), model.getBackgroundsPosX(), model.getScore());
	}
	
	/**
	 * Methode, um das Spiel zu starten
	 *
	 * @version 21.06.2022
	 */
	public void start() {
		if (mainWindow.getName().isBlank()) 
			return;
		leaderBoard.setPlayerName(mainWindow.getName().trim());
		mainWindow.startGame();
		programstate = ProgramState.InGame;		
	}
	
	/**
	 * Methode, um das Spiel neuzustarten
	 *
	 * @version 01.06.2022
	 */
	public void restart() {
		model = new Model();
		mainWindow.restart();
		programstate = ProgramState.InGame;
		
	}

	public static void main(String[] args) {
		new Controller();
	}
	
	
}
