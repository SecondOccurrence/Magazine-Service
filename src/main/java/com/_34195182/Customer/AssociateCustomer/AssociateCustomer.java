/*
 * -- AssociateCustomer.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 22/05/2024
 *
 * DESCRIPTION: Model MVC component of the Associate Customer class
 *
 */

package Customer.AssociateCustomer;

import Customer.Customer;
import Supplement.SupplementController;

import java.util.UUID;

import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.lang.ClassNotFoundException;

/**
 * Holds crucial information for an associate customer;
 *  one who does not pay for the magazine
 *
 * @author Josh Smith - 34195182
 */

public class AssociateCustomer implements Customer, Serializable{
    /**
     * Unique identifier for the class. Used in serialization
     */
    private String UID;

    /**
     * First name of the customer
     */
    private String firstName;

    /**
     * Last name of the customer
     */
    private String lastName;

    /**
     * Email address of the customer
     */
    private String emailAddress;

    /**
     * List of supplements customer is subscribed to
     */
    private List<SupplementController> supplementList;

    /**
     * @return Initialises to default values
     */
    public AssociateCustomer() {
        this.UID = UUID.randomUUID().toString();
        this.firstName = "";
        this.lastName = "";
        this.emailAddress = "";
        this.supplementList = new ArrayList<>();
    }

    /**
     * @return Intitialises to parameter values
     * @param first name for the customer
     * @param last name for the customer
     * @param emailaddress for the customer
     */
    public AssociateCustomer(String fName, String lName, String email, List<SupplementController> subscriptions) {
        this.UID = UUID.randomUUID().toString();
        setFirstName(fName);
        setLastName(lName);
        setEmailAddress(email);
        supplementList = new ArrayList<>();

        for(SupplementController s: subscriptions) {
            supplementList.add(s);
        }
    }

    @Override
    public String getUID() {
        return UID;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getEmailAddress() {
        return this.emailAddress;
    }

    @Override
    public List<SupplementController> getSubscribedSupplements() {
        return this.supplementList;
    }

    @Override
    public void setFirstName(String str) {
        this.firstName = str;
    }

    @Override
    public void setLastName(String str) {
        this.lastName = str;
    }

    @Override
    public void setEmailAddress(String str) {
        this.emailAddress = str;
    }

    @Override
    public void addSupplement(SupplementController s) {
        this.supplementList.add(s);
    }

    @Override
    public void removeSupplement(int index) {
        supplementList.remove(index);
    }

    @Override
    public void serializeCustomer(String destination) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(destination + "/associate_customers/" + this.UID + ".dat");
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(this);
        objectOutput.close();
        fileOutput.close();
    }

    @Override
    public void deSerializeCustomer(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileInput = new FileInputStream(filePath);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        AssociateCustomer newObject = (AssociateCustomer)objectInput.readObject();
        objectInput.close();
        fileInput.close();

        this.UID = newObject.getUID();
        this.firstName = newObject.getFirstName();
        this.lastName = newObject.getLastName();
        this.emailAddress = newObject.getEmailAddress();
        this.supplementList = newObject.getSubscribedSupplements();
    }
}
