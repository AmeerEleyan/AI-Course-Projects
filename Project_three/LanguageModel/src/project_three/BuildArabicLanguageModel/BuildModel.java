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


    public HashMap<String, CorpusRecord> constructModel() {
        ArrayList<StringBuilder> builderArrayList = new ArrayList<>();
        StringBuilder dataAfterQualityChecker;
        String dataAsString;
        ArabicNormalizer arabicNormalizer;
        for (File file : this.fileList) {
            dataAsString = this.readFile(file).toString();
            arabicNormalizer = new ArabicNormalizer(dataAsString);
            dataAfterQualityChecker = new StringBuilder(arabicNormalizer.getOutput());
            builderArrayList.add(dataAfterQualityChecker);
        }

        return this.buildModel(builderArrayList);
    }

    private HashMap<String, CorpusRecord> buildModel(ArrayList<StringBuilder> builderArrayList) {
        return null;
    }

}
