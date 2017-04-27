package view.OverviewPane;

import java.time.LocalDate;

import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import model.Course;
import model.Module;
import model.StudentProfile;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The ResultPane Class displays information on the selected course and modules, chosen by the student.
 * */
public class ResultPane extends HBox{
	
	private TextArea results;		//results variable for outputting text into TextArea
	private String print;			//print variable for storing the template which is later used to output
	
	/**
	 * Default Constructor that will construct the ResultPane
	 * */
	
	public ResultPane(){
		
		this.getStylesheets().add(getClass().getResource("overview.css").toExternalForm());		//Looks for stylesheets
		this.getStyleClass().add("overviewpane");												//Adds a class overviewpane, which can be called in css
																								//to edit the style of the .overviewpane class
		
		print = "";																				//set to default ""
		
		results = new TextArea("Waiting for student to complete form ...");						//Instantiates new TextArea with Prompt Info
		results.setEditable(false);																//Cannot be edited/misused
		results.setId("results");																//sets id for css styling	
		results.setPrefSize(1000, 500);															//set size to 1000 w and 500 h
		
		this.setAlignment(Pos.CENTER);															//HBox Aligned Center and adds children
		this.getChildren().add(results);
		
	}
	
	/**
	 * isTextNull() returns the BooleanBinding, if the current overview value is equal to a given text then disable property
	 * 
	 * @return returns BooleanBinding which will set the binding for any node/property that is associated with BooleanBinding
	 * */
	public BooleanBinding isTextNull(){
		return results.textProperty().isEqualTo("Waiting for student to complete form ...");
	}
	
	/**
	 * setOutput() gets the data from the parameter value and sets the output according to the data
	 * 
	 * @param details - is the value passed to the function for finding the required data and outputting it in the given template
	 * */
	public void setOutput(StudentProfile details){
		
		Course course = details.getCourse();							//gets details data and stores each corresponding data 
		String pnum = details.getpNumber();								//to the appropriate variables
		String fname = details.getStudentName().getFirstName();
		String lname = details.getStudentName().getFamilyName();
		String email = details.getEmail();
		LocalDate date = details.getDate();
		
		print = "Course: " + course + "\n";								//print variable starts building the appropriate text 
		print += "P-Number: " + pnum + "\n";							//for displaying text with the data from the details
		print += "Firstname: " + fname + "\n";
		print += "Lastname: " + lname + "\n";
		print += "Email: " + email + "\n";
		print += "Date Submitted: " + date + "\n\n";
		
		print += "Chosen Modules\n";
		print += "====================\n\n";
		
		for(Module m : details.getSelectedModules()){					//checks which module is mandatory and prints out yes or no
																		//depending on the result
			print += m.toString() + "\n";
			if(m.isMandatory() == true){
				print += "Mandatory Course: " + "Yes\n";
			}else{
				print += "Mandatory Course: " + "No\n";
			}
			
			print += "Credits: " + m.getCredits() + "\n\n";
			
		}
		
		results.setText(print);											//finally sets the TextArea value to show print variable details
		
	}
	
	/**
	 * getOutput() returns the output of the TextArea
	 * 
	 * @return returns the result from TextArea
	 * */
	public String getOutput(){
		
		return print;
	}
	
	/**
	 * clearOverview() clears the result output and returns a meaningful text
	 * */
	public void clearOverview(){
		
		results.setText("Waiting for student to complete form ...");
		
	}
	
}
