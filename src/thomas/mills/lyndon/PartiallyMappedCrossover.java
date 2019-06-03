package thomas.mills.lyndon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PartiallyMappedCrossover implements PMX {
	
	private Random rand;
	
	public PartiallyMappedCrossover() {
		rand  = new Random();
	}
	
	public int[] getSwathPoints(char[] p1,char[] p2, int end_type, double limit) {
		//Initialize the swath points
		int[] swath_points = new int[2];
		int permutation_length =  p1.length;
		int swath_start, swath_finish;
		
		//Here is for biased to lower order
		if(end_type == 0) { 
			swath_start = 0;
			int min = 1;
			int max = (int)(limit*permutation_length-1);
			swath_finish = rand.nextInt((max - min) + 1) + min;
		}
		//Here is biased to higher order
		else if (end_type == 1) { 
			swath_start = permutation_length -1;	
			int max = permutation_length-2;
			int min = max-((int)(limit*p1.length-1));
			swath_finish = rand.nextInt((max - min) + 1) + min;
		}
		//No Bias
		else { 
			swath_start = rand.nextInt(permutation_length);
			swath_finish = rand.nextInt(permutation_length);
		}
		//Make sure swath starts are not equal
		if(swath_start == swath_finish) {
			if(swath_start == permutation_length -1) {
				swath_start--;
			}
			else {
				swath_start++;
			}			
		}
		swath_points[0] = swath_start;
		swath_points[1] = swath_finish;
		return swath_points;	
	}
	
	/**
	 * Sort swath points sorts the swath points so that the swath location does not 
	 * overlap. It is within this method where the call to the random selection points 
	 * is gathered. This takes in the given limit of the swath size and whether a double 
	 * sided pmx is to be used. 
	 * @param p1 parent 1
	 * @param p2 parent 2 
	 * @param limit limit of the size of the swath
	 * @param double_sided whether the pmx is double sided
	 * @return
	 */
	public int[] sortSwathPoints(char[] p1,char[] p2, double limit, boolean double_sided) {
		//choose select type of pmx
		int end_type = 2; // End type two means double sided
		if(double_sided) {
			end_type = rand.nextInt(1); //This will either become 1 sided or 2 sided. 
		}		
		//get swath points 
		int[] swath_points = getSwathPoints(p1,p2,end_type,limit);
		//sort swath_points
		int swath_start,swath_finish;	
		if(swath_points[0] > swath_points[1]) {
			swath_finish = swath_points[0];
			swath_start = swath_points[1];
		}else {	
			swath_finish = swath_points[1];
			swath_start = swath_points[0];
		}
		int[] sorted_points = new int[] {swath_start,swath_finish};
		return sorted_points;
	}
	
	/**
	 * PMX is used to first call pmx. It is here in which we can use polymorphism 
	 *  where only the two parents, the swath limit and whether it is double sided 
	 *  is entered as a parameter. 
	 */
	public char[] pmx(char[] p1, char[] p2, double limit, boolean double_sided) {
		//Gains swath points based on swath limit and swath location biasness
		int[] sorted_swath_points = sortSwathPoints(p1,p2,limit,double_sided);
		//Calls the actual PMX value 
		return pmx(p1,p2,sorted_swath_points);
	}
	
	
	/**
	 * Partially mapped crossover is implemented in a way in which only the two parents 
	 * that are to be crossed over are go entered as well as the swath_points. 
	 * This is so the same function can be used for both one sided, double-sided and normal PMX. 
	 * 
	 * Firstly a new child is initalized as the same length as the first parent. 
	 * The swath is then determined by the inserted swath point location paramaters.
	 * 
	 * To populate the child with the elements within the swath of the first parent. We the swath directly 
	 * from the first parent. We also make note of all of the empty indexes in the child that were not filled 
	 * by elements within the swath. 
	 * 
	 * The algorithm then loops through the swath to check for elements that are in p2 that have not been copied 
	 * to the child yet. These characters are denoted as I characters. The algorithm then makes note of the places in 
	 * p1 where these I characters in P2 are. These characters are denoted as J. 
	 * 
	 * The algorithm then attempts to place these I characters in the index of the child that is the same index 
	 * as the J characters in P2. 
	 * 
	 * If even this location within the child is taken, by the value 'k', the I characters are then placed in the index 
	 * of the child that is occupied by K in P2. 
	 * 
	 * If even the same  position in the child of the index of k in p2 is occupied (in extreme cases)  the I char is then 
	 * added to the list of remaining elements. 
	 * 
	 * 
	 * All remaining elements are then copied directly from p2 to the empty positions in the child in the order they appear in p2. 

	 * @param p1 parent1
	 * @param p2 parent2
	 * @param swath_points
	 * @return
	 */
	public char[] pmx(char[] p1, char[] p2, int [] swath_points) {
		
		//Initiate child and swath
		char[] child = new char[p1.length];
	    int swath_start = swath_points[0];
	    int swath_finish = swath_points[1];
		char[] swath = new char[(swath_finish - swath_start) + 1];
		int count = 0;
		
		for(int i = swath_start; i <= swath_finish;i++) {
			swath[count] = p1[i];
			count++;
		}
		
		//Copy swath from p1 to child making note of empties 
		ArrayList<Integer> empty_spaces = new ArrayList<Integer>();
		for(int i = 0; i < swath_start; i++) {
			empty_spaces.add(i);
		}
		for(int i = swath_start; i <=swath_finish; i++) {
			child[i] = p1[i];
		}
		for(int i = swath_finish+1; i < p1.length; i++) {
			empty_spaces.add(i);
		}	
		//Check elements in p2 swath that were not copied.
		for(int i = swath_start; i<=swath_finish;i++) {
			if(!contains(swath, p2[i])) {
				char i_char = p2[i]; //element in p2 not in swath
				char j_char = p1[i]; //char in p1 where i_char was
				int j_pos_in_p2 = getPositionOfChar(p2,j_char);
				if(empty_spaces.contains(j_pos_in_p2)){//if position child isnt already occupied	
					child[j_pos_in_p2] = i_char; // set i char to be in this posisition
					empty_spaces.removeAll(Arrays.asList(j_pos_in_p2));	//remove this from the empty spaces array	
				}
				else {				
					char k_char = child[j_pos_in_p2];	//something if something exists there (char k)
					int k_position_in_p2 = getPositionOfChar(p2,k_char); //get position of k
					if(!empty_spaces.contains(k_position_in_p2)) { // if even k is already occupied
						child = placeInNextBestEmpty(child,i_char,empty_spaces); // Attempts to place in thenext best empthy. 
					}
					else{
						child[k_position_in_p2] = i_char; // unless, place I char in this position
						empty_spaces.removeAll(Arrays.asList(k_position_in_p2)); // then make sure that we remove this from the empty spaces. 
					}
					  
				}
			}
		}
		//all remaining characters are then filled
		fillEmpties(child,p2,empty_spaces);
		return child;
	}
	
	/**
	 * This function attempts to find all the remainig characters that are in P2 and copy them directly to the 
	 * empty spaces in the child, making sure that we remove the empty spaces from the list when they have been replaced. 
	 * @param child
	 * @param p2
	 * @param empty_spaces
	 * @return
	 */
	public char[] fillEmpties(char[] child, char[] p2, ArrayList<Integer> empty_spaces) {
		//Find remaining chars in p2 that are not in child
		ArrayList<Character> remaining_chars = new ArrayList<Character>();
		for(int i = 0; i < p2.length; i++) {
		    if(!contains(child,p2[i])) {
				remaining_chars.add(p2[i]);
			}
		}
		while(empty_spaces.size()!=0) {
			child[empty_spaces.get(0)] = remaining_chars.remove(0);
			empty_spaces.remove(0);
		}
		
		return child;
	}
	
	/** places in the next best empty character 
	 * 
	 * @param child
	 * @param i_char (character to be placed)
	 * @param empties (list of empty indexes)
	 * @return
	 */
	public char[] placeInNextBestEmpty(
			char[] child, char i_char, ArrayList<Integer> empties) {
		
		//try place in next empty leftmost
		if(empties.size() > 0) {
			child[empties.get(0)] = i_char;
			empties.remove(0);
		}

		return child;
	}
	
	/** performs a linear scan to find the indexes of characters in the second parent
	 * 
	 * @param parent2
	 * @param j
	 * @return
	 */
	public int getPositionOfChar(char[] parent2, char j) {
		for(int i = 0; i < parent2.length; i++) {
			if(parent2[i] == j)
				return i;
		}
		return -1;
	}
	
	/**
	 * used to check to see if the swath contains a particular character. 
	 * @param swath
	 * @param to_check
	 * @return
	 */
	public boolean contains(char[]swath, char to_check) {
		for(int i = 0; i < swath.length; i++) {
			if(swath[i] == to_check) 
				return true;
		}
		return false;
	}

}
