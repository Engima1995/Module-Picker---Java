package view.StudentProfilePane;

import java.time.LocalDate;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Course;
import model.Name;
import model.StudentProfile;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * ViewPane Class provides 2 observable lists which indicate unselected and selected modules. 
 * Each list is updated when items are added or removed.
 * */
public class RegisterInputPane extends GridPane{
	
	private ComboBox<Course> cboCourses;											//cboCourses populates combobox with course information
	private TextField txtPNum, txtFName, txtLName, txtEmail;						//TextFields represents student profile
	private DatePicker dpSubDate;													//dpSubDate is the submission date
	private Button btn;																//btn is used to createprofile
	private Label lblCourse, lblPNum, lblFName, lblLName, lblEmail, lblSubDate;		//Labels represent meaningful text that go with the different nodes
	private Text actiontarget;														//actiontarget displays validation/error text
	
	/**
	 * Default Constructor allows the creation of the RegisterInputPane.
	 * */
	public RegisterInputPane(){
		
		this.getStylesheets().add(getClass().getResource("rip.css").toExternalForm());		//Looks for stylesheets
		this.getStyleClass().add("inputpane");												//Adds a class inputpane, which can be called in css
																							//to edit the style of the .inputpane class
		
		this.setVgap(15);																	//sets Vertical and Horizontal Gaps
		this.setHgap(20);																	//sets Insets and GridPane is aligned Centered
		this.setPadding(new Insets(20));
		this.setAlignment(Pos.CENTER);
		
		ColumnConstraints column0 = new ColumnConstraints();								//ColumnConstraints help align columns
		column0.setHalignment(HPos.RIGHT);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.NEVER);
		
		this.getColumnConstraints().addAll(column0, column1);								//GridPane adds columnconstraints
		
		addLabels();																		//calls addLabels() function to add Labels
		
		ObservableList<Course> courses = FXCollections.observableArrayList();				//ObservableList of type Course is used to
		cboCourses = new ComboBox<Course>(courses);											//update/populate courses
		cboCourses.setPrefSize(150, 10);													//size is set 150 w and 10 h
		cboCourses.setVisibleRowCount(2);													//visible row count set and id used for css styling
		cboCourses.setId("combob");															//prompt shown 
		cboCourses.setPromptText("Choose a course ...");
		
		addTextFields();																	//calls addTextFields() function to add TextFields
		
		dpSubDate = new DatePicker();														//dpSubDate is instantiated and set Editable is set
		dpSubDate.setEditable(false);														//to false so it cant be edited/misued and size is 200 w and 10 h
		dpSubDate.setPrefSize(200, 10);														//id is set for css styling and prompt is shown
		dpSubDate.setId("datep");
		dpSubDate.setPromptText("Pick a date ...");
		
		actiontarget = new Text();															//actiontarget is instantiated but no value is set and color is RED
        actiontarget.setFill(Color.RED);
		
		btn = new Button("Create Profile");													//btn is instantiated and set to "Create Profile" and size is 120 w and 20 h
		btn.setPrefSize(120, 20);
		
		btn.setDisable(true);																//btn is disabled for binding functionality
		
		addChildren();																		//calls addChildren() function to add all childrens
		
	}
	
	/*
	 * addLabels() is used for adding labels to each label variable without cluttering the default constructor
	 * */
	private void addLabels(){
		
		lblCourse = new Label("Courses: ");
		lblPNum = new Label("P-Number: ");
		lblFName = new Label("Firstname: ");
		lblLName = new Label("Lastname: ");
		lblEmail = new Label("E-Mail: ");
		lblSubDate = new Label("Submission Date: ");
		
	}
	
	/*
	 * addTextFields() is used for adding TextFields to each TextField variable without cluttering the default constructor
	 * */
	private void addTextFields(){
		
		txtPNum = new TextField();
		txtPNum.setPrefSize(200, 10);
		txtPNum.setId("textf");
		txtPNum.setPromptText("Enter your P-Number ...");
		
		txtFName = new TextField();
		txtFName.setPrefSize(200, 10);
		txtFName.setId("textf");
		txtFName.setPromptText("Enter your Firstname ...");
		
		txtLName = new TextField();
		txtLName.setPrefSize(200, 10);
		txtLName.setId("textf");
		txtLName.setPromptText("Enter your Lastname ...");
		
		txtEmail = new TextField();
		txtEmail.setPrefSize(200, 10);
		txtEmail.setId("textf");
		txtEmail.setPromptText("Enter your E-mail ...");
		
	}
	
	/*
	 * addChildren() is used for adding children to GridPane without cluttering the default constructor
	 * */
	private void addChildren(){
		
		this.add(lblCourse, 0, 0);
		this.add(cboCourses, 1, 0);
		this.add(lblPNum, 0, 1);
		this.add(txtPNum, 1, 1);
		this.add(lblFName, 0, 2);
		this.add(txtFName, 1, 2);
		this.add(lblLName, 0, 3);
		this.add(txtLName, 1, 3);
		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);
		this.add(lblSubDate, 0, 5);
		this.add(dpSubDate, 1, 5);
		this.add(actiontarget, 1, 6);
		this.add(btn, 1, 7);
		
	}
	
	/**
	 * validFields() checks for each node and validates the values, if true then everything has been passed else returns false and
	 * user has to correct issues before proceeding.
	 * 
	 * @return returns a boolean indicating whether fields are valid or not
	 * */
	public boolean validFields(){
		
		String str = "";
		
		if(!(txtPNum.getText().startsWith("P")) || !(txtPNum.getText().length() == 9)){
			str += "Make sure PNumber starts with the letter P in capitals and ends with 8 characters.\n";
		}
		
		if(txtFName.getText().length() < 3){
			str += "Make sure firstname is greater than 3 letters or name is not in alphabets.\n";
		}
		
		if(txtLName.getText().length() < 3){
			str += "Make sure lastname is greater than 3 letters or name is not in alphabets.\n";
		}
		
		if(!isValidEmailAddress(txtEmail.getText())){
			str += "Make sure the email address is valid.\n";
		}
		
		if(!str.isEmpty()){
			actiontarget.setText(str);
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * isValidEmailAddress() checks for whether or not email is valid, if true then everything has been passed else returns false and
	 * user has to correct issues before proceeding.
	 * 
	 * @return returns a boolean indicating whether email is valid or not
	 * */
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	/**
	 * createProfileBtnDisableBind() sets the binding of the button to the value passed through the parameter
	 * 
	 * @param value - BooleanBinding value, which will set the binding for the button and it will enable and disable accordingly
	 * */
	public void createProfileBtnDisableBind(BooleanBinding value){
		btn.disableProperty().bind(value);
	}
	
	/**
	 * areFieldsNull() returns the BooleanBinding, if the current node values match that of the function properties
	 * 
	 * @return returns BooleanBinding which will set the binding for any node/property that is associated with BooleanBinding
	 * */
	public BooleanBinding areFieldsNull(){
		return   cboCourses.valueProperty().isNull()
				.or(txtPNum.textProperty().isEmpty()
				.or(txtFName.textProperty().isEmpty()
				.or(txtLName.textProperty().isEmpty())
				.or(txtEmail.textProperty().isEmpty()
				.or(dpSubDate.valueProperty().isNull()))));
	}
	
	/**
	 * addCreateProfileHandler() function is used for passing EventHandling functionality to the RegisterInputPane, for gathering user information and creating a profile
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addCreateProfileHandler(EventHandler<ActionEvent> handler){
		
		btn.setOnAction(handler);
		
	}
	
	/**
	 * populateComboBox() will use the parameter array value to populate the ComboBox within this Class to display
	 * information on courses
	 * 
	 * @param courses - is the array value used to populate information of type Course
	 * */
	public void populateComboBox(Course[] courses){
		
		cboCourses.getItems().addAll(courses);
		
	}
	
	/**
	 * getStudentProfile() returns all the details within the nodes 
	 * 
	 * @return returns details of nodes
	 * */
	public StudentProfile getStudentProfile(){
		
		Course course = cboCourses.getSelectionModel().getSelectedItem();
		String pnum = txtPNum.getText();
		String fname = txtFName.getText();
		String lname = txtLName.getText();
		String email = txtEmail.getText();
		LocalDate date = dpSubDate.getValue();
		
		StudentProfile sp = new StudentProfile();
		
		sp.setCourse(course);
		sp.setpNumber(pnum);
		sp.setStudentName(new Name(fname, lname));
		sp.setEmail(email);
		sp.setDate(date);
		
		return sp;
		
	}
	
	/**
	 * clearStudentProfile() clears all nodes within this class
	 * */
	public void clearStudentProfile(){
		
		cboCourses.valueProperty().set(null);
		txtPNum.setText("");
		txtFName.setText("");
		txtLName.setText("");
		txtEmail.setText("");
		dpSubDate.setValue(null);
		actiontarget.setText("");
		
	}
	
	/**
	 * loadRIP() is used for loading student data. The parameter value is the data passed for loading the previous
	 * state of the program.
	 * 
	 * @param details - is the value for the RegisterInputPane to load previous state
	 * */
	public void loadRIP(StudentProfile details){
		
		Course course = details.getCourse();
		String pnum = details.getpNumber();
		String fname = details.getStudentName().getFirstName();
		String lname = details.getStudentName().getFamilyName();
		String email = details.getEmail();
		LocalDate date = details.getDate();
		
		cboCourses.setValue(course);
		txtPNum.setText(pnum);
		txtFName.setText(fname);
		txtLName.setText(lname);
		txtEmail.setText(email);
		dpSubDate.setValue(date);
		
	}
	
}
