package fr.julienbeguier.data;

import java.util.ArrayList;
import java.util.List;

public class RssCategory {

	private String			name;
	private List<String>	feeds;
	
	public RssCategory(String name) {
		this.name = name;
		this.feeds = new ArrayList<String>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getFeeds() {
		return this.feeds;
	}
	
	public void setFeeds(List<String> feeds) {
		this.feeds = feeds;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
