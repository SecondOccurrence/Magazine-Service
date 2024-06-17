/*
 * -- SupplementContainer.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * Contains javafx components that define the supplement container
 *
 */

package Containers;

import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.ArrayList;

import Customer.CustomerController;
import Customer.AssociateCustomer.AssociateCustomerController;
import Customer.PayingCustomer.PayingCustomerController;
import Supplement.SupplementController;
import FileUtils.FileUtils;

/**
 * Container for CustomerController used in the main program
 */
public class CustomerContainer extends ClassContainer<CustomerController> {
    public CustomerContainer(ToggleGroup group) {
        super(group);
    }

    /**
-     * @param index of the customer to add to
-     * @param list of supplements to add a subscription to
-     *
-     * @return returns the view of the customer
-     */
    public VBox updatePreview(int index, List<SupplementController> supplements) {
        CustomerController customer = list.get(index);
        if(customer instanceof AssociateCustomerController) {
            return ((AssociateCustomerController)customer).updatePreview(supplements);
        }
        else {
            return new VBox();
        }
    }

    public VBox updatePreview(int index, List<SupplementController> supplements, List<AssociateCustomerController> associates) {
        CustomerController customer = list.get(index);
        if(customer instanceof PayingCustomerController) {
            return ((PayingCustomerController)customer).updatePreview(supplements, associates);
        }
        else {
            return new VBox();
        }
    }

    /**
     * @param index of the customer add to
     *
     * @return adds a new subscription to the view
     */
    public void addNewSubscription(int index) {
        list.get(index).addNewSubscription();
    }

    /**
     * @param index of the customer add to
     * @param event to add
     *
     * @return adds event handler to the newSupplement button
     */
    public void addNewSupplementEvent(int index, EventHandler<ActionEvent> event) {
        list.get(index).addNewSupplementEvent(event);
    }

    /**
     * @param index of the customer to add to
     * 
     * @return returns a new class based off of a classes view
     */
    public CustomerController retrieveNewClass(int index) {
        return list.get(index).makeNewClass();
    }

    public List<AssociateCustomerController> getAssociates() {
        List<AssociateCustomerController> associateList = new ArrayList<>();

        int customerSize = list.size();
        for(int i = 2; i < customerSize; i++) {
            if(list.get(i) instanceof AssociateCustomerController) {
                associateList.add((AssociateCustomerController)list.get(i));        
            }
        }
        
        return associateList;
    }

    public List<CustomerController> getCustomers() {
        List<CustomerController> customerList = new ArrayList<>();

        int customerSize = list.size();
        for(int i = 2; i < customerSize; i++) {
            customerList.add(list.get(i));
        }

        return customerList;
    }   

    @Override
    public void setEditable(boolean editable) {
        for(CustomerController customer: list) {
            customer.setEditable(editable);
        }
    }

    @Override
    public void addNewClass(CustomerController newCustomer) {
        if(newCustomer.updateFirstName() && newCustomer.updateLastName() && newCustomer.updateEmailAddress() && newCustomer.updateSubscribedSupplements()) {
            list.add(newCustomer);

            RadioButton newButton = newCustomer.createButton();
            newButton.setToggleGroup(toggleGroup);
            buttons.add(newButton);
            buttons.getLast().getStyleClass().add("custom-radio-button");

            buttonsContainer.getChildren().add(buttons.getLast());
        }
    }

    @Override
    public void removeButton(int index) {
        FileUtils.deleteSerializedFile("../../data/serialized/associate_customer/", list.get(index).getUID());
        FileUtils.deleteSerializedFile("../../data/serialized/paying_customer/", list.get(index).getUID());
        list.remove(index);
        buttons.remove(index);
        buttonsContainer.getChildren().remove(index);
    }
    
    @Override
    public void updateClass(int index) {
        CustomerController customer = list.get(index);
        customer.updateFirstName();
        customer.updateLastName();
        customer.updateEmailAddress();
        customer.updateSubscribedSupplements();

        buttons.set(index, customer.createButton());
        buttons.get(index).setToggleGroup(toggleGroup);
        buttons.get(index).getStyleClass().add("custom-radio-button");

        buttonsContainer.getChildren().remove(index);
        buttonsContainer.getChildren().add(index, buttons.get(index));
    }

    @Override
    public void serializeButtons() {
        FileUtils.deleteFolderContents("../../data/serialized/associate_customers/");

        int size = list.size();
        for(int i = 2; i < size; i++) {
            list.get(i).serializeCustomer("../../data/serialized/");
        }
    }

    @Override
    protected void createClassList() {
        buttonsContainer = new VBox();
        buttons = new ArrayList<>();

        list = FileUtils.fetchCustomers("../../data/serialized/");

        list.addFirst(new AssociateCustomerController());
        list.add(1, new PayingCustomerController());

        int listSize = list.size();
        for(int i = 0; i < listSize; i++) {
            buttons.add(list.get(i).createButton());
            buttons.get(i).setToggleGroup(toggleGroup);
            buttons.get(i).getStyleClass().add("custom-radio-button");
            buttonsContainer.getChildren().add(buttons.get(i));
        }

        buttons.get(0).setText("[New Associate]");
        buttons.get(1).setText("[New Paying]");
    }
}
