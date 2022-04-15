/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/14/2022    10:52 PM
 */
package AiProjectOne;

import java.util.LinkedList;

public class Node {

    private final Place place;
    private final LinkedList<Adjacent> adjacent;

    public Node(Place place) {
        this.place = place;
        this.adjacent = new LinkedList<>();
    }

    public Place getPlace() {
        return place;
    }

    public LinkedList<Adjacent> getAdjacent() {
        return adjacent;
    }

    public void addAdjacent(Place adjacentPlace, float distance) {
        this.adjacent.addFirst(new Adjacent(adjacentPlace, distance));
    }

    @Override
    public String toString() {
        return place.toString() + ": AdjacentPlace =>" + adjacent;
    }

}
