package fr.julienbeguier.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RssData {

	private List<String>				categories;
	private List<String>				feeds;
	private Map<String, RssCategory>	rssCategories;
	
	public RssData() {
		this.categories = new ArrayList<String>();
		this.feeds = new ArrayList<String>();
		this.rssCategories = new HashMap<String, RssCategory>();
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<String> feeds) {
		this.feeds = feeds;
	}

	public Map<String, RssCategory> getRssCategories() {
		return rssCategories;
	}

	public void setRssCategories(Map<String, RssCategory> rssCategories) {
		this.rssCategories = rssCategories;
	}
}
