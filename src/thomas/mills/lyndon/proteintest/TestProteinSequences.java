package thomas.mills.lyndon.proteintest;

import java.util.ArrayList;
import java.util.Map;

import thomas.mills.lyndon.Run;
import thomas.mills.lyndon.factorization.Duval;
import thomas.mills.lyndon.factorization.FactorVariationCalculator;
import thomas.mills.lyndon.factorization.Factorization;

public class TestProteinSequences {
	//takes in the name of a data set (of fasta format)
	private static final String FILENAME = "GCF_000009605.1_ASM960v1_protein.faa";
	//to store sequences
	private Map<String,String> sequences;
	//object to read the fasta sequences to the string. 
	private ReadFastaToString rfts;
	//Runner to run the GA
	private Run run;
	//Duval to double check outputted solutions. 
	private Factorization duval;
	
	/**
	 * initializes everything
	 */
	public TestProteinSequences() {
		rfts = new ReadFastaToString();
		sequences = rfts.read(FILENAME);
		run = new Run();
		duval = new Duval();	
	}
	
	/**
	 * minimal Factors goes through the whole list of factors for a data set performs a minimum 
	 * evolution, to minimize the amount of factors. 
	 * @return An array List of factor sizes (used to become a summation in comparison to a data set for lexicographical)
	 */
	public ArrayList<Integer> minimalFactors(String option){
		ArrayList<Integer> scores = new ArrayList<>();
		int count = 0;
		for (Map.Entry<String, String> entry : sequences.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    scores.add(duval.factor(run.run(value, 0, -1, key, option), value.toCharArray()).size());
		}
        for(int i : scores) {
			count +=i;
		}
		System.out.println("Total Number of Factors after Performing a minimization on the whole set");
		System.out.println(count);
		return scores;
		
	}
	
	/** calculation of factorl lengths using normal alphabetical order */
	public ArrayList<Integer> LexoFactors(){
		ArrayList<Integer> scores = new ArrayList<Integer>();
		int count = 0;
		for (Map.Entry<String, String> entry : sequences.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
			char[] lexicographical = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			scores.add(duval.factor(lexicographical, value.toCharArray()).size());
		}
		//total amount of factors
		count=0;
		for(int i : scores) {
			
			count +=i;
		}
		System.out.println("Total Number of Factors Under Lexicographical Ordering");
		System.out.println(count);
		return null;
	}
	
	/**
	 * manimal Factors goes through the whole list of factors for a data set performs a maximum 
	 * evolution, to manimize the amount of factors. 
	 * @return An array List of factor sizes (used to become a summation in comparison to a data set for lexicographical)
	*/	/**
	 * manimal Factors goes through the whole list of factors for a data set performs a maximum 
	 * evolution, to manimize the amount of factors. 
	 * @return An array List of factor sizes (used to become a summation in comparison to a data set for lexicographical)
	*/
	public ArrayList<Integer> maximalFactors(String option){
		ArrayList<Integer> scores = new ArrayList<Integer>();
		int count = 0;
		for (Map.Entry<String, String> entry : sequences.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    scores.add(duval.factor(run.run(value, 1, -1, key, option), value.toCharArray()).size());
		}
		for(int i : scores) {
			count +=i;
		}
		System.out.println("Total Number of Factors after Performing a maximization on the whole set");
		System.out.println(count);
		return scores;
		
	}
	
	public ArrayList<Double> balancedFactors(){
		FactorVariationCalculator fvc = new FactorVariationCalculator();
		ArrayList<Double> scores = new ArrayList<Double>();
		for (Map.Entry<String, String> entry : sequences.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
			char[] lexicographpical = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			scores.add(fvc.calculate(duval.factor(lexicographpical, value.toCharArray())));
		}
		Double summation = 0.0;
		for(Double d: scores) {
			summation+=d;
		}
		System.out.println("Total average variance of the data set after calculating duval");
		System.out.println(summation / scores.size());
		return scores;
	}
	
	public ArrayList<Double> balancedEvolvedFactors(String option){
		FactorVariationCalculator fvc = new FactorVariationCalculator();
		ArrayList<Double> scores = new ArrayList<Double>();
		for (Map.Entry<String, String> entry : sequences.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		
			char[] lexicographpical = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			scores.add(fvc.calculate(duval.factor(run.run(value, 2, -1, key, option), value.toCharArray())));
		}
		Double summation = 0.0;
		for(Double d: scores) {
			summation+=d;
		}
		System.out.println("Total average variance of the data set after calculating duval");
		System.out.println(summation / scores.size());
		return scores;
	}
	
	

}
