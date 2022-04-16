/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022    11:42 PM
 */
package AiProjectOne;

public class Distance implements Comparable<Distance> {
    private String place;
    private float actualDistance;
    private float airDistance;

    public Distance() {

    }

    public Distance(String place, float actualDistance, float airDistance) {
        this.place = place;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public int compareTo(Distance o) {
        return Float.compare((actualDistance + airDistance), (o.actualDistance + o.airDistance));
    }
}
