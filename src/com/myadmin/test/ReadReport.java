package com.myadmin.test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReadReport {

    public static void main(String args[]) throws IOException {

        File file = new File("user_visit_data.csv");

        Map<String, ArrayList<String>> resultMap = new HashMap<>();

        if(file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {

                Scanner scanner = new Scanner(fileInputStream);
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                    String[] arr = str.split(",");
                    String customerId = getString(arr[0]);
                    int visitCount = Integer.parseInt(getString(arr[1]));

                    String range = findRange(visitCount);

                    if (null == resultMap.get(range)) {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(customerId);
                        resultMap.put(range, list);

                    } else {
                        resultMap.get(range).add(customerId);
                    }

                }

            } catch (IOException e) {
                e.getMessage();
            }
            System.out.println(createCSVMap((HashMap<String, ArrayList<String>>) resultMap));

        }
        else{
            System.out.println("File does not exist");
        }
    }

    private static String getString(String s) {
        String ss = s.split("\"")[1];

        // System.out.println("ss:"+ss);
        return ss;
    }

    public static String findRange(int num) {
        String range = null;

        if (num > 0 && num <= 1000) {
            range = "0...1000";
        } else if (num > 1000 && num <= 5000) {
            range = "1001 - 5000";
        } else if (num > 5000 && num <= 10000) {
            range = "5001 - 10000";
        } else if (num > 10000 && num <= 25000) {
            range = "10001 - 25000";
        } else if (num > 25000 && num <= 50000) {
            range = "25001 - 50000";
        } else if (num > 50000 && num <= 75000) {
            range = "50001 - 75000";
        } else if (num > 75000 && num <= 100000) {
            range = "75001 - 100000";
        } else if (num > 100000) {
            range = "100000+";
        } else {
            throw new RuntimeException("Range could not be determined");
        }

        return range;
    }

    public static String createCSVMap(HashMap<String, ArrayList<String>> map) throws IOException {
        String file = null;
        int i = 0;
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Please select the file format : 1. csv , 2. txt ");
        String str = sc.nextLine();
        if (str.equals("1")) {

            File fileCheck = new File("dataCSV.csv");
            if (fileCheck.exists()){
                fileCheck.delete();
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
            file =  "CSV File created";
        }
        else if(str.equals("2")){
            File fileCheck = new File("dataCSV.txt");
            if (fileCheck.exists()){
                fileCheck.delete();
            }
            for (Map.Entry<String, ArrayList<String>> string2 : map.entrySet()) {

                try (FileWriter writer = new FileWriter("dataCSV.txt", true);) {

                    writer.write(string2.getKey() +"\n"+string2.getValue());
                    writer.write("\n\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            file =  "Text File created";
        }
        else{
            System.out.println("Please Enter Correct Value");
            System.exit(0);
        }
        return file ;
    }
}
