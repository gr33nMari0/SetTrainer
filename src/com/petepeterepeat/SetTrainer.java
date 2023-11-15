package com.petepeterepeat;

import java.awt.Container;
import javax.swing.JFrame;

public class SetTrainer extends JFrame {
    public static void main(String[] args) {
        SetTrainer trainer = new SetTrainer();
        trainer.setDefaultCloseOperation(3);
        trainer.setVisible(true);
    }

    public SetTrainer() {
        super("SetTrainer");
        Container cp = this.getContentPane();
        cp.add(new SetPanel(this));
        this.pack();
    }
}
