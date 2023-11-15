package com.petepeterepeat;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicPanelUI;

public class SetCardUI extends BasicPanelUI {
    public SetCardUI() {
    }

    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2D = (Graphics2D)g;
        g2D.setClip(0, 0, c.getWidth(), c.getHeight());
        SetCard card = (SetCard)c;
        SetCardModel model = card.getModel();
        g2D.setColor(Color.BLACK);
        g2D.drawRect(0, 0, card.getWidth() - 1, card.getHeight() - 1);
        if (model != null) {
            int count = 0;
            switch (model.getCount()) {
                case 0:
                    count = 1;
                    break;
                case 1:
                    count = 2;
                    break;
                case 2:
                    count = 3;
            }

            int shapeWidth = (int)((double)card.getWidth() * 0.6);
            int shapeHeight = (int)((double)(card.getHeight() / 3) * 0.6);
            Shape shape = null;
            switch (model.getShape()) {
                case 0:
                    shape = new Rectangle2D.Double((double)(-shapeWidth / 2), (double)(-shapeHeight / 2), (double)shapeWidth, (double)shapeHeight);
                    break;
                case 1:
                    shape = new Ellipse2D.Double((double)(-shapeWidth / 2), (double)(-shapeHeight / 2), (double)shapeWidth, (double)shapeHeight);
                    break;
                case 2:
                    Polygon polygon = new Polygon();
                    polygon.addPoint(0, -shapeHeight / 2);
                    polygon.addPoint(shapeWidth / 2, shapeHeight / 2);
                    polygon.addPoint(-shapeWidth / 2, shapeHeight / 2);
                    shape = polygon;
            }

            Color color = null;
            switch (model.getColor()) {
                case 0:
                    color = Color.RED;
                    break;
                case 1:
                    color = Color.GREEN;
                    break;
                case 2:
                    color = Color.BLUE;
            }

            float filled = 0.0F;
            switch (model.getFilled()) {
                case 0:
                    filled = 0.0F;
                    break;
                case 1:
                    filled = 0.2F;
                    break;
                case 2:
                    filled = 1.0F;
            }

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2D.setStroke(new BasicStroke(3.0F));

            for(int object_nr = 0; object_nr < count; ++object_nr) {
                int x = card.getWidth() / 2;
                int y = (int)((double)(card.getHeight() / 2) + ((double)object_nr - (double)(count - 1) / 2.0) * (double)(shapeHeight + 20));
                g2D.translate(x, y);
                g2D.setColor(color);
                g2D.setComposite(AlphaComposite.getInstance(10, filled));
                g2D.fill((Shape)shape);
                g2D.setComposite(AlphaComposite.getInstance(10, 1.0F));
                g2D.draw((Shape)shape);
                g2D.translate(-x, -y);
            }

        }
    }
}
