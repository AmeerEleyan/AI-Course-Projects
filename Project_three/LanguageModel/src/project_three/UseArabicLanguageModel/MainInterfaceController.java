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
import project_three.BuildArabicLanguageModel.ArabicNormalizer;
import project_three.BuildArabicLanguageModel.CorpusRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainInterfaceController implements Initializable {
    @FXML
    private TextField txt;
    @FXML
    private Label label;

    private HashMap<String, CorpusRecord> hashMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.hashMap = new HashMap<>();
        this.readCitiesRecordFromFile();
    }

    @FXML
    void getSelectedText() {

        String selectedText = txt.getSelectedText();
        selectedText = new ArabicNormalizer(selectedText).getOutput();

        String input = txt.getText();
        input = new ArabicNormalizer(input).getOutput();

        this.label.setText(this.getCandidateWords(input, selectedText));
    }


    /**
     * show the top candidate words that can be used to replace this word
     */
    private String getCandidateWords(String inputText, String selectedText) {
        return this.getResult();
    }

    private String getResult() {
        return null;
    }

    /**
     * Methode to read data from file iteratively
     */
    private void readCitiesRecordFromFile() {
        File dataset = new File("/project_three/UseArabicLanguageModel/Model.csv");
        try {
            Scanner input = new Scanner(dataset); // instance of scanner for read data from file
            if (dataset.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are No records in the " + dataset.getName() + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens
                String[] data;
                CorpusRecord corpusRecord;
                String key;
                byte gram;
                int frequency;
                float probability;
                while (input.hasNext()) { // read line of data
                    try {
                        String lineOfData = input.nextLine();

                        // get data record
                        data = lineOfData.split(",");
                        key = data[0].trim();
                        gram = Byte.parseByte(data[1].trim());
                        frequency = Integer.parseInt(data[2].trim());
                        probability = Float.parseFloat(data[3].trim());
                        corpusRecord = new CorpusRecord(gram, frequency, probability);

                        // add this recorde to the hash
                        this.hashMap.put(key, corpusRecord);

                    } catch (Exception ex) {
                        // the record in the file has a problem
                        Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + dataset.getName() + "  ");
                    } finally {
                        line++;// increment the line by one
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + dataset.getName() + "  ");
        }
    }
}
