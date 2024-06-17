/*
 * -- PayingCustomer.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Model MVC component of the Paying Customer class
 *
 */

package Customer.PayingCustomer;

import Customer.Customer;
import Customer.AssociateCustomer.AssociateCustomerController;
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
public class PayingCustomer implements Customer, Serializable {
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
     * Defines how the customer pays for the magazine service
     */
    private PaymentMethod paymentMethod;

    /**
     * List of supplements customer is subscribed to
     */
    private List<SupplementController> supplementList;

    /**
     * A list of associate customers which the paying customer pays for
     */
    private List<AssociateCustomerController> associateCustomers;

    /**
     * @return Initialises to default values
     */
    public PayingCustomer() {
        this.UID = UUID.randomUUID().toString();
        this.firstName = "";
        this.lastName = "";
        this.emailAddress = "";
        this.paymentMethod = PaymentMethod.CREDIT;
        this.supplementList = new ArrayList<>();
        this.associateCustomers = new ArrayList<>();
    }

    /**
     * @return Intitialises to parameter values
     * @param first name for the customer
     * @param last name for the customer
     * @param emailaddress for the customer
     */
    public PayingCustomer(String fName, String lName, String email, PaymentMethod paymentMethod,
            List<SupplementController> subscriptions, List<AssociateCustomerController> associates) {
        this.UID = UUID.randomUUID().toString();
        setFirstName(fName);
        setLastName(lName);
        setEmailAddress(email);
        this.paymentMethod = paymentMethod;
        this.supplementList = new ArrayList<>();
        this.supplementList.addAll(subscriptions);
        this.associateCustomers = new ArrayList<>();
        this.associateCustomers.addAll(associates);
    }

    @Override
    public String getUID() {
        return this.UID;
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

    /**
     * @return returns the payment method the paying customer uses
     */
    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    @Override
    public List<SupplementController> getSubscribedSupplements() {
        return this.supplementList;
    }

    /**
     * @return returns the list of customers that this paying customer pays for
     */
    public List<AssociateCustomerController> getAssociateCustomers() {
        return this.associateCustomers;
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

    /**
     * @param payment method for the Paying Customer
     * @return Assigns a payment method that the customer will use to pay
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void addSupplement(SupplementController s) {
        this.supplementList.add(s);
    }

    @Override
    public void removeSupplement(int index) {
        this.supplementList.remove(index);
    }

    public void addAssociateCustomer(AssociateCustomerController c) {
        this.associateCustomers.add(c);
    }

    public void removeAssociateCustomer(int index) {
        this.associateCustomers.remove(index);
    }

    @Override
    public void serializeCustomer(String destination) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(destination + "/paying_customers/" + this.UID + ".dat");
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(this);
        objectOutput.close();
        fileOutput.close();
    }

    @Override
    public void deSerializeCustomer(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileInput = new FileInputStream(filePath);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        PayingCustomer newObject = (PayingCustomer)objectInput.readObject();
        objectInput.close();
        fileInput.close();

        this.UID = newObject.getUID();
        this.firstName = newObject.getFirstName();
        this.lastName = newObject.getLastName();
        this.emailAddress = newObject.getEmailAddress();
        this.paymentMethod = newObject.getPaymentMethod();
        this.supplementList = newObject.getSubscribedSupplements();
        this.associateCustomers = newObject.getAssociateCustomers();
    }
}
