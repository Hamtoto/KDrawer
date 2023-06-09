package kdr.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class ColorAction extends AbstractAction {
    static class ColorIcon implements Icon {
        Color color;
        String name;
        Graphics componentGraphics;
        int componentX;
        int componentY;

        public ColorIcon(Color color, String name) {
            this.color = color;
            this.name = name;
        }

        public int getIconHeight() {
            return 16;
        }

        public int getIconWidth() {
            return 16;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            componentGraphics = g;
            if (name.equals("Color")) {
                componentX = x;
                componentY = y;
                g.setColor(color);
                g.fillRect(x, y, 16, 16);
                g.setColor(Color.black);
                Font oldFont = g.getFont();
                g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                g.drawString("C", x + 1, y + 15);
                g.setFont(oldFont);
            } else {
                g.setColor(color);
                g.fillRect(x, y, 16, 16);
            }
        }

        public void resetBackground(Color newColor) {
            color = newColor;
            componentGraphics.setColor(color);
            componentGraphics.fillRect(componentX, componentY, 16, 16);
            componentGraphics.setColor(Color.black);
            Font oldFont = componentGraphics.getFont();
            componentGraphics.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            componentGraphics.drawString("C", componentX + 1, componentY + 15);
            componentGraphics.setFont(oldFont);
        }
    }

    Color color;
    private DrawerView view;
    private ColorIcon icon;

    public ColorAction(String name, Color color, DrawerView view) {
        this.color = color;
        this.view = view;
        putValue(Action.ACTION_COMMAND_KEY, name);
        putValue(Action.NAME, name);
        putValue(Action.SMALL_ICON, icon = new ColorIcon(color, name));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Color")) {
            Color newColor = JColorChooser.showDialog(null,
                    "Color Chooser", color);
            color = newColor;
            icon.resetBackground(newColor);
            view.setCurrentColor(newColor);
        }
        view.setCurrentColor(color);
    }
}
