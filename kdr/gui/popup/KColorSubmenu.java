package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KColorSubmenu extends JMenu 
{
	public KColorSubmenu(DrawerView view) {
		super(DrawerView.Labels.get("Colors"));

		JMenuItem blackItem = new JMenuItem(DrawerView.Labels.get("Black"));
		blackItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.black));
		add(blackItem);

		JMenuItem redItem = new JMenuItem(DrawerView.Labels.get("Red"));
		redItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.red));
		add(redItem);

		JMenuItem greenItem = new JMenuItem(DrawerView.Labels.get("Green"));
		greenItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.green));
		add(greenItem);

		JMenuItem blueItem = new JMenuItem(DrawerView.Labels.get("Blue"));
		blueItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.blue));
		add(blueItem);

		JMenuItem chooserItem = new JMenuItem(DrawerView.Labels.get("Chooser"));
		chooserItem.addActionListener((evt) -> view.showColorChooser());
		add(chooserItem);
	}
}
