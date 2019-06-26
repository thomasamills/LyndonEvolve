package thomas.mills.lyndon;
import thomas.mills.lyndon.GreedyAlgorithm.AlphabetReorderingAlg;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
	
	private ArrayList<char[]> population;
	private String alphabet;
	private int population_size;
	
	public Population(String alphabet, int population_size, String option) {
		this.alphabet = alphabet;
	
		population = new ArrayList<>();
		
		this.population_size = population_size;
		if(option.toLowerCase().equals("greedy")){

        }
		else if(option.toLowerCase().equals("srandom")){
            generateRandomPopulation();
        }else if(option.toLowerCase().equals("frandom")){
		    generateFullyRandomPopulation();
        }else{
            System.err.println("Not a valid option when creating population.");
        }
	}

	public Population(String alphabet, int population_size){
	    this(alphabet,population_size, "");
    }

	public void generateFullyRandomPopulation(){
        ArrayList<Character> list = new ArrayList<Character>();
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
		ArrayList<Character> list = new ArrayList<Character>();
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
        //population.add(AlphabetReorderingAlg.reor); TODO: Find a way of retrieving the ordering result of the algorithm, from mapping prob.
        for(int i = 1; i < population_size; i++){
            Collections.shuffle();
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
