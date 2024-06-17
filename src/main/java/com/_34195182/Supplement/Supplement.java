/*
 * -- Supplement.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Supplement class file
 *
 */

package Supplement;


import java.util.UUID;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.lang.ClassNotFoundException;

/**
 * Holds crucial information for a supplement
 *
 * @author Josh Smith - 34195182
 */
public class Supplement implements Serializable{
    /**
     * Unique identifier for the class. Used in serialization
     */
    private String UID; 

    /**
     * The name of the supplement
     */
    private String name;

    /**
     * The weekly cost for the supplement
     */
    private double weeklyCost;

    /**
     * Default constructor
     *
     * @return assigns default values for member variables
     */
    public Supplement() {
        this.UID = UUID.randomUUID().toString();
        this.name = "";
        this.weeklyCost = 0.0;
    }

    /**
     * @return Assigns parameters to member variables
     */
    public Supplement(String name, double cost) {
        this.UID = UUID.randomUUID().toString();
        this.name = name;
        this.weeklyCost = cost;
    }

    /**
     * @return returns the unique ID of the supplement
     */
    public String getUID() {
        return UID;
    }

    /**
     * @return returns the name of the supplement
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return returns the weekly cost of the supplement
     */
    public double getWeeklyCost() {
        return this.weeklyCost;
    }

    /**
     * @param name to set to the supplement
     * @return assigns parameter to the name
     */
    public void setName(String str) {
        this.name = str;
    }

    /**
     * @param cost to set to the supplement
     * @return assigns parameter to the weekly cost
     */
    public void setWeeklyCost(double cost) {
        this.weeklyCost = cost;
    }

    /**
     * @param the location of the data file
     * @return writes the class data to a data file
     */
    public void serializeSupplement(String destination) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(destination + "/supplements/" + this.UID + ".dat");
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(this);
        objectOutput.close();
        fileOutput.close();
    }

    /**
     * Converts a data file into a class. Assumed that the data file is not corrupt or invalid
     *
     * @param the location of the data file to read
     * @param the name of the file to read
     * @return read the data file and stores the data into this class
     */
    public void deSerializeSupplement(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileInput = new FileInputStream(filePath);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        Supplement newObject = (Supplement)objectInput.readObject();
        objectInput.close();
        fileInput.close();

        this.UID = newObject.getUID();
        this.name = newObject.getName();
        this.weeklyCost = newObject.getWeeklyCost();
    }
}
