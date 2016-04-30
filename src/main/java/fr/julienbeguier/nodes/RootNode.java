package fr.julienbeguier.nodes;

import fr.julienbeguier.utils.Constants;

public class RootNode extends AbstractNode {

	public RootNode() {
		super(NodeType.Root, Constants.NODE_ROOT);
	}

	@Override
	public boolean isEmptyNode() {
		return false;
	}
}
