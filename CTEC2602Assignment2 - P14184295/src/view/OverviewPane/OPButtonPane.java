package view.OverviewPane;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The OPButtonPane Class extends HBox to add button horizontally when displayed. It is part of the OverviewPane Class.
 * */
public class OPButtonPane extends HBox{
	
	private Button saveview;		//Button for saving the overview
	
	/**
	 * Default Constructor that will construct the OPButtonPane
	 * */
	
	public OPButtonPane(){
		
		this.getStylesheets().add(getClass().getResource("overview.css").toExternalForm());		//Looks for stylesheets
		this.getStyleClass().add("overviewpane");												//Adds a class overviewpane, which can be called in css
																								//to edit the style of the .overviewpane class
		
		saveview = new Button("Save Overview");													//new button is assigned with its own name
		saveview.setPrefSize(120, 20);															//size has been set to 120 w and 20 h
		
		saveview.setDisable(true);																//button is disabled for binding functionality
		
		this.setAlignment(Pos.CENTER);															//HBox is aligned centered and adds children
		this.getChildren().add(saveview);
		
	}
	
	/**
	 * overviewBtnDisableBind() sets the binding of the save overview button to the value passed through the parameter
	 * 
	 * @param value - BooleanBinding value, which will set the binding for the save overview button and it will enable and disable accordingly
	 * */
	public void overviewBtnDisableBind(BooleanBinding value){
		saveview.disableProperty().bind(value);
	}
	
	/**
	 * addSaveOverviewHandler() function is used for passing EventHandling functionality to the OPButtonPane, for saving the result of the Overview to a txt file
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addSaveOverviewHandler(EventHandler<ActionEvent> handler){
		
		saveview.setOnAction(handler);
		
	} 
	
}
