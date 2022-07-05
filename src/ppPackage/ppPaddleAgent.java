package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.program.GraphicsProgram;
import acm.graphics.*;


/* The template used for this class is used from the Assignment 4 from Prof Frank
 * Ferrie for the class ECSE-202. It is also heavily inspired from the framework of 
 * assignment 3 
 */


public class ppPaddleAgent extends ppPaddle {
	
// instance variables
	
	private double X, Y;
	private ppTable myTable;
	private Color myColor;
	private double Vx, Vy;
	private double lastX, lastY;
	GRect theAgent;
	GRect myPaddleAgent;
	ppBall myBall;

	public ppPaddleAgent (double X, double Y, Color myColor, ppTable myTable) {
	super(X, Y, myColor, myTable); // super gives the ppPaddleAgent the ppPaddles class' constructor
		this.X = X;
		this.Y = Y;
		this.myColor = myColor;
		this.myTable = myTable;
		
		
	}

	public void run() {

		while (true) { // same as ppPaddle class except for the getY() method 
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y;
			this.setY(myBall.getY()); // Agent will follow ball's Y position throughout the siimulation
			myTable.getDisplay().pause(TICK*TIMESCALE);
		}
	}


	public boolean contact(double Sx, double Sy) { // this method checks for contact between ball and agent, it returns true if there was contact and false otherwise
		if (Sy >= (getY() - ppPaddleH / 2) && Sy <= (getY() + ppPaddleH / 2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void attachBall(ppBall myBall) { // attaches ball to agent, which is the ball's initial position 
		this.myBall = myBall;
	}
	
}
