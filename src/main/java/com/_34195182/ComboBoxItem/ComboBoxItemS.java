/*
 * -- ComboBoxAlias.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 24/05/2024
 *
 * DESCRIPTION: Wrapper class that holds a SupplementController and its alias.
 *  Used in ComboBoxes to store a SupplementController, but show the alias as its value.
 *
 */

package ComboBoxItem;

import Supplement.SupplementController;

public class ComboBoxItemS {
    private String alias;
    private SupplementController value;

    public ComboBoxItemS(String alias, SupplementController supplement) {
        this.alias = alias;
        this.value = supplement;
    }

    @Override
    public String toString() {
        return alias;
    }

    public SupplementController getValue() {
        return value;
    }
}
