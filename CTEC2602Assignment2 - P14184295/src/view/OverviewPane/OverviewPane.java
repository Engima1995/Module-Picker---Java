package view.OverviewPane;

import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The OverviewPane Class is a sub pane class, which is added to the RootPane to display the GUI Application.
 * 
 * The OverviewPane Class is associated with displaying information about the modules and course selected by the student. It is like a
 * receipt for the modules and course the student has chosen and can be saved to a text format for future use.
 * */
public class OverviewPane extends StackPane{

	private ResultPane rp;			//ViewPane, CreditPane and ButtonPane Classes are set up
	private OPButtonPane bp;
	
	/**
	 * Default Constructor that will construct the OverviewPane
	 * */
	
	public OverviewPane(){
		
		this.getStylesheets().add(getClass().getResource("overview.css").toExternalForm());		//Looks for stylesheets
		this.getStyleClass().add("overviewpane");												//Adds a class overviewpane, which can be called in css
																								//to edit the style of the .overviewpane class
		
		rp = new ResultPane();																	//Instantiates the Classes
		bp = new OPButtonPane();
		
		VBox container = new VBox(rp, bp);														//VBox vertically aligns the different Panes
		VBox.setVgrow(rp, Priority.ALWAYS);														//rp will be the top pane and bp will be the bottom pane
		VBox.setVgrow(bp, Priority.ALWAYS);														//Priority of Vgrow are set to ALWAYS for resizing purposes
		container.setSpacing(20);
		container.setPadding(new Insets(20));																									//spacing between the panes are set and Insets are set
		
		this.getChildren().add(container);														//children are added to the StackPane
		
	}
	
	/**
	 * getResultPane() returns the ResultPane to the calling class
	 * 
	 * @return returns ResultPane
	 * */
	public ResultPane getResultPane(){
		
		return rp;
		
	}
	
	/**
	 * getOPButtonPane() returns the OPButtonPane to the calling class
	 * 
	 * @return returns OPButtonPane
	 * */
	public OPButtonPane getButtonPane(){
		
		return bp;
		
	}
	
}
