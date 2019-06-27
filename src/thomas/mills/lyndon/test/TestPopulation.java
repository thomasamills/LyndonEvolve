package thomas.mills.lyndon.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.Population;

public class TestPopulation {
	
	private Population p;
    private String alphabet;
    private int population_size;
	
	@Before
	public void setUp() {		
		alphabet = "abc";
		population_size = 50;
		p = new Population(alphabet,population_size,"srandom");
	}

	@Test
	public void createdRightAmount() {
		if(p.getPopulation().size() == population_size) {
			assertTrue(true);
		}else {
			assertTrue(false);
		}
	}
	
	@Test 
	public void charArraysOfCorrectLength() {
		boolean bool = true;
		for(char[] ca: p.getPopulation()) {
			if(ca.length != alphabet.length()) {
				bool = false;
			}
		}
		assertTrue(bool);
	}
	
	/**
	 * testUnique loops through every ordering in the
	 * population and checks there are no duplicate 
	 * values.
	 */
	
	@Test
	public void testUnique() {
		boolean bool = true;
		for(char[] ca : p.getPopulation()) {
			for(int i = 0; i < ca.length;i++) {
		        for(int j = i+1; j < ca.length; j++) {
		            if(ca[i] == ca[j]) {
		                bool = false;
		            }
		        }
		    }
		}
		assertTrue(bool);
	}

}
