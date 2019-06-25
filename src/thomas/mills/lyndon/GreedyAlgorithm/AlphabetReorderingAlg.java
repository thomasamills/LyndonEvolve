package thomas.mills.lyndon.GreedyAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public abstract class AlphabetReorderingAlg {

    public static void reorderSequence(Map<String, String> sequence, boolean noBacktrack){
        String pathname = System.getProperty("user.dir") + "\\GreedyAlgResults";
        File file = new File(pathname);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for(Map.Entry<String,String> entry : sequence.entrySet()){
                Mapping mapping = new Mapping();
                printWriter.println("---------------------------------------------");
                printWriter.print("ID: ");
                printWriter.print(entry.getKey());
                printWriter.println();
                String proteinSeq = entry.getValue().toLowerCase(); //Mapping is implemented in a way that it works with lower case letters
                ArrayList<String> proteinSeqFactors = LyndonFactorizer.factorize(proteinSeq, false);
                printWriter.println("Factorization of the string with standard ordering (roman alphabet): ");
                printWriter.println(proteinSeqFactors);
                printWriter.print(" Number of factors: ");
                printWriter.println(proteinSeqFactors.size());
                printWriter.println();
                ArrayList<String> proteinSeqFactReorder = LyndonFactorizer.factorize(reorder(proteinSeq, mapping, false), false);
                printWriter.print("Factorization of the string with ordering (represented by mapping): ");
                printWriter.println(mapping.toString());
                printWriter.println(proteinSeqFactReorder);
                printWriter.print("Number of factors: ");
                printWriter.println(proteinSeqFactReorder.size());
                printWriter.print("Difference in number of factors: ");
                printWriter.println(proteinSeqFactReorder.size() - proteinSeqFactors.size());
                printWriter.println("---------------------------------------------");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String reorder(String word, Mapping map, boolean noBacktrack){
        //Makes exponent parikh vector
        ExponentParikhVector expParikhVect = new ExponentParikhVector(word);
        //Prints the EPV
        //System.out.println(expParikhVect);
        //factorizes each vector (reverse order)
        ArrayList<CharExpFactPair> factorsEPV = LyndonFactorizer.factorize(expParikhVect);
        //Prints the factors of the Exponent Parikh Vector
        //System.out.println(factorsEPV);
        boolean good = false;
        Collections.sort(factorsEPV);

        String stringPrefix = "";
        ArrayList<String> xs = new ArrayList<>();
        //System.out.println("prs: " + factorsEPV.toString());
        for(CharExpFactPair pri : factorsEPV){
            stringPrefix = beforeChar(word, pri.getCharacter());
            HashSet<String> set = new HashSet<String>(Arrays.asList(stringPrefix.split("")));
            set.remove("");
            int lengthForReset = set.size();
            map.reset(lengthForReset);
            map.assign(pri.getCharacter());
            xs = getXsAfterChar(word, pri.getCharacter());
            good = assignToXs(map, pri, xs);
            if(good || noBacktrack){
                break;
            }
            else{
                map.clear();
            }
        }

        if (good || noBacktrack){
            if (!stringPrefix.isEmpty()){
                int n = map.getAlphabetLoc() - 'a';
                map.reset(0);
                reorder(stringPrefix, map, false);
                map.reset(n);
            }
            map.assignAll(word);
            map.readjust();
            String reordered = map.mapString(word);
            return reordered;
        }
        else{
            char uniqueChar = factorsEPV.get(0).getCharacter();
            map.clear();
            stringPrefix = beforeChar(word, uniqueChar);
            map.reset(stringPrefix.length());
            map.assign(uniqueChar);
            ArrayList<String> Xs = getXsAfterChar(word, uniqueChar);
            assignToXs(map, factorsEPV.get(0), Xs);
            if (!stringPrefix.isEmpty()){
                int n = map.getAlphabetLoc();
                map.reset(0);
                reorder(stringPrefix, map, false);
                map.reset(n);
            }
            map.assignAll(word);
            map.readjust();
            String reordered = map.mapString(word);
            return reordered;
        }
    }


//-------------------------------------------------------------------------

    /**
     * Return the prefix of the string s up until character c
     * @param s Reference String
     * @param c Reference character
     * @return Substring of s until character c
     */
    private static String beforeChar(String s, char c){
        int n = s.indexOf(c);
        return s.substring(0, n);
    }

    /**
     * Get all pieces of string that exist between the runs of the char provided
     * (ignoring any prefix to the first occurrence of char)
     */
    private static ArrayList<String> getXsAfterChar(String s, char aChar) {
        String[] result = s.split(aChar + "+");
        ArrayList<String> resultList = new ArrayList<>();
        Collections.addAll(resultList,result);
        //If the pattern is at the beginning the first item in the list is an empty string. This gets rid of it
        if (resultList.size() > 1) {
            resultList = new ArrayList<>(resultList.subList(1, resultList.size()));
        }
        else{
            resultList = new ArrayList<>();
        }
        return resultList;
     }

    /**
     * Choose the leftmost pr with the minimal number of factors
     */
    private CharExpFactPair choosePi(ArrayList<CharExpFactPair> flPrs){
        return flPrs.get(0);
    }

    /**
     * We need to assign ordering to the letters
     * by considering each exponent group (largest to smallest) and by leftmost first
     * @param map a mapping object "map" for storing the ordering
     * @param facts an object CharExpFactPair, that contains Lyndon factors for the exponents of this letter
     * @param Xs the array of string components occurring after each run of the letter in the original string
     * @return boolean that represent if it is inconsistent ("good or bad")
     */
    private static boolean assignToXs(Mapping map, CharExpFactPair facts, ArrayList<String> Xs) {
        boolean good = true;
        for (String fact : facts.getCountFactors()) {
            int lf = fact.length();
            List<String> xsBlock;
            if(Xs.size() >= lf) {  // If lf is bigger than xs the sublist slicing throws an exception
                xsBlock = Xs.subList(0, lf);

                Xs = new ArrayList<>(Xs.subList(lf, Xs.size()));
                List<ExpAndBlock> zippedExpAndXs = new ArrayList<>();
                for (int i = 0; i < fact.length(); i++) {
                    int factInt = Integer.parseInt(String.valueOf(fact.charAt(i)));
                    zippedExpAndXs.add(new ExpAndBlock(factInt, xsBlock.get(i)));
                }
                // We map each exponent to their set of x blocks
                Map<Integer, ArrayList<String>> expXsDict = new HashMap<>();
                for (ExpAndBlock pair : zippedExpAndXs) {
                    int exponent = pair.getExponent();
                    String xBlock = pair.getxBlock();
                    //checks if exponent is in the hashMap
                    if (expXsDict.containsKey(exponent)) {
                        ArrayList<String> newXBlock = new ArrayList<>(expXsDict.get(exponent));
                        newXBlock.add(xBlock);
                        expXsDict.replace(exponent, newXBlock);
                    } else {
                        ArrayList<String> newXBlock = new ArrayList<>();
                        newXBlock.add(xBlock);
                        expXsDict.put(exponent, newXBlock);
                    }
                }
                //In reverse numeric order of exponents (greatest first) is organized
                Comparator comparator = Comparator.reverseOrder();
                //Sorts by key automatically
                TreeMap<Integer, ArrayList<String>> treeMap = new TreeMap<>(comparator);
                treeMap.putAll(expXsDict);
                Iterator valuesIterator = treeMap.values().iterator();
                // debug-------------------------------------------------------
                //System.out.print("iterator: ");
                //System.out.println("sorted exponents: " + treeMap);
                //System.out.println("e: " + expXsDict.keySet());
                //System.out.println("X_vals: " + expXsDict.values());
                //System.out.println("first: " + treeMap.firstEntry());
                //--------------------------------------------------------------
                ArrayList<String> xVals = (ArrayList<String>) valuesIterator.next();
                if (xVals.size() == 1) {
                    for (char x : xVals.get(0).toCharArray()) {
                        map.assign(x);
                    }
                } else {
                    for (int i = 1; i < xVals.size(); i++) {
                        String laterVal = xVals.get(i);
                        good = true;
                        if (laterVal.length() > xVals.get(0).length()) {
                            // problem because no longer Lyndon word
                            good = false;
                            break;
                        } else {
                            int length;
                            if (laterVal.length() >= xVals.get(0).length()) {
                                length = xVals.get(0).length();
                            } else {
                                length = laterVal.length();
                            }
                            for (int j = 0; j < length; j++) {
                                char x = xVals.get(0).charAt(j);
                                char y = laterVal.charAt(j);
                                //System.out.println("x: " + x + "\n" + "y: " + y);
                                if (x == y) {
                                    map.assign(x);
                                } else {
                                    map.assign(x);
                                    map.assign(y);
                                    //System.out.println(map.lookUp(x) > map.lookUp(y));
                                    if (map.lookUp(x) > map.lookUp(y)) {
                                        good = false; //inconsistent
                                        // we have made an assignment of difference so can stop
                                    }
                                    break;
                                }
                            }
                        }
                        if (!good) {
                            return false;
                        }
                    }
                }
            }
        }
        return good;
    }

    //Used to iterate over pairs of exponents and its substring block
    private static class ExpAndBlock {

        private int exponent;
        private String xBlock;

        public ExpAndBlock(int exponent, String xBlock) {
            this.exponent = exponent;
            this.xBlock = xBlock;
        }

        public int getExponent()  {
            return exponent;
        }

        public String getxBlock() {
            return xBlock;
        }
    }

}
