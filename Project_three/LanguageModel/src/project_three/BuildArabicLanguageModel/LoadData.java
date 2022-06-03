/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: 6/1/2022    4:45 PM
 */
package project_three.BuildArabicLanguageModel;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LoadData {

    public static void main(String[] args) throws IOException {
        Map<String, String> hmap = new HashMap<String, String>();
        hmap.put("xy z", "Apple");
        hmap.put("fd", "Orange");
        hmap.put("g xy", "Kiwi");
        hmap.put("xxy", "Banana");

        Map<String, String> result = hmap.entrySet()
                .stream()
                .filter(map -> map.getKey().toString().contains("xy"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        System.out.println("Result: " +  result.keySet());
    }
    private static void readBabyRecordFromFile() {

        try {
            Scanner input = new Scanner(new File("C:\\MyData\\3ed-Year\\SecondSemester2022\\AI\\Projects\\Project_three\\LanguageModel\\src\\project_three\\BuildArabicLanguageModel\\test.txt")); // instance of scanner for read data from file
            if (false) {
                // no data in file
                //Message.displayMessage("Warning", "  There are no records in the file " + fileName + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens

                while (input.hasNext()) { // read line of data
                    try {
                        String temp = input.nextLine();
                        System.out.println(temp);
                        line++; // increment the line by one

                    } catch (Exception ex) {
                        // the record in the file has a problem
                        //Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + fileName + "  ");
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            //Message.displayMessage("Error", " The system can NOT find the file " + fileName + "  ");
        }
    }

}
