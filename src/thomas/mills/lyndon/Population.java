package thomas.mills.lyndon;
import thomas.mills.lyndon.GreedyAlgorithm.AlphabetReorderingAlg;
import thomas.mills.lyndon.GreedyAlgorithm.Mapping;
import java.util.ArrayList;
import java.util.Collections;

public class Population {
	
	private ArrayList<char[]> population;
	private String alphabet;
	private int population_size;

	/**
	 * Creates a population of an specific size and in a certain manner depending on the option
	 * @param alphabet the Alphabet to be ordered.
	 * @param population_size The size of the population.
	 * @param option "srandom" for semi-random population (first ordering of the population is the given alphabet in the given order), and "frandom" for a fully random population.
	 */
	public Population(String alphabet, int population_size, String option) {
		this.alphabet = alphabet;
	
		population = new ArrayList<>();
		
		this.population_size = population_size;
		if(option.toLowerCase().equals("srandom")){
            generateRandomPopulation();
        }else if(option.toLowerCase().equals("frandom")){
		    generateFullyRandomPopulation();
        }else{
            System.err.println("Not a valid option when creating population.");
        }
	}

	/**
	 * Creates a population using the Greedy Algorithm for the first ordering
	 * @param alphabet The alphabet to be ordered
	 * @param sequence The peptide sequence to process with the algorithm to get the first order.
	 * @param population_size The size of the population
	 */
	public Population(String alphabet, String sequence, int population_size){
		this.alphabet = alphabet;
		population = new ArrayList<>();
		this.population_size = population_size;

		generatePopulationGreedyAlg(sequence);
    }

	public void generateFullyRandomPopulation(){
        ArrayList<Character> list = new ArrayList<>();
        for(char c : alphabet.toCharArray()) {
            list.add(c);
        }
        for(int i = 0; i < population_size; i++	) {
            Collections.shuffle(list);
            StringBuilder sb = new StringBuilder(list.size());
            for (Character c : list) {
                sb.append(c);
            }
            String order = sb.toString();
            population.add(order.toCharArray());
        }
    }
	
	public void generateRandomPopulation() {
		ArrayList<Character> list = new ArrayList<>();
		for(char c : alphabet.toCharArray()) {
		    list.add(c);
		}
		for(int i = 0; i < population_size; i++	) {
			StringBuilder sb = new StringBuilder(list.size());
			for (Character c : list) {
			  sb.append(c);
			}
			String order = sb.toString();
			population.add(order.toCharArray());
			Collections.shuffle(list);
		}
	}

	public void generatePopulationGreedyAlg(String s) {
	    ArrayList<Character> list = new ArrayList<>();
	    for(char c : alphabet.toCharArray()){
	        list.add(c);
        }
		Mapping mapping = new Mapping();
		AlphabetReorderingAlg.reorder(s, mapping, false);
        population.add(mapping.asOrder());
        for(int i = 1; i < population_size; i++){
            Collections.shuffle(list);
            StringBuilder sb = new StringBuilder();
            for(Character c : list){
            	sb.append(c);
			}
            population.add(sb.toString().toCharArray());
        }
    }
	
	public ArrayList<char[]> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<char[]> population) {
		this.population = population;
	} 
	
	public int getPopulationSize() {
		return population_size;
	}
}
