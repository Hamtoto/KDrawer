package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KLinesPopup extends KFigurePopup
{
	JMenuItem doorItem;
	public KLinesPopup(DrawerView view, String title, boolean fillFlag) {
		super(view, title, fillFlag);

//////	
		fillItem.setEnabled(false);
//////		

		JMenuItem arrowItem = new JMenuItem("Arrow");
		arrowItem.addActionListener((evt) -> view.setLineArrow());
		popupPtr.add(arrowItem);

		doorItem = new JMenuItem("Close");
		doorItem.addActionListener((evt) -> view.changeLinesDoor());
		popupPtr.add(doorItem);
	}
	public void setDoorLabel(String name) {
		doorItem.setLabel(name);
	}
	public void setEnableDoorItem(boolean flag) {
		doorItem.setEnabled(flag);
	}
	public void setEnableFillItem(boolean flag) {
		fillItem.setEnabled(flag);
	}
}
