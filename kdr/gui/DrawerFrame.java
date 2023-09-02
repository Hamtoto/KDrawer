package kdr.gui;

import kdr.gui.dlg.*;
import kdr.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.*;
import java.awt.print.*;
import java.util.LinkedList;

public class DrawerFrame extends JFrame {

	public static final int FONT_NAME = 1;
	public static final int FONT_STYLE = 2;
	public static final int FONT_SIZE = 3;


	//Load and filter font list
	public static String[] getFontList() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		LinkedList<String> fontLinkedList = new LinkedList<>();

        for (Font font : fonts) {
            if (!font.getFontName().contains("Bold")
                    && !font.getFontName().contains("bold")
                    && !font.getFontName().contains("Italic")
                    && !font.getFontName().contains("italic")
                    && !font.getFontName().contains("Dialog")) {
                fontLinkedList.add(font.getFontName());
            }
        }
		return fontLinkedList.toArray(new String[0]);
	}

	public static  String[] getFontSize(){
		String[] fontSizeArray = new String[29];
		for (int i = 6, j = 0; i <= 62; i += 2, j++){
			fontSizeArray[j]=String.valueOf(i);
		}
		return fontSizeArray;
	}

	static class FontComboBox extends JComboBox implements ActionListener {
		DrawerView canvas;
		static String[] fontList = getFontList();
		static String[] style = {"Regular", "Bold", "Italic", "Bold Italic"};
		static String[] fontSize = getFontSize();

		FontComboBox(DrawerView canvas, int n) {
			super(getComboBoxItems(n));
			this.canvas = canvas;
			setName(Integer.toString(n));
			addActionListener(this);
		}

		private static Object[] getComboBoxItems(int n) {
			if (n == FONT_NAME) {
				return fontList;
			} else if (n == FONT_STYLE) {
				return style;
			} else {
				return fontSize;
			}
		}

		public void actionPerformed(ActionEvent e) {
			JComboBox box = (JComboBox) e.getSource();
			int comboBoxNumber = Integer.parseInt(box.getName());
			Font currentFont = canvas.getCurrentFont();
			Font newfont = null;

			if (comboBoxNumber == FONT_NAME) {
				newfont = new Font((String) box.getSelectedItem(), currentFont.getStyle(), currentFont.getSize());
			} else if (comboBoxNumber == FONT_STYLE) {
				newfont = new Font(currentFont.getFontName(), box.getSelectedIndex(), currentFont.getSize());
			} else if (comboBoxNumber == FONT_SIZE) {
				int newSize = Integer.parseInt((String) box.getSelectedItem());
				newfont = new Font(currentFont.getFontName(), currentFont.getStyle(), newSize);
			}
			canvas.changeFontToolBar(newfont);
		}
	}

	static class ZoomBox extends JComboBox implements ActionListener {
		DrawerView canvas;
		static String[] size = {"100", "90", "80", "70", "60", "50", "40", "30", "20", "10"};

		ZoomBox(DrawerView canvas) {
			super(size);
			this.canvas = canvas;
			setMaximumSize(new Dimension(1500, 200));
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			JComboBox box = (JComboBox) e.getSource();
			String ratio = (String) box.getSelectedItem();
			canvas.zoom(Integer.parseInt(ratio));
		}
	}

	static class PrintableView implements Printable {
		DrawerView canvas;
		String fileName;

		PrintableView(DrawerView canvas, String fileName) {
			this.canvas = canvas;
			this.fileName = fileName;
		}

		public int print(Graphics g, PageFormat format, int pagenum) {
			if (pagenum > 0) return Printable.NO_SUCH_PAGE;

			Graphics2D g2 = (Graphics2D) g;
			double pageX = format.getImageableX() + 1;
			double pageY = format.getImageableY() + 1;
			g2.translate(pageX, pageY);

			int pageWidth = (int) format.getImageableWidth() - 2;
			int pageHeight = (int) format.getImageableHeight() - 2;

			g2.drawRect(-1, -1, pageWidth + 2, pageHeight + 2);

			g2.setClip(0, 0, pageWidth, pageHeight);
			g2.scale(0.5, 0.5);

			canvas.paint(g);

			g2.scale(2.0, 2.0);
			g2.drawString(fileName, 0, pageHeight);
			return Printable.PAGE_EXISTS;
		}
	}

	DrawerView canvas;
	JToolBar selectToolBar;
	JToolBar colorToolBar;
	JToolBar fontToolBar;
	JToolBar networkToolBar;
	JCheckBox realtimeButton;
	JButton sendMeButton;
	JButton sendButton;
	StatusBar statusBar;
	FigureDialog dialog = null;
	TableDialog tableDialog = null;
	TreeDialog treeDialog = null;
	KTalkDialog talkDialog = null;
	InfoDialog infoDialog = null;

	String fileName = "noname.jdr";

	KTalkDialog getTalkDialog() {
		return talkDialog;
	}

	public void communicationEstablished() {
		talkDialog.setVisible(true);
		showNetworkToolBar(true);
		setRTButton(false);
		canvas.setRealTimeExchangeFlag(false);
		enableSendMeButton(true);
		enableSendButton(false);
	}

	public void communicationClosed() {
		talkDialog.setVisible(false);
		showNetworkToolBar(false);
		setRTButton(false);
		canvas.setRealTimeExchangeFlag(false);
		enableSendMeButton(false);
		enableSendButton(false);
	}

	public void showNetworkToolBar(boolean flag) {
		networkToolBar.setVisible(flag);
	}

	public void setRTButton(boolean flag) {
		realtimeButton.setSelected(flag);
	}

	public void enableSendMeButton(boolean flag) {
		sendMeButton.setEnabled(flag);
	}

	public void enableSendButton(boolean flag) {
		sendButton.setEnabled(flag);
	}

	public void changeSendMeButtonName(String name) {
		sendMeButton.setLabel(name);
	}

	public DrawerView getCanvas() {
		return canvas;
	}

	public void writePosition(String s) {
		statusBar.writePosition(s);
	}

	public void writeFigureType(String s) {
		statusBar.writeFigureType(s);
	}

	public void doOpen() {
		JFileChooser chooser =
				new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("KDrawer file", "jdr"));
		int value = chooser.showOpenDialog(null);
		if (value != JFileChooser.APPROVE_OPTION) return;
		fileName = chooser.getSelectedFile().getPath();
		canvas.doOpen(fileName);
		setTitle("KDrawer - [" + fileName + "]");
	}

	public void doSaveAs() {
		JFileChooser chooser =
				new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("KDrawer file", "jdr"));
		int value = chooser.showSaveDialog(null);
		if (value != JFileChooser.APPROVE_OPTION) return;
		fileName = chooser.getSelectedFile().getPath();
		if (!fileName.endsWith(".jdr")) {
			fileName = fileName + ".jdr";
		}
		setTitle("KDrawer - [" + fileName + "]");
		canvas.doSave(fileName);
	}

	public void doPrint() {
		PrinterJob job = PrinterJob.getPrinterJob();

		PageFormat page = job.defaultPage();
		page.setOrientation(PageFormat.LANDSCAPE);

		Printable printable = new PrintableView(canvas, fileName);
		job.setPrintable(printable, page);

		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException ex) {
				JOptionPane.showMessageDialog(this, ex.toString(),
						"PrinterException", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	DrawerFrame() {
		setTitle("KDrawer - [noname.jdr]");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;
		setSize(screenWidth * 2 / 3, screenHeight * 2 / 3);
		setLocation(screenWidth / 6, screenHeight / 6);
		Image img = tk.getImage("logo.png");
		setIconImage(img);

		Container container = this.getContentPane();
		statusBar = new StatusBar();
		container.add(statusBar, "South");
		canvas = new DrawerView(this);
		JScrollPane sp = new JScrollPane(canvas);
		container.add(sp, "Center");

		talkDialog = new KTalkDialog(this);

		sp.registerKeyboardAction(new ActionListener() {
									  public void actionPerformed(ActionEvent evt) {
										  JScrollBar scrollBar = sp.getVerticalScrollBar();
										  scrollBar.setValue(scrollBar.getValue()
												  + scrollBar.getBlockIncrement());
									  }
								  }
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction(new ActionListener() {
									  public void actionPerformed(ActionEvent evt) {
										  JScrollBar scrollBar = sp.getVerticalScrollBar();
										  scrollBar.setValue(scrollBar.getValue()
												  - scrollBar.getBlockIncrement());
									  }
								  }
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction((evt) -> {
					JScrollBar scrollBar = sp.getHorizontalScrollBar();
					scrollBar.setValue(scrollBar.getValue()
							+ scrollBar.getBlockIncrement());
				}
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, InputEvent.CTRL_MASK)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction((evt) -> {
					JScrollBar scrollBar = sp.getHorizontalScrollBar();
					scrollBar.setValue(scrollBar.getValue()
							- scrollBar.getBlockIncrement());
				}
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, InputEvent.CTRL_MASK)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.increaseHeight()
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, InputEvent.ALT_MASK)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction((evt) -> canvas.increaseWidth()
				, KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, InputEvent.ALT_MASK)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.stopLinesDrawing()
				, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.deleteFigure()
				, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.copyFigure()
				, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.undo()
				, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK)
				, JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		selectToolBar = new JToolBar();
		selectToolBar.add(new MouseAction(("Select (S)"),
				new Icon() {
					public int getIconHeight() {return FigureIcon.HEIGHT;}

					public int getIconWidth() {return FigureIcon.WIDTH;}

					public void paintIcon(Component c, Graphics g, int x, int y) {
						g.setColor(Color.black);
						int[] x_points = new int[7];
						int[] y_points = new int[7];

						x_points[0] = x+4;		y_points[0] = y+2;
						x_points[1] = x+4;		y_points[1] = y+FigureIcon.HEIGHT-2;
						x_points[2] = x+7;		y_points[2] = y+FigureIcon.HEIGHT-5;
						x_points[3] = x+9;		y_points[3] = y+FigureIcon.HEIGHT-1;
						x_points[4] = x+11;		y_points[4] = y+FigureIcon.HEIGHT-2;
						x_points[5] = x+10;		y_points[5] = y+FigureIcon.HEIGHT-5;
						x_points[6] = x+13;		y_points[6] = y+FigureIcon.HEIGHT-6;

						g.drawPolygon(x_points, y_points, 7);
					}
				}
				, canvas));
		selectToolBar.add(canvas.getPointAction());
		selectToolBar.add(canvas.getBoxAction());
		selectToolBar.add(canvas.getLineAction());
		selectToolBar.add(canvas.getLinesAction());
		selectToolBar.add(canvas.getCurveAction());
		selectToolBar.add(canvas.getArrowAction());
		selectToolBar.add(canvas.getCircleAction());
		selectToolBar.add(canvas.getTriangleAction());
		selectToolBar.add(canvas.getRightTriangleAction());
		selectToolBar.add(canvas.getDiamondAction());
		selectToolBar.add(canvas.getStarAction());
		selectToolBar.add(canvas.getScribbleAction());
		selectToolBar.add(canvas.getTextAction());
		selectToolBar.add(canvas.getImageAction());
		selectToolBar.add(canvas.getEraserAction());
		selectToolBar.add(new ZoomBox(canvas));

		colorToolBar = new JToolBar();
		colorToolBar.add(new ColorAction("Black", Color.black, canvas));
		colorToolBar.add(new ColorAction("Red", Color.red, canvas));
		colorToolBar.add(new ColorAction("Green", Color.green, canvas));
		colorToolBar.add(new ColorAction("Blue", Color.blue, canvas));
		colorToolBar.add(new ColorAction("Color", Color.yellow, canvas));
		colorToolBar.add(Box.createGlue());

		//Font toolbar
		fontToolBar = new JToolBar();
		fontToolBar.add(new FontComboBox(canvas, FONT_NAME));
		fontToolBar.add(new FontComboBox(canvas, FONT_STYLE));
		fontToolBar.add(new FontComboBox(canvas, FONT_SIZE));
		fontToolBar.add(Box.createGlue());

		Box toolBarPanel = Box.createHorizontalBox();

		networkToolBar = new JToolBar();
		networkToolBar.add(realtimeButton = new JCheckBox("Real Time"));
		realtimeButton.addActionListener((e) -> {
			if (realtimeButton.isSelected()) {
				talkDialog.sendString("RealTimeBegin");
				enableSendMeButton(false);
				enableSendButton(false);
				canvas.setRealTimeExchangeFlag(true);
			} else {
				talkDialog.sendString("RealTimeEnd");
				enableSendMeButton(true);
				canvas.setRealTimeExchangeFlag(false);
			}
		});
		networkToolBar.add(sendMeButton = new JButton("Send Me"));
		sendMeButton.addActionListener((e) -> {
			if (sendMeButton.getLabel().equals("Send Me")) {
				talkDialog.sendString("SendMe");
				changeSendMeButtonName("Cancel");
			} else { // Cancel
				talkDialog.sendString("Cancel");
				changeSendMeButtonName("Send Me");
			}
		});
		networkToolBar.add(sendButton = new JButton("Send"));
		sendButton.addActionListener((e) -> talkDialog.sendFigures());
		networkToolBar.setVisible(false);

		toolBarPanel.add(selectToolBar);
		toolBarPanel.add(colorToolBar);
		toolBarPanel.add(fontToolBar);
		toolBarPanel.add(networkToolBar);

		container.add(toolBarPanel, BorderLayout.NORTH);

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension sz = canvas.getSize();
				String s = "" + sz.width + " X " + sz.height + " px";
				statusBar.writeSize(s);
			}
		});

		JMenuBar menus = new JMenuBar();
		setJMenuBar(menus);

		JMenu fileMenu = new JMenu(DrawerView.Labels.get("File (F)"));
		menus.add(fileMenu);
		fileMenu.setMnemonic('F');

		JMenuItem newFile
				= new JMenuItem(DrawerView.Labels.get("New File (N)"));
		fileMenu.add(newFile);
		newFile.setMnemonic('N');
		newFile.setBackground(Color.white);
		newFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("./image/newFile.png")));
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		newFile.addActionListener((e) -> canvas.doFileNew());

		JMenuItem openFile
				= new JMenuItem(DrawerView.Labels.get("Open File (O)"));
		fileMenu.add(openFile);
		openFile.setMnemonic('O');
		openFile.setBackground(Color.white);
		openFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("./image/openFile.png")));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		openFile.addActionListener((e) -> doOpen());

		JMenuItem saveFile
				= new JMenuItem(DrawerView.Labels.get("Save (S)"));
		fileMenu.add(saveFile);
		saveFile.setMnemonic('S');
		saveFile.setBackground(Color.white);
		saveFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("./image/saveFile.png")));
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		saveFile.addActionListener((e) -> canvas.doSave(fileName));

		JMenuItem saveAsFile
				= new JMenuItem(DrawerView.Labels.get("Save As (A)"));
		fileMenu.add(saveAsFile);
		saveAsFile.setMnemonic('A');
		saveAsFile.setBackground(Color.white);
		saveAsFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("./image/saveAsFile.png")));
		saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		saveAsFile.addActionListener((e) -> doSaveAs());

		fileMenu.addSeparator();

		JMenuItem printFile
				= new JMenuItem(DrawerView.Labels.get("Print (P)"));
		fileMenu.add(printFile);
		printFile.setMnemonic('P');
		printFile.setBackground(Color.white);
		printFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("./image/printFile.png")));
		printFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		printFile.addActionListener((e) -> doPrint());

		fileMenu.addSeparator();

		JMenuItem exit
				= new JMenuItem(DrawerView.Labels.get("Exit (X)"));
		fileMenu.add(exit);
		exit.setMnemonic('X');
		exit.setBackground(Color.white);
		exit.setIcon(new ImageIcon(DrawerFrame.class.getResource("./image/exit.png")));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		exit.addActionListener(e -> setVisible(false));

		JMenu figureMenu = new JMenu(DrawerView.Labels.get("Figure (G)"));
		figureMenu.setMnemonic('G');
		menus.add(figureMenu);

		JMenuItem figurePoint = new JMenuItem(canvas.getPointAction());
		figureMenu.add(figurePoint);

		JMenuItem figureBox = new JMenuItem(canvas.getBoxAction());
		figureMenu.add(figureBox);

		JMenuItem figureLine = new JMenuItem(canvas.getLineAction());
		figureMenu.add(figureLine);

		JMenuItem figureLines = new JMenuItem(canvas.getLinesAction());
		figureMenu.add(figureLines);

		JMenuItem figureCurve = new JMenuItem(canvas.getCurveAction());
		figureMenu.add(figureCurve);

		JMenuItem figureArrow = new JMenuItem(canvas.getArrowAction());
		figureMenu.add(figureArrow);

		JMenuItem figureCircle = new JMenuItem(canvas.getCircleAction());
		figureMenu.add(figureCircle);

		JMenuItem figureTriangle = new JMenuItem(canvas.getTriangleAction());
		figureMenu.add(figureTriangle);

		JMenuItem figureRightTriangle = new JMenuItem(canvas.getRightTriangleAction());
		figureMenu.add(figureRightTriangle);

		JMenuItem figureDiamond = new JMenuItem(canvas.getDiamondAction());
		figureMenu.add(figureDiamond);

		JMenuItem figureStar = new JMenuItem(canvas.getStarAction());
		figureMenu.add(figureStar);

		JMenuItem figureScribble = new JMenuItem(canvas.getScribbleAction());
		figureMenu.add(figureScribble);

		JMenuItem figureText = new JMenuItem(canvas.getTextAction());
		figureMenu.add(figureText);

		JMenuItem JImgMenu = new JMenuItem(canvas.getImageAction());
		figureMenu.add(JImgMenu);

		JMenu toolMenu = new JMenu(DrawerView.Labels.get("Tools (T)"));
		toolMenu.setMnemonic('T');
		menus.add(toolMenu);

		JMenuItem talkItem = new JMenuItem(DrawerView.Labels.get("Talk (K)"));
		talkItem.setMnemonic('K');
		toolMenu.add(talkItem);
		talkItem.addActionListener((e) -> talkDialog.setVisible(true));

		JMenuItem modalTool = new JMenuItem(DrawerView.Labels.get("Figure Dialog (D)"));
		modalTool.setMnemonic('D');
		toolMenu.add(modalTool);
		modalTool.addActionListener((e) -> {
			if (dialog == null) {
				dialog =
						new FigureDialog("Figure Dialog", canvas);
				dialog.setModal(true);
			}
			dialog.setVisible(true);
		});

		JMenuItem tableTool = new JMenuItem(DrawerView.Labels.get("Figure Table (B)"));
		tableTool.setMnemonic('B');
		toolMenu.add(tableTool);
		tableTool.addActionListener((e) -> {
			if (tableDialog == null) {
				tableDialog =
						new TableDialog("Table Dialog", canvas);
				tableDialog.setModal(true);
			}
			tableDialog.setVisible(true);
		});

		JMenuItem treeTool = new JMenuItem(DrawerView.Labels.get("Figure Tree (R)"));
		treeTool.setMnemonic('R');
		toolMenu.add(treeTool);
		treeTool.addActionListener((e) -> {
			if (treeDialog == null) {
				treeDialog =
						new TreeDialog("Tree Dialog", canvas);
				treeDialog.setModal(true);
			}
			treeDialog.setVisible(true);
		});

		JMenu zoomMenu = new JMenu(DrawerView.Labels.get("Zoom"));
		zoomMenu.setMnemonic('Z');
		toolMenu.add(zoomMenu);

		JMenuItem zoom100 = new JMenuItem("100%");
		zoom100.setMnemonic('1');
		zoomMenu.add(zoom100);
		zoom100.addActionListener((e) -> canvas.zoom(100));

		JMenuItem zoom90 = new JMenuItem("90%");
		zoom90.setMnemonic('9');
		zoomMenu.add(zoom90);
		zoom90.addActionListener((e) -> canvas.zoom(90));

		JMenuItem zoom80 = new JMenuItem("80%");
		zoom80.setMnemonic('8');
		zoomMenu.add(zoom80);
		zoom80.addActionListener((e) -> canvas.zoom(80));

		JMenuItem zoom70 = new JMenuItem("70%");
		zoom70.setMnemonic('7');
		zoomMenu.add(zoom70);
		zoom70.addActionListener((e) -> canvas.zoom(70));

		JMenuItem zoom60 = new JMenuItem("60%");
		zoom60.setMnemonic('6');
		zoomMenu.add(zoom60);
		zoom60.addActionListener((e) -> canvas.zoom(60));

		JMenuItem zoom50 = new JMenuItem("50%");
		zoom50.setMnemonic('5');
		zoomMenu.add(zoom50);
		zoom50.addActionListener((e) -> canvas.zoom(50));

		JMenuItem zoom40 = new JMenuItem("40%");
		zoom40.setMnemonic('4');
		zoomMenu.add(zoom40);
		zoom40.addActionListener((e) -> canvas.zoom(40));

		JMenuItem zoom30 = new JMenuItem("30%");
		zoom30.setMnemonic('3');
		zoomMenu.add(zoom30);
		zoom30.addActionListener((e) -> canvas.zoom(30));

		JMenuItem zoom20 = new JMenuItem("20%");
		zoom20.setMnemonic('2');
		zoomMenu.add(zoom20);
		zoom20.addActionListener((e) -> canvas.zoom(20));

		JMenuItem zoom10 = new JMenuItem("10%");
		zoom10.setMnemonic('0');
		zoomMenu.add(zoom10);
		zoom10.addActionListener((e) -> canvas.zoom(10));


		JMenu helpMenu = new JMenu(DrawerView.Labels.get("Help (H)"));
		helpMenu.setMnemonic('H');
		menus.add(helpMenu);

		JMenuItem infoHelp = new JMenuItem(DrawerView.Labels.get("Drawer information (I)"));
		infoHelp.setMnemonic('I');
		helpMenu.add(infoHelp);
		infoHelp.addActionListener((e) ->
				{
					if (infoDialog == null) {
						infoDialog = new InfoDialog("Info",canvas);
						infoDialog.setModal(true);
					}
					infoDialog.setVisible(true);
				}
		);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}