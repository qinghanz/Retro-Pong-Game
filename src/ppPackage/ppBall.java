package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.*;
import acm.gui.IntField;
import acm.program.GraphicsProgram;


	/* The template used for this class is used from the Assignment 4 from Prof Frank
	 * Ferrie for the class ECSE-202. It is also heavily inspired from the framework of 
	 * assignment 3 
	 */


public class ppBall extends Thread {

	// Instance variables
	
	private double Xinit; // Initial position of ball - X
	private double Yinit; // Initial position of ball - Y
	private double Vo; // Initial velocity (Magnitude)
	private double theta; // Initial direction
	private double loss; // Energy loss on collision
	private Color color; // Color of ball
	private ppTable table; // Instance of ping-pong table
	private ppPaddle myPaddle;
	private ppPaddleAgent theAgent;
	private boolean traceOn;
	double Yo, Y;
	boolean hasEnergy = true;
	GOval ball; // Graphics object representing ball
	int agentPoints, humanPoints;

	public ppBall(double Xinit, double Yinit, double Vo, double theta, Color color, double loss, ppTable table, boolean traceOn) {
		this.Xinit = Xinit; // Copy constructor parameters to instance variables
		this.Yinit = Yinit;
		this.Vo = Vo;
		this.theta = theta;
		this.loss = loss;
		this.table = table;
		this.traceOn = traceOn;

		ball = new GOval(XLWALL * SCALE, 600 / 2, bSize * 2 * SCALE, bSize * 2 * SCALE);
		ball.setFilled(true);
		ball.setColor(color);
		table.getDisplay().add(ball);

	}

	public void run() {

		double time = 0; // time (reset at each interval)
		double Vt = bMass * g / (4 * Pi * bSize * bSize * k); // Terminal velocity
		double Xo, X, Vx; // X position and velocity variables
		double Vy; // Y position and velocity variables
		double Vox = Vo * Math.cos(theta * Pi / 180); // Initial velocity components in // X and Y
		double Voy = Vo * Math.sin(theta * Pi / 180);

		Xo = XINIT; // Initial X offset
		Yo = Yinit; // Initial Y offset
				

		while (hasEnergy) { // loop will continue until (KEx+KEy+PE)<= ETHR)
			
			humanPoints = agentPoints = 0;

			X = (Vox * Vt / g * (1 - Math.exp(-g * time / Vt))); // Update parameters of position of the ball (x and y coordinates)
			Y = (Vt / g * (Voy + Vt) * (1 - Math.exp(-g * time / Vt)) - Vt * time);
			Vx = Vox * Math.exp(-g * time / Vt);
			Vy = (Voy + Vt) * Math.exp(-g * time / Vt) - Vt;
			double KEx = 0.5 * bMass * Vx * Vx * (1 - loss), KEy = 0.5 * bMass * Vy * Vy * (1 - loss); // Kinetic energy
																										// in X and Y
																										// directions
			double PE = bMass * g * (Y + Yo);

			if (Vy < 0 && (Yo + Y <= bSize)) { // Simulating ball's collision from floor

				KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
				KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
				PE = 0;
				Vox = Math.sqrt(2 * KEx / bMass);
				Voy = Math.sqrt(2 * KEy / bMass);

				if (Vx < 0) { // if ball is moving from right to left (ie. after bouncing off right wall)
					Vox = -Vox;
				}

				time = 0; // time is reset at every collision
				Xo += X; // need to accumulate distance between collisions
				Yo = bSize; // the absolute position of the ball on the ground
				X = 0; // (X,Y) is the instantaneous position along an arc,
				Y = 0; // Absolute position is (Xo+X,Yo+Y).

			}

			if (Vx<0 && (Xo + X) < theAgent.getX()+bSize-ppPaddleW/2) { // Simulating ball's collision with Agent
				
				
				if (theAgent.contact(Xo+X, Yo+Y)==true) {
										
					KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
					KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
					PE = bMass * g * (Y + Yo);
					Vox = Math.sqrt(2*KEx/bMass)*ppPaddleXgain; // Scale X component of velocity
					
					if (Vy > 0) {
						
						Voy = Math.sqrt(2*KEy/bMass)*ppPaddleYgain*myPaddle.getSgnVy();
					
					}else {
						
						Voy = -Math.sqrt(2*KEy/bMass)*ppPaddleYgain*myPaddle.getSgnVy();
						
					}
					Vox = Vox*ppPaddleXgain;

					time = 0; // time is reset at every collision
					Xo = theAgent.getX()+bSize+ppPaddleW/2; // need to accumulate distance between collisions
					Yo += Y; // the absolute position of the ball on the ground
					X = 0; // (X,Y) is the instantaneous position along an arc,
					Y = 0; // Absolute position is (Xo+X,Yo+Y).

					if (Vy < 0) {
						Voy = -Voy;
					}
					
				} else { // exit loop of ball does not hit paddle
					table.getDisplay().humanScore();;
					hasEnergy = false;
				}

			}
			
			if (Vx>0 && (Xo + X) > myPaddle.getX()-bSize-ppPaddleW/2) { // Simulating ball's collision with paddle
				
				if (myPaddle.contact(Xo+X, Yo+Y)==true) {
					
					KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
					KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
					PE = bMass * g * (Y + Yo);
					Vox = -Math.sqrt(2*KEx/bMass)*ppPaddleXgain; // Scale X component of velocity
					Voy = Math.sqrt(2*KEy/bMass)*ppPaddleYgain*myPaddle.getSgnVy(); // Scale Y + same dir. as paddle

					time = 0; // time is reset at every collision
					Xo = ppPaddleXinit-bSize; // need to accumulate distance between collisions
					Yo += Y; // the absolute position of the ball on the ground
					X = 0; // (X,Y) is the instantaneous position along an arc,
					Y = 0; // Absolute position is (Xo+X,Yo+Y).

					if (Vy < 0) {
						Voy = -Voy;
					}
					
				} else { // exit loop of ball does not hit paddle
					table.getDisplay().agentScore();
					hasEnergy = false;
				}

			}
			
			if (ball.getY()<0 && Vx<0) {
				
				hasEnergy = false;
				table.getDisplay().agentScore();
				System.out.print("Out of bounds! Point goes to Agent");
				
			}
			
			if (ball.getY()<0 && Vx>0) {
				
				hasEnergy = false;
				table.getDisplay().humanScore();
				System.out.print("Out of bounds! Point goes to Human");
				
			}

			double ScrX = table.toScr(Xo + X - bSize); // updated X position of ball after loop
			double ScrY = table.toScrY(Yo + Y + bSize); // updated Y position of ball after loop
			ball.setLocation(ScrX, ScrY); // position of ball updated after every loop
			time += TICK; // Increment time

			if ((KEx + KEy + PE) <= ETHR) { // condition to exit while loop
				break;
			}
			if (traceOn) {
				
				GOval trail = new GOval(ScrX + table.toScr(bSize), ScrY + table.toScr(bSize), PD, PD); // creates trail behind ball
				trail.setFilled(true);
				trail.setColor(Color.black);
			
	
				table.getDisplay().add(trail); // adds trail to screen
			}

			table.getDisplay().pause(TICK * 5000); // pauses to visualize ball mouvement

			final boolean TEST = true; // print if test true
			if (TEST) {
				System.out.printf("t: %.2f X: %.2f Y: %.2f Vx: %.2f Vy:%.2f\n", time, Xo + X, Yo + Y, Vx, Vy);
			}

		}
		
		table.getDisplay().agentPoints.setValue(table.getDisplay().agentScore); // adds the agent point if he wins at the end of while loop
		table.getDisplay().humanPoints.setValue(table.getDisplay().humanScore); // adds the human point if he wins at the end of while loop

	}

	public GObject getBall() { // the method returns ball and is used in ppSim
		return ball;
	}
	
	public double getY() { // method is used by Agent to follow Y position of ball
		return Yo+Y;
	}
	
	public void setPaddle(ppPaddle myPaddle) {
		this.myPaddle = myPaddle;
	}

	public void setAgent(ppPaddleAgent theAgent) {
		this.theAgent = theAgent;
	}
	
	public boolean ballInPlay() { // true when ball is moving and false otherwise
		return hasEnergy;
	}
	
}

