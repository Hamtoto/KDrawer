package kdr.gui;

import kdr.fig.*;
import kdr.gui.dlg.*;
import kdr.gui.popup.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.event.*;
import java.awt.image.*;

public class DrawerView extends JPanel 
				implements MouseListener, MouseMotionListener 
{	
	static class TextEditor extends JTextArea 
		implements DocumentListener, MouseListener, KeyListener
	{
		private DrawerView canvas;
		private int INIT_WIDTH = 100;
		private int INIT_HEIGHT = 150;
		private int DELTA = 30;
		private Font font;
		private FontMetrics fm;
		int x;
		int y;
		int width;
		int height;
		TextEditor(DrawerView canvas) {
			super();
			this.canvas = canvas;
			setBackground(canvas.getBackground());
			font = canvas.currentFont;
			fm = null;
			
			getDocument().addDocumentListener(this);
		}
		public void start(KText text) {
			font = canvas.currentFont;
			setText(text.getText());

			fm = null;
			this.x = text.getX1();
			this.y = text.getY1();
			this.width = text.getX2() - text.getX1();
			this.height = text.getY2() - text.getY1();

			setBounds(x,y,width,height);
			Graphics g = canvas.getGraphics();
			g.setColor(Color.blue);
			g.drawRect(x,y,width-2,height);
			setBorder(BorderFactory.createLineBorder(Color.blue));
			setCaretPosition(0);

			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);

			canvas.add(this);
			requestFocus();

			canvas.addMouseListener(this);
			this.addKeyListener(this);
		}
		public void start(int x,int y) {
			font = canvas.currentFont;
			setText("");
			fm = null;
			this.x = x;
			this.y = y;
			this.width = INIT_WIDTH;
			this.height = INIT_HEIGHT;

			setBounds(x,y,width,height);
			Graphics g = canvas.getGraphics();
			g.setColor(Color.blue);
			g.drawRect(x,y,INIT_WIDTH-2,INIT_HEIGHT);
			setBorder(BorderFactory.createLineBorder(Color.blue));
			setCaretPosition(0);

			canvas.removeMouseListener(canvas);
			canvas.removeMouseMotionListener(canvas);

			canvas.add(this);
			requestFocus();

			canvas.addMouseListener(this);
			this.addKeyListener(this);
		}
		public void stopEditor() {
			if (fm == null)
			{
				canvas.getGraphics().setFont(font);
				fm = canvas.getGraphics().getFontMetrics();
			}

			canvas.remove(this);

			canvas.removeMouseListener(this);
			this.removeKeyListener(this);

			canvas.addMouseListener(canvas);
			canvas.addMouseMotionListener(canvas);

			String text = getText();
			String[] lines = text.split("\n");
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

			KText newFigure = 
				new KText(Color.black,font,x,y,x+maxWidth,y+maxHeight,lines);
			newFigure.setPopup(canvas.getTextPopup());
			newFigure.changeFont(canvas.getGraphics(),font);

			canvas.addFigure(newFigure);

			canvas.setWhatToDraw(ID_MOUSE);

			canvas.repaint();
		}
		public void insertUpdate(DocumentEvent e) {
			if (fm == null)
			{
				setFont(font);
				fm = getGraphics().getFontMetrics();
			}
			String text = getText();
			String[] lines = text.split("\n");
			
			int w;
			int maxWidth = 0;
			for (int i = 0; i < lines.length; i++)
			{
				String s = lines[i];
				w = fm.stringWidth(s);
				if (w > maxWidth) maxWidth = w;
			}
			if (maxWidth > width)
			{
				width = width + DELTA;
				setBounds(x,y,width,height);
			}
			int maxHeight = lines.length * fm.getHeight();
			if (maxHeight > height)
			{
				height = height + DELTA;
				setBounds(x,y,width,height);
			}
		}
		public void removeUpdate(DocumentEvent e) {
		}
		public void changedUpdate(DocumentEvent e) {
		}
		public void mouseClicked(MouseEvent e) {
			stopEditor();
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				stopEditor();
			}
		}
		public void keyTyped(KeyEvent e) {
		}
		public void keyReleased(KeyEvent e) {
		}
	}

	public static String[] figureType 
		= { "Point", "Box", "Line", "Lines", "Curve", "Arrow", "Circle", 
			"Triangle", "Diamond", "Star", "Scribble", "Text", "Image", "Eraser" };
	public static String[] koreanFigureType 
		= { "점", "사각형", "선분", "여러 선", "곡선", "화살표", "원", 
			"삼각형", "마름모", "별", "낙서", "텍스트", "그림", "지우개" };
	public static ArrayList<String> figureTypeNames 
									= new ArrayList<String>();
	public static HashMap figureTypes = new HashMap<String,String>();
	// static initializer
	static {
		for(int i = 0; i < figureType.length; i++) {
			figureTypeNames.add("K"+figureType[i]);
		}
		for(int i = 0; i < figureType.length; i++) {
			figureTypes.put(figureType[i],koreanFigureType[i]);
		}
	}

	public static int INIT_WIDTH = 3000;
	public static int INIT_HEIGHT = 1500;
	public static int DELTA = 500;

	public static int ID_MOUSE = -1;

	public static int ID_KPOINT = 0;
	public static int ID_KBOX = 1;
	public static int ID_KLINE = 2;
	public static int ID_KLINES = 3;
	public static int ID_KCURVE = 4;
	public static int ID_KARROW = 5;
	public static int ID_KCIRCLE = 6;
	public static int ID_KTRIANGLE = 7;	
	public static int ID_KDIAMOND = 8;
	public static int ID_KSTAR = 9;
	public static int ID_KSCRIBBLE = 10;
	public static int ID_KTEXT = 11;
	public static int ID_KIMAGE = 12;
	public static int ID_KERASER = 13;

	public static int NOTHING = 0;
	public static int DRAWING = 1;
	public static int MOVING = 2;
	public static int RESIZING = 3;
	public static int SELECTING = 4;

	private int actionMode;
	private int whatToDraw;
	private KFigure selectedFigure;
	private ArrayList<KFigure> figures;

	private int currentX;
	private int currentY;
	private int popupX;
	private int popupY;
	private MouseMotionListener mouseGrabber = null;

	private KSelector selector;

	private KPopup mainPopup;
	private KPopup groupPopup;
	private KPopup pointPopup;
	private KPopup boxPopup;
	private KPopup linePopup;
	private KPopup linesPopup;
	private KPopup curvePopup;
	private KPopup arrowPopup;
	private KPopup circlePopup;
	private KPopup trianglePopup;
	private KPopup diamondPopup;
	private KPopup starPopup;
	private KPopup scribblePopup;
	private KPopup textPopup;
	private KPopup imagePopup;
	private KPopup eraserPopup;
	private KPopup[] popups = new KPopup[figureType.length];

	private SelectAction pointAction;
	private SelectAction boxAction;
	private SelectAction lineAction;
	private SelectAction linesAction;
	private SelectAction curveAction;
	private SelectAction arrowAction;
	private SelectAction circleAction;
	private SelectAction triangleAction;
	private SelectAction diamondAction;
	private SelectAction starAction;
	private SelectAction scribbleAction;
	private SelectAction textAction;
	private SelectAction imageAction;
	private SelectAction eraserAction;

	private DrawerFrame mainFrame;

	private double zoomRatio = 1.0;

	private int width = INIT_WIDTH;
	private int height = INIT_HEIGHT;

	private TextEditor textEditor;

	boolean realTimeExchangeFlag = false;
	boolean modifiedFlag = false;

	private JFileChooser imageChooser = null;
	private FontDialog dlg = null;
	private Font currentFont;
	private static String FONT_FAMILY = "Arial";
	private static int FONT_STYLE = Font.PLAIN;
	private static int FONT_SIZE = 16;

	private Color currentColor = Color.black;

	Stack<ArrayList<KFigure>> undoLists;
	boolean firstUndoFlag = true;

	DrawerView(DrawerFrame mainFrame) {
		setLayout(null);
		this.mainFrame = mainFrame;
		figures = new ArrayList<KFigure>();
		undoLists = new Stack<ArrayList<KFigure>>();

		pointAction = new SelectAction("Point (P)",KeyEvent.VK_P,
						new FigureIcon(figureType[0]),this,ID_KPOINT);
		boxAction = new SelectAction("Box (B)",KeyEvent.VK_B,
						new FigureIcon(figureType[1]),this,ID_KBOX);
		lineAction = new SelectAction("Line (L)",KeyEvent.VK_L,
						new FigureIcon(figureType[2]),this,ID_KLINE);
		linesAction = new SelectAction("Lines (S)",KeyEvent.VK_S,
						new FigureIcon(figureType[3]),this,ID_KLINES);
		curveAction = new SelectAction("Curve (V)",KeyEvent.VK_V,
						new FigureIcon(figureType[4]),this,ID_KCURVE);
		arrowAction = new SelectAction("Arrow (A)",KeyEvent.VK_A,
						new FigureIcon(figureType[5]),this,ID_KARROW);
		circleAction = new SelectAction("Circle (C)",KeyEvent.VK_C,
						new FigureIcon(figureType[6]),this,ID_KCIRCLE);
		triangleAction = new SelectAction("Triangle (T)",KeyEvent.VK_T,
							new FigureIcon(figureType[7]),this,ID_KTRIANGLE);
		diamondAction = new SelectAction("Diamond (D)",KeyEvent.VK_D,
							new FigureIcon(figureType[8]), this, ID_KDIAMOND);
		starAction = new SelectAction("Star (R)",KeyEvent.VK_R, 
							new FigureIcon(figureType[9]), this, ID_KSTAR);
		scribbleAction = new SelectAction("Scribble (B)",KeyEvent.VK_B,
							new FigureIcon(figureType[10]), this, ID_KSCRIBBLE);
		textAction = new SelectAction("Text (X)",KeyEvent.VK_X,
							new FigureIcon(figureType[11]),this,ID_KTEXT);
		imageAction = new SelectAction("Image (I)",KeyEvent.VK_I,
							new FigureIcon(figureType[12]),this,ID_KIMAGE);
		eraserAction = new SelectAction("Eraser (E)",KeyEvent.VK_E,
							new FigureIcon(figureType[13]),this,ID_KERASER);
		
		mainPopup = new KMainPopup(this);
		groupPopup = new KGroupPopup(this,"Group");
		pointPopup = new KPointPopup(this,"Point",false);
		boxPopup = new KBoxPopup(this,"Box",true);
		linePopup = new KLinePopup(this,"Line",false);
		linesPopup = new KLinesPopup(this,"Lines",true);
		curvePopup = new KCurvePopup(this,"Curve",false);
		arrowPopup = new KArrowPopup(this,"Arrow",true);
		circlePopup = new KCirclePopup(this,"Circle",true);
		trianglePopup = new KTrianglePopup(this,"Triangle",true);
		diamondPopup = new KDiamondPopup(this, "Diamond", true);
		starPopup = new KStarPopup(this, "Star", true);
		scribblePopup = new KScribblePopup(this, "Scribble", false);
		textPopup = new KTextPopup(this,"Text");
		imagePopup = new KImagePopup(this,"Image");
		eraserPopup = new KEraserPopup(this,"Eraser");

		int i = 0;
		popups[i++] = pointPopup;
		popups[i++] = boxPopup;
		popups[i++] = linePopup;
		popups[i++] = linesPopup;
		popups[i++] = curvePopup;
		popups[i++] = arrowPopup;
		popups[i++] = circlePopup;
		popups[i++] = trianglePopup;
		popups[i++] = diamondPopup;
		popups[i++] = starPopup;
		popups[i++] = scribblePopup;
		popups[i++] = textPopup;
		popups[i++] = imagePopup;
		popups[i++] = eraserPopup;

		imageChooser = createImageChooser();
		currentFont = new Font(FONT_FAMILY, FONT_STYLE, FONT_SIZE);
		dlg = new FontDialog("Font dialog", this);

		textEditor = new TextEditor(this);
		selector = new KSelector(this);

		actionMode = NOTHING;
		setWhatToDraw(ID_KBOX);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(width,height));
		setBackground(Color.white);
	}

	private JFileChooser createImageChooser() {
		JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || 
                        filename.endsWith(".png") || filename.endsWith(".gif");
                }
            }
            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });
		return chooser;
	}
	public void setCurrentColor(Color color) {
		currentColor = color;
	}
	public ArrayList<KFigure> getFigures() {
		return figures;
	}
	void setModified() {
		modifiedFlag = true;
		firstUndoFlag = true;
		if (realTimeExchangeFlag) {
			mainFrame.getTalkDialog().sendFigures();
		}
		ArrayList<KFigure> backup = new ArrayList<KFigure>();
		for(int i = 0; i < figures.size(); i++) {
			backup.add(figures.get(i).copy());
		}
		undoLists.push(backup);
	}
	public void undo() {
		if (undoLists.empty()) return;
		if (firstUndoFlag)
		{
			firstUndoFlag = false;
			undoLists.pop();
		}
		if (undoLists.empty()) return;
		
		figures = undoLists.pop();
		for(int i = 0; i < figures.size(); i++) {
			figures.get(i).makeRegion();
		}
		repaint();
	}
	public void setRealTimeExchangeFlag(boolean flag) {
		realTimeExchangeFlag = flag;
	}
	private void setBound() {
		setPreferredSize(new Dimension(width,height));
		updateUI();
	}
	private void setHeight() {
		setBound();
	}
	public void increaseHeight() {
		height = height + DELTA;
		setHeight();
	}
	private void setWidth() {
		setBound();
	}
	public void increaseWidth() {
		width = width + DELTA;
		setWidth();
	}
	public SelectAction getPointAction() {
		return pointAction;
	}
	public SelectAction getBoxAction() {
		return boxAction;
	}
	public SelectAction getLineAction() {
		return lineAction;
	}
	public SelectAction getLinesAction() {
		return linesAction;
	}
	public SelectAction getCurveAction() {
		return curveAction;
	}
	public SelectAction getArrowAction() {
		return arrowAction;
	}
	public SelectAction getCircleAction() {
		return circleAction;
	}
	public SelectAction getTriangleAction() {
		return triangleAction;
	}
	public SelectAction getDiamondAction() {
		return diamondAction;
	}
	public SelectAction getStarAction() {
		return starAction;
	}
	public SelectAction getScribbleAction() {
		return scribbleAction;
	}
	public SelectAction getTextAction() {
		return textAction;
	}
	public SelectAction getImageAction() {
		return imageAction;
	}
	public SelectAction getEraserAction() {
		return eraserAction;
	}

	public KPopup getGroupPopup() {
		return groupPopup;
	}
	public KPopup getPointPopup() {
		return pointPopup;
	}
	public KPopup getBoxPopup() {
		return boxPopup;
	}
	public KPopup getLinePopup() {
		return linePopup;
	}
	public KPopup getLinesPopup() {
		return linesPopup;
	}
	public KPopup getArrowPopup() {
		return arrowPopup;
	}
	public KPopup getCirclePopup() {
		return circlePopup;
	}
	public KPopup getTrianglePopup() {
		return trianglePopup;
	}
	public KPopup getDiamondPopup() {
		return diamondPopup;
	}
	public KPopup getStarPopup() {
		return starPopup;
	}
	public KPopup getScribblePopup() {
		return scribblePopup;
	}
	public KPopup getTextPopup() {
		return textPopup;
	}	
	public KPopup getImagePopup() {
		return imagePopup;
	}
	public KPopup getEraserPopup() {
		return eraserPopup;
	}

	void setWhatToDraw(int id) {
		whatToDraw = id;
		if(id >= 0) mainFrame.writeFigureType(figureType[id]);
		else  mainFrame.writeFigureType("Select");
	}
	public void doFileNew() {
		figures.clear();
		repaint();
		modifiedFlag = false;
	}
	public void doReceive(ObjectInputStream ois) {
		try
		{
			width = (int)(ois.readObject());
			height = (int)(ois.readObject());
			currentFont = (Font)(ois.readObject());
			figures = (ArrayList<KFigure>)(ois.readObject());

			for(KFigure ptr : figures) {
				String figureTypeName = ptr.getClass().getSimpleName();
				int index = figureTypeNames.indexOf(figureTypeName);
				ptr.setPopup(popups[index]);
			}

			setBound();
			repaint();
		}
		catch (IOException ex)
		{
		} catch (ClassNotFoundException ex) {
		}
	}
	public void doSend(ObjectOutputStream oos) {
		try
		{
			oos.writeObject(width);
			oos.writeObject(height);
			oos.writeObject(currentFont);
//			oos.writeObject(figures); // troublesome: do not overwrite
			ArrayList<KFigure> list = new ArrayList<KFigure>();
			for(int i = 0; i < figures.size(); i++) {
				KFigure fig = figures.get(i);
				KFigure copied = fig.copy();
				copied.makeRegion();
				list.add(copied);
			}
			oos.writeObject(list);
			oos.flush();
		}
		catch (IOException ex)
		{
		}
	}
	public void doOpen(String fileName) {
		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);

			doReceive(ois);
			
			ois.close();
			fis.close();
		}
		catch (IOException ex)
		{
		}
		modifiedFlag = false;
	}
	public void doSave(String fileName) {
		try
		{
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(width);
			oos.writeObject(height);
			oos.writeObject(currentFont);
			oos.writeObject(figures);
			oos.flush();
			oos.close();
			fos.close();
		}
		catch (IOException ex)
		{
		}
		modifiedFlag = false;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		((Graphics2D)g).scale(zoomRatio,zoomRatio);

		for(int i = 0; i < figures.size(); i++) {
			KFigure pFigure = figures.get(i);
			pFigure.draw(g); // dynamic binding
		}
	}
	public void zoom(int ratio) {
		zoomRatio = (double)ratio/100.0;
		repaint();
		removeMouseListener(this);
		removeMouseMotionListener(this);
		if (ratio == 100)
		{
			addMouseListener(this);
			addMouseMotionListener(this);
		}
	}
	public void removeGrabListener(MouseMotionListener l) {
		// MouseMotionListener
		MouseMotionListener listener[] = getMouseMotionListeners();
		for(int i = 0; i < listener.length; i++) {
			removeMouseMotionListener(listener[i]);
		}
	}
	public void cancelCurveDrawing() {
		removeGrabListener(mouseGrabber);
		addMouseMotionListener(this);
		actionMode = NOTHING;
		if (selectedFigure == null) return;
		selectedFigure = null;
		setWhatToDraw(ID_MOUSE);
	}
	public void stopCurveDrawing() {
		removeGrabListener(mouseGrabber);
		addMouseMotionListener(this);
		actionMode = NOTHING;
		if (selectedFigure == null) return;
		selectedFigure.draw(getGraphics());
		addFigure(selectedFigure);
		selectedFigure = null;
		setWhatToDraw(ID_MOUSE);
	}
	public void stopLinesDrawing() {
		removeGrabListener(mouseGrabber);
		addMouseMotionListener(this);
		actionMode = NOTHING;
		if (selectedFigure == null) return;
		if (!(selectedFigure instanceof KLines)) return;
		KLines lines = (KLines)selectedFigure;
		if (lines.constructPointArray() == true) {
			// line segment 
			selectedFigure.draw(getGraphics());
			addFigure(selectedFigure);
		}	
		selectedFigure = null;
		setWhatToDraw(ID_MOUSE);
	}
    public void mouseClicked(MouseEvent e) {

	}
    public void mousePressed(MouseEvent e) {
		if (actionMode == RESIZING)
		{
			removeGrabListener(mouseGrabber);
			addMouseMotionListener(this);
			actionMode = DRAWING;
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			return;
		}

		int x = e.getX();
		int y = e.getY();

		if ((actionMode == DRAWING) && (selectedFigure != null)
			&& (selectedFigure instanceof KLines)) {
			KLines lines = (KLines)selectedFigure;
			lines.setXY12(x,y,x,y);

			removeGrabListener(mouseGrabber);
			addMouseMotionListener(this);
			setCursor(Cursor.getPredefinedCursor
				(Cursor.CROSSHAIR_CURSOR));
			return;
		}
		if ((actionMode == DRAWING) && (selectedFigure != null)
			&& (selectedFigure instanceof KCurve)) {
			KCurve curve = (KCurve)selectedFigure;

			removeGrabListener(mouseGrabber);
			addMouseMotionListener(this);
			setCursor(Cursor.getPredefinedCursor
				(Cursor.CROSSHAIR_CURSOR));
			return;
		}
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			actionMode = NOTHING;
			return;
		}
		selectedFigure = find(x,y);
		if (selectedFigure != null)
		{
			if (selectedFigure instanceof KEraser) {
				actionMode = NOTHING;
				selectedFigure = null;
				return;
			}

			actionMode = MOVING;
			currentX = x;
			currentY = y;
			if (selectedFigure.getDotFlag() == true &&
				selector.contains(selectedFigure) == true) {
				selector.removeFrom(figures);
			} else {
				figures.remove(selectedFigure);
			}
			repaint();
			return;
		}
		if (whatToDraw == ID_MOUSE) {
			actionMode = SELECTING;
			selector.emptyBag(getGraphics());
			selector.setXY1(x,y);
			return;
		}
		if (whatToDraw == ID_KPOINT) {
			selectedFigure = new KPoint(currentColor,x,y);
			selectedFigure.setPopup(pointPopup);
		} else if (whatToDraw == ID_KBOX) {
			selectedFigure = new KBox(currentColor,x,y);
			selectedFigure.setPopup(boxPopup);
		} else if (whatToDraw == ID_KLINE) {
			selectedFigure = new KLine(currentColor,x,y);
			selectedFigure.setPopup(linePopup);
		} else if (whatToDraw == ID_KLINES) {
			selectedFigure = new KLines(currentColor,x,y);
			selectedFigure.setPopup(linesPopup);
		} else if (whatToDraw == ID_KCURVE) {
			selectedFigure = new KCurve(currentColor,x,y);
			selectedFigure.setPopup(curvePopup);
		} else if (whatToDraw == ID_KARROW)	{
			selectedFigure = new KArrow(currentColor,x,y);
			selectedFigure.setPopup(arrowPopup);
		} else if (whatToDraw == ID_KCIRCLE) {
			selectedFigure = new KCircle(currentColor,x,y);
			selectedFigure.setPopup(circlePopup);
		} else if (whatToDraw == ID_KTRIANGLE) {
			selectedFigure = new KTriangle(currentColor,x,y);
			selectedFigure.setPopup(trianglePopup);
		} else if (whatToDraw == ID_KDIAMOND) {
			selectedFigure = new KDiamond(currentColor,x,y);
			selectedFigure.setPopup(diamondPopup);
		} else if (whatToDraw == ID_KSTAR) {
			selectedFigure = new KStar(currentColor,x,y);
			selectedFigure.setPopup(starPopup);
		} else if (whatToDraw == ID_KSCRIBBLE) {
			selectedFigure = new KScribble(currentColor,x,y);
			selectedFigure.setPopup(scribblePopup);
			selectedFigure.setThickness(4);
		} else if (whatToDraw == ID_KTEXT) {
			selectedFigure = null;
			actionMode = NOTHING;
			textEditor.start(x,y);
			setModified();
			return;
		} else if (whatToDraw == ID_KIMAGE)	{
			Image image = getImageFromImageChooser();
			if(image == null){
				selectedFigure = null;
				actionMode = NOTHING;
				return;
			}
			selectedFigure = new KImage((BufferedImage)image, x, y);
			selectedFigure.setPopup(imagePopup);
			selectedFigure.makeRegion();
			addFigure(selectedFigure);
			selectedFigure = null;
			actionMode = NOTHING;
			setWhatToDraw(ID_MOUSE);
			return;
		} else if (whatToDraw == ID_KERASER) {
			selectedFigure = new KEraser(x,y);
			selectedFigure.setPopup(eraserPopup);
			((KEraser)selectedFigure).drawEraserRect(getGraphics());
		}

		actionMode = DRAWING;
	}

	public void changeFontForText() {
		dlg.setVisible(true);
		Font font = dlg.getFont();
		if (font == null) return;
		currentFont = font;
		if (selectedFigure == null) return;
		if (!(selectedFigure instanceof KText)) return;
		KText text = (KText)selectedFigure;
		text.changeFont(getGraphics(), font);
		text.makeRegion();
		repaint();
		setModified();
	}

	private Image getImageFromImageChooser(){
		Image newImage = null;
		int returnVal = imageChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = imageChooser.getSelectedFile();
            try {
                newImage = ImageIO.read(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        return newImage;	
	}
    public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (e.isPopupTrigger())
		{
			if ((selectedFigure != null && 
					selectedFigure instanceof KLines) || 
				(selectedFigure != null && 
					selectedFigure instanceof KCurve)
				)
			{
				stopLinesDrawing();
				cancelCurveDrawing();
			}

			popupX = x;
			popupY = y;

			selectedFigure = find(x,y);
			if (selectedFigure == null)
			{
				mainPopup.popup(this,x,y);
			} else if (selectedFigure.getDotFlag() == false) {
				selectedFigure.popup(this,x,y);
			} else if (selector.contains(selectedFigure)) {
				groupPopup.popup(this,x,y);
			}
			return;
		}

		if (actionMode == SELECTING)
		{
			Graphics g = getGraphics();
			g.setColor(getBackground());
			selector.draw(g);
			actionMode = NOTHING;
			repaint();
			return;
		}

		if (selectedFigure == null) return;
		
		Graphics g = getGraphics();
		if (actionMode == DRAWING) {
			selectedFigure.setXY2(x,y);

			if (selectedFigure instanceof KScribble) {
				((KScribble)selectedFigure).constructPointArray();
			} else if (selectedFigure instanceof KEraser) {
				((KEraser)selectedFigure).constructPointArray();
			} else if (selectedFigure instanceof KLines) {
				KLines lines = (KLines)selectedFigure;

				if (lines.doYouWantToStop()) 
				{
					stopLinesDrawing();
					return;
				}

				if (lines.isResizing()) {
					lines.draw(getGraphics());
					addFigure(selectedFigure);
					selectedFigure = null;
					actionMode = NOTHING;
					return;
				}

				Point panelPosition = getLocationOnScreen();
				removeMouseMotionListener(this);
				mouseGrabber = new MouseGrabber(panelPosition.x + x,
												panelPosition.y + y);
				addMouseMotionListener(mouseGrabber);

				setCursor(Cursor.getPredefinedCursor
								(Cursor.HAND_CURSOR));

				lines.resetLineSegment(x,y);
				return;
			} else if (selectedFigure instanceof KCurve) {
				KCurve curve = (KCurve)selectedFigure;

				curve.plusClickCount();
				if (curve.doYouWantToStop()) 
				{
					stopCurveDrawing();
					return;
				}

				if (curve.isResizing()) {
					curve.draw(getGraphics());
					addFigure(selectedFigure);
					selectedFigure = null;
					actionMode = NOTHING;
					return;
				}

				Point panelPosition = getLocationOnScreen();
				removeMouseMotionListener(this);
				mouseGrabber = new MouseGrabber(panelPosition.x + x,
												panelPosition.y + y);
				addMouseMotionListener(mouseGrabber);

				setCursor(Cursor.getPredefinedCursor
								(Cursor.HAND_CURSOR));
				return;
			}
		} 
		// MOVING
		if (selectedFigure.getDotFlag() == true &&
				selector.contains(selectedFigure) == true) {
			selector.drawAndAddFiguresInBag(g);
			selectedFigure = null;
			actionMode = NOTHING;
		} else {
			selectedFigure.draw(g);
			addFigure(selectedFigure);
			selectedFigure = null;
			actionMode = NOTHING;
		}
	}
    public void mouseEntered(MouseEvent e) {
	}
    public void mouseExited(MouseEvent e) {
	}
    public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		mainFrame.writePosition("[" + x + "," + y + "]");

		Graphics g = getGraphics();

		if (actionMode == SELECTING)
		{
			g.setColor(Color.lightGray);
			g.setXORMode(getBackground());
			Graphics dotG = getGraphics();
			selector.drawing(g,x,y,dotG);
			return;
		}
		if (selectedFigure == null) return;

		if (actionMode == DRAWING && selectedFigure instanceof KScribble) {
			// do not rubberbanding
			selectedFigure.drawing(g,x,y);
			return;
		}
		if (actionMode == DRAWING && selectedFigure instanceof KEraser) {
			// do not rubberbanding
			selectedFigure.drawing(g,x,y);
			return;
		}

		g.setXORMode(getBackground());
		if (actionMode == DRAWING)
		{
			selectedFigure.drawing(g,x,y);
		} else if (actionMode == MOVING)
		{
			if (selectedFigure.getDotFlag() == true &&
				selector.contains(selectedFigure) == true) {
				selector.moveFiguresInBag(g,x-currentX,y-currentY);
			} else {
				selectedFigure.move(g,x-currentX,y-currentY);
			}
			currentX = x;
			currentY = y;
		}
	}
	private KFigure find(int x, int y) {
		for(int i = 0; i < figures.size(); i++) {
			KFigure pFigure = figures.get(i);
			if (pFigure.contains(x,y))
			{
				return pFigure;
			}
		}
		return null;
	}
    public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		selectedFigure = find(x,y);
		if (selectedFigure != null)
		{
			setCursor(Cursor.getPredefinedCursor
				(Cursor.CROSSHAIR_CURSOR));
		} else {
			setCursor(Cursor.getDefaultCursor());
		}

		mainFrame.writePosition("[" + x + "," + y + "]");
	}
	public void removeFromFigures(KFigure ptr) {
		selectedFigure = null;
		figures.remove(ptr);
		repaint();
		setModified();
	}
	public void removeFromFigures(int index) {
		if (index < 0) return;
		selectedFigure = null;
		figures.remove(index);
		repaint();
		setModified();
	}
	public void addFigure(KFigure newFigure) {
		if (newFigure.isObsolete()) return; // if figure dimension is zero
		newFigure.makeRegion();
		figures.add(newFigure);
		repaint();
		setModified();
	}
	public void fillFigure() {
		if (selectedFigure == null) return;
		selectedFigure.setFill();
		repaint();
		setModified();
	}
	public void copyFigure() {
		if (selectedFigure == null && selector.isEmpty() == false) {
			selector.copyFiguresInBag();
		}
		if (selectedFigure == null) return;
		if (selectedFigure.getDotFlag() == true &&
			selector.contains(selectedFigure) == true) {
			selector.copyFiguresInBag();
		} else {
			KFigure newFigure = selectedFigure.copyAndMove();
			addFigure(newFigure);
			selectedFigure = newFigure;
		}
	}
	public void deleteFigure() {
		if (selectedFigure == null && selector.isEmpty() == false) {
			selector.removeFiguresInBag();
			repaint();
			setModified();
		}
		if (selectedFigure == null) return;
		if (selectedFigure.getDotFlag() == true &&
			selector.contains(selectedFigure) == true) {
			selector.removeFiguresInBag();
		} else {
			figures.remove(selectedFigure);
		}

		selectedFigure = null;
		repaint();
		setModified();
	}
	public void resizeFigure() {
		if (selectedFigure == null) return;

		actionMode = RESIZING;
		figures.remove(selectedFigure);
		Point figurePosition 
				= selectedFigure.getStartPositionToResize(popupX,popupY);
		Point panelPosition = getLocationOnScreen();

		Thread thread = new Thread() {
			// 
			public void run() {
				selectedFigure.draw(getGraphics());
			}
		};
		thread.start();

		removeMouseMotionListener(this);
		mouseGrabber = new MouseGrabber(panelPosition.x+figurePosition.x,
										panelPosition.y+figurePosition.y);
		addMouseMotionListener(mouseGrabber);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	public void setColorForSeletedFigure(Color color) {
		if (selectedFigure == null) return;
		selectedFigure.setColor(color);
		repaint();
		setModified();
	}
	public void setThicknessForSeletedFigure(int thickness) {
		if (selectedFigure == null) return;
		selectedFigure.setThickness(thickness);
		repaint();
		setModified();
	}
	public void showColorChooser() {
		Color color = JColorChooser.showDialog(null,
									"Color Chooser",Color.black);
		setColorForSeletedFigure(color);
	}
	public void boxToRoundBox() {
		if (selectedFigure == null) return;
		if (!(selectedFigure instanceof KBox)) return;
		KBox box = (KBox)selectedFigure;
		box.toRoundBox();
		repaint();
		setModified();
	}
	public void setAngleForTriangle(double angle) {
		if (selectedFigure == null) return;
		if (!(selectedFigure instanceof KTriangle)) return;
		KTriangle triangle = (KTriangle)selectedFigure;
		triangle.setCornerAngle(angle);
		repaint();
		setModified();
	}
	public void setLineArrow() {
		if (selectedFigure == null) return;
		if (selectedFigure instanceof KLine) {
			KLine line = (KLine)selectedFigure;
			line.setLineArrow(popupX,popupY);
		} else if (selectedFigure instanceof KLines) {
			KLines lines = (KLines)selectedFigure;
			lines.setLineArrow(popupX,popupY);
		} else if (selectedFigure instanceof KCurve) {
			KCurve curve = (KCurve)selectedFigure;
			curve.setLineArrow(popupX,popupY);
		}
		repaint();
		setModified();
	}
	public void changeLinesDoor() {
		if (selectedFigure == null) return;
		if (!(selectedFigure instanceof KLines)) return;
		KLines lines = (KLines)selectedFigure;
		lines.changeDoor();
		repaint();
		setModified();
	}
	public void changeShape() {
		if (selectedFigure == null) return;
		if (!(selectedFigure instanceof KStar)) return;
		KStar star = (KStar)selectedFigure;
		star.changeShape();
		repaint();
		setModified();
	}
	public void editText() {
		if (selectedFigure == null) return;
		if (!(selectedFigure instanceof KText)) return;
		KText text = (KText)selectedFigure;
		textEditor.start(text);
		figures.remove(selectedFigure);
		selectedFigure = null;
	}
}
