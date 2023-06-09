package kdr.fig;

import java.awt.*;

public abstract class KTwoPointFigure extends KFigure {
	private static final long serialVersionUID = -6589521908297711448L;
	protected int x1;
	protected int y1;
	protected int x2;
	protected int y2;

	public KTwoPointFigure(Color color) {
		this(color, 0, 0);
	}

	public KTwoPointFigure(Color color, int x, int y) {
		this(color, x, y, x, y);
	}

	public KTwoPointFigure(Color color, int x1, int y1, int x2, int y2) {
		this(color, 1, x1, y1, x2, y2);
	}

	public KTwoPointFigure(Color color, int thickness, int x1, int y1, int x2, int y2) {
		super(color, thickness);
		this.x1 = x1; this.y1 = y1;
		this.x2 = x2; this.y2 = y2;
	}

	void move(int dx, int dy) {
		x1 = x1 + dx; y1 = y1 + dy;
		x2 = x2 + dx; y2 = y2 + dy;
	}

	public boolean isObsolete() {
		if (x1 == x2 && y1 == y2) return true;
		return false;
	}

	protected void drawDotEx(Graphics g) {
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		int maxX = Math.max(x1, x2);
		int maxY = Math.max(y1, y2);

		g.fillRect(minX - DOTSIZE, minY - DOTSIZE, DOTSIZE, DOTSIZE);
		g.fillRect(maxX, minY - DOTSIZE, DOTSIZE, DOTSIZE);
		g.fillRect(maxX, maxY, DOTSIZE, DOTSIZE);
		g.fillRect(minX - DOTSIZE, maxY, DOTSIZE, DOTSIZE);
	}

	void eraseDot(Graphics g) {
		super.eraseDot(g);

		drawDotEx(g);
	}

	void drawEx(Graphics g) {
		if (dotFlag == false) return;

		drawDotEx(g);
	}

	public void makeRegion() {
		if (x1 > x2) {
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
		}
		if (y1 > y2) {
			int tmp = y1;
			y1 = y2;
			y2 = tmp;
		}

		int xpoints[] = new int[4];
		int ypoints[] = new int[4];

		xpoints[0] = x1; ypoints[0] = y1;
		xpoints[1] = x2; ypoints[1] = y1;
		xpoints[2] = x2; ypoints[2] = y2;
		xpoints[3] = x1; ypoints[3] = y2;

		region = new Polygon(xpoints, ypoints, 4);
	}

	public void setXY2(int x, int y) {
		setX2(x);
		setY2(y);
	}

	public void setXY12(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	public void setX1(int x) {
		x1 = x;
	}

	public void setY1(int y) {
		y1 = y;
	}

	public void setX2(int x) {
		x2 = x;
	}

	public void setY2(int y) {
		y2 = y;
	}

	public Point getStartPositionToResize(int x, int y) {
		return new Point(x2, y2);
	}

}