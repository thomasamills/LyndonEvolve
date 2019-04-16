package thomas.mills.lyndon.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import thomas.mills.lyndon.FitnessTest;
import thomas.mills.lyndon.Population;
import thomas.mills.lyndon.factorization.Duval;
import thomas.mills.lyndon.factorization.FactorVariationCalculator;

public class FitnessTestTest {
	
	private Population p;
	private String input,alphabet;
	private FactorVariationCalculator fvc = new FactorVariationCalculator(); // run test for this class prior
	
	@Before
	public void setUp() {
		input = "jababababiideecccjjiikbiicdfffeffffghiccjk";
		alphabet = "abcdefghijk";
		p = new Population(alphabet,10);
	}

	@Test	
	public void testFitnessReturnsCorrectSizeMinimal() {
		FitnessTest fitness = new FitnessTest(false);
		boolean bool = true;
		for(int i = 0; i < 1; i++) {
		    int size = fitness.testFitness(p, input.toCharArray()
				    , 0, -1, "").size();
		    if(size != p.getPopulationSize()/2){
		    		bool =false;
		    }
		    if(size == 1) { //for times where it exits with optimal solution (one factor)
		    	bool = true;
		    }
		    p.generateRandomPopulation();
		}
		assertTrue(bool);
	
	}
	
	@Test
	public void testFitnessReturnsInOrderMinimal() {
		Duval duval = new Duval();//assuming duval works
		FitnessTest fitness = new FitnessTest(false);
		boolean bool =true;
		for(int i = 0; i < 100; i++) {
		    ArrayList<char[]> outcome = fitness.testFitness(p, input.toCharArray()
				    , 0, -1, "");
		    ArrayList<Integer> duval_scores = new ArrayList<Integer>();
		    for(char[] ca: outcome) {
		        int size = duval.factor(ca, input.toCharArray()).size();
		    		duval_scores.add(size);
		    }
		    for(int j = 0; j <= duval_scores.size()-2; j++) {
		    	if(duval_scores.get(j) > duval_scores.get(j+1)) {
		    		bool = false;
		    	}
		    }
		p.generateRandomPopulation();
		}
		assertTrue(bool);	
	}

	
	@Test
	public void testFitnessReturnsCorrectSizeMaximal() {
		FitnessTest fitness = new FitnessTest(false);
		boolean bool =true;
		for(int i = 0; i < 100; i++) {
		    ArrayList<char[]> outcome = fitness.testFitness(p, input.toCharArray()
				    , 1, -1, "");
		    if(outcome.size() !=p.getPopulationSize()/2 && outcome.size() !=1){
		    		bool =false;
		    }
		  
		    p.generateRandomPopulation();
		}
		assertTrue(bool);
	
	}
	
	@Test
	public void testFitnessReturnsInOrderMaximal() {
		Duval duval = new Duval();//assuming duval works
		FitnessTest fitness = new FitnessTest(false);
		boolean bool =true;
		for(int i = 0; i < 100; i++) {
		    ArrayList<char[]> outcome = fitness.testFitness(p, input.toCharArray()
				    , 1, -1, "");
		    ArrayList<Integer> duval_scores = new ArrayList<Integer>();
		    for(char[] ca: outcome) {
		        int size = duval.factor(ca, input.toCharArray()).size();
		    		duval_scores.add(size);
		    }
		    for(int j = 0; j <= duval_scores.size()-2; j++) {
		    	if(duval_scores.get(j) < duval_scores.get(j+1)) {
		    		bool = false;
		    	}
		    }
		p.generateRandomPopulation();
		}
		assertTrue(bool);
	}
	
	
	@Test
	public void testFitnessReturnsCorrectSizeSpecific() {
		int ps = p.getPopulationSize();
		FitnessTest fitness = new FitnessTest(false);
		boolean bool =true;
		for(int i = 0; i < 100; i++) {
		    ArrayList<char[]> outcome = fitness.testFitness(p, input.toCharArray()
				    , 3, 10, "");
		    if(outcome.size() != ps/2 && outcome.size() != 1){
		    		bool =false;
		    }	  
		    p.generateRandomPopulation();
		}
		assertTrue(bool);	
	}
	
	@Test
	public void testFitnessReturnsCorrectSizeBalanced() {
		int ps = p.getPopulationSize();
		FitnessTest fitness = new FitnessTest(false);
		boolean bool =true;
		for(int i = 0; i < 100; i++) {
		    ArrayList<char[]> outcome = fitness.testFitness(p, input.toCharArray()
				    , 2, 10,"");
		    if(outcome.size() != ps/2 && outcome.size() != 1){
		    		bool =false;
		    }	  
		    p.generateRandomPopulation();
		}
		assertTrue(bool);	
	}
	
	@Test
	public void testFitnessReturnsInOrderSpecific() {
		Duval duval = new Duval();//assuming duval works
		FitnessTest fitness = new FitnessTest(false);
		boolean bool =true;
		for(int i = 0; i < 100; i++) {
		    ArrayList<char[]> outcome = fitness.testFitness(p, input.toCharArray()
				    , 3, 10,"");
		    ArrayList<Integer> duval_scores = new ArrayList<Integer>();
		    for(char[] ca: outcome) {
		        int size = duval.factor(ca, input.toCharArray()).size();
		    		duval_scores.add(size);
		    }
		    //desired =
		    for(int j = 0; j <= duval_scores.size()-2; j++) {
		    	if(calculateDistance(duval_scores.get(j),10) >
		    			calculateDistance(duval_scores.get(j+1),10)) {
		    		bool = false;
		    	}
		    }
		    
		p.generateRandomPopulation();
		}
		assertTrue(bool);	
	}
	
	@Test
	public void testFitnessReturnsInOrderBalanced() {
		Duval duval = new Duval();//assuming duval works
		FitnessTest fitness = new FitnessTest(false);
		boolean bool =true;
		for(int i = 0; i < 100; i++) {
		    ArrayList<char[]> outcome = fitness.testFitness(p, input.toCharArray()
				    , 2, 10,"");
		    int count = 0;
		    for(char[] ca: outcome) {
		    	if(count != outcome.size()-1 && outcome.size()!=1) {
		    		ArrayList<String> factors = duval.factor(ca, input.toCharArray());
			    	ArrayList<String> factors2 = duval.factor(outcome.get(count+1), input.toCharArray());
			    	double f1_variance = fvc.calculate(factors);
			    	double f2_variance = fvc.calculate(factors2);
			    	if(f1_variance == 0) {
			    		f1_variance = 999999999;
			    	}
			    	if(f2_variance == 0) {
			    		f2_variance = 999999999;
			    	}
			    	if(f1_variance > f2_variance) {
			    		System.out.println("here " + f1_variance + " " + f2_variance);
			    		bool = false;
			    	}
		    	}
		    	count++;
		    }
		  
		p.generateRandomPopulation();
		}
		assertTrue(bool);	
	}
	
	public int calculateDistance(int desired_factors,int factors) {
		//calculates distance from two numbers, makes sure is positive.
		if(desired_factors > factors ) {
			return desired_factors-factors;
		}
		else {
			return factors-desired_factors;
		}
	}

}
