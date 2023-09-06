package kdr.fig;

import kdr.gui.*;
import kdr.gui.popup.*;

import java.awt.*;

public class KLines extends KLinearFigure {
    static private int ARROW_LENGTH = 15;
    static private int NONE = 0;
    static private int HEAD = 1;
    static private int TAIL = 2;
    static private int BOTH = 3;
    private int whichDirection;

    public KLines(Color color) {
        this(color, 0, 0);
    }

    public KLines(Color color, int x, int y) {
        this(color, x, y, x, y);
    }

    public KLines(Color color, int x1, int y1, int x2, int y2) {
        this(color, 1, x1, y1, x2, y2);
    }

    public KLines(Color color, int thickness, int x1, int y1, int x2, int y2) {
        super(color, thickness, x1, y1, x2, y2);
        whichDirection = NONE;
    }

    public void preparePopup() {
        super.preparePopup();
        KLinesPopup linesPopup = (KLinesPopup) popup;
        if (closedFlag) {
            linesPopup.setDoorLabel(DrawerView.Labels.get("Open"));
            linesPopup.setEnableFillItem(true);
        } else {
            linesPopup.setDoorLabel(DrawerView.Labels.get("Close"));
            linesPopup.setEnableFillItem(false);
        }
        if (fillFlag && closedFlag) {
            linesPopup.setEnableDoorItem(false);
        } else {
            linesPopup.setEnableDoorItem(true);
        }
    }

    public void setLineArrow(int x, int y) {
        int px1 = pt[0].x;
        int py1 = pt[0].y;
        int px2 = pt[pt.length - 1].x;
        int py2 = pt[pt.length - 1].y;
        double distance1 = Math.sqrt((x - px1) * (x - px1) + (y - py1) * (y - py1));
        double distance2 = Math.sqrt((x - px2) * (x - px2) + (y - py2) * (y - py2));
        int whichSide = TAIL;
        if (distance1 > distance2) whichSide = HEAD;

        switch (whichDirection) {
            case 0: // NONE
                whichDirection = whichSide;
                break;
            case 1: // HEAD
                if (whichSide == TAIL) whichDirection = BOTH;
                else whichDirection = NONE;
                break;
            case 2: // TAIL
                if (whichSide == TAIL) whichDirection = NONE;
                else whichDirection = BOTH;
                break;
            case 3: // BOTH
                if (whichSide == TAIL) whichDirection = HEAD;
                else whichDirection = TAIL;
                break;
        }
    }

    protected void drawEx(Graphics g) {
        int px1, px2, py1, py2;
        if (whichDirection == BOTH || whichDirection == HEAD) {
            px1 = pt[pt.length - 2].x;
            py1 = pt[pt.length - 2].y;
            px2 = pt[pt.length - 1].x;
            py2 = pt[pt.length - 1].y;
            KLine.drawArrow(g, px1, py1, px2, py2);
        }
        if (whichDirection == BOTH || whichDirection == TAIL) {
            px1 = pt[0].x;
            py1 = pt[0].y;
            px2 = pt[1].x;
            py2 = pt[1].y;
            KLine.drawArrow(g, px2, py2, px1, py1);
        }
        super.drawEx(g);
    }

    public KFigure copy() {
        int x1 = lastLine.getX1();
        int y1 = lastLine.getY1();
        int x2 = lastLine.getX2();
        int y2 = lastLine.getY2();
        KLines newLines = new KLines(color, thickness, x1, y1, x2, y2);
        newLines.list.clear();
        newLines.pt = new Point[pt.length];
        for (int i = 0; i < pt.length; i++) {
            // deep copy
            newLines.pt[i] = new Point(pt[i].x, pt[i].y);
        }
        newLines.popup = popup;
        newLines.fillFlag = fillFlag;
        newLines.closedFlag = closedFlag;
        newLines.whichDirection = whichDirection;
        return newLines;
    }
}






