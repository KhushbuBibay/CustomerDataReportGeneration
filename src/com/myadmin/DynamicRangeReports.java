package com.myadmin;

import java.io.*;
import java.util.*;

public class DynamicRangeReports {

    public static void main(String args[]) throws IOException {

        File file = new File("user_visit_data.csv");

        Map<String, Integer> resultMap = new HashMap<>();
//        Map<String, ArrayList<String>> output = new HashMap<>();
        ArrayList<String> range = new ArrayList<>();

        if (file.exists()) {
            //Fetching range and user data from file
            try (FileInputStream fileInputStream = new FileInputStream(file);Scanner scanner = new Scanner(fileInputStream);) {


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
            } catch (IOException e) {
                e.getMessage();
            }
            System.out.println(createOutputFile((HashMap<String, ArrayList<String>>) findRangeValues(range, resultMap)));
        } else {
            System.out.println("File does not exist");
        }
    }

    private static String getString(String s) {
        String ss = s.split("\"")[1];
        return ss;
    }

    public static Map<String, ArrayList<String>> findRangeValues(ArrayList<String> range, Map<String, Integer> resultMap) {
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

    public static String createOutputFile(HashMap<String, ArrayList<String>> map) throws IOException {
        String file = null;
        int i = 0;
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Please select the file format : 1. csv , 2. txt ");
        String str = sc.nextLine();
        if (str.equals("1")) {

            File outputFile = new File("dataCSV.csv");
            if (outputFile.exists()) {
                outputFile.delete();
            }

            for (Map.Entry<String, ArrayList<String>> string2 : map.entrySet()) {
                try (FileWriter writer = new FileWriter("dataCSV.csv", true);) {

                    writer.write(string2.getKey());
                    writer.write(",");
                    for (String s : string2.getValue()) {
                        writer.write(s + ",");
                    }
                    writer.write("\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            file = "CSV File created";
        } else if (str.equals("2")) {
            File outputFile = new File("dataCSV.txt");
            if (outputFile.exists()) {
                outputFile.delete();
            }
            for (Map.Entry<String, ArrayList<String>> string2 : map.entrySet()) {

                try (FileWriter writer = new FileWriter("dataCSV.txt", true);) {

                    writer.write(string2.getKey() + "\n" + string2.getValue());
                    writer.write("\n\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            file = "Text File created";
        } else {
            System.out.println("Please Enter Correct Value");
            System.exit(0);
        }
        return file;
    }
}