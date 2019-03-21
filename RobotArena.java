package test;

import java.util.ArrayList;
import java.util.Random;

public class RobotArena {
	double xSize, ySize;						// size of arena
	private ArrayList<RobotType> allRobots;			// array list of all Robots in arena
	/**
	 * construct an arena
	 */
	RobotArena() {
			this(400,500);
	}
	/**
	 * construct arena of size xS by yS
	 * @param xS
	 * @param yS
	 */
	RobotArena(double xS, double yS){
		xSize = xS;
		ySize = yS;
		allRobots = new ArrayList<RobotType>();
		allRobots.add(new PlainRobot(xSize/2, ySize/2, 10, 105, 5));
	}
	/**
	 * return arena size in x direction
	 * @return
	 */
	public double getXSize() {
		return xSize;
	}
	/**
	 * return arena size in y direction
	 * @return
	 */
	public double getYSize() {
		return ySize;
	}
	/**
	 * draw all Robots in the arena into interface ri
	 * @param ri
	 */

	
	
	public void drawArena(RobotInterface ri) {
		for (RobotType b : allRobots) b.drawRobot(ri);		// draw all robots
	}
	/**
	 * check all robots .. see if need to change angle of moving robots, etc 
	 */
	public void checkRobots() {
		for (RobotType b : allRobots) b.checkRobot(this);	// check all robots
		
	}
	
	/**
	 * adjust all Robots .. move any moving ones
	 */
	public void adjustRobots() {
		for (RobotType b : allRobots) b.adjustRobot();
	}
	/**
	 * return list of strings defining each robot
	 * @return
	 */
	public ArrayList<String> describeAll() {
		ArrayList<String> ans = new ArrayList<String>();		// set up empty arraylist
		for (RobotType b : allRobots) ans.add(b.toString());			// add string defining each robot
		return ans;												// return string list
	}
	
	/** 
	 * Check angle of robot ... if hitting wall, rebound; if hitting robot, change angle
	 * @param x				robot x position
	 * @param y				y
	 * @param rad			radius
	 * @param ang			current angle
	 * @param notID			identify of robot not to be checked
	 * @return				new angle 
	 */
	public double CheckBallAngle(double x, double y, double rad, double ang, int notID) {
		double ans = ang;
		if (x - 25 < rad || x+25 > xSize - rad) ans = -105 + ans;
		// if robot hit (tried to go through) left or right walls, set angle: -135 + angle
		if (y -25 < rad || y +25 > ySize - rad) ans = 135 + ans;
		// if try to go off top or bottom, set 
		
		for (RobotType b : allRobots) 
			if (b.getID() != notID && b.hitting(x, y, rad)) ans = 180*Math.atan2(y-b.getY(), x-b.getX())/Math.PI;
				// check all robots except one with given id
				// if hitting, return angle between the other robot and this one.
		
		return ans;		// return the angle
	}

	/**
	 * check if the target robot has been hit by another robot
	 * @param target	the target robot
	 * @return 	true if hit
	 */
	public boolean checkHit(RobotType target) {
		boolean ans = false;
		for (RobotType b : allRobots)
			if (b instanceof PlainRobot && b.hitting(target)) ans = true;
				// try all robots, if plainrobot, check if hitting the target
		return ans;
	}	
	public void addRobot() {
		allRobots.add(new PlainRobot(xSize/2, ySize/2, 10, 105, 5));
	}
	public void addBoulder() {
		Random rand = new Random();
		allRobots.add(new Boulder(rand.nextInt(350)+15,rand.nextInt(450)+15,20));
	}
	public void addLight() {
		allRobots.add(new Light(390,490,10));
	}
	public void addWhiskers() {
		allRobots.add(new Whiskers(xSize/2, ySize/2, 10, 105, 5));
	}
}
