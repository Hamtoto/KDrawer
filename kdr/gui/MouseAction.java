package kdr.gui;

import javax.swing.*;
import java.awt.event.*;

class MouseAction extends AbstractAction {
    private DrawerView view;

    public MouseAction(String name, Icon icon, DrawerView view) {
        putValue(Action.NAME, name);
        putValue(Action.SMALL_ICON, icon);
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {
        view.setWhatToDraw(DrawerView.ID_MOUSE);
    }
}
