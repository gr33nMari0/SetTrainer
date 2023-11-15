package com.petepeterepeat;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class SetCard extends JPanel {
    private SetCardModel model = null;

    public SetCard() {
        this.setUI(new SetCardUI());
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(200, 300));
    }

    public SetCardModel getModel() {
        return this.model;
    }

    public void setModel(SetCardModel model) {
        this.model = model;
        this.repaint();
    }
}
