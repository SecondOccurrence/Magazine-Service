/*
 * -- AssociateCustomerController.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Controller MVC component of the Associate Customer class
 *
 */

package Customer.AssociateCustomer;

import Customer.CustomerController;
import Supplement.SupplementController;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.Serializable;

/**
 * Controller component, following the MVC architecture, of an Associate Customer class.
 *
 * This class controls interactions with an Associate Customer class and its view
 *
 * @author Josh Smith - 34195182
 */
public class AssociateCustomerController implements CustomerController, Serializable{
    /**
     * The customer class to store details
     */
    private AssociateCustomer customer;

    /**
     * The customer's view MVC component class
     */
    private transient AssociateCustomerView customerView;

    /**
     * @return Calls default constructors for both Customer and CustomerView classes
     */
    public AssociateCustomerController() {
        this.customer = new AssociateCustomer();
        this.customerView = new AssociateCustomerView();
    }

    /**
     * @return Calls constructor
     */
    public AssociateCustomerController(String fName, String lName, String email, List<SupplementController> subscriptions) {
        this.customer = new AssociateCustomer(fName, lName, email, subscriptions);
        this.customerView = new AssociateCustomerView();
        updateView();
    }

    @Override
    public String getUID() {
        return this.customer.getUID();
    }

    @Override
    public String getFirstName() {
        return this.customer.getFirstName();
    }

    @Override
    public String getLastName() {
        return this.customer.getLastName();
    }

    @Override
    public String getEmailAddress() {
        return this.customer.getEmailAddress();
    }

    @Override
    public List<SupplementController> getSubscribedSupplements() {
        return this.customer.getSubscribedSupplements();
    }

    @Override
    public void forceSetFirstName(String str) {
        customer.setFirstName(str);
    }

    @Override
    public void forceSetLastName(String str) {
        customer.setLastName(str);
    }

    @Override
    public void forceSetEmailAddress(String str) {
        customer.setEmailAddress(str);
    }

    @Override
    public boolean updateFirstName() {
        boolean errorFlag = true;
        try {
            String firstName = customerView.getFirstName();
            customer.setFirstName(firstName);
        }
        catch(IllegalArgumentException e) {
            errorFlag = false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error on save");
            alert.setHeaderText(null);
            alert.setContentText("An error occured. The name of the customer must not include special characters or spaces. Please try again.");
            alert.showAndWait();
        }
        return errorFlag;
    }

    @Override
    public boolean updateLastName() {
        boolean errorFlag = true;
        try {
            String lastName = customerView.getLastName();
            customer.setLastName(lastName);
        }
        catch(IllegalArgumentException e) {
            errorFlag = false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error on save");
            alert.setHeaderText(null);
            alert.setContentText("An error occured. The name of the customer must not include special characters or spaces. Please try again.");
            alert.showAndWait();  
        }
        return errorFlag;
    }

    @Override
    public boolean updateEmailAddress() {
        boolean errorFlag = true;
        try {
            String emailAddress = customerView.getEmail();
            customer.setEmailAddress(emailAddress);
        }
        catch(IllegalArgumentException e) {
            errorFlag = false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error on save");
            alert.setHeaderText(null);
            alert.setContentText("An error occured. The email of the customer must contain an '@' symbol followed by a '.'. Please try again.");
            alert.showAndWait();
        }
        return errorFlag;
    }
    
    @Override
    public boolean updateSubscribedSupplements() {
        boolean errorFlag = true;

        List<SupplementController> list = customerView.getSupplements();
        customer.getSubscribedSupplements().clear();
        for(SupplementController supplement: list) {
            customer.addSupplement(supplement);
        }
        
        return errorFlag;
    }

    @Override
    public void updateView() {
        List<SupplementController> placeholderOptions = new ArrayList<>();
        customerView.updateView(customer.getFirstName(), customer.getLastName(), customer.getEmailAddress(), customer.getSubscribedSupplements(), placeholderOptions);
    }

    public void updateView(String firstName, String lastName, String email, List<SupplementController> supplements) {
        customerView.updateView(firstName, lastName, email, customer.getSubscribedSupplements(), supplements);
    }

    @Override
    public void addSupplement(SupplementController s) {
        int supplementSize = customer.getSubscribedSupplements().size();
        for(int i = 0; i < supplementSize; i++) {
            if(customer.getSubscribedSupplements().get(i).equals(s)) {
                return;
            }
        }
        this.customer.addSupplement(s);
    }

    @Override
    public void removeSupplement(SupplementController s) {
        String name = s.getName();
        double cost = s.getWeeklyCost();

        int listSize = customer.getSubscribedSupplements().size();
        boolean nameMatch, costMatch;
        for(int i = 0; i < listSize; i++) {
            nameMatch = customer.getSubscribedSupplements().get(i).getName().equals(name);
            costMatch = customer.getSubscribedSupplements().get(i).getWeeklyCost() == cost;
            if(nameMatch && costMatch) {
                customer.removeSupplement(i);
                break;
            }
        }
    }

    @Override
    public double calculateWeeklySupplementCost() {
        double weeklyCost = 0.0;
        int supplementSize = customer.getSubscribedSupplements().size();
        for(int i = 0; i < supplementSize; i++) {
            weeklyCost += customer.getSubscribedSupplements().get(i).getWeeklyCost();
        }

        return weeklyCost;
    }

    @Override
    public void addNewSubscription() {
        customerView.addNewSubscription();
    }

    @Override
    public void addNewSupplementEvent(EventHandler<ActionEvent> event) {
        customerView.addNewSupplementEvent(event);
    }

    public VBox updatePreview(List<SupplementController> supplements) {
        List<SupplementController> subscriptionList = new ArrayList<>();

        // more choices in the preview than there are actual subscriptions.
        if(customerView.getSupplements().size() != customer.getSubscribedSupplements().size()) {
            // function call is in response to new subscription choice
            subscriptionList.addAll(customerView.getSupplements());
        }
        else {
            // function call is in response to general preview update
            subscriptionList.addAll(customer.getSubscribedSupplements());
        }

        return customerView.updatePreview(customer.getFirstName(), customer.getLastName(), customer.getEmailAddress(), subscriptionList, supplements);
    }

    @Override
    public AssociateCustomerController makeNewClass() {
        return this.customerView.makeNewClass();
    }

    @Override
    public RadioButton createButton() {
        return customerView.createButton(customer.getFirstName());
    }

    @Override
    public void setEditable(boolean editable) {
        customerView.setEditable(editable);
    }

    @Override
    public void serializeCustomer(String destination) {
        try {
            this.customer.serializeCustomer(destination);
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

    @Override
    public void deSerializeCustomer(String filePath) {
        try {
            this.customer.deSerializeCustomer(filePath);
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

