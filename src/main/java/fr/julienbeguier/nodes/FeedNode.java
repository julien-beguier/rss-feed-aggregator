package fr.julienbeguier.nodes;

import java.net.URL;

public class FeedNode extends AbstractNode {

	public FeedNode(String name, String category, URL url) {
		super(NodeType.Feed, name);
		this.setCategory(category);
		this.setFeedUrl(url);
	}

	@Override
	public boolean isEmptyNode() {
		return false;
	}
}
