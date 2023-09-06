package kdr.gui.popup;

import kdr.gui.*;
import javax.swing.*;

public class KEraserPopup extends KPopup {
    public KEraserPopup(DrawerView view, String title) {
        super(title);

        JMenuItem deleteItem = new JMenuItem(DrawerView.Labels.get("Delete"));
        deleteItem.addActionListener((evt) -> view.deleteFigure());
        popupPtr.add(deleteItem);
    }
}