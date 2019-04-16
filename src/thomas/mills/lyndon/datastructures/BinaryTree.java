package thomas.mills.lyndon.datastructures;


public class BinaryTree {
	
	private OrderingFitnessValue root;
	
	public BinaryTree() {
		root = null;
	}
	
	public void add(OrderingFitnessValue node) {
		if(root == null) {
			root = node;
		}else {
			add(root,node);
		}
	}
	
	public void add(OrderingFitnessValue index, OrderingFitnessValue node) {
		if(node.getValue() <= index.getValue()) {
			if(index.getLeft_child() == null) {
				index.setLeft_child(node);
			}
			else {
				add(index.getLeft_child(), node);
			}
		}else {
			if(index.getRight_child() == null) {
				index.setRight_child(node);
			}
			else {
				add(index.getRight_child(),node);
			}
		}
	}

	
	public OrderingFitnessValue getRoot() {
		return root;
	}
	
	public void setRoot(OrderingFitnessValue root) {
		this.root = root;
	}
	
}
