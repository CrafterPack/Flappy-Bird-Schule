/**
 * 
 */
package controller;

/**
 * Enum-Klasse für den derzeitigen Status im Programm
 * Ziel: bestimmte Methoden werden nur in einem bestimmten Zustand ausgeführt
 * 
 * @author Simon Le
 * @version 15.06.2022
 *
 */
public enum ProgramState {
	InMenu,
	InGame,
	Dead,
	Paused
}
