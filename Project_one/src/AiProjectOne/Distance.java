/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022    11:42 PM
 */
package AiProjectOne;

public class Distance implements Comparable<Distance> {
    private String placeName;
    private float actualDistance;
    private float airDistance;

    public Distance() {

    }

    public Distance(String placeName, float actualDistance, float airDistance) {
        this.placeName = placeName;
        this.actualDistance = actualDistance;
        this.airDistance = airDistance;
    }

    public float getDistance() {
        return this.actualDistance + this.airDistance;
    }

    public float getActualDistance() {
        return actualDistance;
    }

    public void setActualDistance(float actualDistance) {
        this.actualDistance = actualDistance;
    }

    public float getAirDistance() {
        return airDistance;
    }

    public void setAirDistance(float airDistance) {
        this.airDistance = airDistance;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public int compareTo(Distance o) {
        return Float.compare(this.getDistance(), o.getDistance());
    }
}
