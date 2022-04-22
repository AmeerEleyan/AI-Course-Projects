/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022   1:02 AM
 */
package ai_project_one;

import java.util.*;

public class Graph {

    private final HashMap<String, Vertex> hashMap;
    private final int numberOfPlaces;

    public record ShortestPath(int spaceComplexity, int timeComplexity, float totalDistance,
                               LinkedList<Place> placesInThePath) {
    }

    public Graph(int numberOfPlaces) {
        this.hashMap = new HashMap<>();
        this.numberOfPlaces = numberOfPlaces;
    }

    public void addNewPlace(Place place) {
        if (this.hashMap.size() < this.numberOfPlaces) {
            if (this.hashMap.get(place.getPlaceName()) != null) {
                Message.displayMessage("Warning", place.getPlaceName() + " is already existing");
            } else {
                this.hashMap.put(place.getPlaceName(), new Vertex(place));
            }
        } else {
            Message.displayMessage("Warning", "You have exceeded the number of places allowed to be added\nso " + place.getPlaceName() + " will not be added");
        }
    }

    // new version from the shortest path
    public ShortestPath findShortestPath_AStarAlgorithm(String sourcePlaceName, String destinationPlaceName) {

        // there is no adjacent fore source place. so there is no path from source place to destination place
        if (this.hashMap.get(sourcePlaceName) == null) {
            throw new RuntimeException("Source place does not exist");
        } else if (this.hashMap.get(destinationPlaceName) == null) {
            throw new RuntimeException("Destination place does not exist");
        } else if (this.hashMap.get(sourcePlaceName).getAdjacent().size() == 0) {
            throw new RuntimeException("Can not move from this place to another place");
        }

        // Contains the places that will be processed from the source to destination
        Map<String, Distance> map = new HashMap<>();
        map.put(sourcePlaceName, new Distance(sourcePlaceName));
        map.put(destinationPlaceName, new Distance(destinationPlaceName));

        // Contains the places that were visited during the search for the destination
        LinkedHashSet<Distance> visitedPlaces = new LinkedHashSet<>();

        // Contains nodes that we've encountered, but haven't analyzed yet
        PriorityQueue<Distance> heap = new PriorityQueue<>();

        Place destinationPlace = this.hashMap.get(destinationPlaceName).getPlace();

        Distance start = map.get(sourcePlaceName);
        start.setG(0);
        start.setF(start.getG() + calculateHeuristic(this.hashMap.get(sourcePlaceName).getPlace(), destinationPlace));
        heap.add(start);

        Distance end = map.get(destinationPlaceName);

        float totalDistance;
        short spaceComplexity = 1, timeComplexity = 0;
        Vertex edgeVertex;
        Distance distance;
        Distance currentPlace = null;

        while (!heap.isEmpty()) {

            currentPlace = heap.poll();

            timeComplexity++;
            if (currentPlace.equals(end)) {
                //The destination was found
                break;
            }
            // Processing all adjacent places of the current palace
            for (Edge adjacentPlace : this.hashMap.get(currentPlace.getPlaceName()).getAdjacent()) {

                totalDistance = currentPlace.getG() + adjacentPlace.getDistance();
                edgeVertex = this.hashMap.get(adjacentPlace.getAdjacentPlace().getPlaceName());

                // Check if an adjacent place exists on the map( was processed previously)
                if (map.get(adjacentPlace.getAdjacentPlace().getPlaceName()) == null) {
                    map.put(adjacentPlace.getAdjacentPlace().getPlaceName(), new Distance(adjacentPlace.getAdjacentPlace().getPlaceName()));
                }

                distance = map.get(adjacentPlace.getAdjacentPlace().getPlaceName());

                timeComplexity++;
                if (!heap.contains(distance) && !visitedPlaces.contains(distance)) {

                    distance.setParentName(currentPlace.getPlaceName());
                    distance.setG(totalDistance);
                    distance.setF(distance.getG() + calculateHeuristic(edgeVertex.getPlace(), destinationPlace));
                    heap.add(distance);
                } else {

                    timeComplexity++;
                    if (totalDistance < distance.getG()) {
                        distance.setParentName(currentPlace.getPlaceName());
                        distance.setG(totalDistance);
                        distance.setF(distance.getG() + calculateHeuristic(edgeVertex.getPlace(), destinationPlace));

                    }
                }
            }
            // To calculate space complexity as required in the project
            if (heap.size() > spaceComplexity) {
                spaceComplexity = (short) (heap.size());
            }
            // Current place was visited
            visitedPlaces.add(currentPlace);
        }

        // We could not reach the destination
        if (map.get(destinationPlaceName) == null || map.get(destinationPlaceName).getParentName() == null) {
            throw new RuntimeException("There is no path to " + destinationPlace);
        }

        // Get all places in the path from src to dest place
        LinkedList<Place> placesInThePath = getPlacesInPath(map, destinationPlaceName);
        placesInThePath.addFirst(this.hashMap.get(sourcePlaceName).getPlace());

        assert currentPlace != null;
        return new ShortestPath(spaceComplexity, timeComplexity, currentPlace.getF(), placesInThePath);

    }

    // Get all places in the path from src to dest place
    private LinkedList<Place> getPlacesInPath(Map<String, Distance> map, String destinationPlace) {
        LinkedList<Place> listPath = new LinkedList<>();
        Distance distance = map.get(destinationPlace);
        while (distance.getParentName() != null) {
            listPath.addFirst(this.hashMap.get(distance.getPlaceName()).getPlace());
            distance = map.get(distance.getParentName());
        }
        return listPath;
    }

    // Add adjacent to each other, because the road is two direction
    public void addBranch(String parentName, String adjacentName, float distance) {
        //Check them if they are in the hash
        if (this.hashMap.get(parentName.trim()) != null && this.hashMap.get(adjacentName.trim()) != null) {
            Place place = this.hashMap.get(parentName.trim()).getPlace();
            Place adjacent = this.hashMap.get(adjacentName.trim()).getPlace();
            if (this.hashMap.get(parentName.trim()).getAdjacent().contains(new Edge(adjacent))) {
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
        for (Vertex place : this.hashMap.values()) {
            result.append(place.toString()).append("\n");
        }
        return result.toString();
    }
}
