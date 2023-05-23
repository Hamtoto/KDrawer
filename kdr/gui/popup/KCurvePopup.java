package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KCurvePopup extends KFigurePopup
{
	public KCurvePopup(DrawerView view, String title, boolean fillFlag) {
		super(view, title, fillFlag);

		JMenuItem arrowItem = new JMenuItem("Arrow");
		arrowItem.addActionListener((evt) -> view.setLineArrow());
		popupPtr.add(arrowItem);
	}
}
