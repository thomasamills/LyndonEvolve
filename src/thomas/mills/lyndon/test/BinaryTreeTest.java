package thomas.mills.lyndon.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.datastructures.BinaryTree;
import thomas.mills.lyndon.datastructures.OrderingFitnessValue;

public class BinaryTreeTest {

	private BinaryTree binary_tree;
	@Before
	public void setUp() {
		binary_tree = new BinaryTree();
		//expected to become root
		OrderingFitnessValue ofs = new OrderingFitnessValue("root".toCharArray(),5);
		binary_tree.add(ofs);
				
	    //expected to become right child of root
		OrderingFitnessValue ofs_right = new OrderingFitnessValue(
		"right".toCharArray(),10);
		binary_tree.add(ofs_right);
				
	    //expected to become left child of root
	    OrderingFitnessValue ofs_left = new OrderingFitnessValue(
		"left".toCharArray(),4);
	    binary_tree.add(ofs_left);
				
		//expect to become left of left of root
	    OrderingFitnessValue ofs_left_left = new OrderingFitnessValue(
		"left_left".toCharArray(),3);
	    binary_tree.add(ofs_left_left);
				
		//expected to become right of right of root
		OrderingFitnessValue ofs_right_right = new OrderingFitnessValue(
		"right_right".toCharArray(),15);
		binary_tree.add(ofs_right_right);
	}
	/**
	 * testAddition checks to see that the orderings added in the setup
	 * have been added in the correct positions in the binary tree. 
	 */

	@Test
	public void testAddition() {
		//Check tree is built as expected
		if(String.valueOf(binary_tree.getRoot().getOrdering()).equals("root")) {
			if(String.valueOf(binary_tree.getRoot()
					.getRight_child()
					.getOrdering())
					.equals("right")) {
				
				if(!String.valueOf(binary_tree.getRoot()
						.getRight_child()
						.getRight_child()
						.getOrdering())
						.equals("right_right")) {
					assertTrue(false);
				}
			}
			else {
				assertTrue(false);
			}
			if(String.valueOf(binary_tree.getRoot()
					.getLeft_child()
					.getOrdering())
					.equals("left")) {
				
				if(!String.valueOf(binary_tree.getRoot()
						.getLeft_child()
						.getLeft_child()
						.getOrdering()
						).equals("left_left")) {
					assertTrue(false);
				}
			}else {
				assertTrue(false);
			}
		}
		else {
			assertTrue(false);
		}
		
		assertTrue(true);
		
	}

}
