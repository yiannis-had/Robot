package test;

public abstract class RobotType {
	protected double x, y, rad;						// position and size of robot
	protected char col;								// used to set colour
	static int RobotCounter = 0;					// used to give each robot a unique identifier
	protected int RobotID;							// unique identifier for item

RobotType() {
			this(100,100,10);
	}

RobotType (double ix, double iy, double ir) {
	x = ix;
	y = iy;
	rad = ir;
	RobotID = RobotCounter++;
	col = 'o';
	}
/**
 * return x position
 * @return
 */
public double getX() { return x; }
/**
 * return y position
 * @return
 */
public double getY() { return y; }

/**
 * return radius of robot
 * @return
 */
public double getRad() { return rad; }
/** 
 * set the Robot at position nx,ny
 * @param nx
 * @param ny
 */
public void setXY(double nx, double ny) {
	x = nx;
	y = ny;
}
/**
 * return the identity of Robot
 * @return
 */
public int getID() {return RobotID; }
/**
 * draw a robot into the interface bi
 * @param bi
 */
protected abstract void drawRobot(RobotInterface ri);

protected String getStrType() {
	return "Robot";
}	
	/** 
	 * return string describing Robot
	 */
	public String toString() {
		return getStrType()+" with ID "+ RobotID +" at "+Math.round(x)+", "+Math.round(y);
	}
	
	/**
	 * abstract method for checking a Robot in arena b
	 * @param b
	 */
	protected abstract void checkRobot(RobotArena b);
	/**
	 * abstract method for adjusting a Robot (?moving it)
	 */
	protected abstract void adjustRobot();
	/**
	 * is Robot at ox,oy size or hitting this Robot
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	public boolean hitting(double ox, double oy, double or) {
		return (ox-x)*(ox-x) + (oy-y)*(oy-y) < (or+rad)*(or+rad);
	}		// hitting if dist between robot and ox,oy < ist rad + or
	
	/** is Robot hitting the other Robot
	 * 
	 * @param oRobot - the other Robot
	 * @return true if hitting
	 */
	public boolean hitting (RobotType oRobot) {
		return hitting(oRobot.getX(), oRobot.getY(), oRobot.getRad());
	}
}