package controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Module;
import model.StudentProfile;
import view.RootPane;
import view.MenuBar.ModuleChooserMenuBar;
import view.ModulePane.ButtonPane;
import view.ModulePane.CreditPane;
import view.ModulePane.ViewPane;
import view.OverviewPane.OPButtonPane;
import view.OverviewPane.ResultPane;
import view.StudentProfilePane.RegisterInputPane;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The Controller Class sets up the event handling for the RootPane Class and any underlying Classes for handling
 * user data as well as providing dynamic interaction and validation/error checking.
 * */

public class Controller {

	private ModuleChooserMenuBar mb;	//Sets up variable for ModuleChooserMenuBar Class
	private RootPane view;				//Sets up variable for RootPane Class
	private RegisterInputPane rip;		//Sets up variable for RegisterInputPane Class
	private ViewPane vp;				//Sets up variable for ViewPane Class
	private ButtonPane vbp;				//Sets up variable for ButtonPane Class
	private OPButtonPane obp;			//Sets up variable for OPButtonPane Class
	private ResultPane rp;				//Sets up variable for ResultPane Class
	private CreditPane cp;				//Sets up variable for CreditPane Class
	
	private StudentProfile model;		//Sets up variable for StudentProfile Class
	private Course[] courses;			//Sets up array variable for Course Class
	private int maxLimit;				//Sets up primitive type variable maxLimit
	
	/**
	 * Controller() constructor constructs and initialises the view and model passed through the main class
	 * and sets up the entire controller for event handling
	 * 
	 * @param view - initialises local view with the passed view parameter
	 * @param model - initialises local model with the passed model parameter
	 * */
	
	public Controller(RootPane view, StudentProfile model){
		
		this.view = view;		//initialises local view with the passed view parameter
		this.model = model;		//initialises local model with the passed model parameter
		
		maxLimit = 120;			//refers to the maximum credit limit for each course, it can be changed in the future
		
		rip = view.getStudentProfilePane().getRegisterInputPane();	//refers to the 1st tab in GUI, it gets the 
																	//RegisterInputPane() through the view variable and initialises local variable
		
		try {
			rip.populateComboBox(setupAndGetCourses());				//uses the try and catch method to populate comboboxes of the RegisterInputPane()
		}catch (FileNotFoundException e) {							//which is done so using the coursedata.txt file and can be edited for adding 
			e.printStackTrace();									//further courses and modules
		}
		
		vp = view.getModulesPane().getViewPane();					//refers to the 2nd tab in GUI, it gets the ViewPane Class through the view variable and initialises local variable
		vbp = view.getModulesPane().getButtonePane();				//refers to the 2nd tab in GUI, it gets the ButtonPane Class through the view variable and initialises local variable
		obp = view.getOverviewPane().getButtonPane();				//refers to the 3rd tab in GUI, it gets the OPButtonPane Class through the view variable and initialises local variable
		rp = view.getOverviewPane().getResultPane();				//refers to the 3rd tab in GUI, it gets the ResultPane Class through the view variable and initialises local variable
		cp = view.getModulesPane().getCreditPane();					//refers to the 2nd tab in GUI, it gets the CreditPane Class through the view variable and initialises local variable
		mb = view.getMenuBar();										//refers to the MenuBar in GUI, it gets the ModuleChooserMenuBar Class through the view variable and initialises local variable
		
		vp.setMaxLimit(maxLimit);									//sets the credits limits in the ViewPane Class to keep track of
		cp.setMaxLimit(maxLimit);									//sets the credits limits in the CreditPane Class to keep track of
		
		try {
			courses = this.setupAndGetCourses();					//sets up courses through setupAndGetCourses() method and passed to courses variable for checking compulsory modules in the ViewPane Class
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.attachBindings();										//Attaches all the Bindings to different classes, provided by the Controller Class
		this.attachEventHandlersorListeners();						//Attaches all the EventHandlers/Listeners to different classes, provided by the Controller Class
		
	}
	
	/**
	 * attachBindings() method simply attaches all the bindings to methods found in the different Pane Classes, for dynamic changes and validation
	 * */
	private void attachBindings(){
		
		rip.createProfileBtnDisableBind(rip.areFieldsNull());		//sets button in 1st tab to be disabled until all Fields are not Null and they meet the requirements
		vbp.addBtnDisableBind(cp.isMax());							//sets add button in 2nd tab to be disabled if the max credit limit is reached 	
		vbp.submitBtnDisableBind(cp.isNotEqToMax());				//sets the submit button in 2nd tab to be disabled until the max credit limit is reached
		obp.overviewBtnDisableBind(rp.isTextNull());				//sets the "save overview" button in 3rd tab to be disabled if there is no user information
		
	}
	
	/**
	 * attachEventHandlersorListeners() method simply attaches all the event-handlers/listeners to methods found in the different Pane Classes, for dynamic changes and validation
	 * */
	private void attachEventHandlersorListeners(){
		
		cp.getCreditsField().textProperty().addListener((observable, oldValue, newValue) -> cp.setCredits(cp.getCredits()));	//sets the CreditPane to listen for changes to the value it holds
		
		rip.addCreateProfileHandler(new CreateProfileHandler());	//sets button in 1st tab to allow the student to create their profile if the validation/error checking phase is passed
		vbp.addAddHandler(new AddModuleHandler());					//allows a module from unselected observable list to be added to the selected list
		vbp.addRemoveHandler(new RemoveModuleHandler());			//removes a module from selected observable list and adds it back to unselected observable list
		vbp.addResetHandler(new ResetModuleHandler());				//resets the current selections and brings the view back to its initial start point
		vbp.addSubmit(new SubmitModuleHandler());					//submits the selected modules if the credit values adds up to the max credit limits
		mb.addSaveHandler(new SaveMenuHandler());					//saves the current state of the application
		mb.addLoadHandler(new LoadMenuHandler());					//loads the previous state of the application
		mb.addAboutHandler(new AboutHandler());						//displays information regarding the current program
		mb.addExitHandler(new ExitHandler());						//closes the application
		obp.addSaveOverviewHandler(new SaveOverviewHandler());		//saves the overview of the selected modules in detail
		
	}
	
	/**
	 * setupAndGetCourses() method allows the application to populate details of course and modules during the startup of the application.
	 * It provides the file "coursedata.txt" for manual adding/removing of course and module details, providing flexible and efficient means
	 * of populating details for the application to use.
	 * 
	 * @return returns array of courses to be populated
	 * */
	private Course[] setupAndGetCourses() throws FileNotFoundException{
		
		List<Course> course = new ArrayList<Course>();	//course list holds the details of any new course added to the application
		
		Course c;										//c will temporarily hold details of the current course provided in the file for adding to the application

        Scanner sc = new Scanner(new File("coursedata.txt"));	//sc loads the details from the file

        sc.useDelimiter("[,\r\n]+");							//delimiters are used to help the adding of modules to be much easier

        String id = sc.next();           						//id refers to the course title
        c = new Course(id);										//initiates a new Course titled "id"
        while (!id.equals("end of courses")) {   				//reads file until it reaches "end of courses"
        	
        	String modulecode = sc.next();						//modulecode stores the modulecode
        	String modulename = sc.next();						//modulename stores the modulename
        	int modulecredits = 0;								//modulecredits stores the credits
        	if(sc.hasNextInt()){
        		modulecredits = sc.nextInt();
        	}
        	boolean state = sc.nextBoolean();					//state checks whether the course is mandatory - true or not mandatory - false and stores the value
        
        	c.addModule(new Module(modulecode, modulename, modulecredits, state));	//adds all the details provided by the file to the temporary c variable
            
            String previd = id;									
            id = sc.next();
            
            if(!id.equals(previd)){								//previd refers to the id used previously and is later compared to the current id
            	course.add(c);									//if they dont match then the courses are different so the list adds a new course to its list
            	c = new Course(id);								//initiates new Course titled "id"
            }
            
        }
        sc.close();												//after reading the file, the scanner safely closes down

        Course[] courses = new Course[course.size()];			//new array is created of the size of the list which indicates the total number of courses in the list
        
        for(int i=0; i<courses.length; i++){					//loops through the length of the array and at each point adds the new course to the current index
        	courses[i] = course.get(i);
        }
		
		return courses;											//returns the array of courses to the Class populating the courses
		
	}
	
	/*
	 * CreateProfileHandler Inner Class works inside the Controller Class to provide event handling functionality
	 * to create the user profile.
	 * */
	private class CreateProfileHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e){
			
			if(!rip.validFields()){					//Validates fields, if all fields are validated then the student account will be created
				
			}else{
				model = rip.getStudentProfile();	//After fields are validated, the model variable holds information from the
													//RegisterInputPane Class as data to keep track of
				
				vp.clearModules();					//When the account has been created, by default it clears all panes to prevent possible
				cp.clearCreditPanes();				//bugs as well as keeping the entire application tidy
				rp.clearOverview();
				
				for(Module m : model.getCourse().getModulesOnCourse()){		//The foreach loop runs through the modules for the current course
																			//inside the model variable that are not mandatory and loads them
					if(m.isMandatory() == false){							//in the ViewPane Class and into the unselected observable list
						
						vp.addUnselectedModules(m);
						
					}
					
				}
				
				for(Module m : model.getCourse().getModulesOnCourse()){		//The foreach loop runs through the modules for the current course
																			//inside the model variable that are mandatory and loads them
					if(m.isMandatory() == true){							//in the ViewPane Class and into the selected observable list
						cp.addCredits(m.getCredits());
						vp.addMandatorySelectedModules(m);
					}
					
				}
				
				if(!vp.getContents().isEmpty()){							//This statement makes sure that the contents aren't empty
																			//if not empty then the foreach loop runs through the ViewPane Class contents
					for(Module m : vp.getContents()){						//which returns the selected modules and then the model variable will add it to it's
																			//selected modules list to keep track of all selected modules
						model.addSelectedModule(m);
						
					}
					
				}
				
				rip.clearStudentProfile();									//Once profile has been created, this following method will clear the 1st tab
				view.changeTab(1);											//Once createprofile button has been clicked on, the view is dynamicly changed to then 2nd tab
			}
			
		}
		
	}
	
	/*
	 * AddModuleHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to add modules to the ViewPane Class selected observable list
	 * */
	private class AddModuleHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e){
			
			cp.clearCreditPanes();										//before adding any modules, the CreditPane Class is first cleared, which resets the amount of credit
			rp.clearOverview();											//ResultPane Class is also cleared if students decide to edit their module choices
			
			if(cp.getCredits() >= 0 && cp.getCredits() < maxLimit){		//before adding any credits, the values should be less than the max credit limit
				
				vp.addUnselectedModuleToSelected();						//ViewPane Class adds modules to the selected list
				cp.addCredits(vp.getSelectedModuleCredits());			//After clearing the CreditPane Class, for each module added, the credits 
																		//will update the listener and the listener will display the correct credit points
				
				model.clearSelectedModules();							//Before updating the model variable, it is first cleared
				
				if(!vp.getContents().isEmpty()){						//Then it is checked if the ViewPane Class contents are empty or not
					
					for(Module m : vp.getContents()){					//Then for each selected module, it is added to the model variable
						model.addSelectedModule(m);
					}
					
				}
				
			}

		}
		
	}
	
	/*
	 * RemoveModuleHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to remove modules to the ViewPane Class unselected observable list
	 * */
	private class RemoveModuleHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e){
			
			cp.clearCreditPanes();										//before adding any modules, the CreditPane Class is first cleared, which resets the amount of credit
			rp.clearOverview();											//ResultPane Class is also cleared if students decide to edit their module choices
				
			if(cp.getCredits() >= 0 && cp.getCredits() <= maxLimit){	//before adding any credits, the values should be less than the max credit limit
				
				vp.removeSelectedModuleAddToUnselected(model, courses);	//ViewPane Class removes modules to the selected list and in order to do that, it requires model and courses
																		//so it can check for compulsory modules
				
				cp.removeCredits(vp.getSelectedModuleCredits());		//After clearing the CreditPane Class, for each module added, the credits 
																		//will update the listener and the listener will display the correct credit points
				
				
				model.clearSelectedModules();							//Before updating the model variable, it is first cleared
				
				if(!vp.getContents().isEmpty()){						//Then it is checked if the ViewPane Class contents are empty or not
					
					for(Module m : vp.getContents()){					//Then for each selected module, it is added to the model variable
						model.addSelectedModule(m);
					}
					
				}
				
			}
			
		}
		
	}
	
	/*
	 * ResetModuleHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to reset modules to the ViewPane Class initial startup phase
	 * */
	private class ResetModuleHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e){
				
			vp.clearModules();						//ViewPane Class is cleared
			cp.clearCreditPanes();					//CreditPane Class is cleared
			rp.clearOverview();						//ResultPane class is cleared because there is nothing to submit and the student needs to
													//select modules again
			
			for(Module m : model.getCourse().getModulesOnCourse()){		//Non mandatory modules are added back to the ViewPane Class after cleared
				
				if(m.isMandatory() == false){
					
					vp.addUnselectedModules(m);
					
				}
				
			}
			
			for(Module m : model.getCourse().getModulesOnCourse()){		//Mandatory modules are added back to the ViewPane Class after cleared
				
				if(m.isMandatory() == true){
						
					cp.addCredits(m.getCredits());						//CreditPane is also updated 
					vp.addMandatorySelectedModules(m);
					
				}
				
			}
			
		}
		
	}
	
	/*
	 * SubmitModuleHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to show the final result for the students and their chosen modules
	 * */
	private class SubmitModuleHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e){
			
			model.clearSelectedModules();			//Before submitting, the entire model is cleared of its Selected Modules
			
			if(!vp.getContents().isEmpty()){		//They are then updated to the current ViewPane Class, to keep track of recent changes
				
				for(Module m : vp.getContents()){
					model.addSelectedModule(m);
				}
			
				rp.setOutput(model);				//Once submitted, the ResultPane will show the choices made by the student
				
				view.changeTab(2);					//view is also dynamically changed to the 3rd tab
				
			}
			
			try {									//The try and catch clauses are for creating a new submit.txt file to keep track
													//of the submit state when the application closes. This is used for saving and loading
													//purposes to keep track of the previous state.
				
				PrintWriter out = new PrintWriter(new File("submit.txt"));
				out.println("Data has been submitted.");
				out.close();
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	/*
	 * SaveOverviewHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to save the current state of the overview and sets the output into a text file 
	 * */
	private class SaveOverviewHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e){
			
			try {				//The try and catch clauses are used to create a new File which will then set the output
								//from the ResultPane class into a File else it will throw an exception
				
				PrintWriter out = new PrintWriter(new File("overview.txt"));
				out.println(rp.getOutput());
				out.close();

				alertDialogBuilder("Information Dialog", "Save success", "Overview saved to overview.txt");		//Alert Dialog to inform the success of saving
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	/*
	 * SaveMenuHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to save the current state of the application
	 * */
	private class SaveMenuHandler implements EventHandler<ActionEvent>{
			
		public void handle(ActionEvent e) { 
			
			File olddat = new File("oldstudentprofile.dat");	//Two Files are created, to keep track of the previous and current state
			File newdat = new File("studentprofile.dat");		//One file is to check if in previous state, the application was submitted
			File submit = new File("submit.txt");
			
			if(submit.exists()){								//If the submit.txt exists then the program will save in studentprofile.dat
																//Represents that student previously submitted and the state was saved
				
				try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("studentprofile.dat"));) {	//Data is saved using the model variable which keeps track of
																														//the current changes
					oos.writeObject(model); 
					oos.flush();
					
					alertDialogBuilder("Information Dialog", "Save success", "Student Profile to studentprofile.dat");	//Alert Dialog to inform the success of saving
				}
				catch (IOException ioExcep){
					System.out.println("Error saving");
				}
				
				if(olddat.exists() || submit.exists()){		//If either of the file exists, they will be deleted. This is because when user loads
					olddat.delete();						//their data, we want them to load from oldstudentprofile.dat 
					submit.delete();
				}
				
			}else{
				
				try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("oldstudentprofile.dat"));) {		//same as above
					
					oos.writeObject(model); 
					oos.flush();
					
					alertDialogBuilder("Information Dialog", "Save success", "Student Profile to oldstudentprofile.dat");
				}
				catch (IOException ioExcep){
					System.out.println("Error saving");
				}
				
				if(newdat.exists()){						//If studentprofile.dat exists, it will be deleted. The profile will keep being loaded
					newdat.delete();						//from oldstudentprofile.dat until user submits again to keep track of the state.
				}
				
			}
			
		}
		
	}
	
	/*
	 * LoadMenuHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to load the previous state of the application
	 * */
	private class LoadMenuHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			/*
			 * ERROR IS THROWN WHEN LOADING NULL DATA !
			 * */
			
			File olddat = new File("oldstudentprofile.dat");		//Loads two files
			File newdat = new File("studentprofile.dat");
			
			if(olddat.exists()){		//If oldstudentprofile.dat exists, data will be loaded from it
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("oldstudentprofile.dat"));) {
					
					model = (StudentProfile) ois.readObject(); 	
					alertDialogBuilder("Information Dialog", "Load success", "Student Profile from oldstudentprofile.dat"); //Alert Dialog to inform the success of loading
					
		        }
		        catch (IOException ioExcep){
		            System.out.println("Error loading");
		        }
				catch (ClassNotFoundException c) {
		            System.out.println("Class Not found");
		        }	
			}else if(newdat.exists()){ //If studentprofile.dat exists, data will be loaded from it
				
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("studentprofile.dat"));) {
					
					model = (StudentProfile) ois.readObject(); 	
					alertDialogBuilder("Information Dialog", "Load success", "Student Profile from studentprofile.dat"); //Alert Dialog to inform the success of loading
					
		        }
		        catch (IOException ioExcep){
		            System.out.println("Error loading");
		        }
				catch (ClassNotFoundException c) {
		            System.out.println("Class Not found");
		        }
				
			}

			rip.clearStudentProfile();		//Clears all Panes before loading
			vp.clearModules();
			rp.clearOverview();
			cp.clearCreditPanes();
			
			rip.loadRIP(model);				//Loads previous data into the Panes
			vp.loadVP(model);
			
			for(Module m : model.getSelectedModules()){		//Updates the Listener to keep track of the previous credits
				cp.addCredits(m.getCredits());
			}
			
			if(olddat.exists()){							//If previously saved state did not submit then no output is shown in the ResultPane
															//Otherwise output is shown
			}else if(newdat.exists()){
				rp.setOutput(model);
			}
			
			
			view.changeTab(0);								//After loading, view is changed back to the 1st tab
			
		}
	}
	
	/*
	 * AboutHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to inform the students of what the application is about
	 * */
	private class AboutHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) { //Uses the private function alertDialogBuilder() to show some output
			alertDialogBuilder("Application Information - ", " Student Module Chooser V1.0 ", "This program helps students to choose their"
					+ " courses more dynamically. With the help of the new credit display module, it enables students to track their points as well as"
					+ " providing boundary limits for their selection, preventing students from exceeding the required amount.\n\n"
					+ "It is a great program to help keep track of all the modules chosen by the student as well as providing ease through functions like"
					+ " saving and loading, doing all the manual labour for the students.");
		}
	}
	
	/*
	 * ExitHandler Inner Class works inside the Controller Class to provide event handling functionality 
	 * to close and terminate the current application
	 * */
	private class ExitHandler implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent e){
			
			Platform.exit();	//closes the current state of the application
			
		}
		
	}
	
	/*
	 * alertDialogBuilder() helps by building the required information passed through the parameters. Proving efficient 
	 * for different types of alert dialogs.
	 * */
	private void alertDialogBuilder(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(content);
    	alert.showAndWait();
	}
	
}

