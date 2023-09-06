package kdr.gui.dlg;

import javax.swing.*;
import java.awt.*;

public class InfoDialog extends JDialog {
    private final int frameWidth = 400;
    private final int frameheight = 300;
    private InfoPanel infoPanel = new InfoPanel();

    public void InfoDialogFalseVisible() {
        this.setVisible(false);
    }

    public class InfoPanel extends JPanel {
        private JLabel[] labels = new JLabel[4];
        private JButton okBut = new JButton("확인");
        //private ImageIcon logoIcon = new ImageIcon(".../image/logo.png");
        //private Image logoImage = logoIcon.getImage();

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //g.drawImage(logoImage, frameWidth / 2 - 25, 20, 50, 50, null);
            g.drawRect(10, 10, 360, 240);
        }

        InfoPanel() {
            setLayout(null);
            setVisible(true);
            labels[0] = new JLabel("KDrawer");
            labels[0].setBounds(frameWidth / 2 - 25, 90, 50, 30);
            add(labels[0]);
            labels[1] = new JLabel("Project Email : k4kdrawer@gmail.com ");
            labels[1].setBounds(frameWidth / 2 - 110, 120, 220, 30);
            add(labels[1]);
            labels[2] = new JLabel("Department of Computer Science In");
            labels[2].setBounds(frameWidth / 2 - 105, 150, 210, 30);
            add(labels[2]);
            labels[3] = new JLabel("Busan University of Foreign Studies");
            labels[3].setBounds(frameWidth / 2 - 105, 180, 210, 30);
            add(labels[3]);

            okBut.setBounds(frameWidth / 2 - 30, 220, 60, 20);
            add(okBut);

            okBut.addActionListener(e -> InfoDialogFalseVisible());
        }
    }
    public InfoDialog(String title) {
        super((JFrame) null, title);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        setModal(true);
        setLocation(screenWidth / 2 - frameWidth / 2, screenHeight / 2 - frameheight / 2);
        setResizable(false);
        setSize(frameWidth, frameheight);
        setContentPane(infoPanel);
    }

}
