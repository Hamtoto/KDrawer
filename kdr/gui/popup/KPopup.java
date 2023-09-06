package kdr.gui.popup;

import kdr.gui.*;
import javax.swing.*;
import java.io.*;

public class KPopup implements Serializable {
    JPopupMenu popupPtr;

    public KPopup(String title) {
        popupPtr = new JPopupMenu();
        popupPtr.add(DrawerView.Labels.get(title));
        popupPtr.addSeparator();
    }

    public void popup(JPanel view, int x, int y) {
        popupPtr.show(view, x, y);
    }
}
