package kdr.fig;

import java.awt.*;

public class KPoint extends KOnePointFigure
{
	private static final long serialVersionUID = 573154677467364041L;
	public KPoint(Color color) {
		super(color);
	}
	public KPoint(Color color,int x,int y) {
		super(color,x,y);
	}
	public KPoint(Color color,int thickness,int x,int y) {
		super(color,thickness,x,y);
	}
	protected void drawEx(Graphics g) {
		g.drawOval(x1-GAP,y1-GAP,2*GAP,2*GAP);
		g.fillOval(x1-GAP,y1-GAP,2*GAP,2*GAP);
	}
	public KFigure copy() {
		KPoint newPoint = new KPoint(color,thickness,x1,y1);
		newPoint.popup = popup;
		return newPoint;
	}
}
