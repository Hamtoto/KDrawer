package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KScribblePopup extends KFigurePopup
{
	public KScribblePopup(DrawerView view, String title, boolean fillFlag) {
		super(view, title, fillFlag);

		popupPtr.remove(resizeItem);
	}
}
