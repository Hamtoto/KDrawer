package kdr.fig;

import java.awt.*;

public class KCircle extends KTwoPointFigure
{
	private static final long serialVersionUID = -8390635094601811752L;
	private boolean fillFlag;

	public KCircle(Color color) {
		this(color,0,0);
	}
	public KCircle(Color color,int x,int y) {
		this(color,x,y,x,y);
	}
	public KCircle(Color color,int x1,int y1,int x2,int y2) {
		this(color,1,x1,y1,x2,y2);
	}
	public KCircle(Color color,int thickness,int x1,int y1,int x2,int y2) {
		super(color,thickness,x1,y1,x2,y2);
		fillFlag = false;
	}
	boolean getFillFlag() {
		return fillFlag;
	}
	void setFillFlag(boolean flag) {
		fillFlag = flag;
	}
	public void setFill() {
		fillFlag = !fillFlag;
	}
	protected void drawEx(Graphics g) {
//////
		super.drawEx(g);
//////

		int minX = Math.min(x1,x2);
		int minY = Math.min(y1,y2);
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);

		g.drawOval(minX,minY,width,height);

		if (fillFlag == true)
		{
			g.fillOval(minX,minY,width,height);
		}
	}
	public KFigure copy() {
		KCircle newCircle = new KCircle(color,thickness,x1,y1,x2,y2);
		newCircle.popup = popup;
		newCircle.fillFlag = fillFlag;
		return newCircle;
	}
}
