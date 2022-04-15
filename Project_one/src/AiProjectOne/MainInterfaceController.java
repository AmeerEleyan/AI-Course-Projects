/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/14/2022    10:32 PM
 */
package AiProjectOne;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainInterfaceController implements Initializable {

    @FXML // fx:id="anchorPane"
    private AnchorPane anchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="combDestinationPlace"
    private ComboBox<String> combDestinationPlace; // Value injected by FXMLLoader

    @FXML // fx:id="combSourcePlace"
    private ComboBox<String> combSourcePlace; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistance"
    private TextField txtDistance; // Value injected by FXMLLoader

    @FXML // fx:id="txtPath"
    private TextArea txtPath; // Value injected by FXMLLoader

    @FXML // fx:id="txtSpaceComplexity"
    private TextField txtSpaceComplexity; // Value injected by FXMLLoader

    @FXML // fx:id="txtTimeComplexity"
    private TextField txtTimeComplexity; // Value injected by FXMLLoader

    // all lines(path) between source and destination
    private ObservableList<Line> linesForPath;

    private HashMap<String, CreatePlaceInTheMap> placesInMap; // all places in the map

    private Graph graphPlace; // Map

    private String[] placesNames,// places name in the map
            placesInPath;  // places in the path

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.readPlacesRecordFromFile();
        this.readPlacesAdjacentFromFile();
        this.loadPlacesNameToComboBox();
    }

    public void handleBtGo() {
        this.handleBtAnotherPath();
        // The source city not selected
        if (this.combSourcePlace.getValue() == null) {
            Message.displayMessage("Warning", "Please select the source city");
            return;
        }
        // The destination city not selected
        if (this.combDestinationPlace.getValue() == null) {
            Message.displayMessage("Warning", "Please select the destination city");
            return;
        }

        String sourceCity = this.combSourcePlace.getValue();
        String destinationCity = this.combDestinationPlace.getValue();

        //the source city is the same destination city
        if (sourceCity.equals(destinationCity)) {
            Message.displayMessage("Warning", "the source city is the same destination city\nso the distance 0.0 Km");
        } else {
            ShortestPath shortestPath = this.graphPlace.findShortestPath(sourceCity, destinationCity);
            if (shortestPath != null) {
                this.handleBtAnotherPath();
                this.txtDistance.setText(String.format("%.2f Km", shortestPath.getTotalDistance()));
                String citiesInThePath = getPlacesInThePathAsString(shortestPath.getPlacesInThePath());
                this.txtPath.setText(citiesInThePath);
                this.drawPath(shortestPath.getPlacesInThePath());
                this.txtSpaceComplexity.setText(shortestPath.getSpaceComplexity()+"");
                this.txtTimeComplexity.setText(shortestPath.getTimeComplexity()+"");

            } else {// There is no path between the source and destination
                Message.displayMessage("Warning", "There is no path between " + sourceCity + " and " + destinationCity);
            }
        }
    }

    // Reset everything to default
    public void handleBtAnotherPath() {
        this.txtDistance.setText("0.0 Km");
        this.txtSpaceComplexity.setText("0");
        this.txtTimeComplexity.setText("0");
        this.txtPath.clear();
        if (linesForPath != null) {
            this.anchorPane.getChildren().removeAll(linesForPath); // remove lineFromPath
            this.linesForPath = null;
            this.reSetColorsForPlacesInThePath();
            this.placesInPath = null;
        }
    }

    // Methode to read data from file iteratively
    private void readPlacesRecordFromFile() {
        File places = new File("Places.csv");
        int numberOfTowns ;
        try {
            Scanner input = new Scanner(places);
            if (places.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are No records in the " + places.getName() + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens
                String[] data;
                String placeName;
                Place place;
                short layout_X_Map, layout_Y_Map;
                int indexPlaceName = 0;
                String placePosition;
                while (input.hasNext()) { // read line of data
                    try {
                        String lineOfData = input.nextLine();
                        // get data of this place
                        data = lineOfData.split(",");
                        if (line == 1) {
                            // get number of places and initialize the graph and hashMap
                            numberOfTowns = Integer.parseInt(data[0].trim());
                            this.graphPlace = new Graph(numberOfTowns);
                            this.placesInMap = new HashMap<>(numberOfTowns);
                            this.placesNames = new String[numberOfTowns];

                        } else {
                            placeName = data[0].trim();
                            layout_X_Map = Short.parseShort(data[1].trim());
                            layout_Y_Map = Short.parseShort(data[2].trim());
                            place = new Place(placeName, layout_X_Map, layout_Y_Map);
                            this.graphPlace.addNewCities(place);
                            this.placesNames[indexPlaceName++] = placeName;

                            // add this place in the map and store in hash to access it later
                            CreatePlaceInTheMap placeInMap = new CreatePlaceInTheMap(placeName,layout_X_Map, layout_Y_Map);
                            placePosition = layout_X_Map + " " + layout_Y_Map;
                            this.placesInMap.put(placePosition, placeInMap);
                            this.anchorPane.getChildren().add(placeInMap.getPlacePosition());
                        }

                    } catch (Exception ex) {
                        // the record in the file has a problem
                        Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + places.getName() + "  ");
                    } finally {
                        line++;// increment the line by one
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + places.getName() + "  ");
        }
    }

    // Methode to read places adjacent from file iteratively
    private void readPlacesAdjacentFromFile(){
        File roads = new File("Roads.csv");
        try {
            Scanner input = new Scanner(roads);
            if (roads.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are No records in the " + roads.getName() + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens
                String[] data;
                float distance;
                while (input.hasNext()) { // read line of data
                    try {
                        String lineOfData = input.nextLine();
                        data = lineOfData.split(",");
                        if (!data[0].equals(data[1])) {
                            distance=Float.parseFloat(data[2].trim());
                            this.graphPlace.addBranch(data[0], data[1], distance);
                        } else {
                            Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + roads.getName() + "\n Neighbors are the same");
                        }
                    } catch (Exception ex) {
                        // the record in the file has a problem
                        Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + roads.getName() + "  ");
                    } finally {
                        line++;// increment the line by one
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + roads.getName() + "  ");
        }
    }

    // To load places names from file to tha combo box
    private void loadPlacesNameToComboBox() {
        for (String placeName : this.placesNames) {
            this.combSourcePlace.getItems().add(placeName);
            this.combDestinationPlace.getItems().add(placeName);
        }
    }

    // To detect which is the source and destination place
    @FXML
    void handleGetPlace(MouseEvent event) {
        short x, y;
        Node node;
        String placeName;
        node = event.getPickResult().getIntersectedNode();
        if (node instanceof Circle) {
            x = (short) node.getLayoutX();
            y = (short) node.getLayoutY();
            // to detect which is the source place
            if (event.getButton() == MouseButton.PRIMARY) {
                this.handleBtAnotherPath();
                placeName = this.placesInMap.get(x + " " + y).getPlaceName();
                this.combSourcePlace.setValue(placeName);

            }
            // to detect which is the destination place
            if (event.getButton() == MouseButton.SECONDARY) {
                placeName = this.placesInMap.get(x + " " + y).getPlaceName();
                this.combDestinationPlace.setValue(placeName);
            }
        }

    }
    private void drawPath(LinkedList<Place> placesInThePath) {
        Line line; // line between two place
        this.placesInPath = new String[placesInThePath.size()]; // to coloring the places that in the path
        this.linesForPath = FXCollections.observableArrayList(); // all lines in the path
        int i;
        for (i = 0; i < placesInThePath.size() - 1; i++) {
            line = new Line();

            placesInPath[i] = placesInThePath.get(i).getLayouts_Map();
            this.placesInMap.get(placesInThePath.get(i).getLayouts_Map()).getPlacePosition().setFill(Color.BLUE);
            this.placesInMap.get(placesInThePath.get(i + 1).getLayouts_Map()).getPlacePosition().setFill(Color.BLUE);

            CreatePlaceInTheMap src = this.placesInMap.get(placesInThePath.get(i).getLayouts_Map());
            CreatePlaceInTheMap dist = this.placesInMap.get(placesInThePath.get(i + 1).getLayouts_Map());

            line.setStartX(src.getPlacePosition().getLayoutX());
            line.setStartY(src.getPlacePosition().getLayoutY());

            line.setEndX(dist.getPlacePosition().getLayoutX());
            line.setEndY(dist.getPlacePosition().getLayoutY());
            line.setStroke(Color.BLUE);
            this.linesForPath.add(line);
        }
        placesInPath[i] = placesInThePath.get(i).getLayouts_Map(); // add the last place in the path to tha array
        anchorPane.getChildren().addAll(this.linesForPath); // add all line in the anchor pane to draw tha path
    }

    // reset color of the places that in the path to the origin color
    private void reSetColorsForPlacesInThePath() {
        for (String c : placesInPath) {
            this.placesInMap.get(c).getPlacePosition().setFill(Color.RED);
        }
    }

    // get places in the path as a string
    private String getPlacesInThePathAsString(LinkedList<Place> placesInThePath) {
        StringBuilder path = new StringBuilder("Start: ");
        byte newLine = 0;
        for (Place place : placesInThePath) {
            // To style the text area
            if (newLine == 4) {
                path.append("\n");
                newLine = 0;
            }
            path.append(place.getPlaceName()).append(", ");
            newLine++;

        }
        return path.substring(0, path.length() - 2);
    }
}
