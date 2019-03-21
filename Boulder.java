package test;

public class Boulder extends RobotType {

	
	public Boulder(double ix, double iy, double ir) {
		super(ix, iy, ir);
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
		return "Boulder";
	}
	@Override
	protected void drawRobot(RobotInterface ri) {
		ri.showBoulder(x, y, rad,col);
		
	}	

}
