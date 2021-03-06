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
 * Controller f?r die Programmsteuerung
 *
 * @author Simon Le
 * @version 08.07.2022
 */

public class Controller implements ActionListener, KeyListener, Runnable{
	
	private Thread gameThread;
	private Model model;
	private Leaderboard leaderBoard;
	private MainWindow mainWindow;
	private SoundController soundController;
	
	private ProgramState programstate = ProgramState.InMenu;
	
	public Controller() {
		//Instanziieren der Objekte
		model = new Model();
		leaderBoard = new Leaderboard();
		mainWindow = new MainWindow(this, model.getPlayerDimensions(), model.getPipeArray(), model.getBackgroundsPosX(), this);
		soundController = new SoundController();
		
		//Game Loop
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * ActionListener-Methode, die die Buttonklicks liest
	 * 
	 * @param ae
	 * @version 22.06.2022
	 */
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

	/**
	 * KeyListener-Methode, die die Tastatureingaben liest
	 * 
	 * @param e
	 * @version 04.07.2022
	 */
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			if (programstate == ProgramState.InGame) {
				model.springen();
				soundController.jump();
			}
			else if (programstate == ProgramState.Dead)
				restart();
		}
		else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			if (programstate == ProgramState.InMenu || programstate == ProgramState.Dead) 
				System.exit(0);
			else if (programstate == ProgramState.InGame) {
				programstate = ProgramState.Paused;
				mainWindow.pause();
				soundController.pause();
			}
			else if (programstate == ProgramState.Paused) {
				programstate = ProgramState.InGame;
				mainWindow.resume();
				soundController.resume();
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
	 * @version 05.07.2022
	 */
	public void tick() {
		if(programstate == ProgramState.InMenu || programstate == ProgramState.InGame)
		model.moveBackground();
		if(programstate == ProgramState.InGame) {
			model.tick();
			
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					if (model.getPipeArray()[i][j].getX() == 820 || model.getScore() >= 10) {
						Rectangle r = model.getPipeArray()[i][j];
						mainWindow.resizePipeIcon(i, j, (int) r.getWidth(), (int) r.getHeight());
					}
				}
			}
		
			/*
			 * Damit model.getPlayerDimensions nicht zweimal wiederholt werden muss, 
			 * wird pd initialisiert
			 */
			Rectangle pd = model.getPlayerDimensions();   
			if (pd.getY() >= (mainWindow.getWindowSize().getHeight() - pd.getHeight()) || model.isPlayerColliding()) {
				programstate = ProgramState.Dying; // Wenn der Spieler stirbt, f?llt er erst von der Bildflaeche herunter
				soundController.die();
			}
			
			/*
			 * Alle 20 Punkte bekommt der Spieler einen Bonus und kann fuer 10 Sekunden durch die Roehren fliegen
			 */
			if(model.isInvincible()) 
				mainWindow.setNotification("Invincible! time left: " + model.getInvincibilityTime());
			else
				mainWindow.setNotification("");
		}
		else if (programstate == ProgramState.Dying) {			
			model.addPlayerGravitation();		
			
			//Wenn der Spieler am Boden aufkommt, ist er endgueltig tot
			if (model.getPlayerDimensions().getY() > mainWindow.getWindowSize().getHeight()) {
				programstate = ProgramState.Dead;
				
				try {
					leaderBoard.addPlayerToLeaderBoard(model.getScore());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				mainWindow.die(model.getScore(), leaderBoard.getHighScore());
				soundController.explode();
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
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				Rectangle r = model.getPipeArray()[i][j];
				mainWindow.resizePipeIcon(i, j, (int) r.getWidth(), (int) r.getHeight());
			}
		}
		soundController.restart();
	}

	/**
	 * Main-Methode
	 *
	 * @param args
	 * @version 23.05.2022
	 */
	public static void main(String[] args) {
		new Controller();
	}
	
	
}
