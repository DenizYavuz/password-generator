import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;


public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private void generatePasswordAndOutput(String encoding) {
		CryptoUtils cu = new CryptoUtils();
		String password = "";
		
		if (encoding.equals("ASCII85")) {
			password = cu.encodeASCII85(cu.sha512(new String(passwordMasterPass.getPassword()) + textWhereToUse.getText() + comboWhichTime.getSelectedItem()));
		}
		else if (encoding.equals("Base64")) {
			password = cu.encodeBase64(cu.sha512(new String(passwordMasterPass.getPassword()) + textWhereToUse.getText() + comboWhichTime.getSelectedItem()));
		}
		else if (encoding.equals("Number")) {
			password = cu.toNumbers(cu.sha512(new String(passwordMasterPass.getPassword()) + textWhereToUse.getText() + comboWhichTime.getSelectedItem()));
		}
		
		password = password.substring(0, (Integer) comboLength.getSelectedItem());
		
		if (chckbxShowPassword.isSelected())
	    	textGeneratedPassword.setText(password);
		else {
			// Copy password to clipboard.
			StringSelection selection = new StringSelection(password);
		    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents(selection, selection);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
			}
		});
		setTitle("Password Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ImageIcon img = new ImageIcon("resources/icon.png");
		setIconImage(img.getImage());
		
		JLabel lblMasterPassword = new JLabel("Master Password");
		
		JLabel lblWhereToUse = new JLabel("Where To Use");
		
		JLabel lblWhichUse = new JLabel("Number of Change");
		
		textWhereToUse = new JTextField();
		textWhereToUse.setColumns(10);
		
		comboWhichTime = new JComboBox<Integer>();
		comboWhichTime.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20}));
		
		JLabel labelLength = new JLabel("Length");
		
		comboLength = new JComboBox<Integer>();
		comboLength.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1}));
		
		JButton buttonGenerate = new JButton("Generate");
		getRootPane().setDefaultButton(buttonGenerate);
		buttonGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnAscii85.isSelected())
					generatePasswordAndOutput("ASCII85");
				else if (rdbtnBase64.isSelected()) {
					generatePasswordAndOutput("Base64");
				}
				else if (rdbtnNumber.isSelected()) {
					generatePasswordAndOutput("Number");
				}
			}
		});
		
		textGeneratedPassword = new JTextField();
		textGeneratedPassword.setColumns(10);
		
		passwordMasterPass = new JPasswordField();
		
		chckbxShowPassword = new JCheckBox("Show password");
		
		rdbtnAscii85 = new JRadioButton("A-Z, a-z, 0-9, ., -, :, +, =, ^, !, /, *, ?, &, <, >, (, ), [, ], {, }, @, %, $, # (ASCII85)");
		rdbtnBase64 = new JRadioButton("A-Z, a-z, 0-9 (Base64)");
		rdbtnNumber = new JRadioButton("0-9 (Number)");
		
		rdbtnAscii85.setSelected(true);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnAscii85);
		buttonGroup.add(rdbtnBase64);
		buttonGroup.add(rdbtnNumber);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnBase64)
								.addComponent(rdbtnNumber)
								.addComponent(rdbtnAscii85))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblMasterPassword)
								.addComponent(lblWhereToUse)
								.addComponent(lblWhichUse)
								.addComponent(labelLength))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(passwordMasterPass, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(comboLength, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(comboWhichTime, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
								.addComponent(textWhereToUse, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
							.addGap(305))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(buttonGenerate)
									.addGap(18)
									.addComponent(chckbxShowPassword))
								.addComponent(textGeneratedPassword, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordMasterPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMasterPassword))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textWhereToUse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWhereToUse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboWhichTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWhichUse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelLength))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnAscii85)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnBase64)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNumber)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonGenerate)
						.addComponent(chckbxShowPassword))
					.addGap(18)
					.addComponent(textGeneratedPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private JPanel contentPane;
	private JTextField textWhereToUse;
	private JTextField textGeneratedPassword;
	private JPasswordField passwordMasterPass;
	private JCheckBox chckbxShowPassword;
	private JComboBox<Integer> comboWhichTime;
	private JComboBox<Integer> comboLength;
	private JRadioButton rdbtnAscii85;
	private JRadioButton rdbtnBase64;
	private JRadioButton rdbtnNumber;
}