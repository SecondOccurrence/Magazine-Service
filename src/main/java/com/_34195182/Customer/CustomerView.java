/*
 * -- CustomerView.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: View MVC component of the Customer class
 *
 */

package Customer;

import javafx.scene.control.RadioButton;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.lang.IllegalArgumentException;

import java.util.List;

import Supplement.SupplementController;

/**
 * View MVC component interface.
 *
 * Defines common functions used in any customer view implementation
 *
 * @author Josh Smith - 34195182
 */
public interface CustomerView {
    /**
     * Validates the first name in the view and returns it
     *
     * @return returns the first name of the customer
     * @throws IllegalArgumentException
     */
    String getFirstName() throws IllegalArgumentException;

    /**
     * Validates the last name in the view and returns it
     *
     * @return returns the last name of the customer
     * @throws IllegalArgumentException
     */
    String getLastName() throws IllegalArgumentException;

    /**
     * Validates the email of the view and returns it
     *
     * @return returns the email of the customer
     * @throws IllegalArgumentException
     */
    String getEmail() throws IllegalArgumentException;
    
    /**
     * @return returns the list of subscribed supplements
     */
    List<SupplementController> getSupplements();

    /**
     * @return adds a new combo box to the supplement choices
     */
    void addNewSubscription();

    /**
     * @param event to add to the addNewSupplement button
     *
     * @return adds an event handler to the addNewSupplement button
     */
    void addNewSupplementEvent(EventHandler<ActionEvent> event);
    
    /**
     * @param name of the button
     *
     * @return returns a button to refer to the view
     */
    RadioButton createButton(String buttonName);

    /**
     * @param editability of the values
     *
     * @return changes the editability of the components
     */
    void setEditable(boolean editable);
}
