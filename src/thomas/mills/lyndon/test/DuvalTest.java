package thomas.mills.lyndon.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import thomas.mills.lyndon.factorization.Duval;

public class DuvalTest {
	
	private char[] ordering = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private Duval duval = new Duval();
	

	@Test
	public void testDuvalCheckOrder() {
		int i = duval.checkOrder(ordering, 'l', 'k');
		assertEquals(i,0);
		i = duval.checkOrder(ordering, 'l', 'l');
		assertEquals(i,-1);
		i = duval.checkOrder(ordering, 'k', 'l');
		assertEquals(i,1);
	}
	
	@Test
	public void testDuval() {
		char[] input = "thisexampleisnotlyndon".toCharArray();
		ArrayList<String> factors = duval.factor(ordering, input);
		assertEquals(factors.get(0),"t");
		assertEquals(factors.get(1),"his");
		assertEquals(factors.get(2),"ex");
		assertEquals(factors.get(3),"ampleisnotlyndon");
		
		input = "hellohellohello".toCharArray();
		factors = duval.factor(ordering, input);
		assertEquals(factors.get(0),"h");
		assertEquals(factors.get(1),"elloh");
		assertEquals(factors.get(2),"elloh");
		assertEquals(factors.get(3),"ello");
		
		ordering = "ACGT".toCharArray();
		input = "ACTGTGTGTGCCGTAAAGTTTTGAA".toCharArray();
		factors = duval.factor(ordering, input);
		assertEquals(factors.get(0),"ACTGTGTGTGCCGT");
		assertEquals(factors.get(1),"AAAGTTTTG");
		assertEquals(factors.get(2),"A");
		assertEquals(factors.get(3),"A");
		
		ordering = "ACGT".toCharArray();
		input = "GCATGATCGTAGCTGCATCAGCGGATGCTA".toCharArray();
		factors = duval.factor(ordering, input);
		assertEquals(factors.get(0),"G");
		assertEquals(factors.get(1),"C");
		assertEquals(factors.get(2),"ATG");
		assertEquals(factors.get(3),"ATCGT");
		assertEquals(factors.get(4),"AGCTGCATC");
		assertEquals(factors.get(5),"AGCGGATGCT");
		assertEquals(factors.get(6),"A");
		
		
	}

}
