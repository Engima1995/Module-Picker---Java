package view.ModulePane;

import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The CreditPane Class keeps track of the credits that are added/removed in the ViewPane Class.
 * */
public class CreditPane extends GridPane{

	private Label credits;				//Label to represent text
	private TextField currcredits;		//Textfield for updating value of credits
	
	private int maxLimit;				//max limit of credits that it can have
	
	/**
	 * Default Constructor allows the creation of the CreditPane.
	 * */
	public CreditPane(){
		
		this.getStylesheets().add(getClass().getResource("modulepane.css").toExternalForm()); //Looks for stylesheets
		this.getStyleClass().add("modulepane");												  //Adds a class modulepane, which can be called in css
																							  //to edit the style of the .modulepane class
		
		maxLimit = 120;																		  //max limit set to 120 but can be changed through function call
		
		this.setAlignment(Pos.CENTER);														  //Aligns the Label and Textfield to Center
		this.setHgap(15);																	  //Horizontal Gap is set
		
		credits = new Label("Current Credits: ");											  //Everything is set. currcredits.setEditable is set to false
		currcredits = new TextField("0");													  //so nothing can be changed or edited or misused
		currcredits.setEditable(false);
		currcredits.setId("currcredits");													  //id is set to keep track in css
		currcredits.setAlignment(Pos.CENTER);												  
		
		this.add(credits, 0, 0);															  //Children are added in their positions
		this.add(currcredits, 1, 0);
		
	}
	
	/**
	 * getMaxLimit() returns the current maxLimit
	 * 
	 * @return returns maxLimit
	 * */
	public int getMaxLimit(){
		return maxLimit;
	}
	
	/**
	 * setMaxLimit sets the maxLimit by calling the function and passing the parameter value
	 * 
	 * @param maxLimit - is the value that is used to set the max limit for the credits
	 * */
	public void setMaxLimit(int maxLimit){
		
		if(!(maxLimit < 1)){
			this.maxLimit = maxLimit;
		}
		
	}
	
	/**
	 * getCreditsField() returns the TextField that is being used to update the credit values
	 * 
	 * @return returns the associated TextField
	 * */
	public TextField getCreditsField(){
		return currcredits;
	}
	
	/**
	 * setCredits() sets the credits value, updating the current value in the TextField
	 * 
	 * @param credits - is the value passed to update the current TextField
	 * */
	public void setCredits(int credits){
		currcredits.setText(String.valueOf(credits));
	}
	
	/**
	 * getCredits() returns the current credit value in the TextField
	 * 
	 * @return returns the current value in the TextField
	 * */
	public int getCredits(){
		return Integer.parseInt(currcredits.getText());
	}
	
	/**
	 * addCredits() will set the current TextField value summed with the value passed to this function
	 * 
	 * @param credits - is the value passed to the function, which is added to the current value
	 * */
	public void addCredits(int credits){
		currcredits.setText(String.valueOf(Integer.parseInt(currcredits.getText()) + credits));
	}
	
	/**
	 * removeCredits() will remove the value from the current TextField by the number passed by credits
	 * 
	 * @param credits - is the value passed to the function, which is set to be the current value in the TextField
	 * */
	public void removeCredits(int credits){
		currcredits.setText("0");								//sets TextField to 0
		if(Integer.parseInt(currcredits.getText()) >= 0){		//sets current TextField to be the parameter value
			currcredits.setText(String.valueOf(credits));
		}
	}
	

	/**
	 * isMax() returns the BooleanBinding, if the current TextField value is at the max limit then disable property
	 * 
	 * @return returns BooleanBinding which will set the binding for any node/property that is associated with BooleanBinding
	 * */
	public BooleanBinding isMax(){
		return currcredits.textProperty().isEqualTo(String.valueOf(maxLimit));
	}
	
	/**
	 * isMax() returns the BooleanBinding, if the current TextField value is not equal to the max limit then disable property
	 * 
	 * @return returns BooleanBinding which will set the binding for any node/property that is associated with BooleanBinding
	 * */
	public BooleanBinding isNotEqToMax(){
		return currcredits.textProperty().isNotEqualTo(String.valueOf(maxLimit));
	}
	
	/**
	 * clearCreditPanes() sets the TextField value to the initial stage
	 * */
	public void clearCreditPanes(){
		currcredits.setText("0");
	}
	
}
