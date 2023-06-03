package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;

public class KObjectPopup extends KPopup {
	public KObjectPopup(DrawerView view,String title) {
		super(title);
		
		JMenuItem deleteItem = new JMenuItem(DrawerView.Labels.get("Delete"));
		deleteItem.addActionListener((evt) -> view.deleteFigure());
		popupPtr.add(deleteItem);

		JMenuItem copyItem = new JMenuItem(DrawerView.Labels.get("Copy"));
		copyItem.addActionListener((evt) -> view.copyFigure());
		popupPtr.add(copyItem);
	}	
}
