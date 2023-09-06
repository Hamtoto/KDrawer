package kdr.gui.popup;

import kdr.gui.*;
import javax.swing.*;

public class KCurvePopup extends KFigurePopup {
    public KCurvePopup(DrawerView view, String title, boolean fillFlag) {
        super(view, title, fillFlag);

        JMenuItem arrowItem = new JMenuItem(DrawerView.Labels.get("Arrow"));
        arrowItem.addActionListener((evt) -> view.setLineArrow());
        popupPtr.add(arrowItem);
    }
}
