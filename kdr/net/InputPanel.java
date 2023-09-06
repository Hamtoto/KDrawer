package kdr.net;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class InputPanel extends JPanel {
    MainPanel mainWnd;
    private JTextField textInput;

    InputPanel(MainPanel mainWnd) {
        setLayout(new GridLayout(1, 1));
        this.mainWnd = mainWnd;

        textInput = new JTextField();
        Font font = new Font(textInput.getFont().getFamily(), Font.PLAIN, 16);
        textInput.setFont(font);
        textInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ev) {
                int keyCode = ev.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    String msg = textInput.getText().trim();
                    Message newMessage = new Message("I", msg);
                    mainWnd.writeText(newMessage);
                    mainWnd.sendLine(msg);
                    textInput.setText("");
                }
            }
        });
        textInput.setBorder(BorderFactory.createLoweredBevelBorder());

        add(textInput);
    }

    void setEditable(boolean flag) {
        textInput.setEditable(flag);
    }
}
