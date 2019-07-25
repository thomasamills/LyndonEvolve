package thomas.mills.lyndon;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

public class PartialOrder {

    private DefaultDirectedGraph<Character, DefaultEdge> nodes;

    public PartialOrder() {
        this.nodes = new DefaultDirectedGraph<>(DefaultEdge.class);
    }


    //TODO: improve time complexity
    public void assignBiggerThan(Character character, Character smaller) {
        if(!nodes.containsVertex(character)){
            nodes.addVertex(character);
        }
        if(!nodes.containsVertex(smaller)){
            nodes.addVertex(smaller);
        }
        nodes.addEdge(character, smaller);
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
}
