package kdr.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MouseGrabber extends MouseMotionAdapter 
{
	int freezeX;
	int freezeY;
	public MouseGrabber(int x,int y) {
		freezeX = x;
		freezeY = y;
		goTo(freezeX,freezeY);
	}
	public void mouseMoved(MouseEvent e) {
		goTo(freezeX,freezeY);
	}
	static private void goTo(int x,int y) {
		try
		{
			Robot robot = new Robot();
			robot.mouseMove(x,y);
		}
		catch (Exception ex)
		{
		}
	}
}
