/*
 * -- ComboBoxC.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Wrapper class that holds an AssociateCustomerController and its alias.
 *  Used in ComboBoxes to store an AssociateCustomerController, but show the alias as its value.
 *
 */

package ComboBoxItem;

import Customer.AssociateCustomer.AssociateCustomerController;

public class ComboBoxItemC {
    private String alias;
    private AssociateCustomerController value;

    public ComboBoxItemC(String alias, AssociateCustomerController supplement) {
        this.alias = alias;
        this.value = supplement;
    }

    @Override
    public String toString() {
        return alias;
    }

    public AssociateCustomerController getValue() {
        return value;
    }
}
