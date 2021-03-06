/**
 * 
 */
package controller;

/**
 * Enum-Klasse f?r den derzeitigen Status im Programm
 * Ziel: bestimmte Methoden werden nur in einem bestimmten Zustand ausgef?hrt
 * 
 * @author Simon Le
 * @version 26.06.2022
 *
 */
public enum ProgramState {
	InMenu,
	InGame,
	Dying,
	Dead,
	Paused
}
