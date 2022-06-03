/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/1/2022    6:25 PM
 */
package project_three.BuildArabicLanguageModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoadDataInterfaceController implements Initializable {
    @FXML
    private Button btBrowse;

    @FXML
    private Button btBuildModel;

    private List<File> fileList;

    @FXML
    private Label lblMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fileList = new ArrayList<>();
        this.btBuildModel.setVisible(false);
    }

    @FXML
    void handleBtBrowse() {
        this.btBuildModel.setVisible(false);
        this.uploadFiles();
        this.btBuildModel.setVisible(true);
    }

    // upload files using a browser
    public void uploadFiles() {
        this.btBuildModel.setVisible(false);
        // File Chooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> fileList = fileChooser.showOpenMultipleDialog(InterfaceLoaderForBuild.window.getScene().getWindow());

        if (fileList != null) {
            this.fileList.addAll(fileList);
        }
        this.btBrowse.setText("Add new files");
        this.btBuildModel.setVisible(true);
    }

    @FXML
    void handleBtBuildModel() {
        BuildModel model = new BuildModel(this.fileList);
        Thread thread = new Thread(model);
        thread.start();
        this.lblMessage.setVisible(true);
    }

}
