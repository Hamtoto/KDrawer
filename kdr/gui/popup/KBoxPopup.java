package kdr.gui.popup;

import kdr.gui.*;
import javax.swing.*;

public class KBoxPopup extends KFigurePopup {
    JCheckBoxMenuItem roundItem;

    public KBoxPopup(DrawerView view, String title, boolean fillFlag) {
        super(view, title, fillFlag);

        roundItem = new JCheckBoxMenuItem(DrawerView.Labels.get("Round"));
        roundItem.addActionListener((evt) -> view.boxToRoundBox());
        popupPtr.add(roundItem);
        roundItem.setSelected(false);
    }

    public void setRoundItem(boolean roundFlag) {
        roundItem.setSelected(roundFlag);
    }
}
