package thomas.mills.lyndon;
import java.util.ArrayList;
import java.util.Collections;

public class Population {
	
	private ArrayList<char[]> population;
	private String alphabet;
	private int population_size;
	
	public Population(String alphabet, int population_size) {
		this.alphabet = alphabet;
	
		population = new ArrayList<char[]>();
		
		this.population_size = population_size;
		
		generateRandomPopulation();
		
		
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
