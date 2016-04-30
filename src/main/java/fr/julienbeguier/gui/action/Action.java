package fr.julienbeguier.gui.action;

import java.util.Map;

public class Action {

	public static final String	ACTION_LOGIN = "ACTION_LOGIN";
	public static final String	ACTION_LOGIN_SUCCESS = "ACTION_LOGIN_SUCCESS";
	public static final String	ACTION_LOGIN_FAILURE = "ACTION_LOGIN_FAILURE";
	
	public static final String	ACTION_REGISTER = "ACTION_REGISTER";
	public static final String	ACTION_REGISTER_SUCCESS = "ACTION_REGISTER_SUCCESS";
	public static final String	ACTION_REGISTER_FAILURE = "ACTION_REGISTER_FAILURE";
	
	public static final String	ACTION_ADD_FEED = "ACTION_ADD_FEED";
	public static final String	ACTION_ADD_FEED_SUCCESS = "ACTION_ADD_FEED_SUCCESS";
	public static final String	ACTION_ADD_FEED_FAILURE = "ACTION_ADD_FEED_FAILURE";

	public static final String	ACTION_ADD_CATEGORY = "ACTION_ADD_CATEGORY";
	public static final String	ACTION_ADD_CATEGORY_SUCCESS = "ACTION_ADD_CATEGORY_SUCCESS";
	public static final String	ACTION_ADD_CATEGORY_FAILURE = "ACTION_ADD_CATEGORY_FAILURE";

	public static final String	ACTION_REMOVE_FEED = "ACTION_REMOVE_FEED";
	public static final String	ACTION_REMOVE_FEED_SUCCESS = "ACTION_REMOVE_FEED_SUCCESS";
	public static final String	ACTION_REMOVE_FEED_FAILURE = "ACTION_REMOVE_FEED_FAILURE";

	public static final String	ACTION_REMOVE_CATEGORY = "ACTION_REMOVE_CATEGORY";
	public static final String	ACTION_REMOVE_CATEGORY_SUCCESS = "ACTION_REMOVE_CATEGORY_SUCCESS";
	public static final String	ACTION_REMOVE_CATEGORY_FAILURE = "ACTION_REMOVE_CATEGORY_FAILURE";
	
	public static final String	ACTION_VIEW_FEED = "ACTION_VIEW_FEED";
	
	private final String		action;
	private Map<String, Object>	params;
	
	public Action(final String action) {
		this.action = action;
		this.params = null;
	}
	
	public Action(final String action, Map<String, Object> params) {
		this.action = action;
		this.params = params;
	}
	
	public final String getActionType() {
		return this.action;
	}
	
	public Map<String, Object> getParams() {
		return this.params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
