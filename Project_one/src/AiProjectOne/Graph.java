/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022   1:02 AM
 */
package AiProjectOne;

import javafx.util.Pair;

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
    public ShortestPath findShortestPath_AStarAlgorithm(String sourcePlace, String destinationPlace) {

        // there is no adjacent fore source place. so there is no path from source place to destination place
        if (this.hashMap.get(sourcePlace) == null) {
            throw new RuntimeException("Source place does not exist");
        } else if (this.hashMap.get(destinationPlace) == null) {
            throw new RuntimeException("Destination place does not exist");
        } else if (this.hashMap.get(sourcePlace).getAdjacent().size() == 0) {
            throw new RuntimeException("Can not move from this place to another place");
        }

        PriorityQueue<Distance> closedList = new PriorityQueue<>();
        PriorityQueue<Distance> openList = new PriorityQueue<>();

        Map<String, Pair<String, String>> path = new HashMap<>();
        path.put(sourcePlace, null);

        Distance start = new Distance(this.hashMap.get(sourcePlace).getPlace().getPlaceName());
        Distance end = new Distance(this.hashMap.get(destinationPlace).getPlace().getPlaceName());

        start.setF(start.getG() + calculateHeuristic(this.hashMap.get(sourcePlace).getPlace(), this.hashMap.get(destinationPlace).getPlace()));
        openList.add(start);

        float totalDistance;
        short spaceComplexity = 1, timeComplexity = 0;
        Vertex edgeVertex;
        Distance distance;
        Distance currentPlace = null;

        while (!openList.isEmpty()) {

            currentPlace = openList.poll();

            timeComplexity++;
            if (currentPlace.equals(end)) {
                break;
            }
            for (Edge edge : this.hashMap.get(currentPlace.getPlaceName()).getAdjacent()) {

                totalDistance = currentPlace.getG() + edge.getDistance();
                edgeVertex = this.hashMap.get(edge.getAdjacentPlace().getPlaceName());
                distance = new Distance(this.hashMap.get(edge.getAdjacentPlace().getPlaceName()).getPlace().getPlaceName());

                timeComplexity++;
                if (!openList.contains(distance) && !closedList.contains(distance)) {

                    path.put(edgeVertex.getPlace().getPlaceName(), new Pair<>(edgeVertex.getPlace().getPlaceName(), currentPlace.getPlaceName()));
                    distance.setG(totalDistance);
                    distance.setF(distance.getG() + calculateHeuristic(edgeVertex.getPlace(), this.hashMap.get(destinationPlace).getPlace()));
                    openList.add(distance);
                } else {
                    timeComplexity++;
                    if (totalDistance < distance.getG()) {

                        path.put(edgeVertex.getPlace().getPlaceName(), new Pair<>(edgeVertex.getPlace().getPlaceName(), currentPlace.getPlaceName()));
                        distance.setG(totalDistance);
                        distance.setF(distance.getG() + calculateHeuristic(edgeVertex.getPlace(), this.hashMap.get(destinationPlace).getPlace()));

                        timeComplexity++;
                        if (closedList.contains(distance)) {
                            closedList.remove(distance);
                            openList.add(distance);
                        }
                    }
                }
            }
            if (openList.size() + closedList.size() > spaceComplexity) {
                spaceComplexity = (short) (openList.size() + closedList.size());
            }
            closedList.add(currentPlace);
        }

        if (path.get(destinationPlace) == null || path.get(destinationPlace).getValue() == null) {
            throw new RuntimeException("There is no path to " + destinationPlace);
        }

        LinkedList<Place> placesInThePath = getPlacesInThePath(path, destinationPlace);
        placesInThePath.addFirst(this.hashMap.get(sourcePlace).getPlace());

        return new ShortestPath(spaceComplexity, timeComplexity, currentPlace.getF(), placesInThePath);

    }

    private LinkedList<Place> getPlacesInThePath(Map<String, Pair<String, String>> path, String destinationPlace) {
        LinkedList<Place> listPath = new LinkedList<>();
        String place = destinationPlace;
        listPath.addFirst(this.hashMap.get(place).getPlace());
        while (path.get(path.get(place).getValue()) != null) {
            place = path.get(path.get(place).getValue()).getKey();
            listPath.addFirst(this.hashMap.get(place).getPlace());
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
