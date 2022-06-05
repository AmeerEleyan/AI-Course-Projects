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

    private HashMap<String, CorpusRecord> model;
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
                sb.append(line).append(" ");
            }
        } catch (Exception e) {
            Message.displayMessage("Warning", e.getMessage());
        }
        return sb;
    }

    private ArrayList<StringBuilder> dataToBuilderArrayList() {

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

        return builderArrayList;
    }

    private void buildModel(ArrayList<StringBuilder> builderArrayList) {

        this.model = new HashMap<>();

        for (StringBuilder sb : builderArrayList) {
            for (int n = 1; n <= 3; n++) {
                String[] ngrams = Utility.ngrams(sb.toString(), n);
                if (ngrams == null) continue;
                for (String ngram : ngrams) {
                    if (ngram.length() <= 1) continue;
                    if (this.model.get(ngram) == null) {
                        this.model.put(ngram.trim(), new CorpusRecord());
                    } else {
                        CorpusRecord corpusRecord = this.model.get(ngram);
                        corpusRecord.setFrequency(corpusRecord.getFrequency() + 1);
                    }
                }
            }
        }

    }

    private void assignProbabilitiesForTheModel() {
        // using for-each loop for iteration over Map.entrySet()
        this.model.forEach((k, v) -> {
            String[] splitter = k.split(" ");
            if (splitter.length == 1) v.setProbability(1);
            else {
                v.setProbability(this.calculateProbability(splitter));
            }
        });

    }

    private float calculateProbability(String[] splitter) {
        int numerator = 0, denominator = 0;
        try {
            if (splitter.length == 2) {
                numerator = this.model.get(splitter[0] + " " + splitter[1]).getFrequency();
                denominator = this.model.get(splitter[0]).getFrequency();
                return (float) numerator / denominator;
            } else { // length -> 3
                String num = splitter[0] + " " + splitter[1] + " " + splitter[2];
                String den = splitter[0] + " " + splitter[1];
                numerator = this.model.get(num).getFrequency();
                denominator = this.model.get(den).getFrequency();
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return (float) numerator / denominator;
    }

    @Override
    public void run() {
        this.buildModel(this.dataToBuilderArrayList());
        this.assignProbabilitiesForTheModel();
        WriteModelToCSVFile writeModelToCSVFile = new WriteModelToCSVFile();
        writeModelToCSVFile.writeModelToCSVFile(this.model);
    }
}
