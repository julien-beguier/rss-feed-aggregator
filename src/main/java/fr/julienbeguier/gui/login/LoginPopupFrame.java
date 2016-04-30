package fr.julienbeguier.gui.login;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.julienbeguier.controller.Controller;
import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.observer.Notification;
import fr.julienbeguier.observer.Observer;
import fr.julienbeguier.utils.Constants;

public class LoginPopupFrame extends JFrame implements Observer {

	/**
	 * Login popup that show at launching if the user has never logged in
	 */
	private static final long serialVersionUID = -339114042562636915L;

	// GUI
	private Dimension	dimension;
	private ImageIcon	imageIcon;

	// CONTROLLER
	private Controller	controller;

	// OTHERS
	private String		login;

	public LoginPopupFrame(Controller controller, String login, Dimension dimension) {
		super(Constants.LOGIN_TITLE);

		this.login = login;
		this.dimension = dimension;

		this.imageIcon = new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_PROGRAM));
		this.setIconImage(this.imageIcon.getImage());
		this.setSize(this.dimension);
		this.setPreferredSize(this.dimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Dimension getDimension() {
		return this.dimension;
	}

	@Override
	public void update(Notification notification) {
		if (notification.getNotification().equals(Action.ACTION_LOGIN_SUCCESS)) {
			this.setVisible(false);
		}else if (notification.getNotification().equals(Action.ACTION_LOGIN_FAILURE)) {
			JOptionPane.showMessageDialog(null, Constants.LOGIN_FAILURE_TITLE, Constants.LOGIN_FAILURE_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}else if (notification.getNotification().equals(Action.ACTION_REGISTER_SUCCESS)) {
			// CALL control(Action.LOGIN)
			Action action = new Action(Action.ACTION_LOGIN);
			action.setParams(notification.getParams());
			controller.control(action);
		}else if (notification.getNotification().equals(Action.ACTION_REGISTER_FAILURE)) {
			JOptionPane.showMessageDialog(null, Constants.REGISTER_FAILURE_TITLE, Constants.REGISTER_FAILURE_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}
}
