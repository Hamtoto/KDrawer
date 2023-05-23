package kdr.fig;

import java.awt.geom.*; 
import java.awt.*;

public class KEraser extends KLinearFigure 
{
	static int ERASER_DELTA = 10;
	public KEraser() {
		this(0,0);
	}
	public KEraser(int x,int y) {
		super(Color.white,1,x,y,x,y);
	}

	public void drawEraserRect(Graphics g) {
		int x = lastLine.getX1();
		int y = lastLine.getY1();
		g.drawRect(x-ERASER_DELTA,y-ERASER_DELTA,
						2*ERASER_DELTA,2*ERASER_DELTA);
	}

	protected void drawDotEx(Graphics g) {
		g.fillRect(pt[0].x-DOTSIZE/2,pt[0].y-DOTSIZE/2,DOTSIZE,DOTSIZE);
		g.fillRect(pt[pt.length-1].x-DOTSIZE/2,pt[pt.length-1].y-DOTSIZE/2,DOTSIZE,DOTSIZE);
	}

	void drawEx(Graphics g) {
		if (dotFlag == true) drawDotEx(g);

		g.setColor(Color.white);
		if (pt != null)	{
			for (int i = 0; i < pt.length; i++) {
				g.fillRect(pt[i].x-ERASER_DELTA,pt[i].y-ERASER_DELTA,
										2*ERASER_DELTA,2*ERASER_DELTA);
			}
			return;
		}
		
		for(int i = 0; i < list.size(); i++) {
			Point pt = list.get(i);
			g.fillRect(pt.x-ERASER_DELTA,pt.y-ERASER_DELTA,
								2*ERASER_DELTA,2*ERASER_DELTA);
		}
		if (lastLine != null) {
			int x = lastLine.getX1();
			int y = lastLine.getY1();
			g.fillRect(x-ERASER_DELTA,y-ERASER_DELTA,
							2*ERASER_DELTA,2*ERASER_DELTA);
			g.drawRect(x-ERASER_DELTA,y-ERASER_DELTA,
							2*ERASER_DELTA,2*ERASER_DELTA);
			x = lastLine.getX2();
			y = lastLine.getY2();
			g.fillRect(x-ERASER_DELTA,y-ERASER_DELTA,
							2*ERASER_DELTA,2*ERASER_DELTA);
			g.setColor(Color.black);
			g.drawRect(x-ERASER_DELTA,y-ERASER_DELTA,
							2*ERASER_DELTA,2*ERASER_DELTA);
		}
	}
	public void setXY2(int x, int y) {
		resetLineSegment(x,y);
	}
	public KFigure copy() {
		int x1 = lastLine.getX1();
		int y1 = lastLine.getY1();
		KEraser newEraser = new KEraser(x1,y1);
		newEraser.list.clear();
		newEraser.pt = new Point[pt.length];
		for (int i = 0; i < pt.length; i++)	{
			// deep copy
			newEraser.pt[i] = new Point(pt[i].x,pt[i].y);
		}
		newEraser.popup = popup;
		newEraser.fillFlag = fillFlag;
		newEraser.closedFlag = closedFlag;
		return newEraser;
	}
}
