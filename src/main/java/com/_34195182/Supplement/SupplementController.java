/*
 * -- SupplementController.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Controller MVC component of the Supplement class
 *
 */

package Supplement;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.VBox;

import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.lang.ClassNotFoundException;

import java.io.Serializable;

/**
 * Supplement component, following the MVC architecture, of a Supplement class.
 *
 * This class controllers interactions with a Supplement class and its view
 *
 * @author Josh Smith - 34195182
 */
public class SupplementController implements Serializable {
    /**
     * Supplement class to store relevant details
     */
    private Supplement supplement;
    
    /**
     * Supplement view MVC component class
     */
    private transient SupplementView supplementView;

    /**
     * Default constructor
     * @return calls default constructor for member variables
     */
    public SupplementController() {
        supplement = new Supplement();
        supplementView = new SupplementView();
    }

    /**
     * @return returns the supplement UID
     */

    public String getUID() {
        return supplement.getUID();
    }

    /**
     * @return returns supplement's name
     */
    public String getName() {
        return supplement.getName();
    }

    /**
     * @return returns supplement's weekly cost
     */
    public double getWeeklyCost() {
        return supplement.getWeeklyCost();
    }

    /**
     * Assigns the name to the supplement without validation.
     * As the controller assigns value through interaction with the view only,
     * there is no method to assign a value without GUI interaction
     *
     * @param the name to set
     *
     * @return assigns the supplements name to the parameter
     */
    public void forceSetName(String name) {
        supplement.setName(name);
    }

    /**
     * Assigns the cost to the supplement without validation.
     * As the controller assigns value through interaction with the view only,
     * there is no method to assign a value without GUI interaction
     *
     * @param the cost to set
     *
     * @return assigns the supplements cost to the parameter
     */
    public void forceSetWeeklyCost(double cost) {
        supplement.setWeeklyCost(cost);
    }

    /**
     * @return updates the view to match with supplement variables
     */
    public void updateView() {
        supplementView.updateView(supplement.getName(), supplement.getWeeklyCost());
    }

    /**
     * @param name to assign
     * @param cost to assign
     *
     * @return updates the view to match with the parameters
     */
    public void updateView(String name, double cost) {
        supplementView.updateView(name, cost);
    }

    /**
     * Retrieves the name from the view, and assigns that to the model.
     *
     * @return assigns view's stored name to supplement's name.
     * However, if the name is empty or longer than 50 characters or contains whitespaces,
     * name will not be set
     */
    public boolean updateName() {
        boolean errorFlag = true;
        try {
            String name = supplementView.getName();
            supplement.setName(name);
        }
        catch(IllegalArgumentException e) {
            errorFlag = false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error on save");
            alert.setHeaderText(null);
            alert.setContentText("An error occured. The name of the supplement must not include special characters or spaces. Please try again.");
            alert.showAndWait();
        }
        return errorFlag;
    }

    /**
     * Retrieves the weekly cost from the view, and assigns that to the model.
     *
     * @return assigns view's stored cost to supplement's weekly cost.
     * However, if the cost is < 0, cost will not be set
     */
    public boolean updateWeeklyCost() {
        boolean errorFlag = true;
        try {
            double cost = supplementView.getCost();
            supplement.setWeeklyCost(cost);
        }
        catch(NumberFormatException e) {
            errorFlag = false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error on save");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred. The weekly cost must be a number with an optional decimal. Please try again.");
            alert.showAndWait();
        }
        catch(IllegalArgumentException e) {
            errorFlag = false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error on save");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred. The weekly cost must be a positive number. Please try again.");
            alert.showAndWait();
        }
        return errorFlag;
    }

    /**
     * @param the path to the place to store the data file
     * @return stores the class data into a .dat file stored in the destination variable path
     */
    public void serializeSupplement(String destination) {
        try {
            this.supplement.serializeSupplement(destination);
        }
        catch(IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Serialization");
            alert.setHeaderText(null);
            alert.setContentText("Error on serialization");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * @param location of the file to read from
     * @param the name of the file to read
     * @return the data file information is stored in the supplement class
     */
    public void deSerializeSupplement(String filePath) {
        try {
            this.supplement.deSerializeSupplement(filePath);
        }
        catch(FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("DeSerialization");
            alert.setHeaderText(null);
            alert.setContentText("Error on deserialization. File not found.");
            alert.showAndWait();
        }
        catch(IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("DeSerialization");
            alert.setHeaderText(null);
            alert.setContentText("Error on deserialization. IO Error");
            alert.showAndWait();
        }
        catch(ClassNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("DeSerialization");
            alert.setHeaderText(null);
            alert.setContentText("Error on deserialization. Class cannot be found");
            alert.showAndWait();
        }
    }

    /**
     * @param Object to compare to
     * @return returns true if object's supplement member variables are equal
     */
    public boolean equals(SupplementController comparison) {
        boolean isEqual = false;

        boolean nameMatch = this.supplement.getName().equals(comparison.supplement.getName());
        boolean priceMatch = this.supplement.getWeeklyCost() == comparison.supplement.getWeeklyCost();
        if(nameMatch && priceMatch) {
            isEqual = true;
        }

        return isEqual;
    }

    /**
     * @return returns the view for the supplement
     */
    public VBox updatePreview() {
        return supplementView.updatePreview(supplement.getName(), supplement.getWeeklyCost());
    }

    /**
     * @return returns a button for the supplement
     */
    public RadioButton createButton() {
        return supplementView.createButton(supplement.getName());
    }

    /**
     * @param boolean to assign
     * 
     * @return all view inputs's editability is set to the parameter
     */
    public void setEditable(boolean editable) {
        supplementView.setEditable(editable);
    }
}
