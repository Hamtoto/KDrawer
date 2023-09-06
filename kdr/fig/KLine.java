package kdr.fig;

import java.awt.*;

public class KLine extends KTwoPointFigure {
	static private int ARROW_LENGTH = 15;
	static private int NONE = 0;
	static private int HEAD = 1;
	static private int TAIL = 2;
	static private int BOTH = 3;
	private int whichDirection;

	public KLine(Color color) {
		this(color, 0, 0);
	}

	public KLine(Color color, int x, int y) {
		this(color, x, y, x, y);
	}

	public KLine(Color color, int x1, int y1, int x2, int y2) {
		this(color, 1, x1, y1, x2, y2);
	}

	public KLine(Color color, int thickness, int x1, int y1, int x2, int y2) {
		super(color, thickness, x1, y1, x2, y2);
		whichDirection = NONE;
	}

	public void setLineArrow(int x, int y) {
		double distance1 = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
		double distance2 = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
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

	static public void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
		double w = x2 - x1;
		double h = y2 - y1;
		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
		if (theta < 0) theta = theta + 2 * Math.PI;

		double angle1 = theta - (Math.PI * 3. / 4. + 0.3);
		double angle2 = theta + (Math.PI * 3. / 4. + 0.3);
		int dx1 = (int) (ARROW_LENGTH * Math.cos(angle1));
		int dy1 = (int) (ARROW_LENGTH * Math.sin(angle1));
		int dx2 = (int) (ARROW_LENGTH * Math.cos(angle2));
		int dy2 = (int) (ARROW_LENGTH * Math.sin(angle2));

		int[] xpoints = new int[2];
		int[] ypoints = new int[2];
		if (w >= 0) {
			xpoints[0] = x2 + dx1;	ypoints[0] = y2 + dy1;
			xpoints[1] = x2 + dx2;	ypoints[1] = y2 + dy2;
		} else {
			xpoints[0] = x2 - dx1;	ypoints[0] = y2 - dy1;
			xpoints[1] = x2 - dx2;	ypoints[1] = y2 - dy2;
		}
		g.drawLine(xpoints[0], ypoints[0], x2, y2);
		g.drawLine(xpoints[1], ypoints[1], x2, y2);
	}

	protected void drawDotEx(Graphics g) {
		g.fillRect(x1 - DOTSIZE / 2, y1 - DOTSIZE / 2, DOTSIZE, DOTSIZE);
		g.fillRect(x2 - DOTSIZE / 2, y2 - DOTSIZE / 2, DOTSIZE, DOTSIZE);
	}

	protected void drawEx(Graphics g) {
		if (dotFlag) drawDotEx(g);

		if (whichDirection == BOTH || whichDirection == HEAD) {
			drawArrow(g, x1, y1, x2, y2);
		}
		if (whichDirection == BOTH || whichDirection == TAIL) {
			drawArrow(g, x2, y2, x1, y1);
		}
		g.drawLine(x1, y1, x2, y2);
	}

	void eraseDot(Graphics g) {
		super.eraseDot(g);

		drawDotEx(g);
	}

	public KFigure copy() {
		KLine newLine = new KLine(color, thickness, x1, y1, x2, y2);
		newLine.popup = popup;
		newLine.whichDirection = whichDirection;
		return newLine;
	}

	public void makeRegion() {
		int regionWidth = 6;
		int x = x1;
		int y = y1;
		int w = x2 - x1;
		int h = y2 - y1;

		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double angle;
		double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int) (regionWidth * Math.cos(angle));
		int dy = (int) (regionWidth * Math.sin(angle));
		int[] xpoints = new int[4];
		int[] ypoints = new int[4];
      	xpoints[0] = x + dx;     ypoints[0] = y + dy;
      	xpoints[1] = x - dx;     ypoints[1] = y - dy;
      	xpoints[2] = x + w - dx; ypoints[2] = y + h - dy;
      	xpoints[3] = x + w + dx; ypoints[3] = y + h + dy;
		region = new Polygon(xpoints, ypoints, 4);
	}
}






