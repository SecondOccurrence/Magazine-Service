/*
 * -- Main.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Client program
 *
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.geometry.Insets;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import java.io.File;

import Supplement.SupplementController;
import Customer.AssociateCustomer.AssociateCustomerController;
import Customer.CustomerController;
import ComboBoxItem.ComboBoxItemS;
import Containers.*;
import InfoType.InfoType;
import FileUtils.FileUtils;


/**
 * Main class
 *
 * @author Josh Smith - 34195182
 */
public class Main extends Application {
    /**
     * the component holding all components
     */
    private BorderPane root;

    /**
     * class holding main components
     */
    private MainComponents mainComponents;

    /**
     * holds the index of the current class shown in the preview, as part of the classes list
     */
    private int currentClassIndex;
    
    /**
     * main content for aplication. includes the class list and preview
     */
    private HBox mainContent;

    /**
     * container for all class buttons
     */
    private VBox classList;

    /**
     * toggle group which belongs to all class buttons in the scene
     */
    private ToggleGroup classToggleGroup;
    
    /**
     * components for supplement classes
     */
    private SupplementContainer supplementContainer;

    /**
     * components for customer classes
     */
    private CustomerContainer customerContainer;

    /**
     * components for magazine menu
     */
    private MagazineContainer magazineContainer;

    /**
     * keeps track of the current class type thats selected
     */
    private InfoType infoBoxType;

    /**
     * unordered map holding all event handlers
     */
    private HashMap<String, EventHandler<ActionEvent>> mainEventHandlerMap;

    /**
     * @return initialises default values
     */
    public Main() {
        classToggleGroup = new ToggleGroup();
        mainComponents = new MainComponents();
        supplementContainer = new SupplementContainer(classToggleGroup);
        customerContainer = new CustomerContainer(classToggleGroup);
        magazineContainer = new MagazineContainer(classToggleGroup);
    }

    /**
     * @param the primary stage
     *
     * @return initialises the scene
n     */
    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();

        mainEventHandlerMap = createMainEventHandlers(primaryStage);

        mainComponents.getViewButton().fire();

        VBox headingContainer = mainComponents.getHeadingContainer();
        headingContainer.getStyleClass().add("header");
        root.setTop(headingContainer);

        mainComponents.addButtonEvent(mainComponents.getViewButton(), mainEventHandlerMap.get("viewButtonEvent"));
        mainComponents.addButtonEvent(mainComponents.getCreateButton(), mainEventHandlerMap.get("createButtonEvent"));
        mainComponents.addButtonEvent(mainComponents.getEditButton(), mainEventHandlerMap.get("editButtonEvent"));

        classList = new VBox(supplementContainer.getContainer(), customerContainer.getContainer());
        classList.setPadding(new Insets(30, 50, 30, 30));
        classList.setSpacing(45);

        supplementContainer.addButtonEvent(supplementContainer.getImportButton(), mainEventHandlerMap.get("importButtonEvent"));
        supplementContainer.addButtonEvent(supplementContainer.getRefreshButton(), mainEventHandlerMap.get("refreshSupplementButtonEvent"));
        supplementContainer.addButtonEventToList(mainEventHandlerMap.get("supplementButtonEvent"));
        supplementContainer.fireButton(0);

        customerContainer.addButtonEvent(customerContainer.getRefreshButton(), mainEventHandlerMap.get("refreshCustomerButtonEvent"));
        customerContainer.addButtonEventToList(mainEventHandlerMap.get("customerButtonEvent"));

        mainContent = new HBox(classList, mainComponents.getPreviewContainer());
        HBox.setHgrow(classList, Priority.ALWAYS);
        HBox.setHgrow(mainComponents.getPreviewContainer(), Priority.ALWAYS);
        mainContent.setPadding(new Insets(30, 30, 30, 30));
        root.setCenter(mainContent);

        mainComponents.addButtonEvent(mainComponents.getSaveButton(), mainEventHandlerMap.get("saveButtonEvent"));
        mainComponents.addButtonEvent(mainComponents.getDeleteButton(), mainEventHandlerMap.get("deleteButtonEvent"));

        Scene scene = new Scene(root, 960, 840);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Magazine Service - 34195182");
        primaryStage.show();
    }

    /**
     * main function
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Defines all main event handlers used throughout the program
     *
     * @param the stage
     *
     * @return creates all event handlers
     */
    private HashMap<String, EventHandler<ActionEvent>> createMainEventHandlers(Stage primaryStage) {
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                supplementContainer.serializeButtons();
                customerContainer.serializeButtons();
                magazineContainer.serializeMagazines();
                primaryStage.close();
            }
        });

        HashMap<String, EventHandler<ActionEvent>> eventMap = new HashMap<>();

        eventMap.put("viewButtonEvent", event -> {
            root.setCenter(mainContent);

            int previewComponentsSize = mainComponents.getPreviewContainer().getChildren().size();
            if(previewComponentsSize >= 3) {
                mainComponents.getPreviewContainer().getChildren().remove(1);
            }

            if(infoBoxType == InfoType.SUPPLEMENT) {
                supplementContainer.setEditable(false);
                if(previewComponentsSize >= 3) {
                    mainComponents.getPreviewContainer().getChildren().add(1, supplementContainer.updatePreview(currentClassIndex));
                }
            }
            else {
                customerContainer.setEditable(false);
                if(previewComponentsSize == 3) {
                    VBox newPreview = customerContainer.updatePreview(currentClassIndex, supplementContainer.getAvailableSupplements(), customerContainer.getAssociates());
                    if(newPreview.getChildren().size() == 0) {
                        newPreview = customerContainer.updatePreview(currentClassIndex, supplementContainer.getAvailableSupplements());
                    }
                    mainComponents.getPreviewContainer().getChildren().add(1, newPreview);
                }
            }
        });

        eventMap.put("createButtonEvent", event -> {
            magazineContainer.updateContainer(customerContainer.getCustomers(), supplementContainer.getAvailableSupplements());
            root.setCenter(magazineContainer.getContainer());
        });

        eventMap.put("editButtonEvent", event -> {
            root.setCenter(mainContent);

            int previewComponentSize = mainComponents.getPreviewContainer().getChildren().size();
            if(infoBoxType == InfoType.SUPPLEMENT || previewComponentSize >=3) {
                mainComponents.getPreviewContainer().getChildren().remove(1);
            }

            if(infoBoxType == InfoType.SUPPLEMENT) {
                supplementContainer.setEditable(true);
                if(previewComponentSize == 3) {
                    mainComponents.getPreviewContainer().getChildren().add(1, supplementContainer.updatePreview(currentClassIndex));
                }
            }
            else {
                customerContainer.setEditable(true);
                if(previewComponentSize == 3) {
                    VBox newPreview = customerContainer.updatePreview(currentClassIndex, supplementContainer.getAvailableSupplements(), customerContainer.getAssociates());
                    if(newPreview.getChildren().size() == 0) {
                        newPreview = customerContainer.updatePreview(currentClassIndex, supplementContainer.getAvailableSupplements());
                    }
                    mainComponents.getPreviewContainer().getChildren().add(1, newPreview);
                }
            }
        });

        eventMap.put("importSupplementButtonEvent", event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose file to import");
            fileChooser.setInitialDirectory(new File("../../data/serialized/supplements"));
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Data files", "*.dat"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if(selectedFile != null) {
                SupplementController newSupplement = FileUtils.fetchSupplement(selectedFile.getAbsolutePath());
                newSupplement.updateView();
                supplementContainer.addNewClass(newSupplement);
            }
        });

        eventMap.put("refreshSupplementButtonEvent", event -> {
            supplementContainer = new SupplementContainer(classToggleGroup);
            supplementContainer.addButtonEvent(supplementContainer.getImportButton(), mainEventHandlerMap.get("importSupplementButtonEvent"));
            supplementContainer.addButtonEvent(supplementContainer.getRefreshButton(), mainEventHandlerMap.get("refreshSupplementButtonEvent"));
            supplementContainer.addButtonEventToList(mainEventHandlerMap.get("supplementButtonEvent"));
            classList.getChildren().remove(0);
            classList.getChildren().add(0, supplementContainer.getContainer());
        });

        eventMap.put("refreshCustomerButtonEvent", event -> {
            customerContainer = new CustomerContainer(classToggleGroup);
            customerContainer.addButtonEvent(customerContainer.getImportButton(), mainEventHandlerMap.get("importCustomerButtonEvent"));
            customerContainer.addButtonEvent(customerContainer.getRefreshButton(), mainEventHandlerMap.get("refreshCustomerButtonEvent"));
            customerContainer.addButtonEventToList(mainEventHandlerMap.get("customerButtonEvent"));
            classList.getChildren().remove(1);
            classList.getChildren().add(1, customerContainer.getContainer());
        });

        eventMap.put("supplementButtonEvent", event -> {
            if(mainComponents.getPreviewContainer().getChildren().size() >= 3) {
                mainComponents.getPreviewContainer().getChildren().remove(1);
            }

            currentClassIndex = findButtonInList(supplementContainer.getButtons());
            infoBoxType = InfoType.SUPPLEMENT;

            if(currentClassIndex == 0) {
                mainComponents.getSaveButton().setText("Create");
            }
            else {
                mainComponents.getSaveButton().setText(" Save ");
            }

            mainComponents.getPreviewContainer().getChildren().add(1, supplementContainer.updatePreview(currentClassIndex));
            mainComponents.getViewButton().fire();
        });

        eventMap.put("customerButtonEvent", event -> {
            int previewContainerSize = mainComponents.getPreviewContainer().getChildren().size();
            if(infoBoxType == InfoType.SUPPLEMENT || previewContainerSize >= 3) {
                mainComponents.getPreviewContainer().getChildren().remove(1);
            }

            currentClassIndex = findButtonInList(customerContainer.getButtons());
            infoBoxType = InfoType.CUSTOMER;

            if(currentClassIndex <= 1) {
                mainComponents.getSaveButton().setText("Create");
            }
            else {
                mainComponents.getSaveButton().setText(" Save ");
            }

            customerContainer.addNewSupplementEvent(currentClassIndex, mainEventHandlerMap.get("addNewSubscription"));
            if(previewContainerSize >= 3) { 
                VBox newPreview = customerContainer.updatePreview(currentClassIndex, supplementContainer.getAvailableSupplements(), customerContainer.getAssociates());
                if(newPreview.getChildren().size() == 0) {
                    newPreview = customerContainer.updatePreview(currentClassIndex, supplementContainer.getAvailableSupplements());
                }
                mainComponents.getPreviewContainer().getChildren().add(1, newPreview);
            }

            mainComponents.getViewButton().fire();
        });

        eventMap.put("saveButtonEvent", event -> {
            if(infoBoxType == InfoType.SUPPLEMENT) {
                if(currentClassIndex != 0) {
                    supplementContainer.updateClass(currentClassIndex);
                }
                else {
                    VBox previewSupplementBox = (VBox)mainComponents.getPreviewContainer().getChildren().get(1);
                    String name = ((TextField)((HBox)previewSupplementBox.getChildren().get(0)).getChildren().get(1)).getText();
                    double cost = Double.parseDouble(((TextField)((HBox)previewSupplementBox.getChildren().get(1)).getChildren().get(1)).getText());
                    SupplementController newSupplement = new SupplementController();
                    newSupplement.updateView(name, cost);
                    supplementContainer.addNewClass(newSupplement);
                    supplementContainer.addButtonEventToList(mainEventHandlerMap.get("supplementButtonEvent"));
                }
            }
            else {
                if(currentClassIndex > 1) {
                    customerContainer.updateClass(currentClassIndex);
                    customerContainer.addButtonEventToList(mainEventHandlerMap.get("customerButtonEvent"));
                }
                else {
                    CustomerController newClass = customerContainer.retrieveNewClass(currentClassIndex);

                    newClass.updateView();
                    customerContainer.addNewClass(newClass);
                    customerContainer.addButtonEventToList(mainEventHandlerMap.get("customerButtonEvent"));
                }
            }
        });

        eventMap.put("deleteButtonEvent", event -> {
            if((infoBoxType == InfoType.SUPPLEMENT && currentClassIndex == 0) || (infoBoxType == InfoType.CUSTOMER && currentClassIndex <= 1)) {
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm delete");
            confirm.setHeaderText("Delete file");
            confirm.setContentText("Are you sure you wish to delete this class?");
            Optional<ButtonType> result = confirm.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                if(infoBoxType == InfoType.SUPPLEMENT) {
                    supplementContainer.removeButton(currentClassIndex);
                    supplementContainer.fireButton(0);
                }
                else {
                    customerContainer.removeButton(currentClassIndex);
                    customerContainer.fireButton(0);
                }
            }
        });

        eventMap.put("addNewSubscription", event -> {
            customerContainer.addNewSubscription(currentClassIndex);
            mainComponents.getPreviewContainer().getChildren().remove(1);
            mainComponents.getPreviewContainer().getChildren().add(1, customerContainer.updatePreview(currentClassIndex, supplementContainer.getAvailableSupplements()));
        });

        return eventMap;
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
}
