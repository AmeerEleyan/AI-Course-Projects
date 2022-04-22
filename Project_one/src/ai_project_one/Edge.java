/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022    1:11 AM
 */
package ai_project_one;

public class Edge{

    private final Place adjacentPlace;
    private float distance;

    // use this constructor when add adjacent to check place name
    public Edge(Place adjacentPlace) {
        this.adjacentPlace = adjacentPlace;
    }

    public Edge(Place adjacentPlace, float distance) {
        this.adjacentPlace = adjacentPlace;
        this.distance = distance;
    }

    public Place getAdjacentPlace() {
        return adjacentPlace;
    }

    public float getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return adjacentPlace.toString() + ", distance: " + distance + "";
    }

    @Override
    public boolean equals(Object o) {
        return this.adjacentPlace.getPlaceName().compareTo(((Edge) o).adjacentPlace.getPlaceName()) == 0;
    }

}
