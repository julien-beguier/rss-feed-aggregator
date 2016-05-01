package fr.julienbeguier.utils;

import fr.julienbeguier.gui.MainFrame.Type;

public class Constants {

	public final static String	CONFIGURATION_FOLDER = "rssfeedaggregator";
	
	public final static String	VALUE_EMPTY = "";
	public final static String	VALUE_LOGIN = "login";
	public final static String	VALUE_PASSWORD = "password";
	public final static String	VALUE_EMAIL = "email";
	public final static String	VALUE_FEED = "feed";
	public final static String	VALUE_CATEGORY = "category";
	public final static String	VALUE_URL = "url";
	public final static String	VALUE_TYPE = "type";
	public final static String	NODE_ROOT = "All Feeds";
	public final static String	NODE_EMPTY = "(Empty)";
	
	// PATHS
	public final static String	PATH_ICON_PROGRAM = "/img/logo_512px.png";
	public final static String	PATH_ICON_ABOUTUS = "/img/logo_128px.png";
	
	// GUI MAINFRAME
	public final static float	RATIO = 1.5f;
	public final static String	JMENU_RSSMENU_TITLE = "Rss";
	public final static String	JMENU_SETTINGS_TILTE = "Settings";
	public final static String	JMENU_ABOUTMENU_TITLE = "About us";
	public final static String	JMENUITEM_ADD_FEED_TITLE = "Add a feed";
	public final static String	JMENUITEM_ADD_CATEGORY_TITLE = "Add a category";
	public final static String	JMENUITEM_REMOVE_FEED_TITLE = "Remove a feed";
	public final static String	JMENUITEM_REMOVE_CATEGORY_TITLE = "Remove a category";
	public final static String	JMENUITEM_QUIT_TITLE = "Quit";
	public final static String	JMENUITEM_SAVE_CONFIGURATION_TITLE = "Save configuration";
	public final static String	JMENUITEM_ABOUT_TITLE = "About RssFeedAggregator";
	public final static String	ABOUT_TITLE = "About RssFeedAggregator";
	public final static String	ABOUT_MESSAGE = "This program is the desktop version\nand part of the project RssFeeAggragor.\n\nMade by :\nJulien Béguier (beguie_j)\nValentin Pinilla (pinill_v)\nBilel Benamira (benami_b)\nYoussef Radoui (radoui_y)\nSarah Schneider (schnei_s)";
	public final static String	MAINFRAME_TITLE = "Rss Feed Aggregator";
	public final static String	MAINFRAME_RSSTREE_CATEGORY_NOT_FOUND_TITLE = "Warning : Category not found";
	public final static String	MAINFRAME_RSSTREE_CATEGORY_NOT_FOUND_MESSAGE(String category) { return "The category '" + category + "' does not exist.\nPlease create it before adding\nanything in it."; }
	
	// GUI ADDNODE
	public final static int		ADDNODE_WIDTH = 300;
	public final static int		ADDNODE_HEIGHT(Type type) { return (type == Type.Feed ? Constants.ADDNODE_HEIGHT_FEED : Constants.ADDNODE_HEIGHT_CATEGORY); } 
	public final static int		ADDNODE_HEIGHT_FEED = 150;
	public final static int		ADDNODE_HEIGHT_CATEGORY = 90;
	public final static String	ADDNODE_TITLE_PREFIX = "Add a ";
	public final static String	ADDNODE_TITLE_SUFFIX_FEED = "feed";
	public final static String	ADDNODE_TITLE_SUFFIX_CATEGORY = "category";	
	public final static String	ADDNODE_TITLE(Type type) { return Constants.ADDNODE_TITLE_PREFIX + (type == Type.Feed ? Constants.ADDNODE_TITLE_SUFFIX_FEED : Constants.ADDNODE_TITLE_SUFFIX_CATEGORY); }
	public final static String	ADDNODE_LABEL_PREFIX_FEED = "Feed";
	public final static String	ADDNODE_LABEL_PREFIX_CATEGORY = "Category";
	public final static String	ADDNODE_LABEL_SUFFIX = " name : ";	
	public final static String	ADDNODE_LABEL_TEXT(Type type) { return (type == Type.Feed ? Constants.ADDNODE_LABEL_PREFIX_FEED : Constants.ADDNODE_LABEL_PREFIX_CATEGORY) + Constants.ADDNODE_LABEL_SUFFIX; }
	public final static String	ADDNODE_LABEL_CATEGORY = "Category : ";
	public final static String	ADDNODE_LABEL_URL = "Url : ";
	public final static String	ADDNODE_BUTTON_CANCEL = "Cancel";
	public final static String	ADDNODE_BUTTON_OK = "Ok";
	public final static String	ADDNODE_WARNING_URL_MALFORMED_POPUP_TITLE = "Warning : Malformed url";
	public final static String	ADDNODE_WARNING_URL_MALFORMED_POPUP_MESSAGE = "The provided url is not valid.\nPlease, type a valid one.";
	
	// GUI REMOVENODE
	public final static int		REMOVENODE_WIDTH = 300;
	public final static int		REMOVENODE_HEIGHT(Type type) { return (type == Type.Feed ? REMOVENODE_HEIGHT_FEED : REMOVENODE_HEIGHT_CATEGORY); }
	public final static int		REMOVENODE_HEIGHT_FEED = 120;
	public final static int		REMOVENODE_HEIGHT_CATEGORY = 90;
	public final static String	REMOVENODE_TITLE_PREFIX = "Remove a ";
	public final static String	REMOVENODE_TITLE_SUFFIX_FEED = "feed";
	public final static String	REMOVENODE_TITLE_SUFFIX_CATEGORY = "category";
	public final static String	REMOVENODE_TITLE(Type type) { return (type == Type.Feed ? REMOVENODE_TITLE_SUFFIX_FEED : REMOVENODE_TITLE_SUFFIX_CATEGORY); }
	public final static String	REMOVENODE_LABEL_FEED = "Feed : ";
	public final static String	REMOVENODE_LABEL_CATEGORY = "Category : ";
	public final static String	REMOVENODE_BUTTON_CANCEL = "Cancel";
	public final static String	REMOVENODE_BUTTON_OK = "Ok";
	
	// GUI LOGIN
	public final static String	LOGIN_TITLE = "Enter your credentials to login";
	public final static String	LOGIN_FORM_LOGIN = "Login : ";
	public final static String	LOGIN_FORM_PASSWORD = "Password : ";
	public final static String	LOGIN_FORM_NEW_ACCOUNT = "New account ?";
	public final static String	LOGIN_FORM_EMAIL = "Email : ";
	public final static String	LOGIN_FORM_BUTTON_EXIT = "Exit";
	public final static String	LOGIN_FORM_BUTTON_OK = "Ok";
	public final static String	LOGIN_FAILURE_TITLE = "Login fail";
	public final static String	LOGIN_FAILURE_MESSAGE = "Error: login or password is incorrect";

	// GUI REGISTER
	public final static String	REGISTER_FAILURE_TITLE = "Register fail";
	public final static String	REGISTER_FAILURE_MESSAGE = "Error: couldn't register account";
}
