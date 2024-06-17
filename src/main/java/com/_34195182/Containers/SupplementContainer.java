package Containers;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.ArrayList;

import Supplement.SupplementController;
import FileUtils.FileUtils;

public class SupplementContainer extends ClassContainer<SupplementController> {
    public SupplementContainer(ToggleGroup group) {
        super(group);
    }

    public VBox updatePreview(int index) {
        return list.get(index).updatePreview();
    }

    public List<SupplementController> getAvailableSupplements() {
        List<SupplementController> newList = list.subList(1, list.size());
        return newList;
    }

    @Override
    public void setEditable(boolean editable) {
        for(SupplementController supplement: list) {
            supplement.setEditable(editable);
        }
    }

    /**
     * Adds a new supplement to the application lists
     *
     * @param the supplement to add to the respective list and button list
     *  (Assumes the SupplementController's view has been set equal to the SupplementController's model variables)
     * @return the supplement is added to the list. a new button is created and the visual list is updated for the user
     *
     */
    @Override
    public void addNewClass(SupplementController newSupplement) {
        if(newSupplement.updateName() && newSupplement.updateWeeklyCost()) {
            list.add(newSupplement);

            RadioButton newButton = newSupplement.createButton();
            newButton.setToggleGroup(toggleGroup);
            buttons.add(newButton);
            buttons.getLast().getStyleClass().add("custom-radio-button");

            buttonsContainer.getChildren().add(buttons.getLast());
        }  
    }
    
    @Override
    public void removeButton(int index) {
        FileUtils.deleteSerializedFile("../../data/serialized/supplements/", list.get(index).getUID());
        list.remove(index);
        buttons.remove(index);
        buttonsContainer.getChildren().remove(index);
    }
    
    @Override
    public void updateClass(int index) {
        SupplementController supplement = list.get(index);
        supplement.updateName();
        supplement.updateWeeklyCost();

        buttons.set(index, supplement.createButton());
        buttons.get(index).setToggleGroup(toggleGroup);
        buttons.get(index).getStyleClass().add("custom-radio-button");
        buttonsContainer.getChildren().remove(index);
        buttonsContainer.getChildren().add(index, buttons.get(index));
    }

    @Override
    public void serializeButtons() {
        FileUtils.deleteFolderContents("../../data/serialized/supplements/");

        int size = list.size();
        for(int i = 1; i < size; i++) {
            list.get(i).serializeSupplement("../../data/serialized/");
        }
    }

    @Override
    protected void createClassList() {
        buttonsContainer = new VBox();
        buttons = new ArrayList<>();

        list = FileUtils.fetchSupplements("../../data/serialized/supplements/");
        list.addFirst(new SupplementController());

        buttons.add(list.get(0).createButton());
        buttons.get(0).setText("[New Supplement]");
        buttons.get(0).setToggleGroup(toggleGroup);
        buttons.get(0).getStyleClass().add("custom-radio-button");
        buttonsContainer.getChildren().add(buttons.get(0));

        int listSize = list.size();
        for(int i = 1; i < listSize; i++) {
            buttons.add(list.get(i).createButton());
            buttons.get(i).setToggleGroup(toggleGroup);
            buttons.get(i).getStyleClass().add("custom-radio-button");
            buttonsContainer.getChildren().add(buttons.get(i));
        }
    }

}
