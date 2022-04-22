/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/14/2022    10:44 PM
 */
package ai_project_one;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CreatePlaceInTheMap {
    private final Circle placePosition;
    private final String placeName;
    public CreatePlaceInTheMap(String placeName, short layout_X_Map, short layout_Y_Map) {

        this.placeName=placeName;

        this.placePosition = new Circle(5);
        this.placePosition.setLayoutX(layout_X_Map);
        this.placePosition.setLayoutY(layout_Y_Map);
        this.placePosition.setStroke(Color.BLACK);
        this.placePosition.setFill(Color.RED);
        this.placePosition.setStrokeType(StrokeType.INSIDE);
        this.placePosition.toFront(); // Clickable

        // To change the design of the circle when placing a mouse arrow on it
        this.placePosition.setOnMouseEntered(e -> {
            this.placePosition.setFill(Color.BLUE);
            this.placePosition.setRadius(5.5);
        });
        // To change the design of the circle when the mouse arrow is removed from it
        this.placePosition.setOnMouseExited(e -> {

            this.placePosition.setFill(Color.RED);
            this.placePosition.setRadius(4);
        });
    }

    public Circle getPlacePosition() {
        return placePosition;
    }

    public String getPlaceName() {
        return placeName;
    }

}
