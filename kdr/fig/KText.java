package kdr.fig;

import java.awt.*;

public class KText extends KBox
{
	private static final long serialVersionUID = -5526552132759568802L;
	String[] lines;
	Font font;

	public KText(Color color, Font font) {
		this(color,font,0,0);
	}
	public KText(Color color, Font font,int x,int y) {
		this(color,font,x,y,x,y,null);
	}
	public KText(Color color, Font font,int x1,int y1,int x2,int y2,String lines[]) {
		super(color,x1,y1,x2,y2);
		this.lines = lines;
		this.font = font;
	}
	public void preparePopup() {
	}
	public String getText() {
		String s = "";
		for(int i = 0; i < lines.length; i++) {
			s = s + lines[i] + "\n\r";
		}
		return s;
	}

	public void setColor(Color color) {
		super.setColor(color);
	}
	void prepareGraphics(Graphics g) {
		g.setColor(color);
		g.setFont(font);
	}
	protected void drawEx(Graphics g) {
		if (dotFlag) drawDotEx(g);

		prepareGraphics(g);

		Font font = g.getFont();
		FontMetrics fm = g.getFontMetrics(font);
		int height = fm.getHeight();
		int ascent = fm.getAscent();
		for(int i = 0; i < lines.length; i++) {
			g.drawString(lines[i],x1,y1+(ascent+i*height));
		}
	}
	void drawDot(Graphics g) {
		dotFlag = true;
		g.setColor(Color.black);
		((Graphics2D)g).setStroke(new BasicStroke(1));
		drawDotEx(g);
	}

	public KFigure copy() {
		KText newText = new KText(color,font,x1,y1,x2,y2,lines);
		newText.popup = popup;
		return newText;
	}
	public void changeFont(Graphics g, Font font){
		this.font = font;
		FontMetrics fm = g.getFontMetrics(font);
		int w;

		if (lines.length == 1 && lines[0].equals("")) return;

		int maxWidth = 0;
		for (int i = 0; i < lines.length; i++)
		{
			String s = lines[i];
			w = fm.stringWidth(s);
			if (w > maxWidth) maxWidth = w;
		}
		int maxHeight = lines.length * fm.getHeight();

		x2 = x1 + maxWidth;
		y2 = y1 + maxHeight;
	}
}
