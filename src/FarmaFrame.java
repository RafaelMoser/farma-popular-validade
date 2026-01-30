import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.text.*;

public class FarmaFrame extends JFrame {

	private final JLabel mainLabel;

	private final JLabel calcLabel1;
	private final JTextField calcTextField;
	private final JLabel calcLabel2;

	private JPopupMenu popupMenu;
	private boolean calcOpen;
	private String prevValidDate;

	private static class DateDocumentFilter extends DocumentFilter {
		private String digitsOnly(String s) { return s == null ? "" : s.replaceAll("\\D", ""); }

		private String formatDigits(String digits) {
			if (digits.length() == 0) return "";
			StringBuilder sb = new StringBuilder();
			int len = Math.min(digits.length(), 8);
			if (len >= 2) {
				sb.append(digits.substring(0, 2));
				sb.append('/');
			} else {
				sb.append(digits);
				return sb.toString();
			}
			if (len >= 4) {
				sb.append(digits.substring(2, 4));
				sb.append('/');
			} else {
				sb.append(digits.substring(2, len));
				return sb.toString();
			}
			if (len > 4) sb.append(digits.substring(4, len));
			return sb.toString();
		}

		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
			if (string == null || string.isEmpty()) return;
			String current = fb.getDocument().getText(0, fb.getDocument().getLength());
			StringBuilder proposed = new StringBuilder(current);
			proposed.insert(offset, string);
			String digits = digitsOnly(proposed.toString());
			if (digits.length() > 8) digits = digits.substring(0, 8);
			String formatted = formatDigits(digits);
			fb.replace(0, fb.getDocument().getLength(), formatted, attr);
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			String current = fb.getDocument().getText(0, fb.getDocument().getLength());
			StringBuilder proposed = new StringBuilder(current);
			proposed.replace(offset, offset + length, text == null ? "" : text);
			String digits = digitsOnly(proposed.toString());
			if (digits.length() > 8) digits = digits.substring(0, 8);
			String formatted = formatDigits(digits);
			fb.replace(0, fb.getDocument().getLength(), formatted, attrs);
		}

		@Override
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			String current = fb.getDocument().getText(0, fb.getDocument().getLength());
			StringBuilder proposed = new StringBuilder(current);
			proposed.delete(offset, offset + length);
			String digits = digitsOnly(proposed.toString());
			String formatted = formatDigits(digits);
			fb.replace(0, fb.getDocument().getLength(), formatted, null);
		}
	}

	public FarmaFrame() {
		super();
		mainLabel = new JLabel("", SwingConstants.CENTER);
		calcLabel1 = new JLabel("| Calcular data de vencimento: ", SwingConstants.CENTER);
		calcTextField = new JTextField(Utils.getTodayDate(), 8);
		calcLabel2 = new JLabel("", SwingConstants.CENTER);
		calcOpen = true;
		initUI();
	}
    
	private void initUI() {

		//main interface
		setUndecorated(true);
		setType(Window.Type.UTILITY);
		setComponentStyle(mainLabel);
		setComponentStyle(calcLabel1);
		setComponentStyle(calcTextField);
		setComponentStyle(calcLabel2);
		calcTextField.setBackground(new Color(40,40,40));
		calcTextField.setCaretColor(new Color(0,255,0));

		((AbstractDocument) calcTextField.getDocument()).setDocumentFilter(new DateDocumentFilter());

		getContentPane().setLayout(new GridBagLayout());
		getContentPane().add(mainLabel);
		getContentPane().add(calcLabel1);
		getContentPane().add(calcTextField);
		getContentPane().add(calcLabel2);
		
		// popup menu
		popupMenu = new JPopupMenu();
		setComponentStyle(popupMenu);

		JMenuItem calc = new JMenuItem("Calculador de vencimento");
		JMenuItem closeItem = new JMenuItem("Fechar");
		setComponentStyle(calc);
		setComponentStyle(closeItem);
		calc.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			calcOpen = !calcOpen;
			calcLabel1.setVisible(calcOpen);
			calcTextField.setVisible(calcOpen);
			calcLabel2.setVisible(calcOpen);
			pack();
			setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - (getSize().width)/2,0);
		}));
		closeItem.addActionListener(e -> SwingUtilities.invokeLater(() -> dispose()));
		popupMenu.add(calc);
		popupMenu.add(closeItem);

		//listeners

		MouseAdapter ma = new MouseAdapter() {

			private void actions(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				if (e.getButton() == MouseEvent.BUTTON1){
					calcTextField.requestFocusInWindow();
				}
				
			}

			public void mousePressed(MouseEvent e) { actions(e); }
			public void mouseReleased(MouseEvent e) { actions(e); }
		};

		FocusAdapter fa = new FocusAdapter() {

			public void focusGained(FocusEvent e) {
				String txt = calcTextField.getText();
				if (Utils.isValidDate(txt)) {
					prevValidDate = calcTextField.getText();
				}
				calcTextField.setText("");
				calcTextField.setForeground(new Color(0,255,0));
			};

			public void focusLost(FocusEvent e) {
				handleDateCalcInput(calcTextField.getText());
			};
		};

		KeyAdapter ka = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){					
					handleDateCalcInput(calcTextField.getText());
				}
			};
		};

		addMouseListener(ma);
		calcTextField.addFocusListener(fa);
		calcTextField.addKeyListener(ka);

		//finish

		pack();
		setFocusableWindowState(true);
		setAlwaysOnTop(true);

		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - (getSize().width)/2,0);
	}

	private static void setComponentStyle(JComponent c){
		c.setFont(new Font("SansSerif", Font.PLAIN, 12));
		c.setOpaque(true);
		c.setForeground(new Color(0,255,0));
		c.setBackground(new Color(0,0,0));
		c.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

	}

	public void setMainText(String text) {
		SwingUtilities.invokeLater(() -> {
			mainLabel.setText(text);
			pack();
			setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - (getSize().width)/2,0);
		});
	}
	
	public void setCalcText(String text){
		SwingUtilities.invokeLater(() -> {
			calcLabel2.setText(text);
			pack();
			setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - (getSize().width)/2,0);
			System.out.println(getSize());
		});
	}

	public void showWindow() {
		SwingUtilities.invokeLater(() -> setVisible(true));
	}

	public void hideWindow() {
		SwingUtilities.invokeLater(() -> setVisible(false));
	}

	private void handleDateCalcInput(String txt){
		if(txt.length() == 8 || txt.length() == 10){
			txt = Utils.quickYearFix(txt);
			calcTextField.setText(txt);
			if(Utils.isValidDate(txt)){
				setCalcText(Utils.formatCalcString(DateCalc.calcExpiryDate(txt)));
			} else {	
				calcTextField.setForeground(new Color(255,0,0));
			}
		} else {
			calcTextField.setText(prevValidDate);
		}
	}

	public String getCurrentCalcText() {
		return calcTextField.getText();
	}
}
 