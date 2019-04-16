package thomas.mills.lyndon.datastructures;

public class DoubleEndedPQ extends BinaryTree {
	
	public DoubleEndedPQ() {
		super();
	}
	
	public OrderingFitnessValue getMost() {
		if(getRoot() == null)	{
			return null;
		}
		return getMost(getRoot(),null);
	}
	
	
	public OrderingFitnessValue getMost(OrderingFitnessValue index, OrderingFitnessValue parent) {
		if(index.getRight_child()==null) {
			if(index.getLeft_child()!=null) {
				if(parent == null) {
					setRoot(index.getLeft_child());
					return index;
				}
				parent.setRight_child(index.getLeft_child());
				
				return index;
			}
			if(parent == null) {
				setRoot(null);
			}
			parent.setRight_child(null);
			return index;
			
		}else {
			return getMost(index.getRight_child(),index);
		}
	}

	public OrderingFitnessValue getLeast() {
		if(getRoot() == null) {
			return null;
		}
		return getLeast(getRoot(),null);
	}
	
	public OrderingFitnessValue getLeast(OrderingFitnessValue index, OrderingFitnessValue parent) {
		if(index.getLeft_child()==null) {
			if(index.getRight_child()!=null) {
				if(parent == null) {
					setRoot(index.getRight_child());
					return index;
				}
				parent.setLeft_child(index.getRight_child());
				
				return index;
			}
			if(parent == null) {
				setRoot(null);
			}
			parent.setLeft_child(null);
			return index;
			
		}else {
			return getLeast(index.getLeft_child(),index);
		}
	}
	
	
	

}
