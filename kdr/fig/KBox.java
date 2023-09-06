package kdr.fig;

import kdr.gui.popup.*;

import java.awt.*;

public class KBox extends KTwoPointFigure {
    protected boolean fillFlag;

    private static final int ArcLength = 30;
    private boolean roundFlag;

    public KBox(Color color) {
        this(color, 0, 0);
    }

    public KBox(Color color, int x, int y) {
        this(color, x, y, x, y);
    }

    public KBox(Color color, int x1, int y1, int x2, int y2) {
        this(color, 1, x1, y1, x2, y2);
    }

    public KBox(Color color, int thickness, int x1, int y1, int x2, int y2) {
        super(color, thickness, x1, y1, x2, y2);
        fillFlag = false;
        roundFlag = false;
    }

    public void toRoundBox() {
        roundFlag = !roundFlag;
    }

    public void setFill() {
        fillFlag = !fillFlag;
    }

    protected void drawEx(Graphics g) {
        super.drawEx(g);

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        if (roundFlag) {
            g.drawRoundRect(minX, minY, width, height, ArcLength, ArcLength);
        } else {
            g.drawRect(minX, minY, width, height);
        }

        if (fillFlag) {
            if (roundFlag) {
                g.fillRoundRect(minX, minY, width, height, ArcLength, ArcLength);
            } else {
                g.fillRect(minX, minY, width, height);
            }
        }
    }

    public void preparePopup() {
        super.preparePopup();
        KBoxPopup boxPopup = (KBoxPopup) popup;
        boxPopup.setRoundItem(roundFlag);
    }

    public KFigure copy() {
        KBox newBox = new KBox(color, thickness, x1, y1, x2, y2);
        newBox.popup = popup;
        newBox.fillFlag = fillFlag;
        newBox.roundFlag = roundFlag;
        return newBox;
    }
}
