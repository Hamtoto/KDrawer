package kdr.fig;

import kdr.gui.popup.*;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;

public class KCurve extends KLinearFigure
{
	private static final long serialVersionUID = 1L;

	static private int ARROW_LENGTH = 15;
	static private int NONE = 0;
	static private int HEAD = 1;
	static private int TAIL = 2;
	static private int BOTH = 3;
	private int whichDirection;
	private int clickCount;

	public KCurve(Color color) {
		this(color,0,0);
	}
	public KCurve(Color color,int x,int y) {
		this(color,x,y,x,y);
	}
	public KCurve(Color color,int x1,int y1,int x2,int y2) {
		this(color,1,x1,y1,x2,y2);
	}
	public KCurve(Color color,int thickness,int x1,int y1,int x2,int y2) {
		super(color,thickness,x1,y1,x2,y2);
		whichDirection = NONE;
		clickCount = 1;
		list = null;
		lastLine = null;
		pt = new Point[4];
		for(int i = 0; i < 4; i++) {
			pt[i] = new Point(x1,y1);
		}
	}
	public void setLineArrow(int x,int y) {
		int px1 = pt[0].x;
		int py1 = pt[0].y;
		int px2 = pt[pt.length-1].x;
		int py2 = pt[pt.length-1].y;
		double distance1 = Math.sqrt((x-px1)*(x-px1) + (y-py1)*(y-py1));
		double distance2 = Math.sqrt((x-px2)*(x-px2) + (y-py2)*(y-py2));
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
	public boolean doYouWantToStop() {
		if (clickCount > 3) {
			return true;
		}
		return false;
	}
	protected void drawDotEx(Graphics g) {
		g.fillRect(pt[0].x-DOTSIZE/2,pt[0].y-DOTSIZE/2,DOTSIZE,DOTSIZE);
		g.fillRect(pt[pt.length-1].x-DOTSIZE/2,pt[pt.length-1].y-DOTSIZE/2,DOTSIZE,DOTSIZE);
	}
	protected void drawEx(Graphics g) {
		int px1,px2,py1,py2;
		if (whichDirection == BOTH || whichDirection == HEAD) {
			px1 = pt[pt.length-2].x;
			py1 = pt[pt.length-2].y;
			px2 = pt[pt.length-1].x;
			py2 = pt[pt.length-1].y;
			KLine.drawArrow(g,px1,py1,px2,py2);
		}
		if (whichDirection == BOTH || whichDirection == TAIL) {
			px1 = pt[0].x;
			py1 = pt[0].y;
			px2 = pt[1].x;
			py2 = pt[1].y;
			KLine.drawArrow(g,px2,py2,px1,py1);
		}

		if (dotFlag == true) drawDotEx(g);

		CubicCurve2D.Double curve = new CubicCurve2D.Double();
		curve.setCurve(pt[0].x,pt[0].y,pt[1].x,pt[1].y,
							pt[2].x,pt[2].y,pt[3].x,pt[3].y);
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(curve);
	}
	public void plusClickCount() {
		clickCount++;
	}
	public void setXY2(int x, int y) {
		if (hotPoint == -1) {
			for(int i = clickCount; i < pt.length; i++) {
				pt[i].x = x;
				pt[i].y = y;
			}
		} else {
			pt[hotPoint].x = x;
			pt[hotPoint].y = y;
		}
	}
	public void makeRegion() {
		ptRegion = new Polygon[2];

		ptRegion[0] = createPtRegion(pt[0]);
		ptRegion[1] = createPtRegion(pt[pt.length-1]);
	}
	public KFigure copy() {
		KCurve curve = new KCurve(color,thickness,pt[0].x,pt[0].y,pt[0].x,pt[0].y);
		curve.list = null;
		curve.pt = new Point[pt.length];
		for (int i = 0; i < pt.length; i++)	{
			// deep copy
			curve.pt[i] = new Point(pt[i].x,pt[i].y);
		}
		curve.clickCount = clickCount;
		curve.popup = popup;
		curve.whichDirection = whichDirection;
		return curve;
	}
}
