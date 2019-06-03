package thomas.mills.lyndon;
import java.util.Random;

public class Mutations implements Mutator {

	private Random rand;
	
	public Mutations() {	
		rand = new Random();
	}
	
	public char[] performMutation(char[] ordering) {
		if(rand.nextInt(3) < 0 ) { //1/3 probability
			return randMutate(ordering);
		}else {
			return randDoubleMutate(ordering);
		}	
	}
		
	public char[] randMutate(char[] o){
		int[] selection_points = randomSelectionPoints(o);
		int rand_int = rand.nextInt(10);
	    if(rand_int > 8 ) { // 1/10 probability
	    	return randSwap(o, selection_points);
	    }
	    else {
	    	return randShift(o,selection_points);
	    }
	}
	
	public int[] randomSelectionPoints(char[] o) {
		int selection_point = rand.nextInt(o.length-1);
		int move_point = rand.nextInt(o.length);		
		
		//Local optima escaping heuristic here
		int rand_int = rand.nextInt(9);		
                if(rand_int > 8 ) {
                	if(rand.nextBoolean())
			            move_point = 0; 	
                	else
                	    selection_point = 0;
                }
                else if(rand_int>7)		
                	if(rand.nextBoolean())
			            move_point = 2; 	
                	else
                	    selection_point = 2;
                else if(rand_int>6)
                	if(rand.nextBoolean())
			            move_point = 3; 	
                	else
                	    selection_point = 3;           
                
		if(move_point == selection_point) {
			if(selection_point == o.length-1) {
				move_point--;
			}
			else {
				move_point++;
			}			
		}
		int[] selection_points = new int[2];
		selection_points[0] = selection_point;
		selection_points[1] = move_point;
		return selection_points;
	}
	
	
	public char[] randSwap(char[] ordering, int[] selection_points) {
		char selected_char = ordering[selection_points[0]];
		ordering[selection_points[0]] = ordering[selection_points[1]];
		ordering[selection_points[1]] = selected_char;
		return ordering;
	}
	
	public char[] randShift(char[] o, int[] selection_points) {
		String ordering_as_string = String.valueOf(o);
		StringBuilder sb = new StringBuilder(ordering_as_string);
		char selected_char = ordering_as_string.charAt(selection_points[0]);
		sb.deleteCharAt(selection_points[0]);
		String sb_string = sb.toString();
		String new_order = sb_string.substring(0,selection_points[1]) + selected_char 
				+ sb_string.substring(selection_points[1], sb_string.length());
		return new_order.toCharArray();
	}
	
	public char[] randDoubleMutate(char[] o) {
	    char[] o1 = randMutate(o);
	    return randMutate(o1);
	}

	
}
