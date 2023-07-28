package kdr.gui.dlg;

import kdr.gui.DrawerView;

import javax.swing.*;

public class InfoDialog extends JDialog  {
    InfoPanel panel;
    public InfoDialog(String title, DrawerView view){
        setTitle(title);
        setLocation(200,300);
        setSize(400,300);
        panel = new InfoPanel();
        this.add(panel);

    }
    public class InfoPanel extends JPanel{
        JLabel label1;
        InfoPanel(){
            setLayout(null);
            setVisible(true);
            label1 = new JLabel("");
        }
    }
}

