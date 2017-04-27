package view.StudentProfilePane;

import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import view.StudentProfilePane.RegisterInputPane;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The StudentProfilePane Class is a sub pane class, which is added to the RootPane to display the GUI Application.
 * 
 * The StudentProfilePane Class is associated with displaying nodes that require input from the student, to collect their data and
 * allow them to pick modules according to their selected course
 * */
public class StudentProfilePane extends StackPane{

	private RegisterInputPane rip;			//RegisterInputPane class is set up
	
	/**
	 * Default Constructor allows the creation of the ModulesPane.
	 * */
	public StudentProfilePane(){
		
		this.getStylesheets().add(getClass().getResource("rip.css").toExternalForm());		//Looks for stylesheets
		this.getStyleClass().add("inputpane");												//Adds a class inputpane, which can be called in css
																							//to edit the style of the .inputpane class
		
		rip = new RegisterInputPane();														//Instantiates the Classes
		
		VBox container = new VBox(rip);														//VBox vertically aligns the different Panes, rip will be the top pane		
		VBox.setVgrow(rip, Priority.ALWAYS);												//Priority of Vgrow are set to ALWAYS for resizing purposes
		
		this.getChildren().add(container);													//StackPane adds children
		
	}
	
	/**
	 * getRegisterInputPane() returns the RegisterInputPane to the calling class
	 * 
	 * @return returns RegisterInputPane
	 * */
	public RegisterInputPane getRegisterInputPane(){
		return rip;
	}
	
}
