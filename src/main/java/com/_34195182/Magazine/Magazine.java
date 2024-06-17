/*
 * -- Magazine.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/5/2024
 *
 * DESCRIPTION: Magazine class file
 *
 */

package Magazine;

import Supplement.SupplementController;
import Customer.CustomerController;

import java.util.List;
import java.util.ArrayList;

import java.util.UUID;

import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

/**
 * Holds crucial information for a magazine
 *
 * @author Josh Smith - 34195182
 */
public class Magazine implements Serializable {
    /**
     * Unique identifier for the class. Used in serialization
     */
    private String UID;

    /**
     * title of the magazine
     */
    private String title;

    /**
     * list of supplements featured in the magazine
     */
    private List<SupplementController> supplementList;

    /**
     * list of customers subscribed to the magazine
     */
    private List<CustomerController> customerList;

    /**
     * the cost of the magazine
     */
    private double cost;

    /**
     * default constructor
     */
    public Magazine() {
        this.UID = UUID.randomUUID().toString();
        this.title = "";
        this.cost = 0.0;
        this.supplementList = new ArrayList<>();
        this.customerList = new ArrayList<>();
    }

    public Magazine(String title, double cost, List<SupplementController> supplements, List<CustomerController> subscribers) {
        this.UID = UUID.randomUUID().toString();
        this.title = title;
        this.cost = cost;
        this.supplementList = new ArrayList<>();
        this.supplementList.addAll(supplements);
        this.customerList = new ArrayList<>();
        this.customerList.addAll(subscribers);
    }

    /**
     * @return returns the unique identifier of the class
     */
    public String getUID() {
        return this.UID;
    }

    /**
     * @return returns the title of the magazine
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return returns the list of supplements features in the magazine
     */
    public List<SupplementController> getSupplementList() {
        return supplementList;
    }

    /**
     * @return returns the list of customers that have subscribed to the magazine
     */
    public List<CustomerController> getCustomerList() {
        return customerList;
    }

    /**
     * @return returns the weekly cost of the magazine
     */
    public double getWeeklyCost() {
        return cost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param the cost to assign for the magazine
     * @return assigns the weekly cost of the magazine
     */
    public void setWeeklyCost(double cost) {
        this.cost = cost;
    }

    /**
     * @param supplement to add to the list
     * @return parameter is added to the list
     */
    public void addSupplement(SupplementController s) {
        supplementList.add(s);
    }

    /**
     * @param customer to add to the list
     * @return adds the customer to the magazine subscription list.
     */
    public void addCustomer(CustomerController c) {
        customerList.add(c);
    }

    public void serializeMagazine(String destination) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(destination + "/magazine/" + this.UID + ".dat");
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(this);
        objectOutput.close();
        fileOutput.close();
    }

    public void deSerializeMagazine(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileInput = new FileInputStream(filePath);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        Magazine newObject = (Magazine)objectInput.readObject();
        objectInput.close();
        fileInput.close();

        this.title = newObject.getTitle();
        this.supplementList = newObject.getSupplementList();
        this.customerList = newObject.getCustomerList();
        this.cost = newObject.getWeeklyCost();
    }
}
