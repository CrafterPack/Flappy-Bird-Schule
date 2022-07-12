/**
 * Java-Datei, damit das Programm problemlos auf JavaFX und JSON-simple zugreifen kann
 * 
 * @author Simon Le
 * @version 10.07.2022
 */
module flappybird {
	exports controller;
	exports model;
	exports view;

	requires transitive java.desktop;
	requires javafx.media;
	requires javafx.swing;
	requires json.simple;
	
	opens controller;
	opens view;
	opens model;
}