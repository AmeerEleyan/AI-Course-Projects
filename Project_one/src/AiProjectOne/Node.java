/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/14/2022    10:52 PM
 */
package AiProjectOne;

import java.util.LinkedList;

public class Node {

    private final Place place;
    private final LinkedList<Edge> edge;

    public Node(Place place) {
        this.place = place;
        this.edge = new LinkedList<>();
    }

    public Place getPlace() {
        return place;
    }

    public LinkedList<Edge> getAdjacent() {
        return edge;
    }

    public void addAdjacent(Place adjacentPlace, float distance) {
        this.edge.addFirst(new Edge(adjacentPlace, distance));
    }

    @Override
    public String toString() {
        return place.toString() + ": AdjacentPlace =>" + edge;
    }

}
