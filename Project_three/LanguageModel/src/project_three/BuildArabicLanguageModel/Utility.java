/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/4/2022    7:55 PM
 */
package project_three.BuildArabicLanguageModel;

import java.util.HashMap;

public class Utility {
    public static String[] ngrams(String s, int len) {
        String[] parts = s.split(" ");
        String[] result = new String[parts.length - len + 1];
        for (int i = 0; i < parts.length - len + 1; i++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < len; k++) {
                if (parts[i + k].length() <= 1) continue;
                if (k > 0) sb.append(" ");
                sb.append(parts[i + k]);
            }
            result[i] = sb.toString();
        }
        return result;
    }

    public static float calculateProbability(String[] splitter, HashMap<String, CorpusRecord> model) {
        int numerator = 0, denominator = 0;
        try {
            if (splitter.length == 2) {
                numerator = model.get(splitter[0] + " " + splitter[1]).getFrequency();
                denominator = model.get(splitter[0]).getFrequency();
                return (float) numerator / denominator;
            } else { // length -> 3
                String num = splitter[0] + " " + splitter[1] + " " + splitter[2];
                String den = splitter[0] + " " + splitter[1];
                numerator = model.get(num).getFrequency();
                denominator = model.get(den).getFrequency();
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return (float) numerator / denominator;
    }
}
