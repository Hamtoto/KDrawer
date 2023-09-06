package kdr.gui.popup;

import kdr.gui.*;
import javax.swing.*;

public class KMainPopup extends KPopup {
    public KMainPopup(DrawerView view) {
        super("Figure");

        JMenuItem pointItem = new JMenuItem(view.getPointAction());
        popupPtr.add(pointItem);

        JMenuItem boxItem = new JMenuItem(view.getBoxAction());
        popupPtr.add(boxItem);

        JMenuItem lineItem = new JMenuItem(view.getLineAction());
        popupPtr.add(lineItem);

        JMenuItem linesItem = new JMenuItem(view.getLinesAction());
        popupPtr.add(linesItem);

        JMenuItem curveItem = new JMenuItem(view.getCurveAction());
        popupPtr.add(curveItem);

        JMenuItem arrowItem = new JMenuItem(view.getArrowAction());
        popupPtr.add(arrowItem);

        JMenuItem circleItem = new JMenuItem(view.getCircleAction());
        popupPtr.add(circleItem);

        JMenuItem triangleItem = new JMenuItem(view.getTriangleAction());
        popupPtr.add(triangleItem);

        JMenuItem diamondItem = new JMenuItem(view.getDiamondAction());
        popupPtr.add(diamondItem);

        JMenuItem starItem = new JMenuItem(view.getStarAction());
        popupPtr.add(starItem);

        JMenuItem textItem = new JMenuItem(view.getTextAction());
        popupPtr.add(textItem);

        JMenuItem imageItem = new JMenuItem(view.getImageAction());
        popupPtr.add(imageItem);
    }
}
