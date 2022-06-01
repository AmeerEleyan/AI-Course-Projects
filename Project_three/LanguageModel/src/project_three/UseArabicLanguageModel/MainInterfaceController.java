/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/1/2022    5:14 PM
 */
package project_three.UseArabicLanguageModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainInterfaceController implements Initializable {
    @FXML
    private TextField txt;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void getSelectedText() {
        this.label.setText(txt.getSelectedText());
    }
}
