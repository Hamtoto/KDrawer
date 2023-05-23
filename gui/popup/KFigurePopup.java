package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KFigurePopup extends KObjectPopup
{
	private static final long serialVersionUID = 1311846301252431755L;
//////		
	static int nThickness = 4;
	protected JMenuItem fillItem;
	protected JMenuItem resizeItem;
	protected JRadioButtonMenuItem thichnessItem[];
//////		

	public KFigurePopup(DrawerView view, String title, boolean fillFlag) {
		super(view,title);
//////		
		resizeItem = new JMenuItem("Resize");
		resizeItem.addActionListener((evt) -> view.resizeFigure());
		popupPtr.add(resizeItem);

		JMenu colorMenu = new KColorSubmenu(view);
		popupPtr.add(colorMenu);
//////
		JMenu thicknessMenu = new JMenu("Thickness");
		popupPtr.add(thicknessMenu);

		ButtonGroup group = new ButtonGroup();
		thichnessItem = new JRadioButtonMenuItem[nThickness];
		for(int i = 0; i < thichnessItem.length; i++) {
			thichnessItem[i] = new JRadioButtonMenuItem(""+(i+1));
			int thichness = i+1;
			thichnessItem[i].addActionListener(
				(evt) -> view.setThicknessForSeletedFigure(thichness));
			group.add(thichnessItem[i]);
			thicknessMenu.add(thichnessItem[i]);
		}
		thichnessItem[0].setSelected(true);

		/*JMenuItem styleItem = new JMenuItem("Style");
		styleItem.addActionListener((evt) -> view.setStyle());
		popupPtr.add(styleItem);*/
		
		if (fillFlag == true)
		{
			fillItem = new JMenuItem("Fill");
			fillItem.addActionListener((evt) -> view.fillFigure());
			popupPtr.add(fillItem);
		}		
	}
	public void setThicknessSeletion(int n) {
		thichnessItem[n-1].setSelected(true);
	}
}

