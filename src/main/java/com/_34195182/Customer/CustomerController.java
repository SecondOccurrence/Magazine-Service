/*
 * -- CustomerController.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Controller MVC component of the Customer class
 *
 */

package Customer;

import Supplement.SupplementController;

import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/**
 * Controller MVC component interface.
 *
 * Defines common functions used in any customer controller implementation
 *
 * @author Josh Smith - 34195182
 */
public interface CustomerController {
    /**
     * @return retrieves the unique identifier for the customer class
     */
    String getUID();

    /**
     * @return Returns the first name of the customer
     */
    String getFirstName();
    
    /**
     * @return Returns the last name of the customer
     */
    String getLastName();

    
    /**
     * @return Return the email address of the customer
     */
    String getEmailAddress();
    
    /**
     * @return returns the list of supplements the customer is subscribed to
     */
    List<SupplementController> getSubscribedSupplements();

    /**
     * Assigns the first name to the customer without validation.
     * As the controller assigns value through interaction with the view only,
     * there is no method to assign a value without GUI interaction
     *
     * @param the first name to set
     *
     * @return assigns the customers first name to the parameter
     */
    void forceSetFirstName(String str);

    /**
     * Assigns the last name to the customer without validation.
     * As the controller assigns value through interaction with the view only,
     * there is no method to assign a value without GUI interaction
     *
     * @param the last name to set
     *
     * @return assigns the customers last name to the parameter
     */
    void forceSetLastName(String str);

    /**
     * Assigns the email to the customer without validation.
     * As the controller assigns value through interaction with the view only,
     * there is no method to assign a value without GUI interaction
     *
     * @param the email to set
     *
     * @return assigns the customers email to the parameter
     */
    void forceSetEmailAddress(String str);

    /**
     * @return updates the view to match with customer variables
     */
    void updateView();

    /**
     * Retrieves the first name from the view, and assigns that to the model
     *
     * @return assigns view's stored first name to the customer's first name
     * Name will not be set if any exception is thrown and an alert is displayed
     */
    boolean updateFirstName();

    /**
     * Retrieves the last name from the view, and assigns that to the model
     *
     * @return assigns view's stored last name to the customer's last name
     * Name will not be set if any exception is thrown and an alert is displayed
     */
    boolean updateLastName();

    /**
     * Retrieves the email from the view, and assigns that to the model
     *
     * @return assigns view's stored email to the customer's email
     * Email will not be set if any exception is thrown and an alert is displayed
     */
    boolean updateEmailAddress();

    /**
     * Retrieves all subscribed supplements from the view, and assigns them to the model
     *
     * @return assigns view's stored subscribed supplements to the customer's subscribed supplements
     */
    boolean updateSubscribedSupplements();

    /**
     * @param Supplement to add
     * @return Adds the supplement to the customer's list. If the supplements information already exists in the list, it will not be added
     */
    void addSupplement(SupplementController s);

    /**
     * @param The class of the item to remove from the supplement list
     * @return If the class exists in the supplement list, it will be removed 
     */
    void removeSupplement(SupplementController s);

    /**
     * @return Adds new subscription choice to the view
     */
    void addNewSubscription();

    /**
     * @returns Adds event listener to the supplement addition button in the view
     */
    void addNewSupplementEvent(EventHandler<ActionEvent> event);

    double calculateWeeklySupplementCost();

    /**
     * @param the path to the place to store the data file
     * @return stores the class data into a .dat file stored in the destination variable path
     */
    void serializeCustomer(String destination);

    /**
     * @param location of the file to read from
     * @return the data file information is stored in the customer class
     */
    void deSerializeCustomer(String filePath);

    /**
     * @return returns a new class based off of the view's input member variables
     */
    CustomerController makeNewClass();

    /**
     * @return returns a button to refer to the view
     */
    RadioButton createButton();

    /**
     * @param editability of the values
     *
     * @return changes the editability of the components
     */
    void setEditable(boolean editable);
}
