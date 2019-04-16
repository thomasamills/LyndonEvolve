package thomas.mills.lyndon;
import thomas.mills.lyndon.proteintest.TestProteinSequences;

public class Main  {

	public static void main(String[] args) {
		TestProteinSequences tps =new TestProteinSequences();
		tps.LexoFactors();
		tps.minimalFactors();
		tps.maximalFactors();
		tps.balancedFactors();
		tps.balancedEvolvedFactors();
	}
}
