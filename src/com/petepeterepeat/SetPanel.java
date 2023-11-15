package com.petepeterepeat;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;

public class SetPanel extends JPanel implements ActionListener {
    private ArrayList setCards = SetCardModel.getSetCards();
    private ArrayList displayedCards = new ArrayList();
    private Component parentComponent;
    private long time;
    private long timeSum;
    private int count;
    private int faults;
    private static final int count_MAX = 50;

    public SetPanel(Component parentComponent) {
        this.parentComponent = parentComponent;
        Container cp = this;
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        Component westComponent = this;
        String border = "West";
        SetCard card = null;

        for(int i = 0; i < 3; ++i) {
            card = new SetCard();
            cp.add(card);
            layout.putConstraint("North", card, 10, "North", cp);
            layout.putConstraint("West", card, 10, border, (Component)westComponent);
            westComponent = card;
            border = "East";
            this.displayedCards.add(card);
        }

        layout.putConstraint("East", cp, 10, "East", card);
        JButton startButton = new JButton("Start");
        startButton.setMnemonic(83);
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
        startButton.registerKeyboardAction(this, "start", KeyStroke.getKeyStroke("S"), 2);
        cp.add(startButton);
        layout.putConstraint("North", startButton, 10, "South", card);
        layout.putConstraint("West", startButton, 10, "West", cp);
        JLabel setLabel = new JLabel("Set?");
        cp.add(setLabel);
        layout.putConstraint("North", setLabel, 15, "South", card);
        layout.putConstraint("West", setLabel, 10, "East", startButton);
        JButton yesButton = new JButton("Yes");
        yesButton.setMnemonic(89);
        yesButton.setActionCommand("yes");
        yesButton.addActionListener(this);
        yesButton.registerKeyboardAction(this, "yes", KeyStroke.getKeyStroke("Y"), 2);
        cp.add(yesButton);
        layout.putConstraint("North", yesButton, 10, "South", card);
        layout.putConstraint("West", yesButton, 10, "East", setLabel);
        JButton noButton = new JButton("No");
        noButton.setMnemonic(78);
        noButton.setActionCommand("no");
        noButton.addActionListener(this);
        noButton.registerKeyboardAction(this, "no", KeyStroke.getKeyStroke("N"), 2);
        cp.add(noButton);
        layout.putConstraint("North", noButton, 10, "South", card);
        layout.putConstraint("West", noButton, 10, "East", yesButton);
        layout.putConstraint("South", cp, 10, "South", noButton);
    }

    private boolean isSet(SetCardModel a, SetCardModel b, SetCardModel c) {
        if (!this.testEquality(a.getCount(), b.getCount(), c.getCount()) && !this.testInequality(a.getCount(), b.getCount(), c.getCount())) {
            return false;
        } else if (!this.testEquality(a.getColor(), b.getColor(), c.getColor()) && !this.testInequality(a.getColor(), b.getColor(), c.getColor())) {
            return false;
        } else if (!this.testEquality(a.getShape(), b.getShape(), c.getShape()) && !this.testInequality(a.getShape(), b.getShape(), c.getShape())) {
            return false;
        } else {
            return this.testEquality(a.getFilled(), b.getFilled(), c.getFilled()) || this.testInequality(a.getFilled(), b.getFilled(), c.getFilled());
        }
    }

    private boolean testEquality(int a, int b, int c) {
        return a == b && b == c;
    }

    private boolean testInequality(int a, int b, int c) {
        return a != b && b != c && a != c;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("start")) {
            this.randomCards();
            this.count = 0;
            this.faults = 0;
            this.timeSum = 0L;
            this.time = System.currentTimeMillis();
        } else {
            this.time = System.currentTimeMillis() - this.time;
            SetCardModel a = ((SetCard)this.displayedCards.get(0)).getModel();
            SetCardModel b = ((SetCard)this.displayedCards.get(1)).getModel();
            SetCardModel c = ((SetCard)this.displayedCards.get(2)).getModel();
            if (a != null) {
                boolean isSet = this.isSet(a, b, c);
                if (isSet != cmd.equals("yes")) {
                    this.time = 10000L;
                    ++this.faults;
                    JOptionPane.showMessageDialog(this.parentComponent, "Wrong: This is " + (isSet ? "a" : "no") + " Set!");
                }

                ++this.count;
                this.timeSum += this.time;
                System.out.println("time = " + this.time);
                if (this.count == 50) {
                    JOptionPane.showMessageDialog(this.parentComponent, "Congratulations!\n\nFaults: " + this.faults + " / " + 50 + "\n\tAverage time: " + this.timeSum / 50L + "ms");

                    for(int i = 0; i < this.displayedCards.size(); ++i) {
                        SetCard setCard = (SetCard)this.displayedCards.get(i);
                        setCard.setModel((SetCardModel)null);
                    }
                }

                this.randomCards();
                this.time = System.currentTimeMillis();
            }
        }
    }

    private void randomCards() {
        boolean shouldBeSet = Math.random() * 2.0 < 1.0;

        SetCardModel a;
        SetCardModel b;
        SetCardModel c;
        do {
            for(int i = 0; i < this.displayedCards.size(); ++i) {
                SetCard setCard = (SetCard)this.displayedCards.get(i);
                setCard.setModel((SetCardModel)this.setCards.get((int)(Math.random() * (double)this.setCards.size())));
            }

            a = ((SetCard)this.displayedCards.get(0)).getModel();
            b = ((SetCard)this.displayedCards.get(1)).getModel();
            c = ((SetCard)this.displayedCards.get(2)).getModel();
        } while(this.isSet(a, b, c) != shouldBeSet);

    }
}
