package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KStarPopup extends KFigurePopup
{
	JMenuItem changeShapeItem;
	public KStarPopup(DrawerView view, String title, boolean fillFlag) {
		super(view, title, fillFlag);

		changeShapeItem = new JMenuItem("changeShape");
		changeShapeItem.addActionListener((evt) -> view.changeShape());
		popupPtr.add(changeShapeItem);
	}
	public void setChangeShapeLabel(String name) {
		changeShapeItem.setLabel(name);
	}
}
