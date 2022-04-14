/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022   1:02 AM
 */
package AiProjectOne;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graph {

    private final HashMap<String, Vertex> hashMap;
    private final int numberOfPlaces;
    //private final HashMap<String, DijkstraTable> table;

    public Graph(int numberOfPlaces) {
        this.hashMap = new HashMap<>();
        this.numberOfPlaces = numberOfPlaces;
      //  this.table = new HashMap<>();
    }

    public void addNewCities(Place place) {
        if (this.hashMap.size() < this.numberOfPlaces) {
            if (this.hashMap.get(place.getPlaceName()) != null) {
                Message.displayMessage("Warning", place.getPlaceName() + " is already existing");
            } else {
                this.hashMap.put(place.getPlaceName(), new Vertex(place));
              //  this.table.put(place.getPlaceName(), new DijkstraTable());
            }
        } else {
            Message.displayMessage("Warning", "You have exceeded the number of cities allowed to be added\nso " + place.getPlaceName() + " will not be added");
        }

    }

    // Find the shortest path between two places
    public ShortestPath findShortestPath(String sourcePlace, String destinationPlace) {

        // there is no adjacent fore source place. so there is no path from source city to destination city
        if (this.hashMap.get(sourcePlace).getAdjacent().size() == 0) {
            return null;
        }
        return null;
    }


    // Add adjacent to each other, because the road is two direction
    public void addAdjacent(String parentName, String adjacentName, float distance) {
        //Check them if they are in the hash
        if (this.hashMap.get(parentName.trim()) != null && this.hashMap.get(adjacentName.trim()) != null) {
            Place place = this.hashMap.get(parentName.trim()).getPlace();
            Place adjacent = this.hashMap.get(adjacentName.trim()).getPlace();
            if (this.hashMap.get(parentName.trim()).getAdjacent().contains(new Adjacent(adjacent))) {
                Message.displayMessage("Warning", parentName + " and " + adjacentName + " is already existing as neighbors");
            } else {
                this.hashMap.get(place.getPlaceName()).addAdjacent(adjacent, distance);
                this.hashMap.get(adjacent.getPlaceName()).addAdjacent(place, distance);
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex city : this.hashMap.values()) {
            result.append(city.toString()).append("\n");
        }
        return result.toString();
    }
}
