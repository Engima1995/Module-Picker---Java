package view.ModulePane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import model.Course;
import model.Module;
import model.StudentProfile;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * ViewPane Class provides 2 observable lists which indicate unselected and selected modules. 
 * Each list is updated when items are added or removed.
 * */
public class ViewPane extends GridPane{
	
	private Label unselmod, selmod;								//unselmod and selmod labels for representing the 2 lists
	private ListView<Module> unselected, selected;				//ListView's unselected and selected are both used for selecting
	private ObservableList<Module> unselmodules, selmodules;	//ObservableList's are used to update the values within the 2 lists
	
	private int maxLimit;									    //max limit of credits, used for updating/validating 
	
	public ViewPane(){
		
		this.getStylesheets().add(getClass().getResource("modulepane.css").toExternalForm()); //Looks for stylesheets
		this.getStyleClass().add("modulepane");												  //Adds a class modulepane, which can be called in css
																							  //to edit the style of the .modulepane class
		
		maxLimit = 120;																		  //max limit set to 120 but can be changed through function call
		
		this.setHgap(20);																      //Sets Horizontal gap between the 2 lists
		
		unselmodules = FXCollections.observableArrayList();									  //ObservableLists are passed to ListViews
		unselected = new ListView<>(unselmodules);											  //so any updates can be monitored. Sizes are set
		unselected.getSelectionModel().select(0);											  //and default selects are set as well as the sizes of the ListViews.
		unselected.setId("unselectedList");
		unselected.setPrefSize(500, 600);
		
		selmodules = FXCollections.observableArrayList();
		selected = new ListView<>(selmodules);
		selected.getSelectionModel().select(0);
		selected.setId("selectedList");
		selected.setPrefSize(500, 600);
		
		unselmod = new Label("Unselected Modules");											  //Labels are instantiated
		selmod = new Label("Selected Modules");
		
		this.add(unselmod, 0, 0);															  //GridPane positions childrens
		this.add(unselected, 0, 1);
		
		this.add(selmod, 1, 0);
		this.add(selected, 1, 1);
		
		this.setAlignment(Pos.TOP_CENTER);													  //Everything is aligned at the Top and is Centered
		
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
	 * addUnselectedModules() adds the parameter value to the ObservableList, updating the state of the List
	 * Value that is passed is a non-mandatory module
	 * 
	 * @param module - is the value representing the module to be added into the ViewPane 
	 * */
	public void addUnselectedModules(Module module){
		
		unselmodules.add(module);						//adds the module to the list
		
		this.clearSelection();							//clears selection after adding
		
	}
	
	/**
	 * addMandatorySelectedModules() adds the parameter value to the ObservableList, updating the state of the List
	 * Value that is passed is a mandatory module
	 * 
	 * @param module - is the value representing the module to be added into the ViewPane 
	 * */
	public void addMandatorySelectedModules(Module module){
		
		selmodules.add(module);						   //adds the module to the list
		
		this.clearSelection();						   //clears selection after adding
		
	}
	
	/**
	 * addUnselectedModuleToSelected() adds a module from the Unselected ObservableList to the selected ObservableList
	 * */
	public void addUnselectedModuleToSelected(){
		
		if(!unselected.getSelectionModel().isEmpty()){	//Makes sure the SelectionModel is not empty before updating
			int credits = 0;						    //gets credits by adding up the modules in the selected list
			for(Module m : selmodules){				   
				credits += m.getCredits();
			}
		
			if(((credits + unselected.getSelectionModel().getSelectedItem().getCredits()) <= maxLimit)){	//before adding unselected module to the selected list
				selmodules.add(unselected.getSelectionModel().getSelectedItem());						    //the program checks if it is less than or equal to the maxlimit
				unselmodules.remove(unselected.getSelectionModel().getSelectedItem());				        //by adding previous credits with the selected item to check the value
			}else{																						    //if so then the unselected module is added to selected list else alert dialog is shown
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error Dialog");
		    	alert.setHeaderText("Invalid Adding of Values");
		    	alert.setContentText("Exceeding credit limits.");
		    	alert.showAndWait();
			}
		}
			
	}
	
	/**
	 * removeSelectedModuleAddToUnSelected() adds a module from the selected ObservableList to the unselected ObservableList
	 * 
	 * @param details - gets the data from the parameter passed
	 * @param courses - gets the courses populated for comparison with the details
	 * */
	public void removeSelectedModuleAddToUnselected(StudentProfile details, Course[] courses){
		
		if(!selected.getSelectionModel().isEmpty()){								//if selected item is not empty then check is it is compulsory
			if(this.isCompulsory(details, courses)){							    //if not compulsory then alert dialog is shown else the removing is done
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error Dialog");
		    	alert.setHeaderText("Invalid Removing of Values");
		    	alert.setContentText("Compulsory modules cannot be removed.");
		    	alert.showAndWait();
			}else{
				unselmodules.add(selected.getSelectionModel().getSelectedItem());
				selmodules.remove(selected.getSelectionModel().getSelectedItem());
			}
		}
	
	}
	
	/**
	 * getSelectedModuleCredits() returns the credits currently from the selected list
	 * 
	 * @return returns credits by adding up the number of selected items in the selected list
	 * */
	public int getSelectedModuleCredits(){
		
		int credits = 0;						//credits state is set to 0 each time function is called then adds credits
										        //with the number of items in the selected list then returns it
		if(!selmodules.isEmpty()){
			for(Module m : selmodules){
				credits += m.getCredits();
			}
		}
		
		return credits;
		
	}
	
	/**
	 * isCompulsory checks whether or not the modules are compulsory then returns true/false
	 * 
	 * @param details - gets the data from the parameter passed
	 * @param courses - gets the courses populated for comparison with the details
	 * 
	 * @return returns true/false depending on whether the modules are compulsory
	 * */
	public boolean isCompulsory(StudentProfile details, Course[] courses){
		
		Course c = null;		//sets the variable c of type course to null
		Module[] modules;		//creates array of modules
		int count = 0;			//sets up count, keeps track of mandatory modules
		
		for(int i=0; i<courses.length; i++){			//checks through the courses array and compares the Course title with the data provided
														//by the details, if matched then the variable c will store the value
			
			if(details.getCourse().getCourseName().equals(courses[i].getCourseName())){		
				c = courses[i];
			}
			
		}
		
		for(Module m : c.getModulesOnCourse()){			//for every module stored in the variable c of type course that is true (mandatory)
										                //the count will increase
			if(m.isMandatory() == true){
				count++;
			}
			
		}
		
		modules = new Module[count];					//modules array is initialised to the size of count
		count = 0;										//count is set to 0
		
		for(Module m : c.getModulesOnCourse()){
			
			if(m.isMandatory() == true){				//for every module that is mandatory, it is added to the modules array and count is incremented
				modules[count] = m;
				count++;
			}
			
		}
		
		for(int i=0; i<modules.length; i++){			//for every selected module, if it is equal to that of the modules array then it returns true
														//indicating that the module is compulsory and it cannot be removed
			if(selected.getSelectionModel().getSelectedItem().getModuleCode().equals(modules[i].getModuleCode())){
				return true;
			}
			
		}
		
		return false;									//if the for loop above does not return true then that means the modules are not mandatory and can be removed
	
	}
	
	/**
	 * getContents() returns the modules within the selected list
	 * 
	 * @return returns selected modules
	 * */
	public ObservableList<Module> getContents(){
		
		return selmodules;
		
	} 
	
	/**
	 * clearModules() clears both observable lists
	 * */
	public void clearModules(){
		
		unselmodules.clear();
		selmodules.clear();
		
	}
	
	/**
	 * clearSelection() clears selection from both observable lists
	 * */
	public void clearSelection(){
		
		unselected.getSelectionModel().clearSelection();
		selected.getSelectionModel().clearSelection();
		
	}
	
	/**
	 * loadVP() is used for loading student data. The parameter value is the data passed for loading the previous
	 * state of the program.
	 * 
	 * @param details - is the value for the ViewPane to load previous state
	 * */
	public void loadVP(StudentProfile details){
		
		for(Module m : details.getCourse().getModulesOnCourse()){		//for every module in the details, add it to the corresponding
																	    //lists
			if(m.isMandatory() == true){
				selmodules.add(m);
			}else{
				unselmodules.add(m);
			}
			
		}
																		//for every module that was selected previously, add it to the 
		for(Module m : details.getSelectedModules()){					//selmodules list
			
			if(m.isMandatory() == false){
				unselmodules.remove(m);
				selmodules.add(m);
			}
			
		}
		
	}

}
