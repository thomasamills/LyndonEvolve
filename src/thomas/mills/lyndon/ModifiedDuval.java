package thomas.mills.lyndon;

import thomas.mills.lyndon.GreedyAlgorithm.LyndonFactorizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ModifiedDuval {

    private static PartialOrder partialOrder;

    public static void reorderSequence(Map<String, String> sequence, boolean max){
        String pathname = System.getProperty("user.dir") + "\\GreedyAlgResults2.txt";
        File file = new File(pathname);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            float totalCount = 0;
            float optimizeCount = 0;
            Map<Integer, Integer> amountOfFactors = new HashMap<>();
            for(Map.Entry<String, String> entry : sequence.entrySet()){
                totalCount += 1;
                printWriter.println("-------------------------------------------------");
                printWriter.print("ID: ");
                printWriter.print(entry.getKey());
                printWriter.println();
                String proteinSeq = entry.getValue().toLowerCase() ;
                ArrayList<String> proteinSeqFactors = LyndonFactorizer.factorize(proteinSeq, false);
                printWriter.println("Factorization of the string with standard ordering (roman alphabet): ");
                printWriter.println(proteinSeqFactors);
                printWriter.print(" Number of factors: ");
                int previousSize = proteinSeqFactors.size();
                printWriter.println(previousSize);
                ArrayList<String> proteinSeqFactReorder = ModifiedDuval.factor(proteinSeq, max);
                printWriter.print("Factorization of the string with new ordering");
                printWriter.println(proteinSeqFactReorder);
                printWriter.print("Number of factors: ");
                int newSize = proteinSeqFactReorder.size();
                printWriter.println(newSize);
                printWriter.println("-------------------------------------------------");
                if(previousSize > newSize){
                    optimizeCount += 1;
                }else{
                    System.out.print("ID: ");
                    System.out.println(entry.getKey());
                    System.out.print("Previous factors: ");
                    System.out.println(previousSize);
                    System.out.print("New factors: ");
                    System.out.println(newSize);
                }
                if(!amountOfFactors.containsKey(newSize)){
                    amountOfFactors.put(newSize, 1);
                }else{
                    amountOfFactors.replace(newSize, amountOfFactors.get(newSize) + 1);
                }
                printWriter.flush();
            }
            printWriter.print("Total sequences: ");
            printWriter.println(totalCount);
            printWriter.print("Amount of strings optimized: ");
            printWriter.print(optimizeCount);
            printWriter.print("; Percentage: ");
            printWriter.print((optimizeCount/totalCount)*100);
            printWriter.println("%");
            for(Map.Entry<Integer, Integer> instances : amountOfFactors.entrySet()){
                printWriter.print("Amount of sequences with ");
                printWriter.print(instances.getKey());
                printWriter.print(" factor(s): ");
                printWriter.print((instances.getValue()/totalCount)*100);
                printWriter.println("%");
                printWriter.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> factor(String s, boolean max){
        partialOrder = new PartialOrder(max);
        int h = 0;
        ArrayList<ModCharacter> chars = new ArrayList<>();
        for(Character character : s.toCharArray()){
            chars.add(new ModCharacter(character));
        }
        ArrayList<String> resultList = new ArrayList<>();
        while (h < chars.size()){
            int i = h;
            int j = h + 1;
            while (j < chars.size() && (!partialOrder.hasCharMapped(chars.get(j).getaChar(), chars.get(i).getaChar()) || chars.get(j).compareTo(chars.get(i)) >= 0 )){
                if(chars.get(j).equals(chars.get(i))){
                    i++;
                }else if(!partialOrder.hasCharMapped(chars.get(j).getaChar(), chars.get(i).getaChar())){
                    partialOrder.assignBiggerThan(chars.get(j).getaChar(), chars.get(i).getaChar());
                    i = h;
                }else{
                    i = h;
                }
                j++;
            }
            while(h <= i){
                String newString = "";
                for(int x = h; x < h + j - i; x++){
                    newString += chars.get(x).getaChar();
                }
                resultList.add(newString);
                h += j - i;
            }
        }
        System.out.println("Number of nodes: " + partialOrder.getNumOfNodes());
        return resultList;
    }
    private static class ModCharacter implements Comparable{
        char aChar;

        public ModCharacter(char aChar) {
            this.aChar = aChar;
        }

        public char getaChar() {
            return aChar;
        }

        @Override
        public int compareTo(Object o) {
            if(partialOrder.isBiggerThan(aChar, ((ModCharacter)o).getaChar())) {
                return 1;
            } else if(partialOrder.isBiggerThan(((ModCharacter)o).getaChar(), aChar)){
                return -1;
            }else{
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ModCharacter that = (ModCharacter) o;
            return aChar == that.aChar;
        }

        @Override
        public int hashCode() {
            return Objects.hash(aChar);
        }
    }
}
