package kdr.gui.dlg;

import kdr.gui.DrawerFrame;
import kdr.gui.DrawerView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontDialog extends JDialog {
	static class DialogPanel extends JPanel
				implements ActionListener, ListSelectionListener
	{
		JDialog dialog;
		DrawerView view;
		JButton okButton, cancelButton;
		private final JLabel sampleLabel = new JLabel("Sample...");
		private JList<String> fontNameList,fontStyleList,fontSizeList;
		public Font font = null;
		String[] fontStyleArray;
		public boolean okFlag = false;

		private JPanel makeFontPanel() {
			JPanel fontPanel = new JPanel(new BorderLayout());
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			//FamilyPanel
			JPanel fontFamilyPanel = new JPanel();
			fontFamilyPanel.setLayout(new BorderLayout());

			JLabel fontLabel = new JLabel("Fonts: ");
			fontFamilyPanel.add(fontLabel, BorderLayout.NORTH);

			String[] fontList = DrawerFrame.getFontList();

			fontNameList = new JList<>(fontList); 
			fontNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			fontNameList.setSelectedIndex(3);
			fontNameList.addListSelectionListener(this);
			JScrollPane fontScrollPane = new JScrollPane(fontNameList);
			fontFamilyPanel.add(fontScrollPane, BorderLayout.CENTER);

			topPanel.add(fontFamilyPanel);

			//StylePanel
			JPanel fontStylePanel = new JPanel();
			fontStylePanel.setLayout(new BorderLayout());

			JLabel styleLabel = new JLabel("Style: ");
			fontStylePanel.add(styleLabel, BorderLayout.NORTH);

			fontStyleArray = new String[]{"Regular", "Bold", "Italic", "Bold Italic"};
			fontStyleList = new JList<>(fontStyleArray);
			fontStyleList.setSelectionMode(0);
			fontStyleList.setSelectedIndex(1);
			fontStyleList.addListSelectionListener(this);
			JScrollPane styleScrollPane = new JScrollPane(fontStyleList);
			fontStylePanel.add(styleScrollPane, BorderLayout.CENTER);

			topPanel.add(fontStylePanel);

			//SizePanel
			JPanel fontSizePanel = new JPanel();
			fontSizePanel.setLayout(new BorderLayout());

			String[] fontSize = DrawerFrame.getFontSize();

			JLabel sizeLabel = new JLabel("Sizes: ");
			fontSizePanel.add(sizeLabel, BorderLayout.NORTH);

			fontSizeList = new JList<>(fontSize);
			fontSizeList.setSelectionMode(0);
			fontSizeList.setSelectedIndex(16);
			fontSizeList.addListSelectionListener(this);
			JScrollPane fontSizeScrollPane = new JScrollPane(fontSizeList);
			fontSizePanel.add(fontSizeScrollPane, BorderLayout.CENTER); 

			topPanel.add(fontSizePanel);

			
			fontPanel.add(topPanel, BorderLayout.NORTH);
			return fontPanel;
		}
		
		private JPanel makeBottomPanel() {
			JPanel bottom = new JPanel(new BorderLayout());

			JPanel samplePanel = new JPanel();
			samplePanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Sample"));
			samplePanel.add(sampleLabel);
			bottom.add(samplePanel, BorderLayout.CENTER);

			JPanel buttonPanel = new JPanel();
			buttonPanel.add(cancelButton = new JButton("Cancel"));
			buttonPanel.add(okButton = new JButton("Ok"));
			cancelButton.addActionListener(this);
			okButton.addActionListener(this);
			bottom.add(buttonPanel, BorderLayout.SOUTH); 
		
			return bottom;
		}
		
		public DialogPanel(JDialog dialog, DrawerView view) {
			this.view = view;
			this.dialog = dialog;
			setLayout(new BorderLayout());

			//Font info Panel
			JPanel fontPanel = makeFontPanel();
			add(fontPanel, BorderLayout.CENTER);

			//Sample, Button Panel
			JPanel bottomPanel = makeBottomPanel();
			add(bottomPanel, BorderLayout.SOUTH);
		}

		public void valueChanged(ListSelectionEvent e) {
			font = new Font(fontNameList.getSelectedValue(),
							fontStyleList.getSelectedIndex(),
							Integer.parseInt(fontSizeList.getSelectedValue())
						);
			sampleLabel.setFont(font);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == okButton) {
				okFlag = true;
			} else if (e.getSource() == cancelButton) {
				okFlag = false;
			}
			dialog.setVisible(false);
		}
	}

    DialogPanel panel;

    public Font getFont() {
        if (!panel.okFlag) return null;
        return panel.font;
    }

    public FontDialog(String title, DrawerView view) {
        super((JFrame)null,title);
        setModal(true);
		setLocation(200,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
		setSize(440,440);

		Container container = getContentPane();
		panel = new DialogPanel(this, view);
		container.add(panel);
    }
  
}
