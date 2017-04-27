package main;

import controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.StudentProfile;
import view.RootPane;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The ApplicationLoader Class provides the basis of loading the RootPane Class, which allows 
 * the running of the GUI Application made for students to select their modules from their 
 * chosen courses.
 * 
 * This Class has been established using other Java Classes such as the Stage and Application Class to 
 * setup the default state of the application.
 * */

public class ApplicationLoader extends Application{

	private RootPane view;	//Calls the RootPane class view, which setups the view of the GUI.
	
	/**
	 * The init() method initialises the variables and methods needed for later, to be used in the stage.
	 * */
	
	public void init(){
		
		StudentProfile model = new StudentProfile();	//Calls the StudentProfile class, which gets the data for the students and their courses
		view = new RootPane();
		new Controller(view, model);					//variables "view" and "model" being passed to the Controller class for flexible event-handling
		
	}
	
	/**
	 * The start() method starts the application. It has been setup to provide the desired output.
	 * 
	 * @param stage - a stage can be passed to the parameter to be used.
	 * */
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Student - Final Year Module Chooser");	//sets the stage title
		stage.getIcons().add(new Image("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAMrAAAAJDgxMDZhZWQ3LWE5OGQtNDBmOC1iNTk4LTRmZmVlMDk4NGMyMQ.jpg"));
		stage.setMinWidth(600); 								//sets the minimum width for the stage
		stage.setMinHeight(700);								//sets the maximum width for the stage
		stage.setScene(new Scene(view));						//sets the scene using the RootPane Class view
		stage.show();											//finally shows the output
		
	}
	
	public static void main(String[] args) {
		launch(args);	
	}
	
}
