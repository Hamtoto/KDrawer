package kdr.fig;

import kdr.gui.popup.KFigurePopup;
import kdr.gui.popup.KPopup;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class KFigure implements Serializable
{
	private static final long serialVersionUID = 5115481395465861190L;
	protected static int MOVE_DX = 100;
	protected static int MOVE_DY = 80;
	protected static int GAP = 3;

	protected Polygon region;
	protected Color color;
	protected int thickness;
	transient protected KPopup popup;

	protected static int DOTSIZE = 6;
	transient protected boolean dotFlag;

	public KFigure(Color color) {
		this(color,1);
	}
	public KFigure(Color color, int thickness) {
		this.color = color;
		region = null;
		popup = null;
		this.thickness = thickness;
		dotFlag = false;
	}
	// hook function
	public void setFill() {
	}
	public boolean isObsolete() {
		return false;
	}
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	void prepareGraphics(Graphics g) {
		g.setColor(color);
		((Graphics2D)g).setStroke(new BasicStroke(thickness));
	}
	public void setColor(Color color) {
		this.color = color;
	}
	/*void setStroke(Graphics g) {
		((Graphics2D)g).setStroke(new BasicStroke(thickness));
	}*/
	public void setPopup(KPopup popup) {
		this.popup = popup;
	}
	public void preparePopup() {
		if (popup instanceof KFigurePopup) {
			KFigurePopup figurePopup = (KFigurePopup)popup;
			figurePopup.setThicknessSeletion(thickness);
		}
	}
	public void popup(JPanel view, int x, int y) {
		// delegation
		preparePopup();
		popup.popup(view,x,y);
	}
	public abstract void setXY2(int x, int y);
	public void draw(Graphics g) {
		prepareGraphics(g);
		drawEx(g);
	}
	abstract void drawEx(Graphics g);
	public abstract void makeRegion();
	abstract void move(int dx,int dy);
	public KFigure copyAndMove() {
		KFigure fig = copy();
		fig.move(MOVE_DX,MOVE_DY);
		return fig;
	}
	public abstract KFigure copy();
	public boolean getDotFlag() {
		return dotFlag;
	}
	void drawDot(Graphics g) {
		dotFlag = true;
		g.setColor(Color.black);
		((Graphics2D)g).setStroke(new BasicStroke(1));
		drawEx(g);
	}
	void eraseDot(Graphics g) {
		dotFlag = false;
	}
	public abstract Point getStartPositionToResize(int x, int y);
	boolean isContainedIn(Polygon selectorRegion) {
		if (region == null) return false;
		Rectangle2D boundBox = region.getBounds2D();
		return selectorRegion.contains(boundBox);
	}
	public boolean contains(int x, int y) {
		if (region == null) return false;
		return region.contains(x,y);
	}
	public void move(Graphics g, int dx, int dy) {
		draw(g);
		move(dx,dy);
		draw(g);
	}
	public void drawing(Graphics g, int newX, int newY) {
		draw(g);
		setXY2(newX,newY);
		draw(g);
	}
	// hook function
	public int getX1() {
		return -1;
	}
	public int getY1() {
		return -1;
	}
	public int getX2() {
		return -1;
	}
	public int getY2() {
		return -1;
	}

	public void setX1(int x) {
	}
	public void setY1(int y) {
	}
	public void setX2(int x) {
	}
	public void setY2(int y) {
	}

	public String toString() {
		String s = "" + getClass().getSimpleName().substring(1) + "[" + getX1() + "," + getY1();
		if (getX2() < 0)
		{
			s = s + "]";
			return s;
		}
		s = s + "," + getX2() + "," + getY2() + "]";
		return s;
	}
}
