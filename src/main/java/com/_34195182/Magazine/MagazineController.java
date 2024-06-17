/*
 * -- MagazineController.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/5/2024
 *
 * DESCRIPTION: Controller MVC component of the magazine class
 *
 */

package Magazine;

import Supplement.SupplementController;
import Customer.CustomerController;

import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import java.util.List;

/**
 * Holds crucial information for a magazine
 *
 * @author Josh Smith - 34195182
 */
public class MagazineController {
    /**
     * The magazine class to store details
     */
    private Magazine magazine;

    /**
     * The customer view's MVC component class
     */
    private MagazineView magazineView;

    /**
     * default constructor
     */
    public MagazineController() {
        magazine = new Magazine();
        magazineView = new MagazineView();
    }

    public MagazineController(String title, double cost, List<SupplementController> supplements, List<CustomerController> subscribers) {
        this.magazine = new Magazine(title, cost, supplements, subscribers);
        this.magazineView = new MagazineView();
        updateView();
    }

    /**
     * @return returns the list of supplements features in the magazine
     */
    public List<SupplementController> getMagazineSupplementList() {
        return magazine.getSupplementList();
    }

    /**
     * @return returns the list of customers that have subscribed to the magazine
     */
    public List<CustomerController> getMagazineCustomerList() {
        return magazine.getCustomerList();
    }

    /**
     * @return returns the weekly cost of the magazine
     */
    public double getMagazineWeeklyCost() {
        return magazine.getWeeklyCost();
    }

    /**
     * @param Supplement to add
     * @return Adds the supplement to the magazine's list. If the information already exists in the list, it will not be added
     */
    public void addMagazineSupplement(SupplementController s) {
        magazine.addSupplement(s);
    }

    /**
     * @param customer to add to the subscriber list
     * @return adds the customer to the magazine's subscriber list. If the information already exists, it will not be added
     */
    public void addMagazineCustomer(CustomerController c) {
        magazine.addCustomer(c);
    }

    public boolean updateTitle() {
        String title = magazineView.getTitle();
        magazine.setTitle(title);
        return true;
    }

    public boolean updateWeeklyCost() {
        boolean errorFlag = true;
        try {
            double cost = magazineView.getWeeklyCost();
            magazine.setWeeklyCost(cost);
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

    public void updateView() {
        magazineView.updateView(magazine.getTitle(), magazine.getWeeklyCost());
    }

    public MagazineController makeNewClass() {
        return new MagazineController(magazine.getTitle(), magazine.getWeeklyCost(), magazine.getSupplementList(), magazine.getCustomerList());
    }

    public VBox updatePreview() {
        return this.magazineView.updatePreview();
    }

    public RadioButton createButton() {
        return this.magazineView.createButton();
    }

    public void serializeMagazine(String destination) {
        try {
            this.magazine.serializeMagazine(destination);
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

    public void deSerializeMagazine(String filePath) {
        try {
            this.magazine.deSerializeMagazine(filePath);
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
}
