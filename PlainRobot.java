package test;

public class PlainRobot extends RobotType {

	double rAngle, rSpeed;			// angle and speed of travel
	
	public PlainRobot() {
		
	}
	/** Create plain robot, size ir ay ix,iy, moving at angle ia and speed is
	 * @param ix
	 * @param iy
	 * @param ir
	 * @param ia
	 * @param is
	 */
	public PlainRobot(double ix, double iy, double ir, double ia, double is) {
		super(ix, iy, ir);
		rAngle = ia;
		rSpeed = is;
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

	
	
	/**
	 * return string defining robot type
	 */
	protected String getStrType() {
		return "Plain Robot";
	}
	@Override
	protected void drawRobot(RobotInterface ri) {
		ri.showRobot(x, y, rad, col);
		
	}
}
