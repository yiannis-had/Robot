package test;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class RobotInterface extends Application {
	private GraphicsContext gc;									// graphics context for drawing it
	private AnimationTimer timer;								// timer used for animation
	private VBox rtPane;										// vertical box for putting info
	private RobotArena arena;
	
	/**
	 * function to show in a box ABout the programme
	 */
	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("About");									// say is About
	    alert.setHeaderText(null);
	    alert.setContentText("Yiannis Hadjicharalambous's JavaFX GUI arena simulation");// give text
	    alert.showAndWait();										// show box and wait for user to close
	}
	 
	 /**
	  * set up the mouse event - when mouse pressed, put robot there
	  * @param canvas
	  */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();						// create main menu
	
		Menu mFile = new Menu("File");							// add File main menu
		MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		mFile.getItems().addAll(mExit);							// add exit to File menu
		
		Menu mHelp = new Menu("Help");							// create Help menu
		MenuItem mAbout = new MenuItem("About");				// add About sub men item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									// and its action to print about
            }	
		});
		mHelp.getItems().addAll(mAbout);						// add About to Help main item
		
		menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help
		return menuBar;											// return the menu
	}
	/**
	 * set up the horizontal box for the bottom with relevant buttons
	 * @return
	 */
	private HBox setButtons() {
	    Button btnStart = new Button("Start");					// create button for starting
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	// now define event when it is pressed
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// its action is to start the timer
	       }
	    });

	    Button btnStop = new Button("Pause");					// now button for stop
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// and its action to stop the timer
	       }
	    });
	    Button btnAdd = new Button("Robot");				// now button for stop
	    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addRobot();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    Button btnAddB = new Button("Boulder");
	    btnAddB.setOnAction(new EventHandler<ActionEvent>()	{
	    	@Override
	    	public void handle(ActionEvent event) {
	    		arena.addBoulder();
	    	}
	    });
	    Button btnLgt = new Button("Light");
	    btnLgt.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event)	{
	    		arena.addLight();
	    	}
	    });
	    Button btnWhsk = new Button("Robot with Whiskers");
	    btnWhsk.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent event)	{
	    		arena.addWhiskers();
	    	}
	    });
	    														// now add these buttons + labels to a HBox
	    return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "), btnAdd, btnAddB,btnLgt,btnWhsk);
	}

	/** 
	 * function to convert char c to actual colour used
	 * @param c
	 * @return Color
	 */
	Color colFromChar (char c){
		Color ans = Color.BLACK;
		switch (c) {
		case 'y' :	ans = Color.YELLOW;
					break;
		case 'r' :	ans = Color.RED;
					break;
		case 'g' :	ans = Color.GREEN;
					break;
		case 'b' :	ans = Color.BLUE;
					break;
		case 'o' :	ans = Color.OLIVE;
					break;
		case 'e':	ans = Color.ORANGE;
					break;
		case 'l':	ans = Color.BLACK;
					break;
		}
		return ans;
	}  
	
	/**
	 * show the Robot at position x,y , radius r in colour defined by col
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void showRobot(double x, double y, double rad, char col) {
	 	gc.setFill(colFromChar(col));									// set the fill colour
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
		col = 'c';
		gc.setFill(colFromChar(col));
	gc.fillRect(x-rad, y+rad, 20, 3);
	gc.fillRect(x-rad, y-rad-3, 20, 3);
	}
	
	/** 
	 * draw the world with robot in it
	 */
	public void drawWorld () {
	 	gc.setFill(Color.BISQUE);													// set colour
	 	gc.fillRect(0,  0,  arena.getXSize(),  arena.getYSize());				// clear the arena 
	 	arena.drawArena(this);
	}
	/**
	 * show where robot is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
		ArrayList<String> allRs = arena.describeAll();
		for (String s : allRs) {
			Label l = new Label(s); 		// turn description into a label
			rtPane.getChildren().add(l);	// add label	
		}	
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Yianni's JavaFx project");
	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));

	    bp.setTop(setMenu());											// put menu at the top

	    Group root = new Group();										// create group with canvas
	    Canvas canvas = new Canvas( 400, 500 );
	    root.getChildren().add( canvas );
	    bp.setLeft(root);												// load canvas to left area
	 
	    gc = canvas.getGraphicsContext2D();								// context for drawing


	    arena = new RobotArena(400, 500);								// set up arena
	    drawWorld();
	    
	    timer = new AnimationTimer() {									// set up timer
	        public void handle(long currentNanoTime) {					// and its action when on
	        		arena.checkRobots();									// check the angle of all robots
		            arena.adjustRobots();								// move all robots
		            drawWorld();										// redraw the world
		            drawStatus();										// indicate where robots are
	        }
	    };

	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		bp.setRight(rtPane);											// add rtPane to borderpane right
		  
	    bp.setBottom(setButtons());										// set bottom pane with buttons

	    Scene scene = new Scene(bp, 700, 600);							// set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();

	}

	public static void main(String[] args) {
		  Application.launch(args);			// launch the GUI

	}

	/**
	 * show the Boulder at position x,y , radius r in colour defined by col
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	
	public void showBoulder(double x, double y, double rad,char col) {
		// TODO Auto-generated method stub
			col = 'l';																// set the fill colour
			gc.setFill(colFromChar(col));
			gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);
	}
	
	
	/**
	 * show the Light at position x,y , radius r in colour defined by col
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */

	public void showLight(double x, double y, double rad, char col) {
		// TODO Auto-generated method stub
	 	gc.setFill(colFromChar(col)); 
	 	gc.fillOval(x-rad, y-rad, 20, 30);
	}

	public void showWhiskers(double x, double y, double rad, char col) {
		// TODO Auto-generated method stub
		col = 'o';
	 	gc.setFill(colFromChar(col));									// set the fill colour
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
		col = 'c';
		gc.setFill(colFromChar(col));
		gc.fillRect(x-rad, y+rad, 20, 3);
		gc.fillRect(x-rad, y-rad-3, 20, 3);
		gc.strokeLine(x, y, x-2*rad, y-2*rad);
		gc.strokeLine(x, y, x-2*rad, y+2*rad);
	}

}
