package com.azure;

import java.util.*;
import java.io.File;

public class FilenameSimilarityCheck {
    private static final String ROOT_FOLDER = "D:\\Dr.Fawad Sample\\first\\Object Oriented Programming\\Object Oriented Programming (Theory)";
    private static final String SAMPLE_FOLDER = "D:\\Sample Theory";

    public static void main(String[] args) {
        // For Actual Files names
        List<String> fileList = new ArrayList<>();
        fetchFiles(new File(ROOT_FOLDER), fileList);



        // For the sample Files names
        List<String> sample_fileList = new ArrayList<>();
        fetchFiles(new File(SAMPLE_FOLDER), sample_fileList);


        // Call quantitative_check() after file collection is complete
        List<String> actual = quantitative_check(fileList);
        List<String> sampled = quantitative_check_sample(sample_fileList);

        System.out.println("Cleaned List of Actual Files:");
        for(String file: actual){
            System.out.println(file);
        }

        System.out.println("\n\n\n\n\n\n");


        System.out.println("Cleaned List of Sampled Files:");
        for(String file: sampled){
            System.out.println(file);
        }

        System.out.println("\n\n\n\n\n\n");

        List<String> extraFiles = new ArrayList<>(sampled);
        extraFiles.removeAll(actual);
        List<String> fifff = new ArrayList<>();


        // Print extra files
        System.out.println("Files Missing:");
        if (extraFiles.isEmpty()) {
            System.out.println("None No Missing Files");
        } else {
            for (String file : extraFiles) {
                if (file.contains("obesheet") ||
                        file.contains("theoryreviewreport") ||
                        file.contains("obesheettheory") ||
                        file.contains("obe-sheet") ||
                        file.contains("obe_report") ||
                        file.contains("obeanalysis") ||
                        file.contains("outcome-basededucation") ||
                        file.contains("obeevaluation") ||
                        file.contains("theoryreview") ||
                        file.contains("reviewreport") ||
                        file.contains("objectorientedtheory") ||
                        file.contains("obepreview")){
                    continue;
                }
                else{
                System.out.println(file);
                }
            }
        }


    }

    public static void fetchFiles(File folder, List<String> fileList) {
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("ERROR: Folder not found: " + folder.getAbsolutePath());
            return;
        }

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                fetchFiles(file, fileList); // Recursive call for subdirectories
            } else {
                fileList.add(file.getName()); // Store only file name
            }
        }
    }

    public static List<String> quantitative_check(List<String> finals_list) {

        List<String> cleanFileNames = new ArrayList<>();

        for (String file : finals_list) {
            String cleanedFile = file.toLowerCase();
            cleanedFile = cleanedFile.replace(" ", "");
            cleanedFile = cleanedFile.replace("-","");
            cleanedFile = cleanedFile.replaceFirst("^[0-9]+\\.?","");

            int dotIndex = cleanedFile.lastIndexOf(".");
            if (dotIndex != -1) {
                cleanedFile = cleanedFile.substring(0, dotIndex);
            }

            cleanFileNames.add(cleanedFile);
        }

        Collections.sort(cleanFileNames);
        return cleanFileNames;
    }

    public static List<String> quantitative_check_sample(List<String> finals_list) {

        List<String> cleanFileNames = new ArrayList<>();

        for (String file : finals_list) {
            String cleanedFile = file.toLowerCase();
            cleanedFile = cleanedFile.replace(" ", "");
            cleanedFile = cleanedFile.replace("-","");
            cleanedFile = cleanedFile.replaceFirst("^[0-9]+\\.?","");

            int dotIndex = cleanedFile.lastIndexOf(".");
            if (dotIndex != -1) {
                cleanedFile = cleanedFile.substring(0, dotIndex);
            }

            cleanFileNames.add(cleanedFile);
        }
        Collections.sort(cleanFileNames);
        return cleanFileNames;
    }
}

