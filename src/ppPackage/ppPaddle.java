package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.program.GraphicsProgram;
import acm.graphics.*;

	
	/* The template used for this class is used from the Assignment 4 from Prof Frank
	 * Ferrie for the class ECSE-202. It is also heavily inspired from the framework of 
	 * assignment 3 
	 */


public class ppPaddle extends Thread {
	
// Instance variables
	private double X,Y;
	private ppTable myTable;
	private double Vx,Vy;
	private GRect myPaddle;
	private double lastX=X;
	private double lastY=Y;
	
	public ppPaddle (double X, double Y, Color myColor, ppTable myTable) {
		this.X = X;
		this.Y = Y;
		this.myTable = myTable;
		
		myPaddle = new GRect((X-ppPaddleW/2)*SCALE, scrHEIGHT - (ppPaddleH/2)*SCALE, ppPaddleW*SCALE, ppPaddleH*SCALE); // paddle is this GRect with these parameters
		myPaddle.setFilled(true); // fills the inside of paddle
		myPaddle.setColor(myColor); // sets the color of the paddle to black
		myTable.getDisplay().add(myPaddle); // adds the paddle
	}
	
	public void run() {
		
		while (true) {
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y;
			myTable.getDisplay().pause(TICK*TIMESCALE); // Time to mS
		}
	}
	
	public double getVx() { // returns velocity of paddle in  direction, which is always 0
		return Vx;
	}
	
	public double getVy() { // returns velocity in Y direction
		return Vy;
	}
	
	public void setX(double X) { // sets the position of the paddle
		this.X=X;
		myPaddle.setLocation(myTable.toScr(X-ppPaddleW/2),myTable.toScrY(Y+ppPaddleH/2));
	}
	
	public void setY(double Y) { // sets the position of the paddle
		this.Y=Y;
		myPaddle.setLocation(myTable.toScr(X-ppPaddleW/2),myTable.toScrY(Y+ppPaddleH/2));
	}
	
	public double getX() { // returns X position of the paddle
		return X;
	}
	
	public double getY() { // returns Y position of the paddle
		return Y;
	}
	
	public double getSgnVy() { // returns the sign of velocity of y
		if (Vy<0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public GRect getPaddle() { // returns myPaddle
		return myPaddle;
	}
	
	public boolean contact(double Sx, double Sy) { // this method checks for contact between ball and paddle, it returns true if there was contact and false otherwise
		if (Sy>=(Y-ppPaddleH/2) && Sy<=(Y+ppPaddleH/2)) {
			return true;
		}
		else {
			return false;
		}
	}
}
