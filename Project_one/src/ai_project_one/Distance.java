/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/15/2022    11:42 PM
 */
package ai_project_one;

public class Distance implements Comparable<Distance> {
    private final String placeName;
    private String parentName;
    // is sum of the g and heuristic
    private float f = Float.MAX_VALUE;
    //is something we can (and do) calculate at any given step, and it's the distance between start and n
    private float g = Float.MAX_VALUE;

    public Distance(String placeName) {
        this.placeName = placeName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Distance(String placeName, float f, float g) {
        this.placeName = placeName;
        this.f = f;
        this.g = g;
    }

    public float getDistance() {
        return this.f + this.g;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public String getPlaceName() {
        return placeName;
    }

    @Override
    public int compareTo(Distance o) {
        return Float.compare(this.getDistance(), o.getDistance());
    }

    @Override
    public boolean equals(Object obj) {
        return this.placeName.equals(((Distance) obj).placeName);
    }
}
