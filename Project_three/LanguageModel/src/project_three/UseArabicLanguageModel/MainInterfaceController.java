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
import project_three.BuildArabicLanguageModel.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainInterfaceController implements Initializable {
    @FXML
    private TextField txt;
    @FXML
    private Label label;

    private HashMap<String, CorpusRecord> model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new HashMap<>();
        this.uploadModel();
        this.filterKeys("حيث");
    }

    @FXML
    void getSelectedText() {

        String selectedText = txt.getSelectedText();
        selectedText = new ArabicNormalizer(selectedText).getOutput();

        String input = txt.getText();
        input = new ArabicNormalizer(input).getOutput();

        this.displayCandidateWords(this.getCandidateWords(input, selectedText));
    }

    private void displayCandidateWords(ArrayList<CandidateWords> candidateWords) {
        sortCandidateWords(candidateWords);
        StringBuilder sb = new StringBuilder();
        for (CandidateWords candidateWord : candidateWords) {
            sb.append(candidateWord.probability).append(" ").append(candidateWord.candidateWord).append("\n");
        }
        this.label.setText(String.valueOf(sb));
    }

    // Sort the list depending on the probability field value
    private void sortCandidateWords(ArrayList<CandidateWords> candidateWords) {
        candidateWords.sort((CandidateWords c1, CandidateWords c2) -> Float.compare(c1.probability, c2.probability));
    }

    private record CandidateWords(String candidateWord, float probability) {
    }

    /**
     * show the top candidate words that can be used to replace this word
     */
    private ArrayList<CandidateWords> getCandidateWords(String inputText, String selectedWord) {
        ArrayList<String> allWordsAfterPreviousOfSelectedWord = getAllWordsAfterPreviousOfSelectedWord(selectedWord);
        ArrayList<CandidateWords> finalResults = new ArrayList<>();
        for (String suggestionWord : allWordsAfterPreviousOfSelectedWord) {
            ArrayList<String> enteredTextAfterSplitting = new ArrayList<>();
            for (int n = 1; n <= 3; n++) {
                enteredTextAfterSplitting.addAll(Arrays.asList(Utility.ngrams(inputText, n)));
            }
            replaceInputTextWithCandidateWord(enteredTextAfterSplitting, selectedWord, suggestionWord);
            //calculate probability
        }


        return null;
    }

    private void replaceInputTextWithCandidateWord(ArrayList<String> enteredTextAfterSplitting, String selectedWord, String suggestionWord) {
        String[] splitter;
        for (int i = 0; i < enteredTextAfterSplitting.size(); i++) {
            splitter = enteredTextAfterSplitting.get(i).split(" ");
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < splitter.length; j++) {
                if (j > 0) {
                    str.append(" ");
                }
                if (splitter[j].equals(selectedWord)) {
                    str.append(suggestionWord);
                } else {
                    str.append(splitter[j]);
                }
            }
            enteredTextAfterSplitting.set(i, str.toString());
        }
    }

    private ArrayList<String> getAllWordsAfterPreviousOfSelectedWord(String selectedWord) {
        Map<String, CorpusRecord> model = this.filterKeys(selectedWord);
        ArrayList<String> nextWords = new ArrayList<>();
        model.forEach((k, v) -> {
            String[] splitter = k.split(" ");
            //to get the next word pf the previous word
            int indexOfNextWord = this.searchForTheWordInArray(splitter, selectedWord) + 1;
            if (indexOfNextWord < splitter.length) {
                nextWords.add(splitter[indexOfNextWord]);
            }
        });
        return nextWords;
    }

    private Map<String, CorpusRecord> filterKeys(String word) {

        return this.model.entrySet()
                .stream()
                .filter(map -> map.getKey().contains(word))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private int searchForTheWordInArray(String[] splitter, String previousWord) {
        for (int i = 0; i < splitter.length; i++) {
            if (splitter[i].equals(previousWord))
                return i;
        }
        return -1;
    }

    /**
     * Methode to read data from file iteratively
     */
    private void uploadModel() {
        File dataset = new File("C:\\MyData\\3ed-Year\\SecondSemester2022\\AI\\Projects\\Project_three\\LanguageModel\\src\\project_three\\UseArabicLanguageModel\\Model.csv");
        try {
            Scanner input = new Scanner(dataset); // instance of scanner for read data from file
            if (dataset.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are No records in the " + dataset.getName() + " ");
            } else {
                while (input.hasNext()) { // read line of data
                    this.createRecord(input.nextLine());
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + dataset.getName() + "  ");
        }
    }

    private void createRecord(String lineOfData) {
        int line = 1; // represent line on the file to display in which line has problem If that happens
        String[] data;
        CorpusRecord corpusRecord;
        String key;
        int frequency;
        float probability;
        try {

            // get data record
            data = lineOfData.split(",");
            key = data[0].trim();
            frequency = Integer.parseInt(data[1].trim());
            probability = Float.parseFloat(data[2].trim());
            corpusRecord = new CorpusRecord(frequency, probability);

            // add this recorde to the hash
            this.model.put(key, corpusRecord);
            line++;// increment the line by one
        } catch (Exception ex) {
            // the record in the file has a problem
            Message.displayMessage("Warning", " Invalid format in line # " + line + " in file Model.csv");
        }
    }
}
