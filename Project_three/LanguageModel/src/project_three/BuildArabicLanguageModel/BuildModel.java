/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/1/2022    4:46 PM
 */
package project_three.BuildArabicLanguageModel;

import project_three.UseArabicLanguageModel.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuildModel {

    private final List<File> fileList;

    public BuildModel(List<File> fileList) {
        this.fileList = fileList;
    }

    private StringBuilder readFile(File file) {

        StringBuilder sb = new StringBuilder();
        try {
            String line;
            InputStream fileInputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8); // leave charset out for default
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            Message.displayMessage("Warning", e.getMessage());
        }
        return sb;
    }

    private StringBuilder qualityChecker(StringBuilder data) {
        return null;
    }

    public HashMap<String, Corpus> constructModel() {
        ArrayList<StringBuilder> builderArrayList = new ArrayList<>();
        StringBuilder dataAfterQualityChecker;

        for (File file : this.fileList) {
            dataAfterQualityChecker = this.qualityChecker(this.readFile(file));
            builderArrayList.add(dataAfterQualityChecker);
        }

        return this.buildModel(builderArrayList);
    }
    
    private HashMap<String, Corpus> buildModel(ArrayList<StringBuilder> builderArrayList) {
        return null;
    }

}
