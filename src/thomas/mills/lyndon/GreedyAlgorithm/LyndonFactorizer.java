package thomas.mills.lyndon.GreedyAlgorithm;

import java.util.ArrayList;

public abstract class LyndonFactorizer {

    public static ArrayList<String> factorize(String s, boolean inverseAlphabet) {
        int k = 0;
        char[] chars = s.toCharArray();
        ArrayList<String> resultList = new ArrayList<>();
        while (k < chars.length) {
            int i = k + 1;
            int j = k;

            //In the following "while" and "if" statements there are basically 2 cases: one if the alphabet order is reversed and one if it is not. These separated by and "or".
            while (i < chars.length && ((chars[j] <= chars[i] && !inverseAlphabet) || (chars[j] >= chars[i] && inverseAlphabet))){
                if((chars[j] < chars[i] && !inverseAlphabet) || (chars[j] > chars[i] && inverseAlphabet)){
                    j = k;
                }
                else{
                    j++;
                }
                i++;
            }

            while (k <= j) {
                resultList.add(new String(chars, k, i-j));
                k += i - j;
            }

        }
        return resultList;
    }

    //public static ArrayList<String> factorize(String s){}

    public static CharExpFactPair factorize(CharacterExponentsPair charExpPair) {
        String s = "";
        Character character = charExpPair.getCharacter();
        for (Integer count : charExpPair.getCounts()) {
            s += count.toString();
        }
        return new CharExpFactPair(character, factorize(s, true));
    }

    public static ArrayList<CharExpFactPair> factorize(ExponentParikhVector epv) {
        ArrayList<CharExpFactPair> listOfListOfFactors = new ArrayList<>();
        for (CharacterExponentsPair charExpPair : epv.getCharacterVector()) {
            listOfListOfFactors.add(factorize(charExpPair));
        }
        return listOfListOfFactors;
    }
}
