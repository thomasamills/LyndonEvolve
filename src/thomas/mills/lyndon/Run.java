package thomas.mills.lyndon;
import java.util.ArrayList;
import java.util.Random;

import thomas.mills.lyndon.factorization.Duval;
import thomas.mills.lyndon.factorization.Factorization;

public class Run  {

	private Population population; 
	private Mutator mutator;
	private PMX pmx;
	private FitnessTest fitness;
	private CsvFileWriter extractor;
	private int pop_size; //population size
	private Random rand; //for random numbers
	private int max_generation_count; 
	private int min_generation_count;
	private int bal_generation_count;
	private int spec_generation_count;
	private boolean print; //decides to print the generations to the console
	private boolean double_sided_pmx; //turns on the double sided pmx
	private double swath_limit;	 // sets the swath limit
	private boolean use_mutation; //allows the use of mutation
	private int mutation_rate; //sets the mutation rate
	private int crossover_type;
	
	public Run() {
       	rand = new Random();
		//SETTINGS
		pop_size =24;
		max_generation_count=200;
		min_generation_count=70;
		spec_generation_count =200;
		bal_generation_count = 200;
		crossover_type = 0;
		double_sided_pmx =false; // change to double sided pmx 
		swath_limit = 0.6;
		use_mutation = true;
		mutation_rate = 9; // 100%
		print = false;
        //FITNESS TEST
		fitness = new FitnessTest(print);
		//GENETIC OPERATORS
		mutator = new Mutations();
		if(crossover_type == 0)
		    pmx = new PartiallyMappedCrossover();

	}	
	
	public static String findAlphabetFromString(String input) {
		ArrayList<Character> uniques = new ArrayList<Character>();
		for(char c: input.toCharArray()) {
			if(!uniques.contains(c)) {
				uniques.add(c);
			}
		}
		StringBuilder sb = new StringBuilder(uniques.size());
	    for(char c: uniques){
	        sb.append(c);
	    }
	    return sb.toString();
	}
		
	/**
	 * run(String input, int opt_type, int desired_factors) is the actual method of the Genetic algorithm, 
	 * this is where the method is called. 
	 * @param input The string to be factorized
	 * @param opt_type (0 for minimization, 1 for maximization, 2 for balanced and 3 for specific)
	 * @param desired_factors (the amount of desired factors, this is ignored if not using a specific evolution)
	 * @return The optimal ordering for the evolution type chosen.
	 */
	public char[] run(String input, int opt_type, int desired_factors, String stringId, String option) {
		//It first finds the alphabet from the input string
		//Asserts that the alphabet must be larger than 3
		String alphabet = findAlphabetFromString(input);
		if(alphabet.length() <= 3) {
			throw new IllegalArgumentException("Alphabet Must Be Larger Than 3");
		}
		//if the optimization type is either minimization or maximization
		if(opt_type == 0) { // TODO: check
			//it will attempt to calculate if it can factorize into one factor using the original ordering. 
			Factorization duval = new Duval();
			int factors = duval.factor(alphabet.toCharArray(), input.toCharArray()).size();
			//if it can factor minimally (we cannot split further, therefore there is no point in running the genetic algorithm)
			if(factors == 1) {
				//if it is minimization, we return this optimal ordering
				return alphabet.toCharArray();
			}
		}
		//This will be ran we could not factor using the simple rule
		//A population set will be created
		if(option.toLowerCase().equals("greedy")){
			population = new Population(alphabet, input, pop_size);
		}else {
			population = new Population(alphabet, pop_size, option);
		}
		//The ordering will then be gathered by calling the evolve function passing in the required parameters.

		return evolve(input,alphabet,opt_type,desired_factors,stringId);
	}
	
	/**
	 * Reverse takes in the original alphabet and re-orders under the assumption 
	 * that the heuristic is that it will maximize it. 
	 * @param input
	 * @return
	 */
	public char[] reverse(String input) {
        StringBuilder sb = new StringBuilder();
        
        for(int i = input.length() - 1; i >= 0; i--)
        {
            sb.append(input.charAt(i));
        }
        
        return sb.toString().toCharArray();
	}
	
	/** Evolve is the structure of the evolutionary algorithm, it is called by run if it cannot find the solution 
	 * in a simpler manner)
	 * @param input (input string)
	 * @param alphabet (alphabet)
	 * @param opt_type (optimization type)
	 * @param desired_factors (Desired number of factors if specific amount is needed)
	 * @return The optimal ordering for the specified enhancement type. 
	 */
	public char[] evolve(String input,String alphabet,int opt_type
			,int desired_factors, String stringId) {
		
		//To keep track of the max generations. 
		int generation_count = 1;
		//This uses the GLOBAL settings at the top of the run stage, this is where the maximum count is set. 
		int max_generations;
		if(opt_type == 0) {
			max_generations = min_generation_count;
		}else if(opt_type == 1) {
			max_generations = max_generation_count;
		}
		else if (opt_type == 2) {
			max_generations = bal_generation_count;
		}
		else {
			max_generations = spec_generation_count;
		}
		//Initializes new list to store a population
		ArrayList<char[]> tested_population = new ArrayList<char[]>();	
		//As long as we haven't reached the maximum amount.
		while(generation_count <= max_generations) {
			//If the printing variable is set, it will print out the generation number
			if(print)
			    System.out.println("Generation: " + generation_count);
			//tests the population based upon the desired output (it will return the 50% best candidate solutions)
			tested_population = fitness.testFitness(population, 
					input.toCharArray(), opt_type ,desired_factors, stringId);
			//If there is only one being returned, it must be the optimal (in the case if we have found the K input ordering, or 
			// one that can factor into two factors.
			if(tested_population.size() == 1) {
				this.printToCsv(opt_type, stringId);
				return tested_population.get(0);
			}
			
			//Otherwise, if we haven't found the most optimal solution yet we recreate the population.
			recreatePopulation(tested_population,alphabet,population);
			//increment the generation count
			generation_count++;
			if(print)
				   System.out.println("-----------------");
		}
		
		this.printToCsv(opt_type, stringId);
		
		
		//Once the required amount of generations has been fulfilled it will return the most optimal solution.
	    return population.getPopulation().get(0);
	}
	
	private void printToCsv(int opt_type, String stringId) {
		if(!fitness.getData().isEmpty()) {
			switch(opt_type) {
			case 0: 
				CsvFileWriter.writeCsvFile(stringId + "_" + "min" , stringId, fitness.getData());
				fitness.resetData();
				break;
			case 1: 
				CsvFileWriter.writeCsvFile(stringId + "_" + "max" , stringId,  fitness.getData());
				fitness.resetData();
				break;
			case 2: 
				CsvFileWriter.writeCsvFile(stringId + "_" + "bal" , stringId,  fitness.getData());
				fitness.resetData();
				break;
			}
		}
	}
	
	/**
	 * recreatePopulation is where the main recreation of the next generation is created. 
	 * It takes in the parameter of a list of character orderings (it does not know which type 
	 * of evolution we are doing at this moment in time, e.g. if it is max, min, balanced or for a k specific 
	 * input. 
	 * @param tested_population
	 * @param alphabet
	 * @param population
	 */
		
	public void recreatePopulation(ArrayList<char[]> tested_population,String alphabet,Population population) {
		//to store new input
		ArrayList<char[]> new_population = new ArrayList<char[]>();
		//as the fitness function only returns the 50% we can add this into the new population. 
	    for(char[] ca: tested_population) {
	    	new_population.add(ca);
	    }
	    //We then repopulate the remaining 50% of the population from selecting two random parents 
	    /// from the proportion of 50% best candidates
	    for(int i= 0; i < pop_size/2; i++) {
	    	int parent1 = rand.nextInt(tested_population.size() -1);
	    	int parent2 = rand.nextInt(tested_population.size() -1);
	    	if(parent1 == parent2) { //Makes sure that they are not the same parent
				if(parent1 == alphabet.length()-1) {
					parent1--;
				}
				else {
					parent1++;
				}			
			}
	    	char[]child;
	    	if(crossover_type == 0) { // If crossover type is PMX
	    	    child= pmx.pmx(tested_population.get(parent1 )
	    	    		,tested_population.get(parent2)
	    			    ,swath_limit,double_sided_pmx);
	    	
	    	if(use_mutation) { //if we chooose to use mutation
	    		if(rand.nextInt(10) < mutation_rate) { /*Mutation rate here is represented as an integer 
	    		from 0 to 10, representing 0.1, 0.2, 0.3......1.0 mutation rates. 
	    		*/
	    			// if mutation was used, this is added to the population
	            	new_population.add(mutator.performMutation(child)); 
	            }
	    		else {//if mutation was not used this will not be added to the population
	    			new_population.add(child); 
	    		}
	    	}
	    	else {//if mutation was not used this will not be added to the population
	    		new_population.add(child);
	    		
	    	}
	    	//Now sets the next population for the next generation. 
		    population.setPopulation(new_population);
	    }
	}
	}
}
