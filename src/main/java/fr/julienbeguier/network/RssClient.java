package fr.julienbeguier.network;

import java.awt.Dimension;
import java.util.Map;

import fr.julienbeguier.configuration.Configuration;
import fr.julienbeguier.controller.Controller;
import fr.julienbeguier.data.RssData;
import fr.julienbeguier.gui.MainFrame;
import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.gui.login.LoginPanel;
import fr.julienbeguier.gui.login.LoginPopupFrame;
import fr.julienbeguier.model.AbstractModel;
import fr.julienbeguier.observer.Notification;
import fr.julienbeguier.observer.Observer;
import fr.julienbeguier.utils.GraphicsUtils;
import fr.julienbeguier.utils.MessageDigestUtils;

public class RssClient extends AbstractModel {

	// SERVER
	private final String	SERVER_IP;
	//	private final int		SERVER_PORT;

	// CONTROLLER
	private Controller		controller;

	// DATA
	private RssData			rssData;

	// OTHERS
	private boolean			logged;

	public RssClient(String serverIp/*, int serverPort*/) {
		SERVER_IP = serverIp;
		//		SERVER_PORT = serverPort;

		this.controller = new Controller(this);

		this.rssData = new RssData();

		this.logged = false;

		System.out.println("SERVER IS " + SERVER_IP/* + ":" + SERVER_PORT*/);
	}

	public void login(String login, String password) {
		if (!login.isEmpty() && !password.isEmpty()) {
			if (tryLogin(login, password) == true) {
				logged = true;
				this.showMainFrame();
				return;
			}
		}

		showLoginPopup(login);
	}

	@Override
	public boolean tryLogin(String login, String password) {
		// Login in text & password is md5
		// TODO LOGIN TO SERVER
		if (login.equals("admin") && password.equals(MessageDigestUtils.getInstance().md5("admin"))) {
			System.out.println("Logged !");
			Configuration configuration = Configuration.getInstance();
			Map<String, Object> settings = configuration.getSettings();
			settings.put(configuration.getKeyAuthenticationLogin(), login);
			settings.put(configuration.getKeyAuthenticationPassword(), password);
			configuration.setSettings(settings);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean tryLogout() {
		// TODO LOGOUT TO SERVER
		return false;
	}

	@Override
	public boolean tryRegister(String login, String password, String email) {
		// Login in text & password is md5
		// TODO REGISTER TO SERVER
		//		if (login.equals("admin") && password.equals(MessageDigestUtils.getInstance().md5("admin"))) {
		//			System.out.println("Logged !");
		//			return true;
		//		}
		return false;
	}

	@Override
	public boolean tryAddFeed(String feedName, String feedCategory, String feedUrl) {
		// TODO ADD FEED TO SERVER
		// TODO CHECK IF THE FEED EXISTS
		return true;
	}

	@Override
	public boolean tryAddCategory(String categoryName) {
		// TODO ADD CATEGORY TO SERVER
		// TODO CHECK IF THE CATEGORY EXISTS
		return true;
	}

	@Override
	public boolean tryRemoveFeed(String feedName, String feedCategory) {
		// TODO REMOVE FEED TO SERVER
		// TODO CHECK IF THE FEED EXISTS
		return true;
	}

	@Override
	public boolean tryRemoveCategory(String categoryName) {
		// TODO REMOVE CATEGORY TO SERVER
		// TODO CHECK IF THE CATEGORY EXISTS
		return true;
	}

	private void showLoginPopup(String login) {
		Dimension dimension = new Dimension(400, 175);
		LoginPanel panel = new LoginPanel(this.controller, login);
		LoginPopupFrame lpf = new LoginPopupFrame(this.controller, login, dimension);
		lpf.add(panel);
		this.addObserver(lpf);
	}

	private void showMainFrame() {
		MainFrame mf = new MainFrame(this.controller, GraphicsUtils.getInstance().getScreenDimension(), this.rssData);
		mf.initGUI();
		mf.initRSS();
		mf.setVisible(true);
		this.addObserver(mf);
	}

	public boolean isLogged() {
		return this.logged;
	}

	@Override
	public void notifyObserver(Notification notification) {
		for(Observer obs : listObserver) {
			obs.update(notification);
		}

		if (notification.getNotification().equals(Action.ACTION_LOGIN_SUCCESS)) {
			logged = true;
			this.showMainFrame();
		}
	}
}
