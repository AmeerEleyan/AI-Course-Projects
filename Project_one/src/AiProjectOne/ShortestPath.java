/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 8/12/2021    2:14 AM
 */
package AiProjectOne;

import java.util.LinkedList;

public class ShortestPath {

    private float totalDistance;
    private int spaceComplexity;
    private int timeComplexity;
    private LinkedList<Place> placesInThePath;

    public ShortestPath(int spaceComplexity, int timeComplexity, float totalDistance, LinkedList<Place> placesInThePath) {
        this.spaceComplexity = spaceComplexity;
        this.timeComplexity = timeComplexity;
        this.totalDistance = totalDistance;
        this.placesInThePath = placesInThePath;
    }

    public ShortestPath() {

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
