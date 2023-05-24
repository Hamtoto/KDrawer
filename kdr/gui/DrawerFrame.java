package kdr.gui;

import kdr.gui.dlg.*;
import kdr.net.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.print.*;

public class DrawerFrame extends JFrame
{
	static class ZoomBox extends JComboBox implements ActionListener
	{
		DrawerView canvas;
		static String[] size = { "100", "80", "50" };
		ZoomBox(DrawerView canvas) {
			super(size);
			this.canvas = canvas;
			setMaximumSize(new Dimension(1500,200));
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			JComboBox box = (JComboBox)e.getSource();
			String ratio = (String)box.getSelectedItem();
			canvas.zoom(Integer.parseInt(ratio));
		}
	}
	static class PrintableView implements Printable
	{
		DrawerView canvas;
		String fileName;
		PrintableView(DrawerView canvas,String fileName) {
			this.canvas = canvas;
			this.fileName = fileName;
		}
		public int print(Graphics g, PageFormat format, int pagenum) {
			if (pagenum > 0) return Printable.NO_SUCH_PAGE;

			Graphics2D g2 = (Graphics2D)g;
			double pageX = format.getImageableX()+1;
			double pageY = format.getImageableY()+1;
			g2.translate(pageX,pageY);
			
			int pageWidth = (int)format.getImageableWidth()-2;
			int pageHeight = (int)format.getImageableHeight()-2;

			g2.drawRect(-1,-1,pageWidth+2,pageHeight+2);

			g2.setClip(0,0,pageWidth,pageHeight);
			g2.scale(0.5,0.5);

			canvas.paint(g);

			g2.scale(2.0,2.0);
			g2.drawString(fileName,0,pageHeight);
			return Printable.PAGE_EXISTS;
		}
	}
	DrawerView canvas;
	JToolBar selectToolBar;
	JToolBar colorToolBar;
	JToolBar networkToolBar;
	JCheckBox realtimeButton; 
	JButton sendMeButton;
	JButton sendButton;
	StatusBar statusBar;
	FigureDialog dialog = null;
	TableDialog tableDialog = null;
	FontDialog textDialog = null;
	TreeDialog treeDialog = null;
	KTalkDialog talkDialog = null;

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
		chooser.setFileFilter(new FileNameExtensionFilter("KDrawer file","jdr"));
		int value = chooser.showOpenDialog(null);
		if (value != JFileChooser.APPROVE_OPTION) return;
		fileName = chooser.getSelectedFile().getPath();
		canvas.doOpen(fileName);
		setTitle("KDrawer - ["+fileName + "]");
	}
	public void doSaveAs() {
		JFileChooser chooser = 
			new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("KDrawer file","jdr"));
		int value = chooser.showSaveDialog(null);
		if (value != JFileChooser.APPROVE_OPTION) return;
		fileName = chooser.getSelectedFile().getPath();
		if (fileName.endsWith(".jdr") == false)
		{
			fileName = fileName + ".jdr";
		}
		setTitle("KDrawer - ["+fileName + "]");
		canvas.doSave(fileName);
	}
	public void doPrint() {
		PrinterJob job = PrinterJob.getPrinterJob();

		PageFormat page = job.defaultPage();
		page.setOrientation(PageFormat.LANDSCAPE);

		Printable printable = new PrintableView(canvas,fileName);
		job.setPrintable(printable,page);

		if (job.printDialog())
		{
			try
			{
				job.print();
			}
			catch (PrinterException ex)
			{
				JOptionPane.showMessageDialog(this,ex.toString(),
					"PrinterException",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	DrawerFrame() {
		setTitle("KDrawer - [noname.jdr]");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;
		setSize(screenWidth*2/3, screenHeight*2/3);
		setLocation(screenWidth/6, screenHeight/6);
		Image img = tk.getImage("SulIcon.png");
		setIconImage(img);
		
		Container container = this.getContentPane();
		statusBar = new StatusBar();
		container.add(statusBar,"South");
		canvas = new DrawerView(this);
		JScrollPane sp = new JScrollPane(canvas); 
		container.add(sp,"Center");

		talkDialog = new KTalkDialog(this);

		sp.registerKeyboardAction(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					JScrollBar scrollBar = sp.getVerticalScrollBar();
					scrollBar.setValue(scrollBar.getValue() 
						+ scrollBar.getBlockIncrement());
				}
			}
			,KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN,0)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					JScrollBar scrollBar = sp.getVerticalScrollBar();
					scrollBar.setValue(scrollBar.getValue() 
						- scrollBar.getBlockIncrement());
				}
			}
			,KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP,0)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction((evt) -> {
					JScrollBar scrollBar = sp.getHorizontalScrollBar();
					scrollBar.setValue(scrollBar.getValue() 
						+ scrollBar.getBlockIncrement());
				}
			,KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN,InputEvent.CTRL_MASK)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction((evt) -> {
					JScrollBar scrollBar = sp.getHorizontalScrollBar();
					scrollBar.setValue(scrollBar.getValue() 
						- scrollBar.getBlockIncrement());
				}
			,KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP,InputEvent.CTRL_MASK)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.increaseHeight()
			,KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN,InputEvent.ALT_MASK)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		sp.registerKeyboardAction((evt) -> canvas.increaseWidth()
			,KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP,InputEvent.ALT_MASK)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.stopLinesDrawing()
			,KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.deleteFigure()
			,KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.copyFigure()
			,KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		sp.registerKeyboardAction((evt) -> canvas.undo()
			,KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK)
			,JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		selectToolBar = new JToolBar();
		selectToolBar.add(new MouseAction(("Select (S)"),
						new Icon()
						{
							public int getIconHeight() { return FigureIcon.HEIGHT;}
							public int getIconWidth() { return FigureIcon.WIDTH; }
							public void paintIcon(Component c,Graphics g,int x,int y) {
								g.setColor(Color.black);
								int xpoints[] = new int[7];
								int ypoints[] = new int[7];

								xpoints[0] = x+4;		ypoints[0] = y+2;
								xpoints[1] = x+4;		ypoints[1] = y+FigureIcon.HEIGHT-2;
								xpoints[2] = x+7;		ypoints[2] = y+FigureIcon.HEIGHT-5;
								xpoints[3] = x+9;		ypoints[3] = y+FigureIcon.HEIGHT-1;
								xpoints[4] = x+11;		ypoints[4] = y+FigureIcon.HEIGHT-2;
								xpoints[5] = x+10;		ypoints[5] = y+FigureIcon.HEIGHT-5;
								xpoints[6] = x+13;		ypoints[6] = y+FigureIcon.HEIGHT-6;

								g.drawPolygon(xpoints, ypoints, 7);
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
		selectToolBar.add(canvas.getDiamondAction());
		selectToolBar.add(canvas.getStarAction());
		selectToolBar.add(canvas.getScribbleAction());
		selectToolBar.add(canvas.getTextAction());
		selectToolBar.add(canvas.getImageAction());
		selectToolBar.add(canvas.getEraserAction());
		selectToolBar.add(new ZoomBox(canvas));

		colorToolBar = new JToolBar();
		colorToolBar.add(new ColorAction("Black",Color.black,canvas));
		colorToolBar.add(new ColorAction("Red",Color.red,canvas));
		colorToolBar.add(new ColorAction("Green",Color.green,canvas));
		colorToolBar.add(new ColorAction("Blue",Color.blue,canvas));
		colorToolBar.add(new ColorAction("Color",Color.yellow,canvas));
		colorToolBar.add(javax.swing.Box.createGlue());

		Box toolBarPanel = Box.createHorizontalBox();
		networkToolBar = new JToolBar();
		networkToolBar.add(realtimeButton = 
							new JCheckBox("Real Time"));
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
		toolBarPanel.add(networkToolBar);

		container.add(toolBarPanel,BorderLayout.NORTH);

		addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent e) {
						Dimension sz = canvas.getSize();
						String s = "" + sz.width + " X " + sz.height + " px";
						statusBar.writeSize(s);
					}
				});

		JMenuBar menus = new JMenuBar();
		setJMenuBar(menus);

		JMenu fileMenu = new JMenu("File");
		menus.add(fileMenu);
		fileMenu.setMnemonic('F');

		JMenuItem newFile = new JMenuItem("New File");
		fileMenu.add(newFile);
		newFile.setMnemonic('N');
		newFile.setBackground(Color.white);
		newFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("newFile.png")));
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		newFile.addActionListener((e) -> canvas.doFileNew());

		JMenuItem openFile = new JMenuItem("Open File");
		fileMenu.add(openFile);
		openFile.setMnemonic('O');
		openFile.setBackground(Color.white);
		openFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("openFile.png")));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		openFile.addActionListener((e) -> doOpen());

		JMenuItem saveFile = new JMenuItem("Save");
		fileMenu.add(saveFile);
		saveFile.setMnemonic('S');
		saveFile.setBackground(Color.white);
		saveFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("saveFile.png")));
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));	
		saveFile.addActionListener((e) -> canvas.doSave(fileName));

		JMenuItem saveAsFile = new JMenuItem("Save As");
		fileMenu.add(saveAsFile);
		saveAsFile.setMnemonic('A');
		saveAsFile.setBackground(Color.white);
		saveAsFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("saveAsFile.png")));
		saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		saveAsFile.addActionListener((e) -> doSaveAs());

		fileMenu.addSeparator();

		JMenuItem printFile = new JMenuItem("Print");
		fileMenu.add(printFile);
		printFile.setMnemonic('P');
		printFile.setBackground(Color.white);
		printFile.setIcon(new ImageIcon(DrawerFrame.class.getResource("printFile.png")));
		printFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		printFile.addActionListener((e) -> doPrint());

		fileMenu.addSeparator();
	
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.setMnemonic('X');
		exit.setBackground(Color.white);
		exit.setIcon(new ImageIcon(DrawerFrame.class.getResource("exit.png")));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JMenu figureMenu = new JMenu("Figure (F)");
		figureMenu.setMnemonic('i');
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

		JMenu toolMenu = new JMenu("Tools (T)");
		toolMenu.setMnemonic('T');
		menus.add(toolMenu);

		JMenuItem talkItem = new JMenuItem("Talk (K)");
		talkItem.setMnemonic('K');
		toolMenu.add(talkItem);
		talkItem.addActionListener((e) -> {
					talkDialog.setVisible(true);
				});

		JMenuItem modalTool = new JMenuItem("Figure Dialog (D)");
		modalTool.setMnemonic('D');
		toolMenu.add(modalTool);
		modalTool.addActionListener((e) -> {
					if (dialog == null)
					{
						dialog = 
							new FigureDialog("Figure Dialog",canvas);
						dialog.setModal(true);
					}
					dialog.setVisible(true);
				});

		JMenuItem tableTool = new JMenuItem("Figure Table (B)");
		tableTool.setMnemonic('B');
		toolMenu.add(tableTool);
		tableTool.addActionListener((e) -> {
					if (tableDialog == null)
					{
						tableDialog = 
							new TableDialog("Table Dialog",canvas);
						tableDialog.setModal(true);
					}
					tableDialog.setVisible(true);
				});

		JMenuItem treeTool = new JMenuItem("Figure Tree (R)");
		treeTool.setMnemonic('R');
		toolMenu.add(treeTool);
		treeTool.addActionListener((e) -> {
					if (treeDialog == null)
					{
						treeDialog = 
							new TreeDialog("Tree Dialog",canvas);
						treeDialog.setModal(true);
					}
					treeDialog.setVisible(true);
				});

		JMenu zoomMenu = new JMenu("Zoom");
		zoomMenu.setMnemonic('Z');
		toolMenu.add(zoomMenu);

		JMenuItem zoom100 = new JMenuItem("100%");
		zoom100.setMnemonic('1');
		zoomMenu.add(zoom100);
		zoom100.addActionListener((e) -> canvas.zoom(100));

		JMenuItem zoom80 = new JMenuItem("80%");
		zoom80.setMnemonic('8');
		zoomMenu.add(zoom80);
		zoom80.addActionListener((e) -> canvas.zoom(80));

		JMenuItem zoom50 = new JMenuItem("50%");
		zoom50.setMnemonic('5');
		zoomMenu.add(zoom50);
		zoom50.addActionListener((e) -> canvas.zoom(50));

		JMenu helpMenu = new JMenu("Help (H)");
		helpMenu.setMnemonic('H');
		menus.add(helpMenu);

		JMenuItem infoHelp = new JMenuItem("Drawer information (I)");
		infoHelp.setMnemonic('I');
		helpMenu.add(infoHelp);
		infoHelp.addActionListener((e) ->
					{ JOptionPane.showMessageDialog(null, "SuldenLion & Hamtoto's Drawer"); } 
					);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

