package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KTextPopup extends KObjectPopup
{
	public KTextPopup(DrawerView view, String title) {
		super(view, title);

//////		
		JMenu colorMenu = new KColorSubmenu(view);
		popupPtr.add(colorMenu);

		JMenuItem fontItem = new JMenuItem("Font");
		fontItem.addActionListener((evt) -> view.changeFontForText());
		popupPtr.add(fontItem);

		JMenuItem editItem = new JMenuItem("Edit");
		editItem.addActionListener((evt) -> view.editText());
		popupPtr.add(editItem);
//////
	}
}

