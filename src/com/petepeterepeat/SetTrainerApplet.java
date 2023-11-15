package com.petepeterepeat;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JApplet;

public class SetTrainerApplet extends JApplet {
    public SetTrainerApplet() {
    }

    public void init() {
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new SetPanel(this), "Center");
    }
}
