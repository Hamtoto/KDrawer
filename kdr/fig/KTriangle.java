package kdr.fig;

import kdr.gui.popup.*;
import java.awt.*;

public class KTriangle extends KTwoPointFigure
{
	//private static final long serialVersionUID = -2787538065501750475L;
	protected boolean fillFlag;

	private double cornerAngle;
	public KTriangle(Color color) {
		this(color,0,0);
	}
	public KTriangle(Color color,int x,int y) {
		this(color,x,y,x,y);
	}
	public KTriangle(Color color,int x1,int y1,int x2,int y2) {
		this(color,1,x1,y1,x2,y2);
	}
	public KTriangle(Color color,int thickness,int x1,int y1,int x2,int y2) {
		super(color,thickness,x1,y1,x2,y2);
		fillFlag = false;
		cornerAngle = Math.PI/6.0;
	}
	public void setCornerAngle(double angle)
	{
		cornerAngle = angle;
	}
	void drawTriangle(Graphics g, int x1, int y1, int x2, int y2) {
		int width;
		int x = x1;
		int y = y1;
		int w = x2 - x1;
		int h = y2 - y1;
		double len = Math.sqrt((w*w)+(h*h));
		width = (int)(len * Math.tan(cornerAngle));

		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double angle;
		double theta = (w!=0) ? Math.atan((double)(h)/(double)(w)) : sign_h*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)(width * Math.cos(angle));
		int dy = (int)(width * Math.sin(angle));
		int xpoints[] = new int[3];
		int ypoints[] = new int[3];
		xpoints[0] = x + dx;	ypoints[0] = y + dy;
		xpoints[1] = x - dx;	ypoints[1] = y - dy;
		xpoints[2] = x2;	ypoints[2] = y2;
		g.drawPolygon(xpoints, ypoints, 3);
		
		if (fillFlag == true)
		{
			g.fillPolygon(xpoints, ypoints, 3);
		}
	}
	public void makeRegion() {
		int width;
		int x = x1;
		int y = y1;
		int w = x2 - x1;
		int h = y2 - y1;
		double len = Math.sqrt((w*w)+(h*h));
		width = (int)(len * Math.tan(cornerAngle));

		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double angle;
		double theta = (w!=0) ? Math.atan((double)(h)/(double)(w)) : sign_h*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)(width * Math.cos(angle));
		int dy = (int)(width * Math.sin(angle));
		int xpoints[] = new int[3];
		int ypoints[] = new int[3];
		xpoints[0] = x + dx;	ypoints[0] = y + dy;
		xpoints[1] = x - dx;	ypoints[1] = y - dy;
		xpoints[2] = x2;	ypoints[2] = y2;
		region = new Polygon(xpoints,ypoints,3);
	}
	public void setFill() {
		fillFlag = !fillFlag;
	}
	protected void drawEx(Graphics g) {
		if (dotFlag == true) drawDotEx(g);

		drawTriangle(g, x1, y1, x2, y2);
	}
	protected void drawDotEx(Graphics g) {
		g.fillRect(x1-DOTSIZE/2,y1-DOTSIZE/2,DOTSIZE,DOTSIZE);
		g.fillRect(x2-DOTSIZE/2,y2-DOTSIZE/2,DOTSIZE,DOTSIZE);
	}
	void eraseDot(Graphics g) {
		super.eraseDot(g);

		drawDotEx(g);
	}
	public void preparePopup() {
		super.preparePopup();
		KTrianglePopup trianglePopup = (KTrianglePopup)popup;
		trianglePopup.setAngle(cornerAngle);
	}
	public KFigure copy() {
		KTriangle newTriangle = new KTriangle(color,thickness,x1,y1,x2,y2);
		newTriangle.popup = popup;
		newTriangle.fillFlag = fillFlag;
		newTriangle.cornerAngle = cornerAngle;
		return newTriangle;
	}
}
