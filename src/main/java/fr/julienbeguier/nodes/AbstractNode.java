package fr.julienbeguier.nodes;

import java.net.URL;

public abstract class AbstractNode {
	
	private NodeType	type;
	private String		name;
	private String		category;
	private URL			feedUrl;
	
	public enum NodeType {
		Feed,
		Category,
		Root,
		EmptyNode;
	}

	public AbstractNode(NodeType type, String name) {
		this.type = type;
		this.name = name;
		this.category = null;
		this.feedUrl = null;
	}
	
	public NodeType getNodeType() {
		return this.type;
	}
	
	public void setNodeType(NodeType type) {
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public URL getFeedUrl() {
		return this.feedUrl;
	}

	public void setFeedUrl(URL url) {
		this.feedUrl = url;
	}
	
	public abstract boolean	isEmptyNode();
	
	@Override
	public String toString() {
		return this.name;
	}
}
