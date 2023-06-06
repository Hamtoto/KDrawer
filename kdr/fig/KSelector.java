package kdr.fig;

import kdr.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class KSelector  
{
	private DrawerView canvas;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Polygon region;
	private ArrayList<KFigure> bag;
	public KSelector(DrawerView view) {
		canvas = view;
		bag = new ArrayList<KFigure>();
		reset();
	}
	public void reset() {
		x1 = y1 = x2 = y2 = -1;
		bag.clear();
	}
	public boolean contains(KFigure figure) {
		return bag.contains(figure);
	} 
	public boolean isEmpty() {
		return bag.isEmpty();
	}
	public void copyFiguresInBag() {
		ArrayList<KFigure> copied = new ArrayList<KFigure>();
		for(int i = 0; i < bag.size(); i++) {
			KFigure pFigure = bag.get(i);
			copied.add(pFigure.copyAndMove());
			pFigure.eraseDot(canvas.getGraphics());
		}
		bag = copied;
		for(int i = 0; i < bag.size(); i++) {
			KFigure pFigure = bag.get(i);
			pFigure.drawDot(canvas.getGraphics());
			canvas.addFigure(pFigure);
		}
	}
	public void drawAndAddFiguresInBag(Graphics g) {
		for(int i = 0; i < bag.size(); i++) {
			KFigure pFigure = bag.get(i);
			pFigure.draw(g);
			canvas.addFigure(pFigure);
		}
	}
	public void moveFiguresInBag(Graphics g,int dx,int dy) {
		for(int i = 0; i < bag.size(); i++) {
			KFigure pFigure = bag.get(i);
			pFigure.move(g,dx,dy);
		}
	}
	public void removeFrom(ArrayList<KFigure> figures) {
		for(int i = 0; i < bag.size(); i++) {
			KFigure pFigure = bag.get(i);
			figures.remove(pFigure);
		}
	}
	public void removeFiguresInBag() {
		removeFrom(canvas.getFigures());
		reset();
	}
	public void emptyBag(Graphics g) {
		g.setColor(canvas.getBackground());
		for(int i = 0; i < bag.size(); i++) {
			KFigure pFigure = bag.get(i);
			pFigure.eraseDot(g);
		}
		reset();
	}
	public void setXY1(int x,int y) {
		x1 = x2 = x;
		y1 = y2 = y;
	}
	private void setXY2(int x,int y) {
		x2 = x;
		y2 = y;
	}
	public void drawing(Graphics rubberbandG,int newX,int newY,Graphics plainG) {
		draw(rubberbandG);
		setXY2(newX,newY);
		draw(rubberbandG);
		collectFigures(plainG);
	}
	private void collectFigures(Graphics g) {
		ArrayList<KFigure> figures = canvas.getFigures();
		bag.clear();
		makeRegion();
		for(int i = 0; i < figures.size(); i++) {
			KFigure pFigure = figures.get(i);
			if (pFigure.isContainedIn(region)) {
				pFigure.drawDot(g); 
				bag.add(pFigure);
			} else if (pFigure.getDotFlag() == true) {
				g.setColor(canvas.getBackground());
				pFigure.eraseDot(g);
				bag.remove(pFigure);
			}
		}
	}
	public void draw(Graphics g) {
		g.drawLine(x1,y1,x1,y2);
		g.drawLine(x1,y2,x2,y2);
		g.drawLine(x2,y2,x2,y1);
		g.drawLine(x2,y1,x1,y1);
	}
	private void makeRegion() {
		int xpoints[] = new int[4];
		int ypoints[] = new int[4];

		xpoints[0] = x1; ypoints[0] = y1;
		xpoints[1] = x1; ypoints[1] = y2;
		xpoints[2] = x2; ypoints[2] = y2;
		xpoints[3] = x2; ypoints[3] = y1;

		region = new Polygon(xpoints,ypoints,4);
	}
}
