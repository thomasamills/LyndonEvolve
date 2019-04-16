package thomas.mills.lyndon.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.Mutations;

public class MutationsTest {
	
	private char[] to_mutate;
	private Mutations mutator = new Mutations();
	private Random rand = new Random();
	
	@Before 
	public void setUp() {
		to_mutate = new char [] {'a','b','c','d','e','f','g','h','i'};
	}

	/**
	 * Tests whether the generated selection points to mutate 
	 * are within range of the alphabet ordering. 
	 */
	
	@Test
	public void testSelectRandomPointsAreWithinRange() {
		boolean bool = true;
		for(int i=0; i<1000;i++) {
		    int [] selection_points = mutator
		    		.randomSelectionPoints(to_mutate);
		    if(selection_points[0] > to_mutate.length-1
		    		|| selection_points[0] < 0) {
		    	bool = false;
		    }
		    if(selection_points[1] > to_mutate.length-1
		    		|| selection_points[1] < 0) {
		    	bool = false;
		    }
		}
		assertTrue(bool);
	}
	
	/**
	 * Tests whether the generated selection points to mutate 
	 * are unique so a mutation can occur. 
	 */
	@Test
	public void testSelectRandomPointsAreNotUnique() {
		boolean bool = true;
		for(int i=0; i<1000;i++) {
		    int [] selection_points = mutator
		    		.randomSelectionPoints(to_mutate);
		    if(selection_points[0] == selection_points[1]) {
		    	bool = false;
		    }
		}
		assertTrue(bool);
	}
	
	@Test 
	public void testRandSwap() {
		char[] correct_output = "abgdefchi".toCharArray();
		int[] selection_points = new int [] {2,6};
		char[] actual_output = mutator.randSwap(to_mutate, selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));
		
		to_mutate = new char [] {'a','b','c','d','e','f','g','h','i'};	
		correct_output = "fbcdeaghi".toCharArray();
		selection_points = new int [] {5,0};
		actual_output = mutator.randSwap(to_mutate, selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));
		
        to_mutate = new char [] {'a','b','c','d','e','f','g','h','i'};		
		correct_output = "ibcdefgha".toCharArray();
		selection_points = new int [] {0,8};
		actual_output = mutator.randSwap(to_mutate, selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));
		
		to_mutate = new char [] {'a','b','c','d','e','f','g','h','i'};		
		correct_output = "aecdbfghi".toCharArray();
		selection_points = new int [] {1,4};
		actual_output = mutator.randSwap(to_mutate,selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));	
	}
	
	@Test
	public void testRandSwapOutputsCorrectLength() {
		int limit = to_mutate.length - 1;
		int mutate_length = to_mutate.length;
		for(int i = 0;i < 1000; i++) {
			int [] selection_points = new int[2];	
			for(int p = 0; p < 2; p++) {	
				selection_points[p] = rand.nextInt(limit);		
			}		
			int length = mutator.randSwap(to_mutate, selection_points).length;
			assertEquals(mutate_length,length);
		}
	}
	
	@Test
	public void testRandSwapOutputsUniqueSet() {
		int limit = to_mutate.length - 1;
		boolean bool = true;
		for(int i = 0;i < 1000; i++) {
			int [] selection_points = new int[2];	
			for(int p = 0; p < 2; p++) {	
				selection_points[p] = rand.nextInt(limit);		
			}		
		
			char[] mutated = mutator.randSwap(to_mutate, selection_points);
		    for(int j = 0; i < mutated.length; i++) {	        
		    	for(int k = j + 1; k < mutated.length; k++) {          
		    		if(mutated[j] == mutated[k]) {
	                    bool = false;
	                }
	            }
	        }
		}
		assertTrue(bool);
		
	}
	
	@Test
	public void testRandShift(){
		char[] correct_output = "abdefcghi".toCharArray();
		int[] selection_points = new int [] {2,5};
		char[] actual_output = mutator.randShift(to_mutate, selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));
		
		to_mutate = new char [] {'a','b','c','d','e','f','g','h','i'};	
		correct_output = "bcdefghai".toCharArray();
		selection_points = new int [] {0,7};
		actual_output = mutator.randShift(to_mutate, selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));
		
        to_mutate = new char [] {'a','b','c','d','e','f','g','h','i'};		
		correct_output = "dabcefghi".toCharArray();
		selection_points = new int [] {3,0};
		actual_output = mutator.randShift(to_mutate, selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));
		
		to_mutate = new char [] {'a','b','c','d','e','f','g','h','i'};		
		correct_output = "ahbcdefgi".toCharArray();
		selection_points = new int [] {7,1};
		actual_output = mutator.randShift(to_mutate,selection_points);
		assertEquals(String.valueOf(correct_output)
				,String.valueOf(actual_output));	
	}
	
	@Test
	public void testRandShiftOutputsCorrectLength() {
		int limit = to_mutate.length - 1;
		int mutate_length = to_mutate.length;
		for(int i = 0;i < 1000; i++) {
			int [] selection_points = new int[2];	
			for(int p = 0; p < 2; p++) {	
				selection_points[p] = rand.nextInt(limit);		
			}		
			int length = mutator.randShift(to_mutate, selection_points).length;
			assertEquals(mutate_length,length);
		}
	}
	
	@Test
	public void testRandShiftOutputsUniqueSet() {
		int limit = to_mutate.length - 1;
		boolean bool = true;
		for(int i = 0;i < 1000; i++) {
			int [] selection_points = new int[2];	
			for(int p = 0; p < 2; p++) {	
				selection_points[p] = rand.nextInt(limit);		
			}		
		
			char[] mutated = mutator.randShift(to_mutate,selection_points);
		    for(int j = 0; i < mutated.length; i++) {	        
		    	for(int k = j + 1; k < mutated.length; k++) {          
		    		if(mutated[j] == mutated[k]) {
	                    bool = false;
	                }
	            }
	        }
		}
		assertTrue(bool);
		
	}


}
