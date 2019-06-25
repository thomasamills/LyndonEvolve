package thomas.mills.lyndon.GreedyAlgorithm;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents a character and its consecutive appearances in a string
 * @author Leonel Peña
 * @version 1.0
 */

public class CharacterExponentsPair {

    private Character character;
    private ArrayList<Integer> counts;


    CharacterExponentsPair(Character character) {
        this.character = character;
        counts = new ArrayList<>();
        this.addNewCount();
    }

    public void addNewCount(){
        counts.add(1);
    }

    public void increaseLastCount(){
        int index = counts.size() - 1;
        counts.set(index, counts.get(index) + 1);
    }

//------------------------------------------------------------------------------------

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public ArrayList<Integer> getCounts() {
        return counts;
    }

    public void setCounts(ArrayList<Integer> counts) {
        this.counts = counts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterExponentsPair that = (CharacterExponentsPair) o;
        return Objects.equals(character, that.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }

    @Override
    public String toString() {
        return  character +
                "(" + counts +
                ")\n";
    }
}
