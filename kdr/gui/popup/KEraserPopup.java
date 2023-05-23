package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KEraserPopup extends KPopup {
	public KEraserPopup(DrawerView view,String title) {
		super(title);
		
		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener((evt) -> view.deleteFigure());
		popupPtr.add(deleteItem);
	}
}