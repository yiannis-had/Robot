package test;

public class Whiskers extends RobotType {
	
	double rAngle, rSpeed;			// angle and speed of travel
	
	public Whiskers(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		rAngle = ia;
		rSpeed = is;
	
		col = 'l';
	}
	
	@Override
	protected void drawRobot(RobotInterface ri) {
		// TODO Auto-generated method stub
		ri.showWhiskers(x, y, rad, col);
	}

	@Override
	protected void checkRobot(RobotArena b) {
		rAngle = b.CheckBallAngle(x, y, rad, rAngle, RobotID);
	}



	/**
	 * adjustRobot
	 * move robot depending on speed and angle
	 */
	@Override
	protected void adjustRobot() {
		double radAngle = rAngle*Math.PI/180;		// put angle in radians
		x += rSpeed * 0.20* Math.cos(radAngle);		// new X position
		y += rSpeed * 0.20* Math.sin(radAngle);		// new Y position
	}


}
