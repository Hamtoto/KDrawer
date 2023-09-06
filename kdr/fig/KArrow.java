package kdr.fig;

import java.awt.*;

public class KArrow extends KTwoPointFigure {
	protected boolean fillFlag;

	public KArrow(Color color) {
		this(color, 0, 0);
	}

	public KArrow(Color color, int x, int y) {
		this(color, x, y, x, y);
	}

	public KArrow(Color color, int x1, int y1, int x2, int y2) {
		this(color, 1, x1, y1, x2, y2);
	}

	public KArrow(Color color, int thickness, int x1, int y1, int x2, int y2) {
		super(color, thickness, x1, y1, x2, y2);
		fillFlag = false;
	}

	void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
		int width;
		int x = x1;
		int y = y1;
		int w = x2 - x1;
		int h = y2 - y1;
		double len = Math.sqrt((w * w) + (h * h));
		width = (int) (len * Math.tan(Math.PI / 6));

		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double angle;
		double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int) (width * Math.cos(angle));
		int dy = (int) (width * Math.sin(angle));

		int[] xpoints = new int[7];
		int[] ypoints = new int[7];

		xpoints[0] = x + dx/2;			ypoints[0] = y + dy/2;
		xpoints[1] = x - dx/2;			ypoints[1] = y - dy/2;
		xpoints[2] = x + w/2 - dx/2;	ypoints[2] = y + h/2 - dy/2;
		xpoints[3] = x + w/2 - dx;		ypoints[3] = y + h/2 - dy;
		xpoints[4] = x2;				ypoints[4] = y2;
		xpoints[5] = x + w/2 + dx;		ypoints[5] = y + h/2 + dy;
		xpoints[6] = x + w/2 + dx/2;	ypoints[6] = y + h/2 + dy/2;

		g.drawPolygon(xpoints, ypoints, 7);

		if (fillFlag) {
			g.fillPolygon(xpoints, ypoints, 7);
		}
	}

	public void setFill() {
		fillFlag = !fillFlag;
	}

	protected void drawEx(Graphics g) {
		if (dotFlag) drawDotEx(g);

		drawArrow(g, x1, y1, x2, y2);
	}

	protected void drawDotEx(Graphics g) {
		g.fillRect(x1 - DOTSIZE / 2, y1 - DOTSIZE / 2, DOTSIZE, DOTSIZE);
		g.fillRect(x2 - DOTSIZE / 2, y2 - DOTSIZE / 2, DOTSIZE, DOTSIZE);
	}

	void eraseDot(Graphics g) {
		super.eraseDot(g);
		drawDotEx(g);
	}

	public KFigure copy() {
		KArrow newArrow = new KArrow(color, thickness, x1, y1, x2, y2);
		newArrow.popup = popup;
		return newArrow;
	}

	public void makeRegion() {
		int width;
		int x = x1;
		int y = y1;
		int w = x2 - x1;
		int h = y2 - y1;
		double len = Math.sqrt((w * w) + (h * h));
		width = (int) (len * Math.tan(Math.PI / 6));

		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double angle;
		double theta = (w != 0) ? Math.atan((double) (h) / (double) (w)) : sign_h * Math.PI / 2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int) (width * Math.cos(angle));
		int dy = (int) (width * Math.sin(angle));
		int[] xpoints = new int[7];
		int[] ypoints = new int[7];

		xpoints[0] = x + dx/2;			ypoints[0] = y + dy/2;
		xpoints[1] = x - dx/2;			ypoints[1] = y - dy/2;
		xpoints[2] = x + w/2 - dx/2;	ypoints[2] = y + h/2 - dy/2;
		xpoints[3] = x + w/2 - dx;		ypoints[3] = y + h/2 - dy;
		xpoints[4] = x2;				ypoints[4] = y2;
		xpoints[5] = x + w/2 + dx;		ypoints[5] = y + h/2 + dy;
		xpoints[6] = x + w/2 + dx/2;	ypoints[6] = y + h/2 + dy/2;

		region = new Polygon(xpoints,ypoints,7);
	}
}
