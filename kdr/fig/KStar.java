package kdr.fig;

import kdr.gui.*;
import kdr.gui.popup.*;
import java.awt.*;

public class KStar extends KTwoPointFigure
{
	//private static final long serialVersionUID = -5352317005542174751L;
	protected boolean fillFlag;
	private boolean isStar;

	public KStar(Color color) {
		this(color,0,0);
	}
	public KStar(Color color,int x,int y) {
		this(color,x,y,x,y);
	}
	public KStar(Color color,int x1,int y1,int x2,int y2) {
		this(color,1,x1,y1,x2,y2);
	}
	public KStar(Color color,int thickness,int x1,int y1,int x2,int y2) {
		super(color,thickness,x1,y1,x2,y2);
		fillFlag = false;
		isStar = true;
	}
	public void setFill() {
		fillFlag = !fillFlag;
	}
	public void changeShape() {
		isStar = !isStar; // star <-> pentagon
	}
	protected void drawEx(Graphics g) {
//////
		super.drawEx(g);
//////

		int minX = Math.min(x1,x2);
		int minY = Math.min(y1,y2);
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);

		int minLen = width;
		if (minLen > height) minLen = height;


		if (isStar) {
			int xpoints[] = new int[10];
			int ypoints[] = new int[10];

			makeStarPolygon(minX,minY,minLen,minLen,xpoints,ypoints);

			g.drawPolygon(xpoints, ypoints, 10);	

			if (fillFlag == true)
			{
				g.fillPolygon(xpoints, ypoints, 10);	
			}
		} else {
			int xpoints[] = new int[5];
			int ypoints[] = new int[5];

			makePentaPolygon(minX,minY,minLen,minLen,xpoints,ypoints);

			g.drawPolygon(xpoints, ypoints, 5);	

			if (fillFlag == true)
			{
				g.fillPolygon(xpoints, ypoints, 5);	
			}
		}
	}
	public KFigure copy() {
		KStar newKStar = new KStar(color,thickness,x1,y1,x2,y2);
		newKStar.popup = popup;
		newKStar.fillFlag = fillFlag;
		newKStar.isStar = isStar;
		return newKStar;
	}

	public void makeRegion() {
		if (x1 > x2)
		{
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
		}
		if (y1 > y2)
		{	
			int tmp = y1;
			y1 = y2;
			y2 = tmp;
		}

		int minX = Math.min(x1,x2);
		int minY = Math.min(y1,y2);
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);

		int minLen = width;
		if (minLen > height) minLen = height;
		
		x2 = x1 + minLen;
		y2 = y1 + minLen;

		if (isStar)
		{
			int xpoints[] = new int[10];
			int ypoints[] = new int[10];

			makeStarPolygon(minX,minY,minLen,minLen,xpoints,ypoints);

			region = new Polygon(xpoints,ypoints,10);
		} else {
			int xpoints[] = new int[5];
			int ypoints[] = new int[5];

			makePentaPolygon(minX,minY,minLen,minLen,xpoints,ypoints);

			region = new Polygon(xpoints,ypoints,5);
		}
	}

	public static void makeStarPolygon(int x,int y,int width,int height,
											int xp[],int yp[])
	{
		int cx = x + width/2;
		int cy = y + height/2;
		double r1 = width/2;
		double r2 = 0.3819660113*r1; // sin(54)-tan(36)*cos(54)
		double delta = 2*Math.PI/5;
		int pos = 0;

		double theta = 0.0;

		for(int i = 0; i < 5; i++) {
			int px1 = cx + (int)(r1*Math.cos(theta - Math.PI/2));
			int py1 = cy + (int)(r1*Math.sin(theta - Math.PI/2));
			xp[pos] = px1;
			yp[pos] = py1;
			pos++;

			int px2 = cx + (int)(r2*Math.cos(theta + Math.PI/2 + 3*2*Math.PI/5));
			int py2 = cy + (int)(r2*Math.sin(theta + Math.PI/2 + 3*2*Math.PI/5));
			xp[pos] = px2;
			yp[pos] = py2;
			pos++;

			theta = theta + delta;
		}		
	}
	public static void makePentaPolygon(int x,int y,int width,int height,
											int xp[],int yp[])
	{
		int cx = x + width/2;
		int cy = y + height/2;
		double r1 = width/2;
		double delta = 2*Math.PI/5;
		int pos = 0;

		double theta = 0.0;

		for(int i = 0; i < 5; i++) {
			int px1 = cx + (int)(r1*Math.cos(theta - Math.PI/2));
			int py1 = cy + (int)(r1*Math.sin(theta - Math.PI/2));
			xp[pos] = px1;
			yp[pos] = py1;
			pos++;

			theta = theta + delta;
		}		
	}
	public void preparePopup() {
		super.preparePopup();
		KStarPopup starPopup = (KStarPopup)popup;
		if (isStar == true) {
			starPopup.setChangeShapeLabel(DrawerView.Labels.get("To Pentagon"));
		} else {
			starPopup.setChangeShapeLabel(DrawerView.Labels.get("To Star"));
		}
	}
}