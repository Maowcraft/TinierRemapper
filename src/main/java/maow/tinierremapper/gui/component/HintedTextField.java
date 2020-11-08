/*
 * Copyright (c) 2020 Maow, MIT License.
 */

package maow.tinierremapper.gui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

// Thanks StackOverflow.
public class HintedTextField extends JTextField implements FocusListener {
    private final String hint;
    private boolean showingHint;

    public HintedTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}
