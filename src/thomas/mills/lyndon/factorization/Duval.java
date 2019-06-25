package thomas.mills.lyndon.factorization;
import java.util.ArrayList;

public class Duval implements Factorization {
	
	/**
	 * checkOrder takes in an encoded character array, and two characters
	 * it returns 1 if char a is smaller, 0 if char a is larger and -1 if they are equal. 
	 */
	public int checkOrder(char[] o, char a, char b){
		String s = String.valueOf(o);
		if(s.indexOf(a) < s.indexOf(b))
			return 1;
		else if(s.indexOf(a) > s.indexOf(b))
			return 0;
		else
			return -1;
	}
	
	/**
	 * Slice string is used to create a substring and collect each of the factors 
	 * 
	 * 
	*/
	private String sliceString(char[] array, int start, int last) {
	    return new String(array, start, last - start);
	}

	
	/**
	 * Standard implementation of the duval factorization algorithm aside from is uses the check order
	 * method instead of lexicographical. 
	 */
	
	public ArrayList<String> factor(char[] ordering, char[] s) {
		ArrayList<String> factors = new ArrayList<String>();
		int k = 0;
		while(k < s.length) {
			int i = k+1;
			int j = k+2;
			while(true){
				if(j == s.length + 1||this.checkOrder(ordering,s[j-1], s[i-1]) == 1) {
					while(k<i){
					    factors.add(sliceString(s,k,k+j-i));
					    k=k+j-i;
					}
					break;
				}
				else {
					if(this.checkOrder(ordering,s[j-1], s[i-1]) == 0)
						i = k+1;
					else
						i = i+1;
					j = j+1;
				}
			}
		}
		return factors;
	}
}