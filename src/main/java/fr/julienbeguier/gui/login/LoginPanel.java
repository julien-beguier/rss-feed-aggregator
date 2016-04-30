package fr.julienbeguier.gui.login;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.uncommons.swing.SpringUtilities;

import fr.julienbeguier.controller.Controller;
import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.utils.Constants;
import fr.julienbeguier.utils.MessageDigestUtils;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = -1650427683138582838L;

	// GUI
	private JTextField			loginTextField;
	private JPasswordField		passwordField;
	private JTextField			emailTextField;
	private JCheckBox			emailCheckBox;
	private JButton				exitButton;
	private JButton				okButton;

	// CONTROLLER
	private Controller			controller;

	public LoginPanel(Controller controller, String login) {
		this.controller = controller;

		this.setVisible(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new SpringLayout());
		this.loginTextField = new JTextField(20);
		this.passwordField = new JPasswordField(20);
		this.passwordField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okButton.doClick();
			}
		});
		this.emailCheckBox = new JCheckBox();
		this.emailCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (emailCheckBox.isSelected()) {
					emailTextField.setEnabled(true);
				} else {
					emailTextField.setEnabled(false);
				}
			}
		});
		this.emailTextField = new JTextField(20);
		this.emailTextField.setEnabled(false);

		this.exitButton = new JButton(Constants.LOGIN_FORM_BUTTON_EXIT);
		this.exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		this.okButton = new JButton(Constants.LOGIN_FORM_BUTTON_OK);
		this.okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String login = loginTextField.getText();
				String password = new String(passwordField.getPassword());
				String email = emailTextField.getText();

				if (!emailCheckBox.isSelected()) {
					// LOGIN
					if (login.isEmpty() || password.isEmpty()) {
						return;
					}

					Map<String, Object> params = new HashMap<String, Object>();
					params.put(Constants.VALUE_LOGIN, login);
					params.put(Constants.VALUE_PASSWORD, MessageDigestUtils.getInstance().md5(password));
					Action action = new Action(Action.ACTION_LOGIN);
					action.setParams(params);
					controller.control(action);
				}else {
					// REGISTER
					if (login.isEmpty() || password.isEmpty() || email.isEmpty()) {
						return;
					}

					Map<String, Object> params = new HashMap<String, Object>();
					params.put(Constants.VALUE_LOGIN, login);
					params.put(Constants.VALUE_PASSWORD, MessageDigestUtils.getInstance().md5(password));
					params.put(Constants.VALUE_EMAIL, email);
					Action action = new Action(Action.ACTION_REGISTER);
					action.setParams(params);
					controller.control(action);
				}
			}
		});

		Component[] components = {this.loginTextField, this.passwordField, this.emailCheckBox, this.emailTextField};
		String[] labels = {Constants.LOGIN_FORM_LOGIN, Constants.LOGIN_FORM_PASSWORD, Constants.LOGIN_FORM_NEW_ACCOUNT, Constants.LOGIN_FORM_EMAIL};
		int numPairs = labels.length;

		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			p.add(l);
			l.setLabelFor(components[i]);
			p.add(components[i]);
		}

		SpringUtilities.makeCompactGrid(p, numPairs, 2, 6, 6, 6, 6);

		this.add(p);

		Box hBox = Box.createHorizontalBox();
		hBox.add(this.exitButton);
		hBox.add(Box.createRigidArea(new Dimension(15, 0)));
		hBox.add(this.okButton);
		this.add(hBox);
	}
}
