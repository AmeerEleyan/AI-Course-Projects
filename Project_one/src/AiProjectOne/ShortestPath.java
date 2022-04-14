/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 8/12/2021    2:14 AM
 */
package AiProjectOne;

import java.util.LinkedList;

public class ShortestPath {

    private final float totalDistance;
    private final int spaceComplexity;
    private final int timeComplexity;
    private final LinkedList<Place> placesInThePath;

    public ShortestPath(int spaceComplexity,  int timeComplexity, float totalDistance, LinkedList<Place> placesInThePath) {
        this.spaceComplexity=spaceComplexity;
        this.timeComplexity=timeComplexity;
        this.totalDistance = totalDistance;
        this.placesInThePath = placesInThePath;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public LinkedList<Place> getPlacesInThePath() {
        return placesInThePath;
    }

    public int getSpaceComplexity() {
        return spaceComplexity;
    }

    public int getTimeComplexity() {
        return timeComplexity;
    }
}
