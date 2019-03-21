package test;
/**
 * class for representing a line and performing operations on it
 * such as finding whether/where two lines intersect or how far away the lines is from a point
 */
public class Line {
	private int[] coords;		// coordinates of line (x1, y1, x2, y2)
	private int[] xy;			// xy point used in calculation
	private double gradient;	// gradient of line
	private double offset;		// offset
	/**
	 * construct a basic line
	 */
	Line() {
		this(0,0,1,0);
	}
	/** 
	 * construct line from x1,y1 to x2,y2
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	Line(int x1, int y1, int x2, int y2) {
		coords = new int[] {x1, y1, x2, y2};	// store end points in coords
		xy = new int[] {x1, y1};				// initialise xy to one point
	}
	/**
	 * Construct line whose x1,y1,x2,y2 coordinates are in array cs
	 * @param cs
	 */
	Line(int [] cs) {
		this(cs[0], cs[1], cs[2], cs[3]);
	}
	
	/**
	 * calculate distance from x1,y1 to x2,y2
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return	distance 
	 */
	static int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
	}
	
	/**
	 * calculate square of length of line
	 * @return
	 */
	public int lineLength() {
		return distance(coords[0], coords[1], coords[2], coords[3]);
	}
	/**
	 * return the value of the xy point
	 * @return	array of two integers
	 */
	public int[] getXY() {
		return xy;
	}
	/**
	 * return calculated gradient of line, m as in y = mx + c
	 * @return gradient
	 */
	public double getGradient() {
		return gradient;
	}
	/**
	 * return calculated offset of line, c as in y = mx+c
	 * @return
	 */
	public double getOffset() {
		return offset;
	}
	/** 
	 * calculate the gradient and offset of the line
	 * already ascertained that is not vertical
	 */
	private void calcGradOff() {
		gradient = (double) (coords[3] - coords[1]) / (double) (coords[2] - coords[0]);
		offset = coords[3] - gradient * coords[2];
	}
	/**
	 * calculate y value of line, using pre calculated gradient and offset
	 * @param x
	 * @return y being mx + c
	 */
	public int calcY(int x) {
		return (int) Math.round(gradient*x + offset);
	}
	/**
	 * test if line is vertical (x ccoordinates the same)
	 * @return
	 */
	private boolean isVertical() {
		return coords[2]==coords[0];
	}
	/** 
	 * Is value v between v1 and v2
	 * @param v
	 * @param v1
	 * @param v2
	 * @return result of test
	 */
	private boolean isBetween(int v, int v1, int v2) {
		if (v1>v2)  return v>=v2 && v<=v1;
		else		return v>=v1 && v<=v2;
	}
	/**
	 * is point xyp on the line (ie between its start and end coordinates)
	 * @param xyp	xyp[0] is x; xyp[1] is y
	 * @return
	 */
	public boolean isOnLine(int[] xyp) {
		return isBetween(xyp[0], coords[0], coords[2]) && isBetween(xyp[1], coords[1], coords[3]);
	}
	/**
	 * See if the line intersects with otherLine, return true if so
	 * in which case calcaulate in xyp the point of intersection 
	 * @param otherLine
	 * @return if true
	 */
	public boolean findIntersection (Line otherLine) {
	boolean isOne = true;
		if (isVertical()) {			// is vertical line
			if (otherLine.isVertical()) isOne = false;		// two vertical lines dont intersect
			else {
				xy[0] = coords[0];							// intersect at this x
				otherLine.calcGradOff();					// calc grad and offset of other line
				xy[1] = otherLine.calcY(coords[0]);			// so find y value of intersection
			}
		}
		else {
			calcGradOff();									// calc gradient and offset
			if (otherLine.isVertical()) {
				xy = otherLine.getXY();						// get xy associated with otherLine for x
				xy[1] = calcY(xy[0]);						// y value found using this line's grad/off
			}
			else {
				otherLine.calcGradOff();					// calc gradient and offset of other line
				double ograd = otherLine.getGradient();
				if (Math.abs(ograd-gradient)<1.0e-5)		// check not parallel lines
					isOne = false;
				else {										// calculate intersection
					xy[0] = (int) Math.round( (otherLine.getOffset() - offset) / (gradient  - ograd));
					xy[1] = otherLine.calcY(xy[0]);
				}
			}	
		}
		if (isOne) isOne = isOnLine(xy) && otherLine.isOnLine(xy);
				// if found intersection, check that it is on both lines
		return isOne;
	}
	/**
	 * Calculate the distance the line is from the otherLine
	 * @param otherLine
	 * @return
	 */
	public int distIntersection (Line otherLine) {
		int ans = 100000000;
		if (findIntersection(otherLine)) ans = distance(xy[0], xy[1], coords[0], coords[1]);
		return ans;
	}
	/**
	 * Find the  shortest distance of x,y from line 
	 * @param x
	 * @param y
	 * @return shortest distance
	 */
	public int distanceFrom (int x, int y) {
		int sdist, sdist2;				// used for holding result
					// first calculate in xy point where perpendicular to line meets x,y
		if (coords[0] == coords[2]) {    // vertical line
			xy[0] = coords[0];				// so meet at x coordinate of line
			xy[1] = y;						// and y coordinate is value of y passed
		}
		else if (coords[1] == coords[3]) {	// if horizontal line
			xy[0] = x;						// perpendicular at x 
			xy[1] = coords[1];				// and y is y coord of line
		}
		else {
			calcGradOff();					// calc gradient and offset of line
			double offset2 = y + x / gradient;		// find offset of perpendicular
													// grad of perpendendicular is -1/gradient of this
			xy[0] = (int) Math.round((offset2 - offset)/(gradient + 1.0/gradient));
			xy[1] = (int) Math.round((offset + offset2 * gradient*gradient)/(gradient*gradient + 1.0));
		}
				// now test is intersection is on line
		if (isOnLine(xy)) 
			sdist = distance(x, y, xy[0], xy[1]);			// so answer is dist^2 from x,y to interesction
		else {											// otherwise try distance^2 to end points of line
			sdist = distance(x, y, coords[0], coords[1]);
			sdist2 = distance(x, y, coords[2], coords[3]);
			if (sdist2 < sdist) sdist = sdist2;			// select shorter of two
		}
		return sdist;
	}
}