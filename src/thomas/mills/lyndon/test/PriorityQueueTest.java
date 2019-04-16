package thomas.mills.lyndon.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.datastructures.DoubleEndedPQ;
import thomas.mills.lyndon.datastructures.OrderingFitnessValue;

public class PriorityQueueTest {
	
	private DoubleEndedPQ pq;
	
	@Before
	public void setUp() {
		pq = new DoubleEndedPQ();
		//expected to become root
		OrderingFitnessValue ofs = new OrderingFitnessValue("root".toCharArray(),5);
		pq.add(ofs);
				
	    //expected to become right child of root
		OrderingFitnessValue ofs_right = new OrderingFitnessValue(
		"right".toCharArray(),10);
	    pq.add(ofs_right);
				
	    //expected to become left child of root
	    OrderingFitnessValue ofs_left = new OrderingFitnessValue(
		"left".toCharArray(),4);
	    pq.add(ofs_left);
				
		//expect to become left of left of root
	    OrderingFitnessValue ofs_left_left = new OrderingFitnessValue(
		"left_left".toCharArray(),3);
		pq.add(ofs_left_left);
				
		//expected to become right of right of root
		OrderingFitnessValue ofs_right_right = new OrderingFitnessValue(
		"right_right".toCharArray(),15);
		pq.add(ofs_right_right);
	}
	
	@Test 
	public void testGetLeast() {
		if(String.valueOf(pq.getLeast().getOrdering()).equals("left_left")) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetLeastDeletes() {
		pq.getLeast();
		if(pq.getRoot().getLeft_child().getLeft_child() == null) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}
	}
	
	@Test 
	public void testGetMost() {
		if(String.valueOf(pq.getMost().getOrdering()).equals("right_right")) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetMostDeletes() {
		pq.getMost();
		if(pq.getRoot().getRight_child().getRight_child() == null) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}
	}

}
