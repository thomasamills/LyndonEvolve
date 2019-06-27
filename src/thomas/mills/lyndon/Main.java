package thomas.mills.lyndon;
import thomas.mills.lyndon.GreedyAlgorithm.*;

import java.util.Map;

public class Main  {

	public static void main(String[] args) {
		String paperSequence = "aabdcaacdaabdbabaabcaacaacab";

		/* Thomas program
		TestProteinSequences tps =new TestProteinSequences();
		tps.LexoFactors();
		tps.minimalFactors();
		tps.maximalFactors();
		tps.balancedFactors();
		tps.balancedEvolvedFactors();
		*/

		/* My test program for greedy algorithm
		Map<String, String> sequence = new ReadFastaToString().read(System.getProperty("user.dir") + "\\GCF_000009605.1_ASM960v1_protein.faa");
		System.out.println(sequence.values());
		AlphabetReorderingAlg.reorderSequence(sequence, false);
		 */

		Mapping mapping = new Mapping();
		AlphabetReorderingAlg.reorder(paperSequence, mapping, false);
		System.out.println(mapping.asOrder());
	}
}