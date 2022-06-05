/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/4/2022    7:55 PM
 */
package project_three.BuildArabicLanguageModel;

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

}
