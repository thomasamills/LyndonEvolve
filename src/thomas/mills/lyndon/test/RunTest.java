package thomas.mills.lyndon.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.Population;
import thomas.mills.lyndon.Run;

public class RunTest {
	
	private Run run;
	private Population p;
	private ArrayList<char[]> half_pop;
	
	@Before 
	public void setUp() {
		run = new Run();
		p = new Population("abcdefg",10);
		half_pop = new ArrayList<char[]>();
		for(int i = 0; i< p.getPopulationSize()/2; i++) {
			half_pop.add(p.getPopulation().get(i));
		}
	}

	@Test
	public void testFindAlphabetFromString() {
		String input = "aaabbbbcccccddddeeeffffggg";
		String alphabet = Run.findAlphabetFromString(input);
		assertEquals("abcdefg",alphabet);
		
		input = "qqqwwweeeeqqqwwrrrrrwwwwrrtttyuuwwqwqtwrwqwiwqrwop";
		alphabet = "qwertyuiop";
		alphabet = Run.findAlphabetFromString(input);
		assertEquals("qwertyuiop",alphabet);
	}
	
	@Test
	public void testRecreatePopulationSameSize() {
		boolean bool = true;
		for(int i = 0; i < 1000; i++) {
		    run.recreatePopulation(half_pop,"abcdefg",p);
		    if(p.getPopulationSize() !=10) {
			    bool = false;
		    }
		}
		assertTrue(bool);
		
	}
	
	@Test
	public void testReverse() {
		String x = "abcdefg";
		assertEquals(String.valueOf(run.reverse(x)),"gfedcba");
		x = "ijewofkwe";
		assertEquals(String.valueOf(run.reverse(x)),"ewkfoweji");
	}

}
