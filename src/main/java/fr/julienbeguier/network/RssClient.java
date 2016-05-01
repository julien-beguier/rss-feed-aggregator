package fr.julienbeguier.network;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

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
import fr.julienbeguier.requests.HttpRequest;
import fr.julienbeguier.requests.RequestResponse;
import fr.julienbeguier.utils.Constants;
import fr.julienbeguier.utils.GraphicsUtils;
import fr.julienbeguier.utils.MessageDigestUtils;
import fr.valentinpinilla.library.FluxRss;

public class RssClient extends AbstractModel {

	// SERVER
	private final String	SERVER_IP;
	private final String	SITE_ADD_FEED;
//	private final String	SITE_REMOVE_FEED; // need API
	private final String	SITE_ADD_CATEGORY;
//	private final String	SITE_REMOVE_CATEGORY; // need API
	//	private final int		SERVER_PORT;

	// CONTROLLER
	private Controller		controller;

	// DATA
	private RssData			rssData;

	// OTHERS
//	private User			user; // need API
	private boolean			logged;

	public RssClient(String serverIp/*, int serverPort*/) {
		this.SERVER_IP = serverIp;
		this.SITE_ADD_FEED = serverIp + "/RssFeed/api/rss/add";
//		this.SITE_REMOVE_FEED = serverIp + "/RssFeed/api/rss/delete";
		this.SITE_ADD_CATEGORY = serverIp + "/RssFeed/api/category/add";
//		this.SITE_REMOVE_CATEGORY = serverIp + "/RssFeed/api/category/delete";

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
			//			this.user = new User();
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
		// TODO CHECK IF THE FEED EXISTS
		HttpRequest hr = HttpRequest.getInstance();
		try {
			// USER FROM DB
			RequestResponse rr = hr.getRequest(this.SITE_ADD_FEED, "title=" + feedName, "url=" + feedUrl, "category=" + feedCategory, "uid=1");
			JSONObject obj = rr.getResponse().getJSONObject(rr.getKeyJsonObject());
			if (obj.optBoolean("code") == true) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean tryRemoveFeed(String feedName, String feedCategory) {
		// TODO REMOVE FEED TO SERVER
		// TODO CHECK IF THE FEED EXISTS
//		HttpRequest hr = HttpRequest.getInstance();
//		try {
//			RequestResponse rr = hr.getRequest(this.SITE_REMOVE_FEED, "");
//			JSONObject obj = rr.getResponse().getJSONObject(rr.getKeyJsonObject());
//			if (obj.optBoolean("code") == true) {
//				return true;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return true;
	}

	@Override
	public boolean tryAddCategory(String categoryName) {
		// TODO CHECK IF THE CATEGORY EXIST
		HttpRequest hr = HttpRequest.getInstance();
		try {
			// USER FROM DB
			RequestResponse rr = hr.getRequest(this.SITE_ADD_CATEGORY, "name=" + categoryName, "user=1");
			JSONObject obj = rr.getResponse().getJSONObject(rr.getKeyJsonObject());
			if (obj.optBoolean("code") == true) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean tryRemoveCategory(String categoryName) {
		// TODO REMOVE CATEGORY TO SERVER
		// TODO CHECK IF THE CATEGORY EXISTS
		return true;
	}
	
	@Override
	public boolean tryProcessFlux(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		FluxRss fr = new FluxRss(params.get(Constants.VALUE_URL).toString());

		Iterator<?> entryIter = fr.getFeed().getEntries().iterator();
		while (entryIter.hasNext()) {
			SyndEntry syndEntry = (SyndEntry) entryIter.next();

			if (syndEntry.getContents() != null) {
				Iterator<?> it = syndEntry.getContents().iterator();
				while (it.hasNext()) {
					SyndContent syndContent = (SyndContent) it.next();
					sb.append(syndContent.getValue());
				}
			}
		}
		params.put(Constants.VALUE_FEED, sb.toString());
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
