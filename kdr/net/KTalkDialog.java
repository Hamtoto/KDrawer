package kdr.net;

import kdr.gui.*;

import java.awt.*;
import javax.swing.*;

public class KTalkDialog extends JDialog
{
	static int WIDTH = 400;
	static int HEIGHT = 600;
	MainPanel mainWnd;
    public KTalkDialog(DrawerFrame owner) {
        super(owner, "KTalk");
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        Container contentPane = getContentPane();
		mainWnd = new MainPanel(owner);
        contentPane.add(mainWnd);
    }
	public void sendFigures() {
		mainWnd.sendFigures();
	}
	public void sendString(String s) {
		mainWnd.sendString(s);
	}
}