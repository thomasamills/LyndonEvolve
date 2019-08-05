package thomas.mills.lyndon;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

public class PartialOrder {
    boolean max = false;
    int numOfNodes;

    private DefaultDirectedGraph<Character, DefaultEdge> nodes;

    public PartialOrder() {
        numOfNodes = 0;
        this.nodes = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public PartialOrder(boolean max){
        numOfNodes = 0;
        this.nodes = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.max = max;
    }


    //TODO: improve time complexity
    public void assignBiggerThan(Character character, Character smaller) {
        Character character2;
        Character smaller2;
        if(max){
            character2 = smaller;
            smaller2 = character;
        }else{
            character2 = character;
            smaller2 = smaller;
        }
        if(!nodes.containsVertex(character2)){
            nodes.addVertex(character2);
            numOfNodes += 1;
        }
        if(!nodes.containsVertex(smaller2)){
            nodes.addVertex(smaller2);
            numOfNodes += 1;
        }
        nodes.addEdge(character2, smaller2);
    }

    public boolean hasCharMapped(Character character, Character smaller) {
        boolean characterToSmaller = isBiggerThan(character, smaller, true);
        if(characterToSmaller){
            return true;
        }
        boolean smallerToCharacter = isBiggerThan(smaller, character, true);
        if (smallerToCharacter){
            return true;
        }
        return false;
    }

    public boolean isBiggerThan(Character character, Character smaller, boolean onlyAssignedLetters) {
        if (nodes.containsVertex(character) && nodes.containsVertex(smaller)) {
            BreadthFirstIterator<Character, DefaultEdge> breadthFirstIterator = new BreadthFirstIterator<>(nodes, character);
            while (breadthFirstIterator.hasNext()) {
                Character aChar = breadthFirstIterator.next();
                if (aChar == smaller) {
                    return true;
                }
            }
            return false;
        }else if(!onlyAssignedLetters){
            return character > smaller;
        }else {
            return false;
        }
    }

    public boolean isBiggerThan(Character character, Character smaller){
        return isBiggerThan(character, smaller, false);
    }

    @Override
    public String toString() {
        return "PartialOrder{" +
                "nodes=" + nodes +
                '}';
    }

    public int getNumOfNodes() {
        return numOfNodes;
    }
}
