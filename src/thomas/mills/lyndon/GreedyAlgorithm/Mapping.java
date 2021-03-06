package thomas.mills.lyndon.GreedyAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Mapping {

    private ArrayList<Letter> letters;
    private int alphabetLoc;


    /**
     * Constructor of mapping
     * @param n n >= 0 is the position in the alphabet from which the assignments should begin
     */
    public Mapping(int n) {
        this.alphabetLoc = 'a' + n;
        this.letters = new ArrayList<>();
    }

    public Mapping(){
        this(0);
    }

    /**
     * Assign the next available alphabet character to the letter,
     * if not already assigned
     * @param character character from the alphabet to be assigned
     */
    public void assign(char character){
        Letter newLetter = new Letter(character);
            if (!letters.contains(newLetter)) {
                newLetter.setAlphabetLocation(this.alphabetLoc);
                letters.add(newLetter);
                alphabetLoc += 1;
            }
    }

    /**
     * Unassigns all assignments that were after n (not including n)
     * @param letter
     */
    public void unassignUpTo(Letter letter){
        ArrayList<Letter> newLetters = new ArrayList<>();
        int n = letters.indexOf(letter);
        for(Letter aLetter : letters){
            if(aLetter.getAlphabetLocation() <= n){
                newLetters.add(aLetter);
            }
        }
        letters = newLetters;
        alphabetLoc += 1;
    }

    /**
     * Resets the alphabet_loc to position n in alphabet (n is 0-indexed)
     * @param n index
     */
    public void reset(int n){
        int a = 'a';
        alphabetLoc = a + n;
    }

    /**
     * Discard all assignments and reset the alphabetLoc
     */
    public void clear(){
        letters.clear();
        this.reset(0);
    }


    /**
     * Translate the given string using the current mapping
     * @return The translated string
     */
    public String mapString(String string){
        StringBuilder out = new StringBuilder();
        for(char character : string.toCharArray()){
            if (!letters.contains(new Letter(character))) {
                this.assign(character);
            }
            Letter newLetter = letters.get(letters.indexOf(new Letter(character)));
            out.append((char)newLetter.getAlphabetLocation());
        }
        return out.toString();
    }

    /**
     * Assign all letters in the string if they don't already have assignments
     */
    public void assignAll(String string){
        for(Character character : string.toCharArray()){
            assign(character);
        }
    }

    public int lookUp(char x){
        Letter lookLetter =  new Letter(x);
        if(letters.contains(lookLetter)){
            return letters.get(letters.indexOf(lookLetter)).getAlphabetLocation();
        }
        else{
            return (char) -1;
        }
    }

    /**
     * Reassigns each letter to lower ascii characters (without changing the order) in order
     * to have letters representable in ascii, starting from 'a'.
     */
    public void readjust(){
        Collections.sort(letters);
        for (int i = 0; i < letters.size(); i++){
            letters.get(i).setAlphabetLocation('a' + i);
        }
    }

    public int getAlphabetLoc() {
        return alphabetLoc;
    }

    public char[] asOrder(){
        Collections.sort(letters);
        char[] order = new char[letters.size()];
        for(int i = 0; i < letters.size(); i++){
            order[i] = letters.get(i).getLetter();
        }
        return order;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("The remapping of characters:\n");
        for (Letter letter : letters){
            out.append(letter.getLetter()).append("-->").append((char) letter.getAlphabetLocation()).append("\n");
        }
        return out.toString();
    }

    //---------------Letter class------------------------------------------------
    private class Letter implements Comparable{
        char letter;
        int alphabetLocation;

        Letter(char letter) {
            this.letter = letter;
            this.alphabetLocation = -1; // -1 is used to represent that the current letter of the alphabet has not been assigned to a position yet
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter1 = (Letter) o;
        return letter == letter1.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter);
    }

    public void setAlphabetLocation(int alphabetLocation) {
        this.alphabetLocation = alphabetLocation;
    }

    public char getLetter() {
        return letter;
    }

    public int getAlphabetLocation() {
        return alphabetLocation;
    }

        @Override
        public String toString() {
            return "Letter{" +
                    "letter=" + letter +
                    ", alphabetLocation=" + alphabetLocation +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            int alphLoc1 = this.getAlphabetLocation();
            int alphLoc2 = ((Letter) o).getAlphabetLocation();
            return Integer.compare(alphLoc1, alphLoc2);
        }
    }
//---------------------------------------------------------------------------
}
