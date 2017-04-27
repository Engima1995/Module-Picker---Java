package view.ModulePane;

import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import view.ModulePane.ButtonPane;
import view.ModulePane.CreditPane;
import view.ModulePane.ViewPane;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The ModulesPane Class is a sub pane class, which is added to the RootPane to display the GUI Application.
 * 
 * The ModulesPane Class is associated with displaying information about the modules, credit value and buttons that handle appropriate
 * events.
 * */
public class ModulesPane extends StackPane{

	private ViewPane vp;			//ViewPane, CreditPane and ButtonPane Classes are set up
	private CreditPane cp;
	private ButtonPane bp;
	
	/**
	 * Default Constructor allows the creation of the ModulesPane.
	 * */
	public ModulesPane(){
		
		this.getStylesheets().add(getClass().getResource("modulepane.css").toExternalForm()); //Looks for stylesheets
		this.getStyleClass().add("modulepane");												  //Adds a class modulepane, which can be called in css
																							  //to edit the style of the .modulepane class
		
		vp = new ViewPane();																  //Instantiates the Classes
		cp = new CreditPane();
		bp = new ButtonPane();
		
		VBox container = new VBox(vp, cp, bp);												  //VBox vertically aligns the different Panes
		VBox.setVgrow(vp, Priority.ALWAYS);													  //vp will be the top pane
		VBox.setVgrow(cp, Priority.ALWAYS);                                                   //cp will be the middle and bp will be the bottom pane
		VBox.setVgrow(bp, Priority.ALWAYS);													  //Priority of Vgrow are set to ALWAYS for resizing purposes
		container.setSpacing(50);
		container.setPadding(new Insets(20));												  //spacing between the panes are set and Insets are set
		
		this.getChildren().add(container);												      //children are added to the StackPane
		
	}
	
	/**
	 * getViewPane() returns the ViewPane to the calling class
	 * 
	 * @return returns ViewPane
	 * */
	public ViewPane getViewPane(){															  
		
		return vp;
		
	}
	
	/**
	 * getCreditPane() returns the CreditPane to the calling class
	 * 
	 * @return returns CreditPane
	 * */
	public CreditPane getCreditPane(){
		
		return cp;
		
	}
	
	/**
	 * getButtonPane() returns the ButtonPane to the calling class
	 * 
	 * @return returns ButtonPane
	 * */
	public ButtonPane getButtonePane(){
		
		return bp;
		
	}
	
}
