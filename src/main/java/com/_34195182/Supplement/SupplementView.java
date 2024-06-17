/*
 * -- SupplementView.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: View MVC component of the Supplement class
 *
 */

package Supplement;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

import javafx.geometry.Insets;

import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;

/**
 * View component, following the MVC architecture, of a Supplement class.
 *
 * This class presents supplement data to the user
 *
 * @author Josh Smith - 34195182
 */

public class SupplementView {
    Label nameHeading, costHeading;
    private Region spacer1, spacer2;
    
    TextField nameValue, costValue;

    /**
     * @return default constructor
     */
    public SupplementView() {
        nameHeading = new Label("Name:");
        spacer1 = new Region();
        nameValue = new TextField();
        nameValue.setEditable(false);
        costHeading = new Label("Weekly Cost:");
        spacer2 = new Region();
        costValue = new TextField();
        costValue.setEditable(false);
    }

    /**
     * @return constructor
     */
    public SupplementView(String name, double cost) {
        nameHeading = new Label("Name:");
        nameValue = new TextField(name);
        nameValue.setEditable(false);
        costHeading = new Label("Weekly Cost:");
        costValue = new TextField(Double.toString(cost));
    }


    /**
     * Validates the name in the view and returns it
     *
     * @return returns the name of the supplement
     * @throws IllegalArgumentException
     */
    public String getName() throws IllegalArgumentException {
        String name = nameValue.getText();
        validateName(name);
        return name;
    }

    /**
     * Validates the cost in the view and returns it
     *
     * @return returns the cost of the supplement
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    public double getCost() throws NumberFormatException, IllegalArgumentException {
        double cost = Double.parseDouble(costValue.getText());
        if(cost < 0) {
            throw new IllegalArgumentException();
        }
        return cost;
    }

    /**
     * @param name to assign to the javafx component
     * @param cost to assign to the javafx component
     *
     * @return updates view values to parameters
     */
    public void updateView(String name, double cost) {
        nameValue.setText(name);
        costValue.setText(String.valueOf(cost));
    }

    /**
     *
     * @param name to assign to the javafx component
     * @param cost to assign to the javafx component
     *
     * @return updates view to parameters and returns the view
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    public VBox updatePreview(String name, double cost) throws NumberFormatException, IllegalArgumentException {
        updateView(name, cost);
        HBox nameContainer = new HBox(nameHeading, spacer1, nameValue);
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox costContainer = new HBox(costHeading, spacer2, costValue);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        VBox container = new VBox(nameContainer, costContainer);
        container.setSpacing(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        return container;
    }

    /**
     * @param name to set the button
     *
     * @return returns a button to refer to this supplement
     */
    public RadioButton createButton(String buttonName) { 
        RadioButton button = new RadioButton(buttonName);
        return button;
    }

    /**
     * @param editability of the values
     *
     * @return changes the editability of the components
     */
    public void setEditable(boolean editable) {
        nameValue.setEditable(editable);
        costValue.setEditable(editable);
    }

    /**
     * @param name to validate
     * @return if the string:
     * - is empty OR has a length > 50
     * - has any whitespace or digit
     *   function will return false
     * if not, it will return true
     * @throws IllegalArgumentException
     */
    private void validateName(String str) throws IllegalArgumentException{
        int SIZE = str.length();

        if(str.isEmpty() || SIZE > 50) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < SIZE; i++) {
            if(Character.isWhitespace(str.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
    }
}
