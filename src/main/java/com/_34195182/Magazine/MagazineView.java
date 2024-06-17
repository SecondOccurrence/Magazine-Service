/*
 * -- MagazineView.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Magazine view's class file
 *
 */

package Magazine;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;

import java.util.List;

import Customer.CustomerController;

/**
 * View component, following the MVC architecture, of a Magazine class.
 *
 * This class presents the magazine data to the user
 */
public class MagazineView {
    private Label nameHeading, costHeading;

    private TextField nameValue, costValue;

    private VBox container;

    public MagazineView() {
        nameHeading = new Label("Magazine Title:");
        nameValue = new TextField();

        costHeading = new Label("Weekly Cost:");
        costValue = new TextField();
    }

    public String getTitle() {
        String title = nameValue.getText();
        return title;
    }

    /**
     * Validates the cost in the view and returns it
     *
     * @return returns the cost of the supplement
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    public Double getWeeklyCost() throws NumberFormatException, IllegalArgumentException {
        double cost = Double.parseDouble(costValue.getText());
        if(cost < 0) {
            throw new IllegalArgumentException();
        }
        return cost;
    }

    public void updateView(String title, double cost) {
        nameValue.setText(title);
        costValue.setText(Double.toString(cost));
    }

    public VBox updatePreview() {
        VBox title = new VBox(nameHeading, nameValue);
        VBox cost = new VBox(costHeading, costValue);
        container = new VBox(title, cost);
        container.setSpacing(15);
        return container;
    }

    public RadioButton createButton() {
        return new RadioButton(nameValue.getText());
    }
}
