package kdr.fig;

import kdr.gui.popup.*;

import java.awt.*;

public class KRightTriangle extends KTwoPointFigure {
	private static final long serialVersionUID = -5352317005542174751L;
	protected boolean fillFlag;

	public KRightTriangle(Color color) {
		this(color, 0, 0);
	}

	public KRightTriangle(Color color, int x, int y) {
		this(color, x, y, x, y);
	}

	public KRightTriangle(Color color, int x1, int y1, int x2, int y2) {
		this(color, 1, x1, y1, x2, y2);
	}

	public KRightTriangle(Color color, int thickness, int x1, int y1, int x2, int y2) {
		super(color, thickness, x1, y1, x2, y2);
		fillFlag = false;
	}

	public void setFill() {
		fillFlag = !fillFlag;
	}

	protected void drawEx(Graphics g) {
		super.drawEx(g);

		g.drawLine(x1, y1, x1, y2);
		g.drawLine(x1, y2, x2, y1);
		g.drawLine(x2, y1, x1, y1);

		if (fillFlag == true) {
			int xpoints[] = new int[3];
			int ypoints[] = new int[3];
			xpoints[0] = x1;	ypoints[0] = y1;
			xpoints[1] = x1;	ypoints[1] = y2;
			xpoints[2] = x2;	ypoints[2] = y1;
			g.fillPolygon(xpoints, ypoints, 3);
		}
	}

	protected void drawDotEx(Graphics g) {

		g.fillRect(x1 - DOTSIZE, y1 - DOTSIZE, DOTSIZE, DOTSIZE);
		g.fillRect(x1, y2 - DOTSIZE, DOTSIZE, DOTSIZE);
		g.fillRect(x2, y1, DOTSIZE, DOTSIZE);
	}

	public void makeRegion() {
		int xpoints[] = new int[3];
		int ypoints[] = new int[3];

		xpoints[0] = x1;	ypoints[0] = y1;
		xpoints[1] = x1;	ypoints[1] = y2;
		xpoints[2] = x2;	ypoints[2] = y1;
		region = new Polygon(xpoints, ypoints, 3);
	}

	public KFigure copy() {
		KRightTriangle newRtriangle = new KRightTriangle(color, thickness, x1, y1, x2, y2);
		newRtriangle.popup = popup;
		newRtriangle.fillFlag = fillFlag;
		return newRtriangle;
	}
}
