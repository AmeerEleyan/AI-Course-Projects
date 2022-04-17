/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/14/2022    10:52 PM
 */
package AiProjectOne;

import java.util.LinkedList;

public class Node implements Comparable<Node> {

    private final Place place;
    private final LinkedList<Edge> edge;

    private float f;
    private float g;

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

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float calculateHeuristic(Place destination) {

        /*
         I calculate the distance between Ramallah and Beitunia on my map, the distance is: 90, and I
         calculate it in Google map(air distance), the distance is: 3.85 Km.

         I calculate the distance between DayrQadis and Shabtin on my map, the distance is: 84, and I
         calculate it in Google map(air distance), the distance is: 2.96 Km.

         I calculate the distance between Beit'Anan and Baytillu on my map, the distance is: 414, and I
         calculate it in Google map(air distance), the distance is: 14 Km.

          This function was used to calculate distance between two places on my map:
          Chebyshev distance:
         ---- D(p,q) = max(|px-qx|, |qy-qy|)--------
          This metric is admissible and thus guarantees an optimal solution. It's also quick to calculate,
          so it doesn't put a strain on resources in each iteration.


          Then I calculate the drawingScale=(airDistance)/(distanceInMap), and take average between the three previous values,
          ----------Final result: 0.0377F--------------
          1 distance in map = 0.0377 Km
         */

        float distance = Math.max(Math.abs(this.place.getLayout_X_Map() - destination.getLayout_X_Map()), Math.abs(this.place.getLayout_Y_Map() - destination.getLayout_Y_Map()));

        return distance * 0.0377F;

    }

    @Override
    public String toString() {
        return place.toString() + ": AdjacentPlace =>" + edge;
    }

    @Override
    public int compareTo(Node other) {
        return Float.compare(this.f, other.f);
    }

    @Override
    public boolean equals(Object obj) {
        return this.place.getPlaceName().equals(((Node) obj).getPlace().getPlaceName());
    }
}
