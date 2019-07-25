package thomas.mills.lyndon;
import java.util.ArrayList;

import thomas.mills.lyndon.datastructures.DoubleEndedPQ;
import thomas.mills.lyndon.datastructures.OrderingFitnessValue;
import thomas.mills.lyndon.factorization.Duval;
import thomas.mills.lyndon.factorization.FactorVariationCalculator;
import thomas.mills.lyndon.factorization.Factorization;

/**
 * primary function test fitness 
 *   -return an ArrayList<char[]> of selected elements (to use operators on)
 *   -if exit criteria is selected 
 */

public class FitnessTest {
	
	//For factorization
	private Factorization factorization;
	private FactorVariationCalculator fvc;
	//for printing
	private boolean print = false;
	private Duval duv = new Duval(); //TODO: Delete?
	private CsvFileWriter file_writer = new CsvFileWriter();
    private ArrayList<ArrayList<OrderingFitnessValue>> data;
    
	public FitnessTest(boolean print) {
		factorization = new Duval();
		this.print = print;
		fvc = new FactorVariationCalculator();
		data = new ArrayList<>();
	}
	
	public CsvFileWriter getFileWriter() {
		return this.file_writer;
	}
	
	public void resetData() {
		this.data = new ArrayList<>();
	}
	
	public ArrayList<ArrayList<OrderingFitnessValue>> getData(){
		return this.data;
	}
	
	/**
	 * 
	 * @param p population
	 * @param input string
	 * @param opt_type (0 min, 1 max, 2 desired)
	 * @param desired_factors number of desired factors -1 if none
	 * @return ArrayList<char[]> of selected best, if the size of the list is 1, it contains the optimal.
	 * 
	 */

	public ArrayList<char[]> testFitness(Population p, char[] input, int opt_type,
			int desired_factors, String stringid) {
		
		ArrayList<OrderingFitnessValue> generationData = new ArrayList<>();

		//To store sorted population
		ArrayList<char[]> sorted_population = new ArrayList<>();
			
		//MIN OR MAX
	    if(opt_type == 0 || opt_type == 1) {
	    	//Initializes new PQ
	    	DoubleEndedPQ pq = new DoubleEndedPQ();
	    	//loops through entire population
	    	for(char[] solution : p.getPopulation()) {
	    		//calculates the number of factors
	    		int num_factors = factorization.factor(solution,input).size();
	    		//prints
	    		if(print) // This is where the number of factors is printed. 
	    		    System.out.println(String.valueOf(solution)+ " " + num_factors);
	    		//adds the ordering factor size to the priority queue
	    		OrderingFitnessValue ofv = new OrderingFitnessValue(solution, num_factors);
	    		pq.add(ofv); 
	    		generationData.add(ofv);
	    
	    		
	    		// exit criteria if it finds an alphabet ordering with a factorization of 2, it will
	    		// now return this one optimal solution (this is because we already know that it cannot factor into 
	    		// one. 
	    		if(num_factors == 1 && opt_type == 0) {
                    System.out.println(stringid);
	    		    data.add(generationData); // Added after output problems ---------------------------------------------------------------------------------------------
	    			sorted_population.add(solution);
	    			return sorted_population;
	    		}
	    	}//If minimization
	    	if(opt_type == 0) {
	    		int count = 0; 
	    		//for 50% of population set
	    		for(int i = 0; i < p.getPopulationSize()/2; i++) {
	    			// get the least valued fitness from the double ended priority queue
	    			OrderingFitnessValue ofs = pq.getLeast();
	    		
	    			//sets the population to the array list (without having to perform a sort)
	    			sorted_population.add(ofs.getOrdering());
	    		}
	    		
	    		//If maximization
	    	}else {
	    		//for 50% of population 
	    		int count = 0;
	    		for(int i = 0; i < p.getPopulationSize()/2; i++) {
	    			//get get the highest ordered characters from priority queue
	    			OrderingFitnessValue ofs = pq.getMost();
	    			//will already be sorted due to removal from the tree
	    			sorted_population.add(ofs.getOrdering());
	    		
	    			count++;
	    		}
	    	}
	    }
	    //if we are trying to balance the amount of factors
	    else if(opt_type == 2) {
	    	
	    	//we start a new priority queue
	    	DoubleEndedPQ pq = new DoubleEndedPQ();
	    	for(char[] solution: p.getPopulation()) {
	    		//then get the factors for each solution
	    		ArrayList<String> factors = factorization.factor(solution, input);
	    		//we then calculate the standard deviation of the lengths of the factors
	    		double fitness = fvc.calculate(factors);
	    		if(factors.size() == 1 ) { //if only one factor has been returned
	    			//this is to represent no variation as not a good enhancement
	    		    fitness = 999999999;
	    		}
	    		if(print) { //only prints the fitness if selected
	    			System.out.println(String.valueOf(solution) + " " + fitness);
	    		}
	    		//adds the fitness to the priority queue
	    		
	    		OrderingFitnessValue ofv = new OrderingFitnessValue(solution, fitness);
	    		pq.add(ofv);
	    		generationData.add(ofv);
	    	}
	    	//remove 50% o
	    	for(int i  = 0; i < p.getPopulationSize()/2; i++) {
	    		OrderingFitnessValue ofs = pq.getLeast();
	    		sorted_population.add(ofs.getOrdering());
	    	}
	    }
	    //SPECIFIC
	    else if(opt_type == 3)  {
	    	DoubleEndedPQ pq = new DoubleEndedPQ();
	    	//Loop through population set
	    	for(char [] solution : p.getPopulation()) {
	    		//calculate number of factors for ordering
	    		int num_factors = factorization.factor(solution, input).size();
	    		if(print)
	    			System.out.println(String.valueOf(solution)+ " " + num_factors);
	    		//Fitness is the distance the solution is away from required
	    		int fitness = calculateDistance(desired_factors,num_factors);
	    		//Check for exit criteria
	    		if(num_factors == desired_factors) { // if it is what we are looking for. 
	    			sorted_population.add(solution);
	    			return sorted_population;
	    		}
	    		//Adds the solution fitness pair to the pq
	    		OrderingFitnessValue ofv = new OrderingFitnessValue(solution, fitness);
	    		pq.add(ofv);
	    		generationData.add(ofv);
	    	}
	    
	    	//Fill array list with solutions with shortest distance from required
	    	for(int i = 0; i < p.getPopulationSize()/2; i++) {
	    		OrderingFitnessValue ofs = pq.getLeast();
    			sorted_population.add(ofs.getOrdering());
	    	}
	    }
	    if(data.isEmpty()){
            System.out.println(stringid);
        }
	    this.data.add(generationData);
	    
	    return sorted_population;
	}
	
	private int calculateDistance(int desired_factors,int factors) {
		//calculates distance from two numbers, makes sure is positive.
		if(desired_factors > factors ) {
			return desired_factors-factors;
		}
		else {
			return factors-desired_factors;
		}
	}
}
