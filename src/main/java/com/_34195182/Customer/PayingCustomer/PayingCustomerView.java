/*
 * -- CustomerController.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 13/05/2024
 *
 * DESCRIPTION: View MVC component of the Customer class
 *
 */

package Customer.PayingCustomer;

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
import ComboBoxItem.ComboBoxItemC;
import Supplement.SupplementController;
import Customer.AssociateCustomer.AssociateCustomerController;

/**
 * View MVC component interface.
 *
 * Defines common functions used in any customer view implementation
 *
 * @author Josh Smith - 34195182
 */
public class PayingCustomerView implements CustomerView {
    /**
     * heading labels
     */
    private Label firstNameHeading, lastNameHeading, emailHeading, paymentHeading, supplementHeading, associateHeading;
    private Region spacer1, spacer2, spacer3, spacer4, spacer5, spacer6;

    /**
     * value textfields
     */
    private TextField firstNameValue, lastNameValue, emailValue;

    /**
     * for the payment method
     */
    private ComboBox<PaymentMethod> paymentValue;

    /**
     * button to add a new supplement to subscribe to
     */
    private Button addNewSupplement;
    
    /**
     * List of supplements to subscribe from
     */
    private List<ComboBox<ComboBoxItemS>> supplementChoices;

    /**
     * button to add a new associate to subscribe to
     */
    private Button addNewAssociate;

    /**
     * List of associate customers to pay for
     */
    private List<ComboBox<ComboBoxItemC>> associateChoices;

    /**
     * container for the view
     */
    private VBox container;

    /**
     * @return initialises the class
     */
    public PayingCustomerView() {
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

        paymentHeading = new Label("Payment Method:");
        spacer4 = new Region();
        paymentValue = new ComboBox<>();
        paymentValue.getItems().addAll(PaymentMethod.BANK, PaymentMethod.DEBIT, PaymentMethod.CREDIT);
        paymentValue.setDisable(true);

        supplementHeading = new Label("Subscribed Supplements:");
        spacer5 = new Region();
        addNewSupplement = new Button("New Subscription");
        addNewSupplement.setDisable(true);
        supplementChoices = new ArrayList<>();
        addNewSubscription();

        associateHeading = new Label("Associate Customers:");
        spacer6 = new Region();
        addNewAssociate = new Button("New Associate");
        addNewAssociate.setDisable(true);
        associateChoices = new ArrayList<>();
        addNewAssociate();

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

    public PaymentMethod getPaymentMethod() {
        PaymentMethod method = paymentValue.getValue();
        return method;
    }

    @Override
    public List<SupplementController> getSupplements() {
        List<SupplementController> subscribedList = new ArrayList<>();
        for(ComboBox<ComboBoxItemS> comboBox: supplementChoices) {
            subscribedList.add(comboBox.getValue().getValue());
        }
        return subscribedList;
    }

    public List<AssociateCustomerController> getAssociates() {
        List<AssociateCustomerController> associateList = new ArrayList<>();
        for(ComboBox<ComboBoxItemC> comboBox: associateChoices) {
            associateList.add(comboBox.getValue().getValue());
        }
        return associateList;
    }

    @Override
    public void addNewSubscription() {
        ComboBox<ComboBoxItemS> newComboBox = new ComboBox<>();
        newComboBox.setValue(new ComboBoxItemS("", new SupplementController()));
        supplementChoices.add(newComboBox);
        supplementChoices.getLast().setDisable(addNewSupplement.isDisabled());
    }

    public void addNewAssociate() {
        ComboBox<ComboBoxItemC> newComboBox = new ComboBox<>();
        newComboBox.setValue(new ComboBoxItemC("", new AssociateCustomerController()));
        associateChoices.add(newComboBox);
        associateChoices.getLast().setDisable(addNewAssociate.isDisabled());
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
    public void updateView(String firstName, String lastName, String email, PaymentMethod method,
            List<SupplementController> subscribedSupplements, List<SupplementController> supplements,
            List<AssociateCustomerController> paidAssociates, List<AssociateCustomerController> associates) {
        firstNameValue.setText(firstName);
        lastNameValue.setText(lastName);
        emailValue.setText(email);
        paymentValue.setValue(method);

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

        int paidSize = paidAssociates.size();
        int viewPaidSize = associateChoices.size();
        if(paidSize > viewPaidSize) {
            int diff = paidSize - viewPaidSize;
            for(int i = 0; i < diff; i++) {
                addNewAssociate();
            }
        }

        for(int i = 0; i < paidSize; i++) {
            AssociateCustomerController currentAssociate = paidAssociates.get(i);

            String name = "Associate";
            if(currentAssociate.getFirstName() != "") {
                name = currentAssociate.getEmailAddress();
            }

            associateChoices.get(i).getItems().clear();
            for(AssociateCustomerController associate: associates) {
                associateChoices.get(i).getItems().add(new ComboBoxItemC(associate.getEmailAddress(), associate));
            }

            associateChoices.get(i).setValue(new ComboBoxItemC(name, currentAssociate));
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
    public VBox updatePreview(String firstName, String lastName, String email, PaymentMethod method,
            List<SupplementController> subscribedSupplements, List<SupplementController> supplementsToChoose,
            List<AssociateCustomerController> paidAssociates, List<AssociateCustomerController> associates) {
        updateView(firstName, lastName, email, method, subscribedSupplements, supplementsToChoose, paidAssociates, associates);
        HBox firstNameContainer = new HBox(firstNameHeading, spacer1, firstNameValue);
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox lastNameContainer = new HBox(lastNameHeading, spacer2, lastNameValue);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        HBox emailContainer = new HBox(emailHeading, spacer3, emailValue);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        HBox paymentContainer = new HBox(paymentHeading, spacer4, paymentValue);
        HBox.setHgrow(spacer4, Priority.ALWAYS);
        HBox supplementHeadingContainer = new HBox(supplementHeading, spacer5, addNewSupplement);
        HBox.setHgrow(spacer5, Priority.ALWAYS);
        VBox supplementContainer = new VBox();
        for(ComboBox<ComboBoxItemS> comboBox: supplementChoices) {
            supplementContainer.getChildren().add(comboBox);
        }

        HBox associateHeadingContainer = new HBox(associateHeading, spacer6, addNewAssociate);
        HBox.setHgrow(spacer6, Priority.ALWAYS);
        VBox associateContainer = new VBox();
        for(ComboBox<ComboBoxItemC> comboBox: associateChoices) {
            associateContainer.getChildren().add(comboBox);
        }

        ScrollPane supplementPane = new ScrollPane();
        supplementPane.setContent(supplementContainer);
        supplementPane.setPrefSize(200, 150);

        ScrollPane associatePane = new ScrollPane();
        associatePane.setContent(associateContainer);
        associatePane.setPrefSize(200, 150);

        container = new VBox(firstNameContainer, lastNameContainer, emailContainer, paymentContainer,
                supplementHeadingContainer, supplementPane,
                associateHeadingContainer, associatePane);
        container.setSpacing(5);
        return container;
    }

    /**
     * @return returns a new class based off of the view's input member variables
     */
    public PayingCustomerController makeNewClass() {
        String firstName = this.getFirstName();
        String lastName = this.getLastName();
        String email = this.getEmail();
        PaymentMethod payment = this.getPaymentMethod();
        List<SupplementController> subscriptions = this.getSupplements();
        List<AssociateCustomerController> associates = this.getAssociates();
        return new PayingCustomerController(firstName, lastName, email, PaymentMethod.BANK, subscriptions, associates);
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
        paymentValue.setDisable(!editable);
        addNewSupplement.setDisable(!editable);
        for(ComboBox<ComboBoxItemS> comboBox: supplementChoices) {
            comboBox.setDisable(!editable);
        }
        
        addNewAssociate.setDisable(!editable);
        for(ComboBox<ComboBoxItemC> comboBox: associateChoices) {
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
