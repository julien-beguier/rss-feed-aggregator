package fr.julienbeguier.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import fr.julienbeguier.utils.Constants;
import fr.julienbeguier.utils.OSChecker;

public class Configuration {

	public static Configuration instance = null;

	// CONST VARS
	private final static String	CONFIGURATION_FILE = "config.json";
	private final String	CONFIGURATION_PATH = "/config.json";
	private final URL		CONFIGURATION_URL;
	private boolean			exist;

	// KEYS
	private final String	CONF_KEY_AUTHENTICATION = "authentication";
	private final String	CONF_KEY_AUTHENTICATION_LOGIN = "login";
	private final String	CONF_KEY_AUTHENTICATION_PASSWORD = "password";

	private final String	CONF_KEY_SETTINGS = "settings";
	private final String	CONF_KEY_SETTINGS_SERVER = "server";
	private final String	CONF_KEY_SETTINGS_SERVER_IP = "ip";
//	private final String	CONF_KEY_SETTINGS_SERVER_PORT = "port";
	
	private final String	CONF_KEY_FEEDS = "feeds";
	private final String	CONF_KEY_FEEDS_CATEGORIES = "categories";
	private final String	CONF_KEY_FEEDS_CATEGORIES_NAME = "name";
	private final String	CONF_KEY_FEEDS_RSSFEEDS = "rssFeeds";
	private final String	CONF_KEY_FEEDS_RSSFEEDS_NAME = "name";
	private final String	CONF_KEY_FEEDS_RSSFEEDS_CATEGORY = "category";
	private final String	CONF_KEY_FEEDS_RSSFEEDS_FEEDURL = "feedUrl";

	// CONF OBJ
	private JSONObject	configurationObj;
	private JSONObject	authenticationObj;
	private JSONObject	settingsObj;
	private JSONObject	serverObj;
	private JSONObject	feedsObj;

	private Map<String, Object> settings;

	private Configuration() {
		this.CONFIGURATION_URL = this.getClass().getResource(this.CONFIGURATION_PATH);
		this.exist = false;
		
		this.checkConfigurationFile();
		this.loadConfiguration();
	}
	
	private void checkConfigurationFile() {
		File settings;
		try {
			settings = getSettingsFile();
			if (settings.exists()) {
				this.exist = true;
			}else {
				this.exist = false;
			}
		} catch (PrivilegedActionException e) {
			e.printStackTrace();
		}
	}
	
	private void loadConfiguration() {
		String confFileContent = null;
		BufferedReader br;

		if (this.settingsExists()) {
			try { // appdata/.rssfeedaggregator
				br = new BufferedReader(new InputStreamReader(new FileInputStream(getSettingsFile())));
				String line;
				StringBuffer fileContent = new StringBuffer(); 
				while ((line = br.readLine()) != null) {
					fileContent.append(line);
					fileContent.append('\r');
				}
				confFileContent = fileContent.toString().trim();
			} catch (IOException | PrivilegedActionException e) {
				e.printStackTrace();
			}

			if (confFileContent == null) {
				try {
					System.err.println("Error, the program couldn't read the configuration file: \'" + Configuration.getSettingsFile().getAbsolutePath().toString() + "\'");
				} catch (PrivilegedActionException e) {
					e.printStackTrace();
				}
				System.exit(-1);
			}
		}else { // from jar
			try {
				br = new BufferedReader(new InputStreamReader(this.CONFIGURATION_URL.openStream()));
				String line;
				StringBuffer fileContent = new StringBuffer(); 
				while ((line = br.readLine()) != null) {
					fileContent.append(line);
					fileContent.append('\r');
				}
				confFileContent = fileContent.toString().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (confFileContent == null) {
				System.err.println("Error, the program couldn't read the configuration file: \'" + this.CONFIGURATION_PATH + "\'");
				System.exit(-1);
			}
		}

		try {
			this.configurationObj = new JSONObject(confFileContent);

			this.authenticationObj = this.configurationObj.getJSONObject(this.CONF_KEY_AUTHENTICATION);
			this.settingsObj = this.configurationObj.getJSONObject(this.CONF_KEY_SETTINGS);
			this.serverObj = this.settingsObj.getJSONObject(this.CONF_KEY_SETTINGS_SERVER);
			this.feedsObj = this.configurationObj.getJSONObject(this.CONF_KEY_FEEDS);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		this.settings = new HashMap<String, Object>();
		this.settings.put(this.CONF_KEY_AUTHENTICATION_LOGIN, this.authenticationObj.get(this.CONF_KEY_AUTHENTICATION_LOGIN));
		this.settings.put(this.CONF_KEY_AUTHENTICATION_PASSWORD, this.authenticationObj.get(this.CONF_KEY_AUTHENTICATION_PASSWORD));
		this.settings.put(this.CONF_KEY_SETTINGS_SERVER_IP, this.serverObj.get(this.CONF_KEY_SETTINGS_SERVER_IP));
//		this.settings.put(this.CONF_KEY_SETTINGS_SERVER_PORT, this.serverObj.get(this.CONF_KEY_SETTINGS_SERVER_PORT));
	}
	
	public void saveConfiguration() {
		String saveContent = null;

		try {
			File configurationFile = Configuration.getSettingsFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(configurationFile, false));
			JSONObject newConfiguration = new JSONObject();

			this.authenticationObj.put(this.CONF_KEY_AUTHENTICATION_LOGIN, this.settings.get(this.CONF_KEY_AUTHENTICATION_LOGIN));
			this.authenticationObj.put(this.CONF_KEY_AUTHENTICATION_PASSWORD, this.settings.get(this.CONF_KEY_AUTHENTICATION_PASSWORD));
			newConfiguration.put(this.CONF_KEY_AUTHENTICATION, this.authenticationObj);
			newConfiguration.put(this.CONF_KEY_SETTINGS, this.settingsObj);
			newConfiguration.put(this.CONF_KEY_FEEDS, this.feedsObj);			
			saveContent = newConfiguration.toString(2);
			bw.write(saveContent);
			bw.close();
		} catch (IOException | PrivilegedActionException e) {
			e.printStackTrace();
		}
	}
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	private boolean settingsExists() {
		return this.exist;
	}
	
	public static File getSettingsFile() throws PrivilegedActionException {
		return new File(Configuration.getRssDir(), Configuration.CONFIGURATION_FILE);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getRssDir() throws PrivilegedActionException {
		return (String) AccessController.doPrivileged(new PrivilegedExceptionAction() {
			public Object run() throws Exception {
				return Configuration.getWorkingDirectory(Constants.CONFIGURATION_FOLDER) + File.separator;
			}
		});
	}

	private static File getWorkingDirectory(String applicationName) {
		String userHome = System.getProperty("user.home", ".");
		File workingDirectory;
		if (OSChecker.isUnix() || OSChecker.isSolaris()) {
			workingDirectory = new File(userHome, '.' + applicationName + '/');
		} else if (OSChecker.isWindows()) {
			String applicationData = System.getenv("APPDATA");
			if (applicationData != null) {
				workingDirectory = new File(applicationData, "." + applicationName + '/');
			}else {
				workingDirectory = new File(userHome, '.' + applicationName + '/');
			}
		} else if (OSChecker.isMac()) {
			workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
		} else {
			workingDirectory = new File(userHome, applicationName + '/');
		}
		
		workingDirectory.mkdirs();
		return workingDirectory;
	}

	public Map<String, Object> getSettings() {
		return this.settings;
	}
	
	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}
	
	public JSONObject getFeeds() {
		return this.feedsObj;
	}

	// GET KEYS
	public final String getKeyAuthentication() {
		return this.CONF_KEY_AUTHENTICATION;
	}

	public final String getKeyAuthenticationLogin() {
		return this.CONF_KEY_AUTHENTICATION_LOGIN;
	}

	public final String getKeyAuthenticationPassword() {
		return this.CONF_KEY_AUTHENTICATION_PASSWORD;
	}

	public final String getKeyServer() {
		return this.CONF_KEY_SETTINGS_SERVER;
	}

	public final String getKeyServerIp() {
		return this.CONF_KEY_SETTINGS_SERVER_IP;
	}

//	public final String getKeyServerPort() {
//		return this.CONF_KEY_SETTINGS_SERVER_PORT;
//	}
	
	public final String getKeyFeeds() {
		return this.CONF_KEY_FEEDS;
	}

	public final String getKeyFeedsCategories() {
		return this.CONF_KEY_FEEDS_CATEGORIES;
	}

	public final String getKeyFeedsCategoriesName() {
		return this.CONF_KEY_FEEDS_CATEGORIES_NAME;
	}

	public final String getKeyFeedsRssFeeds() {
		return this.CONF_KEY_FEEDS_RSSFEEDS;
	}

	public final String getKeyFeedsRssFeedsName() {
		return this.CONF_KEY_FEEDS_RSSFEEDS_NAME;
	}

	public final String getKeyFeedsRssFeedsCategory() {
		return this.CONF_KEY_FEEDS_RSSFEEDS_CATEGORY;
	}

	public final String getKeyFeedsRssFeedsUrl() {
		return this.CONF_KEY_FEEDS_RSSFEEDS_FEEDURL;
	}
}
