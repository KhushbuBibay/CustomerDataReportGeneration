package com.myadmin;

import java.io.*;
import java.util.*;

public class DynamicRangeReports {

    public static void main(String args[]) {

        File file = new File("user_visit_data.csv");

        Map<String, Integer> resultMap = new HashMap<>();
        ArrayList<String> range = new ArrayList<>();

        if (file.exists()) {
            // Fetching range and user data from file
            try (FileInputStream fileInputStream = new FileInputStream(file);
                    Scanner scanner = new Scanner(fileInputStream);) {

                scanner.nextLine();

                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                    String[] arr = str.split(",");
                    String customerId = getString(arr[0]);
                    int visitCount = Integer.parseInt(getString(arr[1]));
                    resultMap.put(customerId, visitCount);
                    if (arr.length == 3) {
                        range.add(getString(arr[2]));
                    }
                }

                String message = createOutputFile(findRangeValues(range, resultMap));

                System.out.println(message);

            } catch (IOException e) {
            
                System.out.println("Error with file."+" "+e.getLocalizedMessage());
            
            }catch(Exception e){
                System.out.println("Error occured:"+" "+e.getLocalizedMessage());
            }
        } else {
            System.out.println("File does not exist");
        }
    }

    private static String getString(String s) {
        String ss = s.split("\"")[1];
        return ss;
    }

    private static Map<String, ArrayList<String>> findRangeValues(ArrayList<String> range,
            Map<String, Integer> resultMap) {
        Map<String, ArrayList<String>> output = new HashMap<>();
        for (Map.Entry<String, Integer> str : resultMap.entrySet()) {
            for (String ran : range) {

                int start = Integer.parseInt(ran.split("-")[0]);
                int end = Integer.parseInt(ran.split("-")[1]);
                if (str.getValue() > start && str.getValue() <= end) {
                    if (output.get(ran) == null) {
                        ArrayList<String> r = new ArrayList<>();
                        r.add(str.getKey());
                        output.put(ran, r);
                    } else {
                        output.get(ran).add(str.getKey());
                    }
                }
            }
        }
        return output;
    }

    public static String createOutputFile(Map<String, ArrayList<String>> map) throws IOException {
        String message = "";
        String str = "";
        try (Scanner sc = new Scanner(System.in);) { // System.in is a standard input stream
            System.out.print("Please select the file format : 1. csv , 2. txt ");
            str = sc.nextLine();

        }
        String outputFileFormat;
        String fileName;

        switch (str) {
            case "1":
                outputFileFormat = "csv";
                fileName = "output.csv";
                break;
            case "2":
                outputFileFormat = "txt";
                fileName = "output.txt";
                break;
            default:
                throw new RuntimeException("Please enter correct value.");
        }

        File outputFile = new File(fileName);
        if (outputFile.exists()) {
            outputFile.delete();
        }

        try (FileWriter writer = new FileWriter(outputFile, true);) {
            String separater = outputFileFormat.equalsIgnoreCase("csv") ? "," : "\n";
            for (Map.Entry<String, ArrayList<String>> string2 : map.entrySet()) {

                writer.write(string2.getKey());
                writer.write(separater);
                for (String s : string2.getValue()) {
                    writer.write(s + separater);
                }
                writer.write("\n");

            }
        }
        message = outputFileFormat + " file created";
        return message;
    }

}