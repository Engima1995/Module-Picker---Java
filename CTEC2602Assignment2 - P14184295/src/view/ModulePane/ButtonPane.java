package view.ModulePane;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The ButtonPane Class extends HBox to add buttons horizontally when displayed. It is part of the ModulePane Class.
 * */
public class ButtonPane extends HBox{
	
	private Button add, remove, reset, submit;	//Buttons for adding, removing, reseting and submitting
	
	/**
	 * Default Constructor allows the creation of the ButtonPane.
	 * */
	public ButtonPane(){
		
		this.getStylesheets().add(getClass().getResource("modulepane.css").toExternalForm());	//Looks for stylesheets
		this.getStyleClass().add("modulepane");													//Adds a class modulepane, which can be called in css
																								//to edit the style of the .modulepane class
		
		this.setSpacing(15);																	//sets the space of all buttons 
		
		add = new Button("Add");																//new buttons are assigned, each with its own name
		remove = new Button("Remove");
		reset = new Button("Reset");
		submit = new Button("Submit");
		
		add.setDisable(true);																	//add and submit are disabled for binding functionality
		submit.setDisable(true);
		
		this.getChildren().addAll(add, remove, reset, submit);									//HBox adds all buttons as children and aligns them to be Centered
		this.setAlignment(Pos.CENTER);
		
		for (Node n : this.getChildren()) {														//for each button, the size is set to 120 w & 20 h
			((Button) n).setPrefSize(120, 20);
		}
		
	}
	
	/**
	 * addBtnDisableBind() sets the binding of the add button to the value passed through the parameter
	 * 
	 * @param value - BooleanBinding value, which will set the binding for the add button and it will enable and disable accordingly
	 * */
	public void addBtnDisableBind(BooleanBinding value){
		add.disableProperty().bind(value);
	}
	
	/**
	 * submitBtnDisableBind() sets the binding of the submit button to the value passed through the parameter
	 * 
	 * @param value - BooleanBinding value, which will set the binding for the submit button and it will enable and disable accordingly
	 * */
	public void submitBtnDisableBind(BooleanBinding value){
		submit.disableProperty().bind(value);
	}
	
	/**
	 * addAddHandler() function is used for passing EventHandling functionality to the ButtonPane, for adding information to the ViewPane
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addAddHandler(EventHandler<ActionEvent> handler) {
		add.setOnAction(handler);
	}
	
	/**
	 * addRemoveAddHandler() function is used for passing EventHandling functionality to the ButtonPane, for removing information from the ViewPane
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addRemoveHandler(EventHandler<ActionEvent> handler) {
		remove.setOnAction(handler);
	}

	/**
	 * addResetHandler() function is used for passing EventHandling functionality to the ButtonPane, for reseting information in the ViewPane
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		reset.setOnAction(handler);
	}
	
	/**
	 * addSubmitHandler() function is used for passing EventHandling functionality to the ButtonPane, for submitting information to the OverviewPane
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addSubmit(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}
	
}
