/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022    1:11 AM
 */
package AiProjectOne;

public class Adjacent{

    private final Place adjacentPlace;
    private float distance;

    // use this constructor when add adjacent to check place name
    public Adjacent(Place adjacentPlace) {
        this.adjacentPlace = adjacentPlace;
    }

    public Adjacent(Place adjacentPlace, float distance) {
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
        return this.adjacentPlace.getPlaceName().compareTo(((Adjacent) o).adjacentPlace.getPlaceName()) == 0;
    }

}
