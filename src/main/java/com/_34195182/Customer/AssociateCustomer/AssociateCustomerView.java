/*
 * -- AssociateCustomerView.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: View MVC component of the Customer class
 *
 */

package Customer.AssociateCustomer;

import Customer.CustomerView;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;

import java.lang.IllegalArgumentException;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.ArrayList;

import ComboBoxItem.ComboBoxItemS;
import Supplement.SupplementController;

/**
 * View MVC component interface.
 *
 * Defines common functions used in any customer view implementation
 *
 * @author Josh Smith - 34195182
 */
public class AssociateCustomerView implements CustomerView {
    /**
     * heading labels
     */
    private Label firstNameHeading, lastNameHeading, emailHeading, supplementHeading;
    private Region spacer1, spacer2, spacer3, spacer4;

    /**
     * value textfields
     */
    private TextField firstNameValue, lastNameValue, emailValue;

    /**
     * button to add a new supplement to subscribe to
     */
    private Button addNewSupplement;
    
    /**
     * List of supplements to subscribe from
     */
    private List<ComboBox<ComboBoxItemS>> supplementChoices;

    /**
     * container for the view
     */
    private VBox container;

    /**
     * @return initialises the class
     */
    public AssociateCustomerView() {
        firstNameHeading = new Label("First Name:");
        spacer1 = new Region();
        firstNameValue = new TextField();
        firstNameValue.setEditable(false);

        lastNameHeading = new Label("Last Name:");
        spacer2 = new Region();
        lastNameValue = new TextField();
        lastNameValue.setEditable(false);

        emailHeading = new Label("Email Address:");
        spacer3 = new Region();
        emailValue = new TextField();
        emailValue.setEditable(false);
        
        supplementHeading = new Label("Subscribed Supplements:");
        spacer4 = new Region();
        addNewSupplement = new Button("New Subscription");
        addNewSupplement.setDisable(true);
        supplementChoices = new ArrayList<>();
        addNewSubscription();

        container = new VBox();
    }

    @Override
    public String getFirstName() throws IllegalArgumentException {
        String firstName = firstNameValue.getText();
        validateName(firstName);
        return firstName;
    }

    @Override
    public String getLastName() throws IllegalArgumentException {
        String lastName = lastNameValue.getText();
        validateName(lastName);
        return lastName;
    }

    @Override
    public String getEmail() throws IllegalArgumentException {
        String email = emailValue.getText();
        validateEmail(email);
        return email;
    }

    @Override
    public List<SupplementController> getSupplements() {
        List<SupplementController> subscribedList = new ArrayList<>();
        for(ComboBox<ComboBoxItemS> comboBox: supplementChoices) {
            subscribedList.add(comboBox.getValue().getValue());
        }
        return subscribedList;
    }

    @Override
    public void addNewSubscription() {
        ComboBox<ComboBoxItemS> newComboBox = new ComboBox<>();
        newComboBox.setValue(new ComboBoxItemS("", new SupplementController()));
        supplementChoices.add(newComboBox);
        supplementChoices.getLast().setDisable(addNewSupplement.isDisabled());
    }

    @Override
    public void addNewSupplementEvent(EventHandler<ActionEvent> event) {
        addNewSupplement.setOnAction(event); 
    }

    /**
     * @param first name to assign
     * @param last name to assign
     * @param email to assign
     * @param list of supplements customer is subscribed to
     * @param list of supplements to choose from
     *
     * @return updates the view's variables to match parameters
     */
    public void updateView(String firstName, String lastName, String email, List<SupplementController> subscribedSupplements, List<SupplementController> supplements) {
        firstNameValue.setText(firstName);
        lastNameValue.setText(lastName);
        emailValue.setText(email);

        int subscriptionSize = subscribedSupplements.size();
        int viewSubscriptionSize = supplementChoices.size();
        if(subscriptionSize > viewSubscriptionSize) {
            int diff = subscriptionSize - viewSubscriptionSize;
            for(int i = 0; i < diff; i++) {
                addNewSubscription();
            }
        }

        for(int i = 0; i < subscriptionSize; i++) {
            SupplementController currentSubscription = subscribedSupplements.get(i);

            String name = "Supplement";
            if(currentSubscription.getName() != "") {
                name = currentSubscription.getName();
            }

            supplementChoices.get(i).getItems().clear();
            for(SupplementController supplement: supplements) {
                supplementChoices.get(i).getItems().add(new ComboBoxItemS(supplement.getName() + ", $" + supplement.getWeeklyCost(), supplement));
            }

            supplementChoices.get(i).setValue(new ComboBoxItemS(name + ", $" + currentSubscription.getWeeklyCost(), currentSubscription));
        }
    }

    /**
     * @param first name to assign
     * @param last name to assign
     * @param email to assign
     * @param list of supplements customer is subscribed to
     * @param list of supplements to choose from
     *
     * @return updates the view with the parameters, and returns the container of this view
     */
    public VBox updatePreview(String firstName, String lastName, String email, List<SupplementController> subscribedSupplements, List<SupplementController> supplementsToChoose) {
        updateView(firstName, lastName, email, subscribedSupplements, supplementsToChoose);
        HBox firstNameContainer = new HBox(firstNameHeading, spacer1, firstNameValue);
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox lastNameContainer = new HBox(lastNameHeading, spacer2, lastNameValue);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        HBox emailContainer = new HBox(emailHeading, spacer3, emailValue);
        HBox.setHgrow(spacer3, Priority.ALWAYS);

        HBox supplementHeadingContainer = new HBox(supplementHeading, spacer4, addNewSupplement);
        HBox.setHgrow(spacer4, Priority.ALWAYS);
        VBox supplementContainer = new VBox();

        for(ComboBox<ComboBoxItemS> comboBox: supplementChoices) {
            supplementContainer.getChildren().add(comboBox);
        }

        ScrollPane supplementPane = new ScrollPane();
        supplementPane.setContent(supplementContainer);
        supplementPane.setPrefSize(200, 150);

        container = new VBox(firstNameContainer, lastNameContainer, emailContainer, supplementHeadingContainer, supplementPane);
        container.setSpacing(5);
        return container;
    }

    /**
     * @return returns a new class based off of the view's input member variables
     */
    public AssociateCustomerController makeNewClass() {
        String firstName = this.getFirstName();
        String lastName = this.getLastName();
        String email = this.getEmail();
        List<SupplementController> subscriptions = this.getSupplements();
        return new AssociateCustomerController(firstName, lastName, email, subscriptions);
    }

    @Override
    public RadioButton createButton(String buttonName) {
        RadioButton button = new RadioButton(buttonName);
        return button;
    }

    @Override
    public void setEditable(boolean editable) {
        firstNameValue.setEditable(editable);
        lastNameValue.setEditable(editable);
        emailValue.setEditable(editable);
        addNewSupplement.setDisable(!editable);

        for(ComboBox<ComboBoxItemS> comboBox: supplementChoices) {
            comboBox.setDisable(!editable);
        }
    }

    /**
     * @param name to validate
     *
     * @return if the string is between 0-25 characters, and doesnt contain special characters, the string is valid
     * @throws IllegalArgumentException
     */
    private void validateName(String str) throws IllegalArgumentException {
        int strLength = str.length();

        if(str.isEmpty() || strLength > 25) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < strLength; i++) {
            if(!Character.isLetter(str.charAt(i)) && str.charAt(i) != '-') {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * @param email to validate
     *
     * @return only strings with format '__@__.__' is accepted
     * @throws IllegalArgumentException
     */
    private void validateEmail(String str) throws IllegalArgumentException {
        boolean isOfLength = true;
        boolean valid = false;

        int strLength = str.length();

        if(str.isEmpty() || strLength > 45) {
            isOfLength = true;
        }

        if(isOfLength) {
            boolean partialValid = false;
            for(int i = 0; i < strLength; i++) {
                if(str.charAt(i) == '@') {
                    partialValid = true;
                }
                else if(partialValid && str.charAt(i) == '.') {
                    valid = true;
                }
            }
        }

        if(!valid) {
            throw new IllegalArgumentException();
        }
    }
}
