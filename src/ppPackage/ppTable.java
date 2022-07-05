package ppPackage;

import java.awt.Color;
import static ppPackage.ppSimParams.*;
import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;


	/* The template used for this class is used from the Assignment 4 from Prof Frank
	 * Ferrie for the class ECSE-202. It is also heavily inspired from the framework of 
	 * assignment 3 
	 */


public class ppTable {
	
	ppSimPaddleAgent dispRef;
	
	public ppTable(ppSimPaddleAgent dispRef) {
		
				
		GLine floor = new GLine (0, scrHEIGHT, scrWIDTH+OFFSET, scrHEIGHT); // Setting the line that will represent floor
		floor.setColor(Color.black); // sets the color of the floor to black
		dispRef.add(floor); // adds the created floor to screen
		
		this.dispRef = dispRef;
		
	}
	
	double toScr(double X) {
		return (X*SCALE); // Converts from world X to screen X
	}
	
	
	double toScrY(double Y) {
		return (scrHEIGHT-Y*SCALE); // Converts from world Y to screen Y
	}
	
	
	public ppSimPaddleAgent getDisplay() {
		return dispRef;
	}
	
	
	double ScrtoX (double ScrX) {
		return (ScrX/SCALE); // Converts screen X to world X
	}
	
	double ScrtoY (double ScrY) {
		return (scrHEIGHT-ScrY)/SCALE; // Converts screen Y to world Y
	}
	
	public void newScreen() { // used in "CLEAR" button in ppSimPaddleAgent
		
		dispRef.removeAll(); // removes all items from screen
		dispRef.add(new GLine (0, scrHEIGHT, scrWIDTH+OFFSET, scrHEIGHT)); // adds the floor, which remains on screen 
		
	}
	
}
