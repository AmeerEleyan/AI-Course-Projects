/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/1/2022    6:25 PM
 */
package project_three.BuildArabicLanguageModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class LoadDataInterfaceController implements Initializable {
    @FXML
    private Button btBrowse;

    @FXML
    private Button btBuildModel;

    private List<File> fileList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fileList = new ArrayList<>();
    }

    @FXML
    void handleBtBrowse() {
        this.btBuildModel.setVisible(false);
        this.uploadFiles();
        this.btBuildModel.setVisible(true);
    }

    // upload files using a browser
    public void uploadFiles() {

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> fileList = fileChooser.showOpenMultipleDialog(InterfaceLoaderForBuild.window.getScene().getWindow());

        if (fileList != null) {
            this.fileList.addAll(fileList);
        }
    }

    @FXML
    void handleBtBuildModel() {
        BuildModel model = new BuildModel(this.fileList);
        HashMap<String, CorpusRecord> arabicModel = model.constructModel();
        WriteModelToCSVFile writeModelToCSVFile = new WriteModelToCSVFile(arabicModel);
    }

}
