package thomas.mills.lyndon.datastructures;

/*
 * Class to store an alphabet ordering (not unique) with 
 * the number of factors it generates (not unique)
 */
public class OrderingFitnessValue {

	private char[] ordering;
	private double value;
	
	private OrderingFitnessValue left_child;
	private OrderingFitnessValue right_child;
	
	public OrderingFitnessValue(char[] ordering, double fitness) {
		this.ordering = ordering;
		this.value = fitness;
	}	
	
	public char[] getOrdering() {
		return ordering;
	}
	
	public void setOrdering(char[] ordering) {
		this.ordering = ordering;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public OrderingFitnessValue getLeft_child() {
		return left_child;
	}


	public void setLeft_child(OrderingFitnessValue left_child) {
		this.left_child = left_child;
	}


	public OrderingFitnessValue getRight_child() {
		return right_child;
	}


	public void setRight_child(OrderingFitnessValue right_child) {
		this.right_child = right_child;
	}
}
