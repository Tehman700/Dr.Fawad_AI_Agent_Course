package com.azure;

import org.apache.commons.text.similarity.LevenshteinDistance;
import java.util.*;
import java.io.File;

public class FilenameSimilarityCheck {

    private static final String ROOT_FOLDER = "D:\\Dr.Fawad Sample\\first\\Object Oriented Programming\\Object Oriented Programming (Theory)";



    public static void main(String[] args) {
        List<String> fileList = new ArrayList<>();
        fetchFiles(new File(ROOT_FOLDER), fileList);

        // Call quantitative_check() after file collection is complete
        quantitative_check(fileList);

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

    public static void quantitative_check(List<String> finals_list) {


        if (finals_list.isEmpty()) {
            System.out.println("No files found.");
            return;
        }

        System.out.println("List of Files:");
        for (String file : finals_list) {
            System.out.println(file);
        }

        // List of required filenames
        List<String> requiredFiles = List.of(
                "1. Course Plan and Contents.pdf", "2. Course Log File.docx", "3. Attendance.pdf","Attendance",
                "Quiz 1.docx", "Quiz1-Avg.pdf", "Quiz1-Best.pdf", "Quiz1-Worst.pdf", "Solution Quiz 1.docx",
                "Quiz 2.docx", "Quiz2-Avg.pdf", "Quiz2-Best.pdf", "Quiz2-Worst.pdf", "Solution Quiz 2.docx",
                "Quiz 3.docx", "Quiz3-Avg.pdf", "Quiz3-Best.pdf", "Quiz3-Worst.pdf", "Solution Quiz 3.docx",
                "Assignment1-Avg.pdf","Assignment1-Best.pdf","Assignment1-Worst.pdf",
                "Assignment 1.pdf", "Solution Assignment 1.pdf", "Assignment 2.pdf", "Assignment2-Avg.pdf",
                "Assignment2-Best.pdf", "Assignment2-Worst.pdf", "Solution Assignment 2.docx",
                "Assignment 3.docx", "Assignment3-Avg.pdf", "Assignment3-Best.pdf", "Assignment3-Worst.pdf",
                "Solution Assignment 3.docx", "Final Exam.pdf", "Final Solution.pdf", "Final-Avg.pdf",
                "Final-Best.pdf", "Final-Worst.pdf", "Mid Exam.pdf", "Mid Solution.pdf", "Mid-Avg.pdf",
                "Mid-Best.pdf", "Mid-Worst.pdf", "OBESheet-Theory.xlsx",
                "OOP Theory Review Report.pdf"
        );

        // Initialize Levenshtein Distance
        LevenshteinDistance ld = new LevenshteinDistance();

        // Check for missing files and find best match
        System.out.println("Missing Files and Closest Matches:");
        for (String required : requiredFiles) {
            if (!finals_list.contains(required)) {
                String bestMatch = null;
                int bestDistance = Integer.MAX_VALUE;

                // Find the closest match in present files
                for (String present : finals_list) {
                    int distance = ld.apply(required, present);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestMatch = present;
                    }
                }

                System.out.println("❌ Missing: " + required);
                if (bestMatch != null) {
                    if (bestDistance <= 5) {
                        System.out.println("✅ Similar file found: " + bestMatch + " (Distance: " + bestDistance + ")");
                    } else if (bestDistance > 5 && bestDistance < 10) {
                        System.out.println("⚠️ Missing file, but a similar one exists: " + bestMatch + " (Distance: " + bestDistance + ")");
                    }
                } else {
                    System.out.println("❌ Completely missing: " + required);
                }

            }
        }
    }
}

