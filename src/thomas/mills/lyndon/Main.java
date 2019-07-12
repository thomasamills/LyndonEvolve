package thomas.mills.lyndon;
import thomas.mills.lyndon.GreedyAlgorithm.*;
import thomas.mills.lyndon.proteintest.TestProteinSequences;

import java.util.Map;

public class Main  {

	public static void main(String[] args) {
		String paperSequence = "aabdcaacdaabdbabaabcaacaacab";

		// Thomas program
		TestProteinSequences tps = new TestProteinSequences();
		ReadFastaToString readFastaToString = new ReadFastaToString();
		Map<String, String> sequences = readFastaToString.read("GCF_000009605.1_ASM960v1_protein.faa");
		System.out.println(sequences.size());
		//tps.LexoFactors();
		System.out.println(tps.minimalFactors("frandom").size());
		//tps.minimalFactors("srandom");
		//tps.minimalFactors("greedy");
		/*
		tps.balancedFactors();
		tps.balancedEvolvedFactors();
		*/

		// My test program for greedy algorithm
		/*
		Map<String, String> sequence = new ReadFastaToString().read(System.getProperty("user.dir") + "\\GCF_000009605.1_ASM960v1_protein.faa");
		System.out.println(sequence.values());
		AlphabetReorderingAlg.reorderSequence(sequence, false);
		*/

	}
}