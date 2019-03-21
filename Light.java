package test;

public class Light extends RobotType {

	public Light (double ix, double iy, double ir) {
		super(ix, iy, ir);
		col = 'y';
	}
	@Override
	protected void checkRobot(RobotArena b) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void adjustRobot() {
		// TODO Auto-generated method stub

	}

	protected String getStrType() {
		return "Light";
	}
	@Override
	protected void drawRobot(RobotInterface ri) {
		// TODO Auto-generated method stub
		ri.showLight(x,y,rad,col);
	}	
	
}
