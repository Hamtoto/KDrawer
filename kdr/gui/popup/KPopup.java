package kdr.gui.popup;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KPopup implements Serializable
{
	private static final long serialVersionUID = -6603717701254475241L;
	JPopupMenu popupPtr;

	public KPopup(String title) {
		popupPtr = new JPopupMenu();
		popupPtr.add(title);
		popupPtr.addSeparator();
	}
	public void popup(JPanel view,int x,int y)
	{
		popupPtr.show(view,x,y);
	}
}
