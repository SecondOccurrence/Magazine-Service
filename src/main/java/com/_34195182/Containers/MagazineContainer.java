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
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.util.List;
import java.util.ArrayList;

import Customer.CustomerController;
import Supplement.SupplementController;

import Magazine.MagazineController;
import FileUtils.FileUtils;

/**
 * Container for CustomerController used in the main program
 *
 * @author Josh Smith - 34195182
 */
public class MagazineContainer {
    private VBox container;

    private HBox mainContainer;

    private VBox customerList;
    private List<CustomerController> customerArrayList;
    private List<ToggleButton> customerButtons;

    private VBox supplementList;
    private List<SupplementController> supplementArrayList;
    private List<ToggleButton> supplementButtons;


    private VBox magazineDetails;

    private HBox buttonContainer;
    private Button createMagazine, sendWeeklyEmail, sendMonthlyEmail;
    
    private HBox magazineList;
    private List<MagazineController> magazineArrayList;
    private List<RadioButton> magazineButtons;

    private ToggleGroup magazineToggleGroup;
    private int currentMagazine;

    public MagazineContainer(ToggleGroup group) {
        Label customerHeading = new Label("Customers to add:");
        customerList = new VBox();
        customerList.setSpacing(5);
        ScrollPane customerPane = new ScrollPane();
        customerPane.setContent(customerList);
        customerPane.setPrefSize(250, 200);
        customerPane.setPadding(new Insets(5, 5, 5, 5));
        VBox customerContainer = new VBox(customerHeading, customerPane);

        Label supplementHeading = new Label("Supplements to add:");
        supplementList = new VBox();
        supplementList.setSpacing(5);
        ScrollPane supplementPane = new ScrollPane();
        supplementPane.setContent(supplementList);
        supplementPane.setPrefSize(250, 200);
        supplementPane.setPadding(new Insets(5, 5, 5, 5));
        VBox supplementContainer = new VBox(supplementHeading, supplementPane);

        Label magazineHeading = new Label("Magazine Info:");
        magazineDetails = new VBox();
        ScrollPane magazinePane = new ScrollPane();
        magazinePane.setContent(magazineDetails);
        magazinePane.setPrefSize(250, 200);
        magazinePane.setPadding(new Insets(5, 5, 5, 5));
        VBox magazineContainer = new VBox(magazineHeading, magazinePane);
        mainContainer = new HBox(customerContainer, supplementContainer, magazineContainer);
        mainContainer.setSpacing(30);


        createMagazine = new Button("Create New");
        createMagazine.setOnAction(event -> {
            MagazineController magazine = magazineArrayList.get(currentMagazine);
            if(magazine.updateTitle() && magazine.updateWeeklyCost()) {
                List<Integer> selectedCustomers = getSelected(customerButtons);
                for(Integer index: selectedCustomers) {
                    magazineArrayList.get(currentMagazine).addMagazineCustomer(customerArrayList.get(index));
                }
                List<Integer> selectedSupplements = getSelected(supplementButtons);
                for(Integer index: selectedSupplements) {
                    magazineArrayList.get(currentMagazine).addMagazineSupplement(supplementArrayList.get(index));
                }

                MagazineController newMagazine = magazineArrayList.get(currentMagazine).makeNewClass();

                magazineArrayList.add(newMagazine);
                magazineButtons.add(magazineArrayList.getLast().createButton());
                magazineButtons.getLast().setToggleGroup(magazineToggleGroup);
                magazineList.getChildren().add(magazineButtons.getLast());

                addListenerToMagazines();
            }
        });

        sendWeeklyEmail = new Button("Send Weekly Email");

        sendMonthlyEmail = new Button("Send Monthly Email");
        buttonContainer = new HBox(createMagazine, sendWeeklyEmail, sendMonthlyEmail);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.setSpacing(30);

        magazineList = new HBox();
        magazineList.setSpacing(10);
        magazineArrayList = FileUtils.fetchMagazines("../../data/serialized/magazine/");
        magazineArrayList.addFirst(new MagazineController());

        ScrollPane magazineListPane = new ScrollPane();
        magazineListPane.setContent(magazineList);

        container = new VBox(mainContainer, buttonContainer, magazineListPane);
        container.setPadding(new Insets(90, 90, 90, 90));
        container.setSpacing(30);

        magazineToggleGroup = new ToggleGroup();
    }

    public VBox getContainer() {
        return container;
    }

    public void updateContainer(List<CustomerController> customers, List<SupplementController> supplements) {
        updateMagazineList();
        updateCustomerList(customers);
        updateSupplementList(supplements);
        addListenerToMagazines();
        magazineButtons.get(0).fire();
    }

    public void updateCustomerList(List<CustomerController> customers) {
        customerArrayList = new ArrayList<>();
        customerButtons = new ArrayList<>();
        customerList.getChildren().clear();
        for(CustomerController customer: customers) {
            customerArrayList.add(customer);
            customerButtons.add(customer.createButton());
            customerButtons.getLast().getStyleClass().add("custom-radio-button");
            customerList.getChildren().add(customerButtons.getLast());
        }
    }

    public void updateSupplementList(List<SupplementController> supplements) {
        supplementArrayList = new ArrayList<>();
        supplementButtons = new ArrayList<>();
        supplementList.getChildren().clear();
        for(SupplementController supplement: supplements) {
            supplementArrayList.add(supplement);
            supplementButtons.add(supplement.createButton());
            supplementButtons.getLast().getStyleClass().add("custom-radio-button");
            supplementList.getChildren().add(supplementButtons.getLast());
        }
    }

    public void updateMagazineList() {
        magazineList.getChildren().clear();
        magazineButtons = new ArrayList<>();

        int listSize = magazineArrayList.size();
        for(int i = 0; i < listSize; i++) {
            magazineArrayList.get(i).updateView();
            magazineButtons.add(magazineArrayList.get(i).createButton());
            magazineButtons.get(i).setToggleGroup(magazineToggleGroup);
            magazineList.getChildren().add(magazineButtons.get(i));
        }
        magazineButtons.get(0).setText("[New Magazine]");
    }

    private void addListenerToMagazines() {
        for(RadioButton button: magazineButtons) {
            button.setOnAction(event -> {
                if(magazineDetails.getChildren().size() != 0) {
                    magazineDetails.getChildren().remove(0);
                }

                currentMagazine = findButtonInList(magazineButtons);
                
                magazineDetails.getChildren().add(magazineArrayList.get(currentMagazine).updatePreview());
            });
        }
    }

    public void serializeMagazines() {
        FileUtils.deleteFolderContents("../../data/serialized/magazine/");

        int size = magazineArrayList.size();
        for(int i = 1; i < size; i++) {
            magazineArrayList.get(i).serializeMagazine("../../data/serialized/");
        }

    }

    /**
     * Searches list of buttons and finds the selected button
     *
     * @param list of buttons to search through
     *
     * @return returns the index of the found selected button
     */
    private int findButtonInList(List<RadioButton> buttonList) {
        int foundIndex = 0;

        int listSize = buttonList.size();
        for(int i = 0; i < listSize; i++) {
            if(buttonList.get(i).isSelected()) {
                foundIndex = i;
                break;
            }
        }

        return foundIndex;
    }

    /**
     * Find selected buttons in a togglebutton list and returns the indexes of all selected buttons
     *
     * @param the list to search
     *
     * @return the list of indexes which correspond to a selected toggle button
     */
    private List<Integer> getSelected(List<ToggleButton> list) {
        List<Integer> selectedIndexes = new ArrayList<>();

        int listSize = list.size();
        for(int i = 0; i < listSize; i++) {
            if(list.get(i).isSelected()) {
                selectedIndexes.add(i);
            }
        }
        return selectedIndexes;
    }

}
