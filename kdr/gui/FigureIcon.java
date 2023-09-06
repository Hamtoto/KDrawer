package kdr.gui;

import kdr.fig.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

class FigureIcon implements Icon {
	public static int WIDTH = 16;
	public static int HEIGHT = 16;
	String figureType;

	public FigureIcon(String figureType) {
		this.figureType = figureType;
	}

	public int getIconHeight() {
		return WIDTH;
	}

	public int getIconWidth() {
		return HEIGHT;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.black);

		if (figureType.equals(DrawerView.figureType[0])) // Point
		{
			g.drawOval(x + WIDTH / 2 - 1, y + HEIGHT / 2 - 1, 2, 2);
			g.fillOval(x + WIDTH / 2 - 1, y + HEIGHT / 2 - 1, 2, 2);
		} else if (figureType.equals(DrawerView.figureType[1])) // Box
		{
			g.drawRect(x, y, WIDTH, HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[2])) // Line
		{
			g.drawLine(x, y, x + WIDTH, y + HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[3])) // Lines
		{
			g.drawLine(x, y, x + WIDTH / 3, y + HEIGHT - 3);
			g.drawLine(x + WIDTH / 3, y + HEIGHT - 3, x + WIDTH * 2 / 3, y + 7);
			g.drawLine(x + WIDTH * 2 / 3, y + 7, x + WIDTH, y + HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[4])) // Curve
		{
			int[] xp = new int[4];
			int[] yp = new int[4];

			CubicCurve2D.Double curve = new CubicCurve2D.Double();

			xp[0] = x;				yp[0] = y;
			xp[1] = x+WIDTH/3;		yp[1] = y+HEIGHT;
			xp[2] = x+WIDTH*2/3;	yp[2] = y;
			xp[3] = x+WIDTH;		yp[3] = y+HEIGHT;

			curve.setCurve(xp[0], yp[0], xp[1], yp[1], xp[2], yp[2], xp[3], yp[3]);
			Graphics2D g2 = (Graphics2D) g;
			g2.draw(curve);
		} else if (figureType.equals(DrawerView.figureType[5])) // Arrow
		{
			int[] xpoints = new int[7];
			int[] ypoints = new int[7];

			xpoints[0] = x;				ypoints[0] = y+HEIGHT-4;
			xpoints[1] = x;				ypoints[1] = y+4;
			xpoints[2] = x+WIDTH/2;		ypoints[2] = y+4;
			xpoints[3] = x+WIDTH/2;		ypoints[3] = y;
			xpoints[4] = x+WIDTH;		ypoints[4] = y+HEIGHT/2;
			xpoints[5] = x+WIDTH/2;		ypoints[5] = y+HEIGHT;
			xpoints[6] = x+WIDTH/2;		ypoints[6] = y+HEIGHT-4;

			g.drawPolygon(xpoints, ypoints, 7);
		} else if (figureType.equals(DrawerView.figureType[6])) // Circle
		{
			g.drawOval(x, y, WIDTH, HEIGHT);
		} else if (figureType.equals(DrawerView.figureType[7])) // Triangle
		{
			g.drawLine(x + WIDTH / 2, y - 1, x, y + HEIGHT - 1);
			g.drawLine(x + WIDTH / 2, y - 1, x + WIDTH, y + HEIGHT - 1);
			g.drawLine(x, y + HEIGHT - 1, x + WIDTH, y + HEIGHT - 1);
		} else if (figureType.equals(DrawerView.figureType[8])) // Right Triangle
		{
			g.drawLine(x + 2, y, x + 2, y + HEIGHT - 1);
			g.drawLine(x + 2, y + HEIGHT - 1, x + WIDTH - 4, y + HEIGHT - 1);
			g.drawLine(x + WIDTH - 4, y + HEIGHT - 1, x + 2, y);
		} else if (figureType.equals(DrawerView.figureType[9])) // Diamond
		{
			g.drawPolygon(new int[]{x + (WIDTH / 2), x + WIDTH, x + (WIDTH / 2), x},
					new int[]{y, y + (HEIGHT / 2), y + HEIGHT, y + (HEIGHT / 2)}, 4);
		} else if (figureType.equals(DrawerView.figureType[10])) // Star
		{
			int[] xpoints = new int[10];
			int[] ypoints = new int[10];

			KStar.makeStarPolygon(4, 4, 20, 20, xpoints, ypoints);
			g.drawPolygon(xpoints, ypoints, 10);
		} else if (figureType.equals(DrawerView.figureType[11])) // Scribble
		{
			g.drawPolygon(new int[]{x + 1, x + 3, x + 14, x + 12, x + 1},
					new int[]{y + 14, y + 14, y + 3, y + 1, y + 12}, 5);
			g.fillPolygon(new int[]{x + 2, x + 1, x + 1, x + 3, x + 4},
					new int[]{y + 11, y + 12, y + 14, y + 14, y + 13}, 5);
			g.fillPolygon(new int[]{x + 13, x + 14, x + 12, x + 11},
					new int[]{y + 6, y + 3, y + 1, y + 4}, 4);
			g.drawLine(x + 1, y + 14, x + 13, y + 14);

		} else if (figureType.equals(DrawerView.figureType[12])) // Text
		{
			Font oldFont = g.getFont();
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.drawString("T", x + 1, y + 15);
			g.setFont(oldFont);
		} else if (figureType.equals(DrawerView.figureType[13])) // KImage
		{
			g.fillOval(x + WIDTH / 3 * 2 - 1, y + HEIGHT / 5, WIDTH / 3, HEIGHT / 3);
			g.drawPolygon(new int[]{x, x + WIDTH, x + WIDTH, x},
					new int[]{y, y, y + HEIGHT, y + HEIGHT}, 4);
			g.fillPolygon(new int[]{x, x + 5, x + 8, x + WIDTH / 3 * 2, x + WIDTH},
					new int[]{y + HEIGHT, y + HEIGHT / 7 * 4, y + HEIGHT / 7 * 6, y + HEIGHT / 3 * 2, y + HEIGHT}, 5);
		} else if (figureType.equals(DrawerView.figureType[14])) // Eraser
		{
			g.drawPolygon(new int[]{x + 4, x + 6, x + 14, x + 14, x + 10, x + 9, x + 1, x + 1},
					new int[]{y + 14, y + 14, y + 6, y + 5, y + 1, y + 1, y + 9, y + 11}, 8);
			g.drawLine(x + 4, y + 14, x + 14, y + 14);
			g.drawLine(x + 8, y + 12, x + 3, y + 7);

		} else { // Undefined
			Font oldFont = g.getFont();
			g.setFont(new Font("Times New Roman", Font.BOLD, 20));
			g.drawString("?", x + 1, y + 15);
			g.setFont(oldFont);
		}
	}
}
