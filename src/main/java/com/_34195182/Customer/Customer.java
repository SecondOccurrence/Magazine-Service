/*
 * -- Customer.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Model MVC component of the Customer class
 *
 */

package Customer;

import Supplement.SupplementController;

import java.util.List;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

/**
 * Model MVC component interface.
 *
 * Defines common functions used in any customer model implementation
 *
 * @author Josh Smith - 34195182
 */
public interface Customer {
    /**
     * @return returns the unique ID of the customer
     */
    String getUID();

    /**
     * @return Retrieves the first name of the customer
     */
    String getFirstName();

    /**
     * @return Retrieves the last name of the customer
     */
    String getLastName();


    /**
     * @return Retrieves the email address of the customer
     */
    String getEmailAddress();

    /**
     * @return Retrieves the list of supplements the customer is subscribed to
     */
    List<SupplementController> getSubscribedSupplements();

    /**
     * @param First name to assign to customer
     * @return Assigns the parameter to the first name of the customer
     */
    void setFirstName(String str);

    /**
     * @param Last name to assign to customer
     * @return Assigns the parameter to the last name of the customer
     */
    void setLastName(String str);
    
    /**
     * @param Email address to assign to customer
     * @return Assigns the parameter to the email address of the customer
     */
    void setEmailAddress(String str);
    
    /**
     * @param Supplement to add to the list
     * @return Adds the supplement to the list
     */
    void addSupplement(SupplementController s);
    
    /**
     * @param the index in the list which hold the supplement to remove
     * @return removes the supplement at index
     */
    void removeSupplement(int index);

    /**
     * @param the path to the place to store the data file
     * @return stores the class data into a .dat file stored in the destination variable path
     */
    void serializeCustomer(String destination) throws IOException;

    /**
     * @param location of the file to read from
     * @return the data file information is stored in the customer class
     */
    void deSerializeCustomer(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException;
}
