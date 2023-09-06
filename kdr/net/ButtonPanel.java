package kdr.net;

import javax.swing.*;

class ButtonPanel extends JPanel {
    MainPanel mainWnd;
    JTextField ip;
    JButton connectButton;

    ButtonPanel(MainPanel mainWnd) {
        this.mainWnd = mainWnd;

        ip = new JTextField(15);
        connectButton = new JButton("Connect");

        ip.setText("localhost");

        add(ip);
        add(connectButton);

        connectButton.addActionListener(mainWnd);
    }

    String getIpAddress() {
        return ip.getText();
    }

    void setActionCommand(String s) {
        connectButton.setActionCommand(s);
        connectButton.setLabel(s);
    }

    void setIPAddress(String address) {
        ip.setText(address);
    }
}
