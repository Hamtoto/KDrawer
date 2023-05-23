package kdr.fig;

import java.awt.*;

public class KDiamond extends KTwoPointFigure
{
	//private static final long serialVersionUID = -5352317005542174751L;
	protected boolean fillFlag;

	public KDiamond(Color color) {
		this(color,0,0);
	}
	public KDiamond(Color color,int x,int y) {
		this(color,x,y,x,y);
	}
	public KDiamond(Color color,int x1,int y1,int x2,int y2) {
		this(color,1,x1,y1,x2,y2);
	}
	public KDiamond(Color color,int thickness,int x1,int y1,int x2,int y2) {
		super(color,thickness,x1,y1,x2,y2);
		fillFlag = false;
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

		int xpoints[] = new int[4];
		int ypoints[] = new int[4];

		xpoints[0] = minX + (width / 2); 	ypoints[0] = minY;
		xpoints[1] = minX + width; 			ypoints[1] = minY + (height / 2);
		xpoints[2] = minX + (width / 2);	ypoints[2] = minY + height;
		xpoints[3] = minX;					ypoints[3] = minY + (height / 2);

		g.drawPolygon(xpoints, ypoints, 4);	

		if (fillFlag == true)
		{
			g.fillPolygon(xpoints, ypoints, 4);	
		}
	}
	public KFigure copy() {
		KDiamond newKDiamond = new KDiamond(color,thickness,x1,y1,x2,y2);
		newKDiamond.popup = popup;
		newKDiamond.fillFlag = fillFlag;
		return newKDiamond;
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
		
		int xpoints[] = new int[4];
		int ypoints[] = new int[4];

		xpoints[0] = minX + (width / 2); 	ypoints[0] = minY;
		xpoints[1] = minX + width; 			ypoints[1] = minY + (height / 2);
		xpoints[2] = minX + (width / 2);	ypoints[2] = minY + height;
		xpoints[3] = minX;					ypoints[3] = minY + (height / 2);

		region = new Polygon(xpoints,ypoints,4);
	}
}