package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import view.OverviewPane.OverviewPane;
import view.MenuBar.ModuleChooserMenuBar;
import view.ModulePane.ModulesPane;
import view.StudentProfilePane.StudentProfilePane;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The RootPane Class sets up all the sub-pane classes into one single root.
 * It is a good approach to have sub-classes in one main class because it allows changing of one sub-class
 * whilst maintaining the overall state. It also gets rid of the unnecessary cluster as well as providing
 * a much more efficient way of editing the different panes.
 * */

public class RootPane extends BorderPane{
	
	private TabPane tp;					//TabPane - holds the 3 different Pane Classes in different tabs
	private ModuleChooserMenuBar mb;	//ModuleChooserMenuBar (MenuBar)
	private StudentProfilePane spp;		//StudentProfilePane - 1st tab, allows the creation of student profile data
	private ModulesPane mp;				//ModulesPane - 2nd tab, allows the adding/removing/reseting/submitting of modules as well as keeping track
										//of credits
	private OverviewPane op;			//OverviewPane - 3rd tab, allows the viewing of all modules selected by the student as well as providing 
										//a save option to save the overview
	
	/**
	 * Default Constructor that will construct the RootPane
	 * */
	public RootPane(){
		
		this.getStylesheets().add(getClass().getResource("root.css").toExternalForm());	//Looks for stylesheets
		this.getStyleClass().add("rootpane");											//Adds a class rootpane, which can be called in css
																						//to edit the style of the .rootpane class
		
		mb = new ModuleChooserMenuBar();												//Initiates the Classes
		spp = new StudentProfilePane();
		mp = new ModulesPane();
		op = new OverviewPane();
		tp = new TabPane();
		
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);							//sets the policy to unavailable, which will get rid of the functionality
																						//that allows the student to close the tabs
		
		Tab t1 = new Tab("Create Profile", spp);										//Each tab is set, holding data of the different Pane Class
		Tab t2 = new Tab("Select Modules", mp);
		Tab t3 = new Tab("Overview Selection", op);
		
		tp.getTabs().addAll(t1, t2, t3);												//tabs are added to the parent class
		
		this.setTop(mb);																//BorderPane then sets the Top and Centre
		this.setCenter(tp);
		
	}
	
	/**
	 * getMenuBar() returns the ModuleChooserMenuBar Pane to the calling class
	 * 
	 * @return returns ModuleChooserMenuBar
	 * */
	public ModuleChooserMenuBar getMenuBar(){											//returns ModuleChooserMenuBar() 
		return mb;
	}
		
	/**
	 * getStudentProfilePane() returns the StudentProfilePane to the calling class
	 * 
	 * @return returns StudentProfilePane
	 * */
	public StudentProfilePane getStudentProfilePane(){									//returns StudentProfilePane()
		return spp;
	}
	
	/**
	 * getModulesPane() returns the ModulesPane to the calling class
	 * 
	 * @return returns ModulesPane
	 * */
	public ModulesPane getModulesPane(){												//returns ModulesPane()
		return mp;
	}	
	
	/**
	 * getOverviewPane() returns the OverviewPane to the calling class
	 * 
	 * @return returns OverviewPane
	 * */
	public OverviewPane getOverviewPane(){												//returns OverviewPane()
		return op;
	}
	
	/**
	 * changeTab() allows the changing of tabs dynamically by setting the index
	 * 
	 * @param index - refers to the index value of the tab
	 */
	public void changeTab(int index) {													//allows tabs to be changed dynamically by setting the index
		tp.getSelectionModel().select(index);
	}
	
}
