package kdr.fig;

import java.awt.*;
import java.util.*;
import java.awt.geom.*;

public abstract class KLinearFigure extends KFigure
{
	private static final long serialVersionUID = 1L;
	protected Polygon ptRegion[];
	protected ArrayList<Point> list;
	protected Point pt[];
	protected KLine lastLine;
	protected boolean closedFlag;
	protected boolean fillFlag;
	transient protected int hotPoint; // resize point position
	public KLinearFigure(Color color) {
		this(color,0,0);
	}
	public KLinearFigure(Color color,int x,int y) {
		this(color,x,y,x,y);
	}
	public KLinearFigure(Color color,int x1,int y1,int x2,int y2) {
		this(color,1,x1,y1,x2,y2);
	}
	public KLinearFigure(Color color,int thickness,int x1,int y1,int x2,int y2) {
		super(color,thickness);
		hotPoint = -1;
		ptRegion = null;
		pt = null;
		closedFlag = false;
		fillFlag = false;
		list = new ArrayList<Point>();
		list.add(new Point(x1,y1));
		lastLine = new KLine(color,thickness,x1,y1,x2,y2);
	}
	public boolean doYouWantToStop() {
		if (hotPoint == -1 &&
			lastLine.getX1() == lastLine.getX2() &&
			lastLine.getY1() == lastLine.getY2()) {
			return true;
		}
		return false;
	}
	public void changeDoor() {
		closedFlag = !closedFlag;
	}
	public void setFill() {
		fillFlag = !fillFlag;
	}
	void move(int dx,int dy) {
		for(int i = 0; i < pt.length; i++) {
			pt[i].x = pt[i].x + dx;
			pt[i].y = pt[i].y + dy;
		}
	}
	protected void drawDotEx(Graphics g) {
		for(int i = 0; i < pt.length; i++) {
			g.fillRect(pt[i].x-DOTSIZE/2,pt[i].y-DOTSIZE/2,DOTSIZE,DOTSIZE);
		}
	}
	void eraseDot(Graphics g) {
		super.eraseDot(g);

		drawDotEx(g);
	}
	void drawEx(Graphics g) {
		if (dotFlag == true) drawDotEx(g);

		if (pt != null)	{
			for (int i = 0; i < pt.length-1; i++) {
				g.drawLine(pt[i].x,pt[i].y,pt[i+1].x,pt[i+1].y);
			}
			if (closedFlag)	{
				g.drawLine(pt[0].x,pt[0].y,
							pt[pt.length-1].x,pt[pt.length-1].y);
			}
			if (fillFlag) {
				int xpoints[] = new int[pt.length];
				int ypoints[] = new int[pt.length];
				for (int i = 0; i < pt.length; i++) {
					xpoints[i] = pt[i].x;
					ypoints[i] = pt[i].y;
				}
				g.fillPolygon(xpoints,ypoints,pt.length);
			}
			return;
		}

		// 
		for(int i = 0; i < list.size()-1; i++) {
			Point pt1 = list.get(i);
			Point pt2 = list.get(i+1);
			g.drawLine(pt1.x,pt1.y,pt2.x,pt2.y);
		}
		if (lastLine != null) lastLine.drawEx(g);
	}
	static Polygon createPtRegion(Point pt)
	{
		int xpoints[] = new int[4];
		int ypoints[] = new int[4];

		xpoints[0] = pt.x-GAP; ypoints[0] = pt.y-GAP;
		xpoints[1] = pt.x+GAP; ypoints[1] = pt.y-GAP;
		xpoints[2] = pt.x+GAP; ypoints[2] = pt.y+GAP;
		xpoints[3] = pt.x-GAP; ypoints[3] = pt.y+GAP;

		return new Polygon(xpoints,ypoints,4);
	}
	boolean isContainedIn(Polygon selectorRegion) {
		if (ptRegion == null) return false;
		for(int i = 0; i < ptRegion.length; i++) {
			Rectangle2D boundBox = ptRegion[i].getBounds2D();
			if (selectorRegion.contains(boundBox) == false) return false;
		}
		return true;
	}
	public boolean contains(int x, int y) {
		if (ptRegion == null) return false;
		for(int i = 0; i < ptRegion.length; i++) {
			if (ptRegion[i].contains(x,y)) return true;
		}
		return false;
	}
	public void makeRegion() {
		ptRegion = new Polygon[pt.length];

		for(int i = 0; i < pt.length; i++) {
			ptRegion[i] = createPtRegion(pt[i]);
		}
	}
	public Point getStartPositionToResize(int x, int y) {
		int nearestPos = 0;
		int px = pt[0].x;
		int py = pt[0].y;
		double minDistance = Math.sqrt((x-px)*(x-px) + (y-py)*(y-py));
		double distance = 0.0;
		for(int i = 1; i < pt.length; i++) {
			px = pt[i].x;
			py = pt[i].y;
			distance = Math.sqrt((x-px)*(x-px) + (y-py)*(y-py));
			if (distance < minDistance)	{
				minDistance = distance;
				nearestPos = i;
			}
		}
		hotPoint = nearestPos;
		return pt[nearestPos];
	}
	public boolean isResizing() {
		if (hotPoint == -1) return false;
		return true;
	}
	public void setXY2(int x, int y) {
		if (hotPoint == -1) {
			lastLine.setXY2(x,y);
		} else {
			pt[hotPoint].x = x;
			pt[hotPoint].y = y;
		}
	}
	public boolean constructPointArray()
	{
		if (list.size() < 2) return false;

		pt = new Point[list.size()];
		for (int i = 0; i < pt.length; i++)	{
			pt[i] = list.get(i);
		}
		list.clear();
		return true;
	}
	public void resetLineSegment(int x,int y) {
		list.add(new Point(x,y));
		int x1 = lastLine.getX2();
		int y1 = lastLine.getY2();
		lastLine.setXY12(x1,y1,x,y);
	}
	public void setXY12(int x1, int y1, int x2, int y2) {
		lastLine.setXY12(x1,y1,x2,y2);
	}
	public int getX1() {
		if (lastLine != null) return lastLine.x1;
		return pt[0].x;
	}
	public int getY1() {
		if (lastLine != null) return lastLine.y1;
		return pt[0].y;
	}
	public int getX2() {
		if (lastLine != null) return lastLine.x2;
		return pt[pt.length-1].x;
	}
	public int getY2() {
		if (lastLine != null) return lastLine.y2;
		return pt[pt.length-1].y;
	}
	void setX1(int x) {
		if (lastLine != null) lastLine.x1 = x;
		pt[0].x = x;
	}
	void setY1(int y) {
		if (lastLine != null) lastLine.y1 = y;
		pt[0].y = y;
	}
	void setX2(int x) {
		if (lastLine != null) lastLine.x2 = x;
		pt[pt.length-1].x = x;
	}
	void setY2(int y) {
		if (lastLine != null) lastLine.y2 = y;
		pt[pt.length-1].y = y;
	}
	Point getStartPositionToResize() {
		return new Point(lastLine.x2,lastLine.y2);
	}
}
