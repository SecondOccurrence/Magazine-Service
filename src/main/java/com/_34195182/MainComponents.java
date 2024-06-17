/*
 * -- MainComponents.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Functions used to create Main class javafx components
 *
 */

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

/**
 * Holds information for JavaFX components which defines the main program component layout
 *
 * @author Josh Smith - 34195182
 *
 */
public class MainComponents {
    /**
     * Container to hold the heading
     */
    private VBox headingContainer;

    /**
     * Buttons belonging to the heading container
     */
    private RadioButton viewButton, createButton, editButton;

    /**
     * Toggle Group associated with the heading buttons
     */
    private ToggleGroup toggleGroup;

    /**
     * The container which will hold the preview of a class
     */
    private VBox previewContainer;
    
    /**
     * Buttons to modify a class in the preview
     */ 
    private Button saveChanges, deleteInfo;

    /**
     * @return Initialises all components
     */
    public MainComponents() {
        headingContainer = initHeadingContainer();
        previewContainer = initPreviewContainer();

        toggleGroup = new ToggleGroup();
        viewButton.setToggleGroup(toggleGroup);
        createButton.setToggleGroup(toggleGroup);
        editButton.setToggleGroup(toggleGroup);
    }

    /**
     * @return retrieves the heading container
     */
    public VBox getHeadingContainer() {
        return headingContainer;
    }

    /**
     * @return retrieves the view button in the heading container
     */
    public RadioButton getViewButton() {
        return viewButton;
    }

    /**
     * @return retrieves the create button in the heading container
     */
    public RadioButton getCreateButton() {
        return createButton;
    }

    /**
     * @return retrieves the edit button in the heading container
     */
    public RadioButton getEditButton() {
        return editButton;
    }

    /**
     * @return retrieves the container holding the class preview
     */
    public VBox getPreviewContainer() {
        return previewContainer;
    }

    /**
     * @return retrieves the save button in the class preview
     */
    public Button getSaveButton() {
        return saveChanges;
    }

    /**
     * @return retrieves the delete button in the class preview
     */
    public Button getDeleteButton() {
        return deleteInfo;
    }

    /**
     * @param the button to add an event listener to
     * @param the event listenr
     *
     * @return the event listener is added to the button
     */
    public void addButtonEvent(ButtonBase button, EventHandler<ActionEvent> event) {
        button.setOnAction(event);
    }

    /**
     * @return initialises the heading container
     */
    private VBox initHeadingContainer() {
        VBox container = new VBox();

        Label magazineHeading = new Label("Magazine Service");
        magazineHeading.getStyleClass().add("header-title");

        viewButton = new RadioButton("View");
        viewButton.getStyleClass().add("header-button");
        createButton = new RadioButton("Create");
        createButton.getStyleClass().add("header-button");
        editButton = new RadioButton("Edit");
        editButton.getStyleClass().add("header-button");
        HBox buttonContainer = new HBox(viewButton, createButton, editButton);
        buttonContainer.setSpacing(15);
        buttonContainer.setPadding(new Insets(15, 0, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);

        container = new VBox(magazineHeading, buttonContainer);
        container.setPadding(new Insets(25, 0, 40, 0));
        container.setAlignment(Pos.CENTER);

        return container;
    }

    /**
     * @return initialises the preview container
     */
    private VBox initPreviewContainer() {
        VBox container = new VBox();

        Label heading = new Label("Information Panel");

        saveChanges = new Button("Save");
        deleteInfo = new Button("Delete Info");

        HBox editingContainer = new HBox(saveChanges, deleteInfo);

        container = new VBox(heading, editingContainer);
        container.setPadding(new Insets(30, 30, 30, 30));
        container.setAlignment(Pos.CENTER);

        return container;
    }
}
