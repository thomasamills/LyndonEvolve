package thomas.mills.lyndon.factorization;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class FactorVariationCalculator {
	
	public double calculate(ArrayList<String> factors) {
		int[] factor_lengths = getFactorLengths(factors);
	    return calculateStandardDeviation(factor_lengths);
	}
	
	public double calculateStandardDeviation(int[] factor_lengths) {
		double mu = calculateMean(factor_lengths);
		double x = 0;
	    for(double d :factor_lengths) {//converts to double
	        x += (d-mu)*(d-mu);
	    }
	    double variance = x/(factor_lengths.length);
	    return Math.sqrt(variance);
	}
	
	public double calculateMean(int[] factor_lengths) {
		double sum = IntStream.of(factor_lengths).sum();
		double n = factor_lengths.length;
		return sum/n;
	}
	
	public int[] getFactorLengths(ArrayList<String> factors) {
		int[] factor_lengths = new int[factors.size()];
		for(int i = 0; i < factors.size(); i++) {
			factor_lengths[i] = factors.get(i).length();
		}
		return factor_lengths;
	}
	
}
