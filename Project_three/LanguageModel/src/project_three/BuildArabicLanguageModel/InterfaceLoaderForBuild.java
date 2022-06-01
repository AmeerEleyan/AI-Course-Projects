/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 4/14/2022    11:30 PM
 */
package project_three.BuildArabicLanguageModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InterfaceLoaderForBuild extends Application {
    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("prism", "t2k");
        System.setProperty("prism.lcdtext", "false");
        window = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project_three/BuildArabicLanguageModel/LoadDataInterface.fxml")));
        window.setTitle("Build Model");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
    }
}
