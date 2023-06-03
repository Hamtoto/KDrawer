package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KTrianglePopup extends KFigurePopup
{
	static char degree = 176; 
	static int degrees[] = { 90, 60, 45, 30 };
	static double doubleDegrees[];
	JRadioButtonMenuItem angleItem[];
	public KTrianglePopup(DrawerView view, String title, boolean fillFlag) {
		super(view, title, fillFlag);
//////
		JMenu angleMenu = new JMenu(DrawerView.Labels.get("Angle"));
		popupPtr.add(angleMenu);

		ButtonGroup group = new ButtonGroup();
		angleItem = new JRadioButtonMenuItem[degrees.length];
		
		doubleDegrees = new double[degrees.length];

		for(int i = 0; i < angleItem.length; i++) {
			angleItem[i] = new JRadioButtonMenuItem(""+degrees[i]+(char)degree);
			double rad = Math.PI/360.0*degrees[i];
			doubleDegrees[i] = rad;
			angleItem[i].addActionListener(
				(evt) -> view.setAngleForTriangle(rad));
			angleMenu.add(angleItem[i]);
			group.add(angleItem[i]);
		}

/*
		JMenuItem angleLevel90Item = new JMenuItem("90"+(char)degree);
		angleLevel90Item.addActionListener(
			(evt) -> view.setAngleForTriangle(Math.PI/4.0));
		angleMenu.add(angleLevel90Item);

		JMenuItem angleLevel60Item = new JMenuItem("60"+(char)degree);
		angleLevel60Item.addActionListener(
			(evt) -> view.setAngleForTriangle(Math.PI/6.0));
		angleMenu.add(angleLevel60Item);
		
		JMenuItem angleLevel45Item = new JMenuItem("45"+(char)degree);
		angleLevel45Item.addActionListener(
			(evt) -> view.setAngleForTriangle(Math.PI/8.0));
		angleMenu.add(angleLevel45Item);
		
		JMenuItem angleLevel30Item = new JMenuItem("30"+(char)degree);
		angleLevel30Item.addActionListener(
			(evt) -> view.setAngleForTriangle(Math.PI/12.0));
		angleMenu.add(angleLevel30Item);
*/
//////
	}
	public void setAngle(double degree) {
		int index = 0;
		for(int i = 0; i < degrees.length; i++) {
			if (degree == doubleDegrees[i]) {
				index = i;
				break;
			}
		}
		angleItem[index].setSelected(true);
	}
}
