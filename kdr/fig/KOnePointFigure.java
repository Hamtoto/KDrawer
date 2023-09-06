package kdr.fig;

import java.awt.*;

public abstract class KOnePointFigure extends KFigure
{
	protected int x1;
	protected int y1;
	public KOnePointFigure(Color color) {
		this(color,0,0);
	}
	public KOnePointFigure(Color color, int x, int y) {
		this(color,1,x,y);
	}
	public KOnePointFigure(Color color, int thickness, int x, int y) {
		super(color,thickness);
		x1 = x;
		y1 = y;
	}
	void move(int dx,int dy) {
		x1 = x1 + dx; y1 = y1 + dy;
	}
	public void makeRegion() {
		int[] xpoints = new int[4];
		int[] ypoints = new int[4];

		xpoints[0] = x1-GAP; ypoints[0] = y1-GAP;
		xpoints[1] = x1+GAP; ypoints[1] = y1-GAP;
		xpoints[2] = x1+GAP; ypoints[2] = y1+GAP;
		xpoints[3] = x1-GAP; ypoints[3] = y1+GAP;

		region = new Polygon(xpoints,ypoints,4);
	}
	public void setXY2(int x, int y) {
		setX1(x); setY1(y);
	}
	public int getX1() {
		return x1;
	}
	public int getY1() {
		return y1;
	}
	public void setX1(int x) {
		x1 = x;
	}
	public void setY1(int y) {
		y1 = y;
	}

	public Point getStartPositionToResize(int x, int y) {
		return new Point(x1,y1);
	}
	private void drawDotEx(Graphics g) {
		g.fillRect(x1-DOTSIZE/2,y1-DOTSIZE/2,DOTSIZE,DOTSIZE);
	}
	void eraseDot(Graphics g) {
		super.eraseDot(g);

		drawDotEx(g);
	}
	void drawEx(Graphics g) {
		if (!dotFlag) return;
		
		drawDotEx(g);
	}
}
