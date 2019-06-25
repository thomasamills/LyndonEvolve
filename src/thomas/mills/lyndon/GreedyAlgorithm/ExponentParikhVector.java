package thomas.mills.lyndon.GreedyAlgorithm;

import java.util.ArrayList;

/**
 * Class that represents and initializes the Exponent Parikh Vector
 * @author Leonel Peña
 * @version 1.0
 */

public class ExponentParikhVector {

    private ArrayList<CharacterExponentsPair> characterVector;

    public ExponentParikhVector(String s) {
        characterVector = new ArrayList<>();
        Character lastSeen = null;
        CharacterExponentsPair lastCharExpPair = null;
        for(Character c : s.toCharArray()){
            CharacterExponentsPair newCharacterExponentPair = new CharacterExponentsPair(c);

            if(!c.equals(lastSeen) && !characterVector.contains(newCharacterExponentPair)){
                characterVector.add(newCharacterExponentPair);
                lastSeen = c;
                lastCharExpPair = newCharacterExponentPair;
            }
            else if(!c.equals(lastSeen) && characterVector.contains(newCharacterExponentPair)){
                int index = characterVector.indexOf(newCharacterExponentPair);
                characterVector.get(index).addNewCount();
                lastSeen = c;
                lastCharExpPair = characterVector.get(index);
            }
            else if(c.equals(lastSeen)){
                lastCharExpPair.increaseLastCount();
            }
        }
    }

    public ArrayList<CharacterExponentsPair> getCharacterVector() {
        return characterVector;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Exponent Parikh Vector\n");
        for(CharacterExponentsPair charExpPair : characterVector){
            s.append(charExpPair.toString());
        }
        return s.toString();
    }
}
