/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/1/2022    4:47 PM
 */
package project_three.BuildArabicLanguageModel;

import project_three.UseArabicLanguageModel.Message;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class WriteModelToCSVFile {

    public WriteModelToCSVFile(HashMap<String, CorpusRecord> model) {
        this.writeModelToCSVFile(model);
    }

    private void writeModelToCSVFile(HashMap<String, CorpusRecord> model) {

        try (PrintWriter writer = new PrintWriter("/project_three/UseArabicLanguageModel/Model.csv")) {

            StringBuilder sb = new StringBuilder();

            model.forEach((k, v) -> {
                sb.append(k);
                sb.append(',');
                sb.append(v.gram());
                sb.append(',');
                sb.append(v.frequency());
                sb.append(',');
                sb.append(v.probability());
                sb.append('\n');
            });
            writer.write(sb.toString());
            Message.displayMessage("Successfully", "Model was build successfully");
        } catch (FileNotFoundException e) {
            Message.displayMessage("Warning", e.getMessage());
        }
    }
}
