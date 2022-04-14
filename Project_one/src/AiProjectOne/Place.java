/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/14/2022    10:29 PM
 */
package AiProjectOne;

public class Place implements Comparable<Place> {

    // Attribute
    private final String placeName;

    // Position of town in map
    private short layout_X_Map;
    private short layout_Y_Map;

    // Empty constructor
    public Place(String placeName) {
        this.placeName = placeName.trim();
    }

    // Constructor with parameters
    public Place(String placeName, short layout_X_Map, short layout_Y_Map) {
        this.placeName = placeName.trim();
        this.layout_X_Map = layout_X_Map;
        this.layout_Y_Map = layout_Y_Map;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getLayouts_Map() {
        return this.layout_X_Map + " " + this.layout_Y_Map;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + placeName +
                ", layout_X_Map=" + layout_X_Map +
                ", layout_Y_Map=" + layout_Y_Map +
                '}';
    }

    @Override
    public int compareTo(Place other) {
        return placeName.compareTo(other.placeName);
    }

}
