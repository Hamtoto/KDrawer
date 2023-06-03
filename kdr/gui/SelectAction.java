package kdr.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class SelectAction extends AbstractAction
{
	private DrawerView view;
	public SelectAction(String name,int mnemonic,Icon icon,DrawerView view,int id) {
		putValue(Action.NAME,DrawerView.Labels.get(name));
		putValue(Action.SMALL_ICON,icon);
		String names[] = name.split(" ");
		putValue(Action.SHORT_DESCRIPTION,DrawerView.figureTypes.get(names[0]));
		putValue(Action.MNEMONIC_KEY,mnemonic);
		putValue("id",id);
		this.view = view;
	}
	public void actionPerformed(ActionEvent e) {
		int id = (int)getValue("id");
		view.setWhatToDraw(id);
	}
}

