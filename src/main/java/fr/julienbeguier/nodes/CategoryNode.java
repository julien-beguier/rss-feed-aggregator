package fr.julienbeguier.nodes;

public class CategoryNode extends AbstractNode {

	public CategoryNode(String name) {
		super(NodeType.Category, name);
	}

	@Override
	public boolean isEmptyNode() {
		return false;
	}
}
