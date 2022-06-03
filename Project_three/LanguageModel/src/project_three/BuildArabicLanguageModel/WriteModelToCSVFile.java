/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/1/2022    4:47 PM
 */
package project_three.BuildArabicLanguageModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class WriteModelToCSVFile {

    public WriteModelToCSVFile(HashMap<String, CorpusRecord> model) {
        this.writeModelToCSVFile(model);
    }

    private void writeModelToCSVFile(HashMap<String, CorpusRecord> model) {
        File file;
        PrintWriter writer;
        try {
            file = new File("C:\\MyData\\3ed-Year\\SecondSemester2022\\AI\\Projects\\Project_three\\LanguageModel\\src\\project_three\\UseArabicLanguageModel\\Model.csv");
            writer = new PrintWriter(file);

            model.forEach((k, v) -> writer.println(k + ", " + v.getFrequency() + ", " + v.getProbability()));
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
