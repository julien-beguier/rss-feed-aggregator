package fr.julienbeguier.observer;

import java.util.Map;

public class Notification {

	private final String		notification;
	private Map<String, Object>	params;
	
	public Notification(final String notification) {
		this.notification = notification;
		this.params = null;
	}
	
	public Notification(final String notification, Map<String, Object> params) {
		this.notification = notification;
		this.params = params;
	}
	
	public final String getNotification() {
		return this.notification;
	}
	
	public Map<String, Object> getParams() {
		return this.params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
