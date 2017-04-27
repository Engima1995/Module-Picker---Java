package view.MenuBar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

/**
 * @author Sukhwinder Singh - P14184295
 * 
 * The ModuleChooserMenuBar Class extends MenuBar, providing the functionality of having a Menu Bar
 * at the top of the GUI. It is used like any other Menu Bar previously encountered, with the basic
 * controls like File which contains (save, load, exit) and Help which contains (about).
 * */

public class ModuleChooserMenuBar extends MenuBar{
	
	private MenuItem load, save, exit, about;		//MenuItems that the Menu Bar will contain
	
	/**
	 * Default Constructor that will construct the menu bar
	 * */
	public ModuleChooserMenuBar(){
		
		makeMenuBar();								//calls the makeMenuBar() function to create the Menu Bar.
		
	}
	
	/**
	 * makeMenuBar() creates the menu bar. It is good to have it in a function due to the nature of adding
	 * more menu items in the future.
	 * */
	public void makeMenuBar(){
		
		this.getStylesheets().add(getClass().getResource("menu.css").toExternalForm());		//Looks for stylesheets	
		this.getStyleClass().add("menu");													//Adds a class menu, which can be called in css
																							//to edit the style of the .menu class
		
		Menu menu;																			//sets up menu variable to hold menuitems
		
		menu = new Menu("_File");
		
		load = new MenuItem("_Load");														//load menuitem is added along with a shortcut
		load.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));					//for quick access. It is then added to the menu
		menu.getItems().add(load);
		
		save = new MenuItem("_Save");														//save menuitem is added along with a shortcut
		save.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));					//for quick access. It is then added to the menu
		menu.getItems().add(save);
		
		menu.getItems().add(new SeparatorMenuItem());										//Adds a new menuitem - separator which separates the load and save from exit
		
		exit = new MenuItem("E_xit");														//exit menuitem is added along with a shortcut
		exit.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));					//for quick access. It is then added to the menu
		menu.getItems().add(exit);
		
		this.getMenus().add(menu);
		
		menu = new Menu("_Help");															//new menu is created which addes about menuitem along
																							//with a shortcut for quick access. It is then added to the menu
		about = new MenuItem("_About");
		about.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
		menu.getItems().add(about);

		this.getMenus().add(menu);
		
	}
	
	/**
	 * addLoadHandler() function is used for passing EventHandling functionality to the Menu, for loading previous state
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addLoadHandler(EventHandler<ActionEvent> handler) {
		load.setOnAction(handler);
	}
	
	/**
	 * addSaveHandler() function is used for passing EventHandling functionality to the Menu, for saving current state
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addSaveHandler(EventHandler<ActionEvent> handler) {
		save.setOnAction(handler);
	}

	/**
	 * addExitHandler() function is used for passing EventHandling functionality to the Menu, for exiting from the application
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addExitHandler(EventHandler<ActionEvent> handler) {
		exit.setOnAction(handler);
	}

	/**
	 * addAboutHandler() function is used for passing EventHandling functionality to the Menu, for displaying information about the application
	 * 
	 * @param handler - ActionEvent is passed, setOnAction is then called to apply this event handler
	 * */
	public void addAboutHandler(EventHandler<ActionEvent> handler) {
		about.setOnAction(handler);
	}
	
}
