package kdr.net;

import java.awt.*;
import javax.swing.*;
import java.util.*;

class ChatPanel extends JPanel {
    MainPanel mainWnd;
    ArrayList<Message> text = new ArrayList<Message>();
    ArrayList<String> currentTime = new ArrayList<String>();
    Font font = null;
    Font timeFont = null;
    FontMetrics fm = null;
    static int START_Y = 40;
    static int MAX_WIDTH = 230;
    static int BOX_GAP = 6;
    static int LEFT_GAP = 10;
    static int RIGHT_GAP = 40;
    static int SCROLL_GAP = 20;
    static int LEFT_STRING_X = BOX_GAP + LEFT_GAP;
    static int RIGHT_STRING_X
            = KTalkDialog.WIDTH - MAX_WIDTH - RIGHT_GAP - SCROLL_GAP;
    static int LINE_GAP = 20;
    static int BLOCK_GAP = 20;
    static int TIME_GAP = 40;
    static int SCROLL_DELTA = 100;
    int fontHeight;
    int fontAscent;
    int currentY;

    ChatPanel(MainPanel mainWnd) {
        this.mainWnd = mainWnd;
    }

    void writeText(Message msg, String time) {
        text.add(msg);
        currentTime.add(time);
        repaint();
    }

    private void fillLeftTextBaloon(Graphics g, String s, String time) {
        String[] word = s.split(" ");
        int i = 0;
        String line = word[0];
        int x1 = LEFT_GAP;
        int y1 = currentY - fontAscent;
        int width = 0;
        int height = BOX_GAP / 2;
        int cY = currentY;

        while (i < word.length) {
            i++;
            if (i == word.length) break;
            int len = fm.stringWidth(line);
            int len2 = fm.stringWidth(" " + word[i]);
            if ((len + len2) < MAX_WIDTH) {
                line = line + " " + word[i];
            } else {
                if (width < len) width = len;
                height = height + LINE_GAP;
                cY = cY + LINE_GAP;
                line = word[i];
            }
        }
        int len = fm.stringWidth(line);
        if (width < len) width = len;
        width = width + 2 * BOX_GAP;
        height = height + LINE_GAP;
        cY = currentY + LINE_GAP;
        cY = currentY + BLOCK_GAP;
        g.setColor(Color.white);
        g.fillRoundRect(x1, y1, width, height, BOX_GAP, BOX_GAP);
        g.setColor(Color.black);
        g.drawRoundRect(x1, y1, width, height, BOX_GAP, BOX_GAP);
        g.setFont(timeFont);
        g.drawString(time, x1 + width + BOX_GAP, y1 + height - 4);
        g.setFont(font);
    }

    private void drawLeftAlignedMessage(Graphics g, String s, String time) {
        String[] word = s.split(" ");
        int i = 0;
        String line = word[0];
        int x1 = LEFT_GAP;
        int y1 = currentY - fontAscent;
        int width = 0;
        int height = BOX_GAP / 2;

        fillLeftTextBaloon(g, s, time);

        while (i < word.length) {
            i++;
            if (i == word.length) break;
            int len = fm.stringWidth(line);
            int len2 = fm.stringWidth(" " + word[i]);
            if ((len + len2) < MAX_WIDTH) {
                line = line + " " + word[i];
            } else {
                if (width < len) width = len;
                height = height + LINE_GAP;
                g.drawString(line, LEFT_STRING_X, currentY);
                currentY = currentY + LINE_GAP;
                line = word[i];
            }
        }
        int len = fm.stringWidth(line);
        if (width < len) width = len;
        width = width + 2 * BOX_GAP;
        height = height + LINE_GAP;
        g.drawString(line, LEFT_STRING_X, currentY);
        currentY = currentY + LINE_GAP;
        currentY = currentY + BLOCK_GAP;
    }

    private void fillRightTextBaloon(Graphics g, String s, String time) {
        String[] word = s.split(" ");
        int i = 0;
        String line = word[0];
        int y1 = currentY - fontAscent;
        int width = 0;
        int height = BOX_GAP / 2;
        int cY = currentY;
        boolean multiLineFlag = false;

        while (i < word.length) {
            i++;
            if (i == word.length) break;
            int len = fm.stringWidth(line);
            int len2 = fm.stringWidth(" " + word[i]);
            if ((len + len2) < MAX_WIDTH) {
                line = line + " " + word[i];
            } else {
                multiLineFlag = true;
                if (width < len) width = len;
                height = height + LINE_GAP;
                cY = cY + LINE_GAP;
                line = word[i];
            }
        }

        int len = fm.stringWidth(line);
        if (width < len) width = len;
        width = width + 2 * BOX_GAP;
        height = height + LINE_GAP;
        cY = currentY + LINE_GAP;
        cY = currentY + BLOCK_GAP;
        if (multiLineFlag) {
            width = MAX_WIDTH + 2 * BOX_GAP;
            g.setColor(Color.lightGray);
            g.fillRoundRect(RIGHT_STRING_X - BOX_GAP + LEFT_GAP, y1, width, height, BOX_GAP, BOX_GAP);
            g.setColor(Color.black);
            g.drawRoundRect(RIGHT_STRING_X - BOX_GAP + LEFT_GAP, y1, width, height, BOX_GAP, BOX_GAP);
            g.setFont(timeFont);
            g.drawString(time, RIGHT_STRING_X - BOX_GAP + LEFT_GAP - TIME_GAP - 4, y1 + height - 4);
        } else {
            g.setColor(Color.lightGray);
            g.fillRoundRect(RIGHT_STRING_X + MAX_WIDTH - len + 4, y1, width, height, BOX_GAP, BOX_GAP);
            g.setColor(Color.black);
            g.drawRoundRect(RIGHT_STRING_X + MAX_WIDTH - len + 4, y1, width, height, BOX_GAP, BOX_GAP);
            g.setFont(timeFont);
            g.drawString(time, RIGHT_STRING_X + MAX_WIDTH - len - TIME_GAP, y1 + height - 4);
        }
        g.setFont(font);
    }

    private void drawRightAlignedMessage(Graphics g, String s, String time) {
        String[] word = s.split(" ");
        int i = 0;
        String line = word[0];
        int y1 = currentY - fontAscent;
        int width = 0;
        int height = BOX_GAP / 2;
        boolean multiLineFlag = false;

        fillRightTextBaloon(g, s, time);

        while (i < word.length) {
            i++;
            if (i == word.length) break;
            int len = fm.stringWidth(line);
            int len2 = fm.stringWidth(" " + word[i]);
            if ((len + len2) < MAX_WIDTH) {
                line = line + " " + word[i];
            } else {
                multiLineFlag = true;
                if (width < len) width = len;
                height = height + LINE_GAP;
                g.drawString(line, RIGHT_STRING_X + LEFT_GAP, currentY);
                currentY = currentY + LINE_GAP;
                line = word[i];
            }
        }
        int len = fm.stringWidth(line);
        if (width < len) width = len;
        width = width + 2 * BOX_GAP;
        height = height + LINE_GAP;
        if (multiLineFlag) {
            g.drawString(line, RIGHT_STRING_X + LEFT_GAP, currentY);
        } else {
            g.drawString(line, RIGHT_STRING_X + MAX_WIDTH - len + BOX_GAP + 4, currentY);
        }
        currentY = currentY + LINE_GAP;
        currentY = currentY + BLOCK_GAP;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (font == null) {
            font = new Font(g.getFont().getFamily(), Font.PLAIN, 16);
            timeFont = new Font(g.getFont().getFamily(), Font.PLAIN, 10);
            Dimension sz = getSize();
            setPreferredSize(new Dimension(0, sz.height + 1));
            updateUI();
        }
        g.setFont(font);
        fm = g.getFontMetrics();
        fontHeight = fm.getHeight();
        fontAscent = fm.getAscent();
        currentY = START_Y;
        for (int i = 0; i < text.size(); i++) {
            Message m = text.get(i);
            String who = m.getWho();
            String s = m.getMessage();
            String time = currentTime.get(i);

            if (who.equals("I")) {
                drawRightAlignedMessage(g, s, time);
            } else if (who.equals("U")) {
                drawLeftAlignedMessage(g, s, time);
            } else if (who.equals("Info")) {
                g.drawString(s, LEFT_GAP, currentY);
                currentY = currentY + LINE_GAP;
                currentY = currentY + BLOCK_GAP;
            }
            Dimension sz = getSize();
            if (currentY >= sz.height) {
                setPreferredSize(new Dimension(0, currentY + SCROLL_DELTA));
            }
            mainWnd.scrollDown();
            updateUI();
        }
    }
}
