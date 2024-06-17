/*
 * -- ClassContainer.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * Contains javafx components that define the a class container
 *
 */

package Containers;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

import javafx.scene.control.Label;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.List;

import FileUtils.FileUtils;


/**
 * Template class for a class container to be used in the main program
 *
 * @author Josh Smith - 34195182
 */
public class ClassContainer<T> {
    /**
     * container for the class
     */
    protected VBox container;

    /**
     * container for heading components
     */
    protected HBox headingContainer;
    
    /**
     * import class from directory button
     */
    protected Button importButton;

    /**
     * refresh GUI to match class directory button
     */
    protected Button refreshButton;
    
    /**
     * container for all class buttons
     */
    protected VBox buttonsContainer;

    /**
     * toggle group to assign to all class buttons
     */
    protected ToggleGroup toggleGroup;

    /**
     * List of all the classes
     */
    protected List<T> list;

    /**
     * List of all the buttons for all classes. size must be identical to list
     */
    protected List<RadioButton> buttons;

    /**
     * @return constructor to initialise container components
     */
    public ClassContainer(ToggleGroup group) {
        Label heading = new Label("List of Customers");

        importButton = new Button("Import New Record");
        Region spacer = new Region();
        refreshButton = new Button("Refresh List");
        headingContainer = new HBox(importButton, spacer, refreshButton);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        toggleGroup = group;
        createClassList();
        buttonsContainer.setSpacing(5);
        buttonsContainer.setPadding(new Insets(5, 5, 5, 5));
        buttonsContainer.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(buttonsContainer);
        scrollPane.setPrefSize(200, 200);

        container = new VBox(heading, headingContainer, scrollPane);
        container.setSpacing(8);
        container.setAlignment(Pos.CENTER_LEFT);
    }

    /**
     * @return returns the class container
     */
    public VBox getContainer() {
        return container;
    }

    /**
     * @return returns the import button
     */
    public Button getImportButton() {
        return importButton;
    }

    /**
     * @return returns the refresh button
     */
    public Button getRefreshButton() {
        return refreshButton;
    }

    /**
     * @return returns the list of class buttons
     */
    public List<RadioButton> getButtons() {
        return buttons;
    }

    /**
     * @param toggle group
     *
     * @return assigns a new toggle group
     */
    public void setNewToggleGroup(ToggleGroup group) {
        toggleGroup = group;
    }

    /**
     * @param index of the button in the list to fire
     *
     * @return fires a button ideally to call its attached event handler
     */
    public void fireButton(int index) {
        buttons.get(index).fire();
    }

    /**
     * @param button to add event handler to
     * @param event to add
     *
     * @return add an event handler to a button
     */
    public void addButtonEvent(ButtonBase button, EventHandler<ActionEvent> event) {
        button.setOnAction(event);
    }

    /**
     * @param event handler to add
     *
     * @return adds the event handler to every button in the buttons list
     */
    public void addButtonEventToList(EventHandler<ActionEvent> event) {
        for(RadioButton button: buttons) {
            button.setOnAction(event);
        }
    }

    /**
     * @param editability of all editable components
     *
     * @return changes editability of the editable components
     */
    public void setEditable(boolean editable) {}

    /**
     * @param the new class to add
     *
     * @return adds a new class to the container. in turn adding its own button
     */
    public void addNewClass(T newClass) {}

    /**
     * @param index of the button to remove
     *
     * @return removes a class from the container
     */
    public void removeButton(int index) {}

    /**
     * @param index of the button to remove
     *
     * @return updates the classes values to match its view.
     *  Also updates its associated button
     */
    public void updateClass(int index) {}

    /**
     * @return serializes all classes in the list
     */
    public void serializeButtons() {}

    /**
     * @return initialises the class list
     */
    protected void createClassList() {}
}
