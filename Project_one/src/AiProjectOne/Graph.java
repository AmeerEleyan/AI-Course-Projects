/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022   1:02 AM
 */
package AiProjectOne;

import java.util.HashMap;

public class Graph {

    private final HashMap<String, Node> hashMap;
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
                this.hashMap.put(place.getPlaceName(), new Node(place));
                //  this.table.put(place.getPlaceName(), new DijkstraTable());
            }
        } else {
            Message.displayMessage("Warning", "You have exceeded the number of cities allowed to be added\nso " + place.getPlaceName() + " will not be added");
        }

    }

    // Find the shortest path between two places
    public ShortestPath findShortestPath(String sourcePlace, String destinationPlace) {
        // there is no adjacent fore source place. so there is no path from source city to destination city
        if (this.hashMap.get(sourcePlace)==null) {
            throw new RuntimeException("Source place does not exist");
        }else if(this.hashMap.get(destinationPlace)==null){
            throw new RuntimeException("Destination place does not exist");
        }else if(this.hashMap.get(sourcePlace).getAdjacent().size() == 0){
            throw new RuntimeException("Can not move from this place to another place");
        }

        return null;
    }


    // Add adjacent to each other, because the road is two direction
    public void addBranch(String parentName, String adjacentName, float distance) {
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

    private float calculateHeuristic(Place src, Place destination) {

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

        float distance = Math.max(Math.abs(src.getLayout_X_Map() - destination.getLayout_X_Map()), Math.abs(src.getLayout_Y_Map() - destination.getLayout_Y_Map()));

        return distance * 0.0377F;

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Node city : this.hashMap.values()) {
            result.append(city.toString()).append("\n");
        }
        return result.toString();
    }
}
