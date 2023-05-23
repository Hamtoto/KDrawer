package kdr.fig;

import java.awt.geom.*; 
import java.awt.*;

public class KScribble extends KLinearFigure implements Shape {
    int xmin=0, xmax=0, ymin=0, ymax=0;  
    
	public KScribble(Color color) {
		this(color,0,0);
	}
	public KScribble(Color color,int x,int y) {
		this(color,x,y,x,y);
	}
	public KScribble(Color color,int x1,int y1,int x2,int y2) {
		this(color,1,x1,y1,x2,y2);
	}
	public KScribble(Color color,int thickness,int x1,int y1,int x2,int y2) {
		super(color,thickness,x1,y1,x2,y2);
	}

	public void calcMinMax() {
		xmin = pt[0].x;
		xmax = pt[0].x;
		ymin = pt[0].y;
		ymax = pt[0].y;
		for(int i = 1; i < pt.length; i++) {
			if (xmin > pt[i].x) xmin = pt[i].x;
			if (xmax < pt[i].x) xmax = pt[i].x;
			if (ymin > pt[i].y) ymin = pt[i].y;
			if (ymax < pt[i].y) ymax = pt[i].y;
		}
	}

    public Rectangle2D getBounds2D() {
		calcMinMax();
		return new Rectangle2D.Float(xmin,ymin,xmax-xmin,ymax-ymin);
    }

    public Rectangle getBounds() {
		calcMinMax();
		return new Rectangle(xmin,ymin,xmax-xmin,ymax-ymin);
    }

    public boolean contains(Point2D p) { return false; }
    public boolean contains(Rectangle2D r) { return false; }
    public boolean contains(double x, double y) { return false; }
    public boolean contains(double x, double y, double w, double h) {
		return false;
    }
    public boolean intersects(Rectangle2D r) {
		if (pt.length < 1) return false;
		float lastx = pt[0].x, lasty = pt[0].y;
		for(int i = 1; i < pt.length; i++) {  
			float x = pt[i].x;
			float y = pt[i].y;
			if (r.intersectsLine(x, y, lastx, lasty)) return true;
			lastx = x;
			lasty = y;
		}
		return false;  
    }

    public boolean intersects(double x, double y, double w, double h) {
		return intersects(new Rectangle2D.Double(x,y,w,h));
    }

    public PathIterator getPathIterator(final AffineTransform transform) {
		return new PathIterator() {
			int curseg = -1; 
			int numsegs = pt.length-1;

			public boolean isDone() { return curseg >= numsegs; }

			public void next() { curseg++; }

			// Get coordinates and type of current segment as floats
			public int currentSegment(float[] data) {
				int segtype;
				if (curseg == -1) {       
					data[0] = pt[0].x;         
					data[1] = pt[0].y;
					segtype = SEG_MOVETO; 
				} else { 
					data[0] = pt[curseg+1].x;
					data[1] = pt[curseg+1].y;
					segtype = SEG_LINETO; // Returned as a lineto segment
				}
				if (transform != null)
					transform.transform(data, 0, data, 0, 1);
				return segtype;
			}

			// Same as last method, but use doubles
			public int currentSegment(double[] data) {
				int segtype;
				if (curseg == -1) {
					data[0] = pt[0].x;
					data[1] = pt[0].y;
					segtype = SEG_MOVETO;
				}
				else {
					data[0] = pt[curseg+1].x;
					data[1] = pt[curseg+1].y;
					segtype = SEG_LINETO;
				}
				if (transform != null)
					transform.transform(data, 0, data, 0, 1);
				return segtype;
			}

			public int getWindingRule() { return WIND_NON_ZERO; }
		};
    }

    public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return getPathIterator(at);
    }
	protected void drawDotEx(Graphics g) {
		g.fillRect(pt[0].x-DOTSIZE/2,pt[0].y-DOTSIZE/2,DOTSIZE,DOTSIZE);
		g.fillRect(pt[pt.length-1].x-DOTSIZE/2,pt[pt.length-1].y-DOTSIZE/2,DOTSIZE,DOTSIZE);
	}
	void drawEx(Graphics g) {
		if (dotFlag == true) drawDotEx(g);

		if (pt != null)	{
			Graphics2D g2 = (Graphics2D)g;
			g2.draw(this);
			return;
		}

		for(int i = 0; i < list.size()-1; i++) {
			Point pt1 = list.get(i);
			Point pt2 = list.get(i+1);
			g.drawLine(pt1.x,pt1.y,pt2.x,pt2.y);
		}
		if (lastLine != null) lastLine.drawEx(g);
	}
	public void setXY2(int x, int y) {
		resetLineSegment(x,y);
	}
	public KFigure copy() {
		int x1 = lastLine.getX1();
		int y1 = lastLine.getY1();
		int x2 = lastLine.getX2();
		int y2 = lastLine.getY2();
		KScribble newScribble = new KScribble(color,thickness,x1,y1,x2,y2);
		newScribble.list.clear();
		newScribble.pt = new Point[pt.length];
		for (int i = 0; i < pt.length; i++)	{
			// deep copy
			newScribble.pt[i] = new Point(pt[i].x,pt[i].y);
		}
		newScribble.popup = popup;
		newScribble.fillFlag = fillFlag;
		newScribble.closedFlag = closedFlag;
		return newScribble;
	}
}
