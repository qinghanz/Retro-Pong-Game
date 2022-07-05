package ppPackage;

import static ppPackage.ppSimParams.*;
import static ppPackage.ppBall.*;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import acm.gui.IntField;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.util.*;


	/* The template used for this class is used from the Assignment 4 from Prof Frank
	 * Ferrie for the class ECSE-202. It is also heavily inspired from the framework of 
	 * assignment 3 
	 */


public class ppSimPaddleAgent extends GraphicsProgram {

// instance variables
	
	ppTable myTable;
	ppPaddle myPaddle;
	ppBall myBall;
	ppPaddleAgent theAgent;
	IntField agentPoints;
	IntField humanPoints;
	JToggleButton traceButton;
	JButton button;
	RandomGenerator rgen;
	public int humanScore = 0;
	public int agentScore = 0;

	public static void main(String[] args) {
		new ppSimPaddleAgent().start(args);
	}

	public void init() {
		
		// creating Scoreboard

		
		Scanner sc = new Scanner(System.in); // using a scanner to allow user to enter a name that he wants
		System.out.println("Enter agent's name: "); // asks user for agent's name
		String Agent = sc.nextLine();
		System.out.println("Enter your name: "); // asks user for his name
		String Human = sc.nextLine();
		
		add(new JLabel(Agent), NORTH); // Agent IntField
		agentPoints = new IntField(); 
		add(agentPoints, NORTH);
		
		add(new JLabel(Human), NORTH); // Human IntField
		humanPoints = new IntField();
		add(humanPoints, NORTH);
		
		add(new JButton("Clear"), SOUTH); // add clear button
		add(new JButton("New Serve"), SOUTH); // add new serve button
		traceButton = new JToggleButton("Trace", true); // JToggle trace button
		add(traceButton, SOUTH);
		traceButton.setActionCommand("Trace");
		add(new JButton("QUIT"), SOUTH); // add quit button
		
		// creation of JButtons for the 4 items
		// adding them to the canvas
		

		this.resize(scrWIDTH + OFFSET, scrHEIGHT + OFFSET);

		addMouseListeners(); // sourced from assignment 4 instructions document
		addActionListeners(); // sourced from assignment 4 instructions document

		RandomGenerator rgen = RandomGenerator.getInstance(); // creating a RandomGenrator by calling the getInstance method
		rgen.setSeed(RSEED); // Random number generator seed value that is the same for all students

		myTable = new ppTable(this); // generate myTable
		myBall = newBall(); // sourced from assignment 4 instructions document
		myPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, ColorPaddle, myTable); // sourced from assignment 4 instructions document
		theAgent = new ppPaddleAgent(ppAgentXinit, ppAgentYinit, ColorAgent, myTable); // sourced from assignment 4instructions document

		myBall.setPaddle(myPaddle); // sourced from assignment 4 instructions document
		myBall.setAgent(theAgent); // sourced from assignment 4 instructions document
		theAgent.attachBall(myBall); // sourced from assignment 4 instructions document

		pause(1000);

		add(myBall.getBall()); // sourced from assignment 4 instructions document
		theAgent.start(); // sourced from assignment 4 instructions document
		myPaddle.start(); // sourced from assignment 4 instructions document
		myBall.start(); // sourced from assignment 4 instructions document
	
		
	}

	public void mouseMoved(MouseEvent e) {
		myPaddle.setY(myTable.ScrtoY((double) e.getY()));
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("Clear")) { // instructions for Clear button
			myTable.newScreen();
			humanScore = 0;
			humanPoints.setValue(humanScore);
			agentScore = 0;
			agentPoints.setValue(agentScore);
			
		
		} else if (command.equals("New Serve")) { // instructions for New Serve button

			if (myBall.ballInPlay()) { // New Serve button will not do anything if the ball in still in motion

			} else {

				myTable.newScreen();
				myTable = new ppTable(this); // generate myTable
				myBall = newBall(); // sourced from assignment 4 instructions document

				myPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, ColorPaddle, myTable); // sourced from assignment 4 instructions document
				theAgent = new ppPaddleAgent(ppAgentXinit, ppAgentYinit, ColorAgent, myTable); // sourced from assignment 4 instructions document

				myBall.setPaddle(myPaddle); // sourced from assignment 4 instructions document
				myBall.setAgent(theAgent); // sourced from assignment 4 instructions document
				theAgent.attachBall(myBall); // sourced from assignment 4 instructions document

				pause(1000);

				add(myBall.getBall()); // sourced from assignment 4 instructions document
				theAgent.start(); // sourced from assignment 4 instructions document
				myPaddle.start(); // sourced from assignment 4 instructions document
				myBall.start(); // sourced from assignment 4 instructions document
			}

		} else if (command.equals("QUIT")) { // sourced from assignment 4 instructions document
			System.exit(0); // sourced from assignment 4 instructions document

		}

	}

	public ppBall newBall() { // generating new ball
		RandomGenerator rgen = RandomGenerator.getInstance();
		rgen.setSeed(RSEED); // seed value for random generator
		Color iColor = Color.red; // color of ball is red
		double iYinit = rgen.nextDouble(YinitMIN, YinitMAX); // initial Y position
		double iLoss = rgen.nextDouble(EMIN, EMAX); // Energy loss
		double iVel = rgen.nextDouble(VoMIN, VoMAX); // Velocity
		double iTheta = rgen.nextDouble(ThetaMIN, ThetaMAX); // Launch angles
		ppBall myBall = new ppBall(XINIT, iYinit, iVel, iTheta, iColor, iLoss, myTable, traceButton.isSelected());

		return myBall;
	}
	
	public void humanScore() { // method that allows the incrementation of the human's Score
		humanScore+=1;
	}
	
	public void agentScore() { // method that allows the incrementation of the agent's Score
		agentScore+=1;
	}
	
	public void resetagentPoints() { // resets agent's score to 0
		agentScore = 0;		
	}
	
	public void resethumanPoints() { // resets human's score to 0
		humanScore = 0;
	}	
}