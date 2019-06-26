package thomas.mills.lyndon.GreedyAlgorithm;

import java.util.ArrayList;

public class CharExpFactPair implements Comparable<CharExpFactPair>{

    char character;
    ArrayList<String> countFactors;

    public CharExpFactPair(char character, ArrayList<String> countFactors) {
        this.character = character;
        this.countFactors = countFactors;
    }

    public char getCharacter() {
        return character;
    }

    public ArrayList<String> getCountFactors() {
        return countFactors;
    }

    public int numOfFactors(){
        return countFactors.size();
    }

    @Override
    public String toString() {
        return "(" + character +
                ')' + countFactors;
    }

    @Override
    public int compareTo(CharExpFactPair o) {
        if(this.numOfFactors() > o.numOfFactors()){
            return 1;
        }
        else if(this.numOfFactors() < o.numOfFactors()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
