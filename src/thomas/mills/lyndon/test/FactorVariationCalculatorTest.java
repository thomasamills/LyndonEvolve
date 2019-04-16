package thomas.mills.lyndon.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.factorization.FactorVariationCalculator;

public class FactorVariationCalculatorTest {

	private FactorVariationCalculator fvc;
	private int [] factor_lengths;
	
	@Before
	public void setUp() {
		fvc = new FactorVariationCalculator();
		factor_lengths = new int[] {2,5,5,35};
	}

	@Test
	public void testCalculateMean() {
		boolean bool = true;
		double mu = fvc.calculateMean(factor_lengths);
		if(mu != 11.75) {
			bool = false;
		}
		factor_lengths = new int[] {6,2,6,77,8,4,5,6};
		mu = fvc.calculateMean(factor_lengths);
		if(mu!=14.25) {
			bool = false;
		}
		factor_lengths = new int[] {-2,2,6,32,-1};
		mu = fvc.calculateMean(factor_lengths);
		if(mu!=7.4) {
			bool = false;
		}
		
		assertTrue(bool);
	}
	
	@Test 
	public void testCalculateStandardDeviation() {
		boolean bool = true;
		//{2,5,5,35};
		double variance = fvc.calculateStandardDeviation(factor_lengths);
		if (Math.abs(variance-1.0000) == 181.6875) { //4dp
			bool = false;
		}
		assertTrue(bool);
	}
	
	@Test 
	public void testCalculateFactorLengths() {
		ArrayList<String> factors = new ArrayList<String>();
		factors.add("a");
		factors.add("aa");
		factors.add("aaa");
		factors.add("aaaa");
		int[] f_lengths = fvc.getFactorLengths(factors);
		assertEquals(1,f_lengths[0]);
		assertEquals(2,f_lengths[1]);
		assertEquals(3,f_lengths[2]);
		assertEquals(4,f_lengths[3]);	
	}

}
