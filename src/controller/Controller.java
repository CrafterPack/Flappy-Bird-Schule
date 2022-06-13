package controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Model;
import view.MainWindow;

/**
 * Controller für die Programmsteuerung
 *
 * @author Simon Le
 * @version 13.06.2022
 */

public class Controller implements ActionListener, KeyListener, Runnable{
	
	private Thread gameThread;
	private Model model;
	private MainWindow mainWindow;
	
	private ProgramState programstate = ProgramState.InMenu;
	
	public Controller() {
		model = new Model();
		mainWindow = new MainWindow(this, model.getPlayerDimensions(), model.getPipeArray(), model.getBackgroundsPosX());
		mainWindow.addKeyListener(this);
		mainWindow.requestFocus();
		
		
		//Game Loop
		gameThread = new Thread(this);
		gameThread.start();
	}

	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == mainWindow.getStartButton()) {
			mainWindow.startGame();
			programstate = ProgramState.InGame;
		}
		else if (ae.getSource() == mainWindow.getQuitButton()) {
			System.exit(0);
		}
		else if (ae.getSource() == mainWindow.getRestartButton()) {
			restart();
		}
	}


	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			if(programstate == ProgramState.InGame)
				model.springen();             
		}
		else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
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
	 * @version 23.05.2022
	 */
	public void tick() {
		if(programstate != ProgramState.Dead)
		model.moveBackground();
		if(programstate == ProgramState.InGame)
			model.tick();
		
		/*
		 * Damit model.getPlayerDimensions nicht zweimal wiederholt werden muss, 
		 * wird pd initialisiert
		 */
		Rectangle pd = model.getPlayerDimensions();   
		if (pd.getY() >= (mainWindow.getWindowSize().getHeight() - pd.getHeight()) || model.isPlayerColliding()) {
			//System.out.println("Am Boden gelandet --> Verloren!");
			programstate = ProgramState.Dead;
			mainWindow.die(model.getScore());
		}
		/*
		 * else if (model.isPlayerColliding()) {
		 * System.out.println("Roehre beruehrt --> Verloren!"); System.exit(0); }
		 */
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
