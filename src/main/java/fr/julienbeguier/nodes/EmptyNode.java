package fr.julienbeguier.nodes;

import fr.julienbeguier.utils.Constants;


public class EmptyNode extends AbstractNode {

	public EmptyNode() {
		super(NodeType.EmptyNode, Constants.NODE_EMPTY);
	}

	@Override
	public boolean isEmptyNode() {
		return true;
	}
}
