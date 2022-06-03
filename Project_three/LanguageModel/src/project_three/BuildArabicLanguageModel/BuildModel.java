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

public class BuildModel implements Runnable {

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
        HashMap<String, CorpusRecord> model = new HashMap<>();
        for (StringBuilder sb : builderArrayList) {
            for (int n = 1; n <= 3; n++) {
                for (String ngram : ngrams(n, sb.toString())){
                    if(ngram.length()<=1)continue;
                    if (model.get(ngram) == null) {
                        model.put(ngram, new CorpusRecord());
                    } else {
                        CorpusRecord corpusRecord = model.get(ngram);
                        corpusRecord.setFrequency(corpusRecord.getFrequency() + 1);
                    }
                }
            }
        }
        return model;
    }

    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i + n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append(i > start ? " " : "").append(words[i]);
        return sb.toString();
    }

    @Override
    public void run() {
        HashMap<String, CorpusRecord> arabicModel = this.constructModel();
        WriteModelToCSVFile writeModelToCSVFile = new WriteModelToCSVFile(arabicModel);
    }
}
