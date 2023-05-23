package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KColorSubmenu extends JMenu 
{
	public KColorSubmenu(DrawerView view) {
		super("Colors");

		JMenuItem blackItem = new JMenuItem("Black");
		blackItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.black));
		add(blackItem);

		JMenuItem redItem = new JMenuItem("Red");
		redItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.red));
		add(redItem);

		JMenuItem greenItem = new JMenuItem("Green");
		greenItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.green));
		add(greenItem);

		JMenuItem blueItem = new JMenuItem("Blue");
		blueItem.addActionListener(
			(evt) -> view.setColorForSeletedFigure(Color.blue));
		add(blueItem);

		JMenuItem chooserItem = new JMenuItem("Chooser");
		chooserItem.addActionListener((evt) -> view.showColorChooser());
		add(chooserItem);
	}
}
