package thomas.mills.lyndon.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.PartiallyMappedCrossover;

public class PMXTest {
	
	private PartiallyMappedCrossover pmx = new PartiallyMappedCrossover();
	private char[] p1;
	private char[] p2;
	private double limit = 0.6;

	@Before 
	public void setUp() {
		 p1 = "abcdefghi".toCharArray();
		 p2 = "gefbhdiac".toCharArray();
	}

	@Test
	public void testGetSwathLowerOrder() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 0, limit);
			if(swath_points[0]!=0) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	@Test
	public void testGetSwathLowerOrderWithinLimit() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 0, limit);
			if(swath_points[1] > (int)(p1.length*limit)) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	@Test
	public void testGetSwathLowerOrderIsUnique() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 0, limit);
			if(swath_points[1] == swath_points[0]) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	@Test
	public void testGetSwathHigherOrder() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 1, limit);
			if(swath_points[0]!=p1.length-1) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	@Test
	public void testGetSwathHigherOrderWithinLimit() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 1, limit);
			if(swath_points[1] < ((p1.length-2)- ((int)(limit*p1.length-1)))) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	@Test
	public void testGetSwathHigherOrderIsUnique() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 1, limit);
			if(swath_points[0]==swath_points[1]) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	@Test
	public void testGetSwathRandomOrderWithinRange() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 2, limit);
			if(!(swath_points[0]>=0&&swath_points[0]<p1.length
					&&swath_points[1]>=0&&swath_points[1]<p1.length)) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	@Test
	public void testGetSwathRandomOrderIsUnique() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
			int [] swath_points = pmx.getSwathPoints(p1, p2, 2, limit);
			if(swath_points[0]==swath_points[1]) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	
	@Test 
	public void testPlaceInNextBestEmpty() {	
		//test 1
		char[] child = new char[8];
		ArrayList<Integer> empties = new ArrayList<Integer>();
        empties.add(1);
		empties.add(4);
		empties.add(5);
		child[0] = 'a';
		child[2] = 'c';
		child[3] = 'd';
		child[6] = 'g';
		child[7] = 'h';
		pmx.placeInNextBestEmpty( child, 'b', empties);
		pmx.placeInNextBestEmpty( child, 'e', empties);
		pmx.placeInNextBestEmpty( child, 'f', empties);
		assertEquals("abcdefgh",String.valueOf(child));
		
		//test 2
		child = new char[8];
		empties = new ArrayList<Integer>();
        empties.add(2);
        empties.add(4);
		empties.add(6);
		empties.add(7);
		child[0] = 'a';
		child[1] = 'b';
		child[3] = 'd';
		child[5] = 'f';
		pmx.placeInNextBestEmpty(child, 'c', empties);
		pmx.placeInNextBestEmpty(child, 'e', empties);
		pmx.placeInNextBestEmpty(child, 'g', empties);
		pmx.placeInNextBestEmpty(child, 'h', empties);
		assertEquals("abcdefgh",String.valueOf(child));		
	}
	
	@Test 
	public void testFillEmpties() {
		char[] child = new char[8];
		ArrayList<Integer> empties = new ArrayList<Integer>();
        empties.add(1);
		empties.add(4);
		empties.add(5);
		child[0] = 'a';
		child[2] = 'c';
		child[3] = 'd';
		child[6] = 'g';
		child[7] = 'h';
		char[] p2 = "abcdefgh".toCharArray();
		pmx.fillEmpties(child, p2, empties);
		assertEquals("abcdefgh",String.valueOf(child));
		
		//reset for next test case
		child = new char[8];
		empties = new ArrayList<Integer>();
        empties.add(2);
        empties.add(4);
		empties.add(6);
		empties.add(7);
		child[0] = 'a';
		child[1] = 'b';
		child[3] = 'd';
		child[5] = 'f';
		pmx.fillEmpties(child, p2, empties);
	}
	
	//tests that swath points are in order for both double sided and random. 
	@Test 
	public void testPMXSwathPointsInOrderBothCases() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) { // RANDOM PMX
			int[] sorted_swath_points = pmx.sortSwathPoints(p1, p2, 0.6, false);
			if(sorted_swath_points[0] > sorted_swath_points[1]) {
				bool = false;
			}
		}
		for(int i = 0; i < 1000; i++) { //DOUBLE SIDED PMX
			int[] sorted_swath_points = pmx.sortSwathPoints(p1, p2, 0.6, true);
			if(sorted_swath_points[0] > sorted_swath_points[1]) {
				bool = false;
			}
		}
		assertTrue(bool);
	}

	
	@Test
	public void testPMX() {
		/* P1:abcdefghi
		 * P2:gefbhdiac
		 * swath start = 2 swath end = 3
		 * 
		 * child step 1 (copy swath from p1)
		 * 
		 * __cd_____
		 * 
		 * Next locate elements from p2 in the swath that were not
		 * copied from child  (i_char)
		 *
		 *i: fb
		 * 
		 * Next locate elements in child where the positions if ichars were (jchars)
		 * 
		 * j: cd
		 * 
		 * Then loop through i chars to put them in the position occupied by j in p2 but in the child
		 * 
		 * i = f; j = c
		 * 
		 * __cd____f
		 * 
		 * i = b; j = d
		 * 
		 * __cd_b__f
		 * 
		 * Then fill remaining elements from p2
		 * 
		 * = gecdhbiaf
		 */
		char[] expected = "gecdhbiaf".toCharArray();
		char[] actual = pmx.pmx(p1,p2, new int[] {2,3});
		assertEquals(String.valueOf(expected),String.valueOf(actual));
		
		
		/*
		 * p1: abcdefghi
		 * p2: gefbhdiac
		 * 
		 * 
		 * swath size 1 - 7 
		 * 
		 * step 1 (copy child)
		 * 
		 * _bcdefgh_
		 * 
		 * ichars = ia
		 * jchars = gh
		 * 
		 * loop through them 
		 * i = i
		 * j = g
		 * find the position of j in p2, if this index is empty in child
		 * place i within this empty place,
		 * it is, so child = ibcdefh_
		 * 
		 * i = a
		 * j = h
		 * find the position of j in p2, if this index is empty in child
		 * place i within this empty place. 
		 * it isnt so check what is there instead = k_char
		 * 
		 * k = e
		 * place i in position of k in p_2
		 * if(empty) add k to remaining chars in p2. 
		 * 
		 * Fill remaining chars
		 * 
		 * child = ibcdefgha
		 */
		
		expected = "ibcdefgha".toCharArray();
		actual = pmx.pmx(p1,p2, new int[] {1,7});
		assertEquals(String.valueOf(expected),String.valueOf(actual));
		
		/*
		 * p1: abcdefghi
		 * p2: gefbhdiac
		 *     
		 * 
		 * swath size 0 - 4 
		 * 
		 * step 1 copy swath from p1 to child
		 * abcde____
		 * 
		 * step 2 find i, and j variables
		 * i:gf
		 * j:ac
		 * 
		 * step loop through i 
		 * i = g 
		 * j = a
		 * index of j in p2 is empty in child 
		 * place 'g' in position of 'a' in p2 
		 * child = abcde__g_
		 * 
		 * i = f
		 * j = c
		 * index of j in p2 is empty in child 
		 * place 'f' in position of 'c' in p2
		 * child = abcde__gf
		 * 
		 * fill remaining chars from p2
		 * child = abcdehigf
		 * 
		 */
		
		expected = "abcdehigf".toCharArray();
		actual = pmx.pmx(p1,p2, new int[] {0,4});
		assertEquals(String.valueOf(expected),String.valueOf(actual));
		
		/*
		 * p1: abcdefghi
		 * p2: gefbdhiac
		 * 
		 * swath size 
		 * 3 - 8 
		 * 
		 * step 1 copy swath from p1 to child 
		 * 
		 * ___defghi
		 * 
		 * step 2 find i and j variables 
		 * 
		 * i:bac
		 * j:dhi
		 * 
		 * loop through i and j to place i
		 * 
		 * i = b 
		 * j - d
		 * index of j in p2 is not empty in child 
		 * k = e
		 * place ichar in position of kchar in p2
		 * child = _b_defghi
		 * 
		 * i = a
		 * j = h
		 * index of j in p2 is not empty in child
		 * k = f
		 * place i in position of k in p2
		 * child = _badefghi
		 * 
		 * There is only one last place for remaining i character 
		 * 
		 * child = cbadefghi
		 * 
		 */
		expected = "cabdefghi".toCharArray();
		actual = pmx.pmx(p1,p2, new int[] {3,8});
		assertEquals(String.valueOf(expected),String.valueOf(actual));
	}

}
