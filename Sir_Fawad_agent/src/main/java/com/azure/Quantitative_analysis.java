package com.azure;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Quantitative_analysis {
    private JTextArea outputTextArea; // Declare JTextArea
    public Quantitative_analysis(JTextArea outputTextArea) {
        this.outputTextArea = outputTextArea;
    }

    ArrayList<String> missing_files=new ArrayList<>();
    ArrayList<String> expectd_Dir_Of_mising_file=new ArrayList<>();

    private boolean isKeywordPresent_folder(String originalName, String currentName) {
        com.azure.StringSimilarity stringSimilarity = new StringSimilarity();



        // Normalize both strings (convert to lowercase and trim spaces)
        String normalizedCurrent = currentName.toLowerCase().trim();
        String normalizedOriginal = originalName.toLowerCase().trim();

        normalizedCurrent = stringSimilarity.preprocessString(normalizedCurrent);
        normalizedOriginal = stringSimilarity.preprocessString(normalizedOriginal);
        return stringSimilarity.areStringsSimilar(normalizedCurrent,normalizedOriginal,2,0.80);

    }


    //only Searches for a folder in the root directory
    public String searchFolder(File targetFolderName, String rootDirectory) {
        boolean valid_file_folder=false;
        String founded_file_OR_Folder="";

        String Original_file_OR_folder="";
        File rootDir = new File(rootDirectory);

        if (!rootDir.exists() || !rootDir.isDirectory()) {
            return null;
        }

        if(rootDir.isDirectory()){

        }

        for (File folder : rootDir.listFiles(File::isDirectory)) {


            valid_file_folder=isKeywordPresent_folder(folder.getName(),targetFolderName.getName());

            if (valid_file_folder) {

                founded_file_OR_Folder=folder.toString().toLowerCase().trim();
                Original_file_OR_folder=targetFolderName.toString().toLowerCase().trim();

                if(Original_file_OR_folder.toString().toLowerCase().contains("lab")&& founded_file_OR_Folder.toLowerCase().contains("lab")){

                    return folder.getAbsolutePath();
                }

                else if (Original_file_OR_folder.toString().toLowerCase().contains("theory")  && founded_file_OR_Folder.toLowerCase().contains("theory") ) {


                    return folder.getAbsolutePath();

                }


            }

            String result = searchFolder(targetFolderName, folder.getAbsolutePath());
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    // Searches both files and folders
    public String searchFileOrFolder(File targetName, String rootDirectory) {
        boolean valid_file_OR_folder=false;
        File rootDir = new File(rootDirectory);

        if (!rootDir.exists() || !rootDir.isDirectory()) {
            return null;
        }

        String founded_file_OR_Folder="";
        String Original_file_OR_folder="";

        // Loop through all files and folders in the current directory
        for (File file : rootDir.listFiles()) {

            valid_file_OR_folder=isKeywordPresent_folder(file.getName(),targetName.getName());

            if (valid_file_OR_folder) {

                founded_file_OR_Folder=file.toString().toLowerCase().trim();
                Original_file_OR_folder=targetName.toString().toLowerCase().trim();


                if(Original_file_OR_folder.toString().toLowerCase().contains("lab")&& founded_file_OR_Folder.toLowerCase().contains("lab")){

                    return file.getAbsolutePath();
                }

                else if (Original_file_OR_folder.toString().toLowerCase().contains("theory")  && founded_file_OR_Folder.toLowerCase().contains("theory") ) {

                    return file.getAbsolutePath();
                }

            }

            // If it's a directory, recursively search inside it
            if(file.isDirectory()) {
                String result = searchFileOrFolder(targetName, file.getAbsolutePath());
                if (result != null) {
                    return result;
                }
            }
        }

        return null; // Return null if the target file or folder is not found
    }



//this function takes each file/folder from sample folder and finds its copy in original folder

    public String traverseDirectory(String sample_directoryPath,String root_dir_path) {
        File directory = new File(sample_directoryPath);
        String current_file="";
        boolean is_granpa=false;
        String missing_file_Dir="";
        String[] temp;

        String founded_file_OR_folder="";

        // Check if the path is valid and is a directory
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path: " + sample_directoryPath);
            return "Invalid directory path";
        }

        // List all files and folders in the directory
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return "Invalid directory path"; // If the directory is empty or inaccessible
        }

        for (File file : files) {

            founded_file_OR_folder=searchFileOrFolder(file,root_dir_path);


//             System.out.println("\nexpected Folders: "+ file);

            if(founded_file_OR_folder!=null){

//                 System.out.println(founded_file_OR_folder);

            }


            else{

//                 System.out.println("\nthe file/folder {"+file.getName()+"} is not founded in the given  directory\n");

                missing_files.add(file.getName());
                missing_file_Dir=file.toString().replace("\\"+file.getName(),"");
                temp=missing_file_Dir.split("\\\\");

                missing_file_Dir=removeUpToSubstring(missing_file_Dir,"sample_folder");

//                 System.out.println("Expected dirtory : "+ missing_file_Dir);

                expectd_Dir_Of_mising_file.add(missing_file_Dir);

            }


//             System.out.println("\n///////////////////////////////////////////////\n");


            // If it's a directory, recursively traverse it
            if (file.isDirectory()) {
                traverseDirectory(file.getAbsolutePath(),root_dir_path);
            }

        }
        return  null;
    }




    public static String removeUpToSubstring(String str, String substring) {
        int index = str.indexOf(substring);
        if (index != -1) {
            return str.substring(index + substring.length());
        }
        return str;  // Return the original string if substring not found
    }



    public void starter() {
        Quantitative_analysis fuzzyRegexSearch = new Quantitative_analysis(outputTextArea);

        String rootDirectory = SecondGUI.originalFolderPath;
        String SampleFolder_root_Dir = SecondGUI.sampleFolderPath;

        File file = new File(rootDirectory);
        File file_sample_folder = new File(SampleFolder_root_Dir);

        String Target_sample_Folder_path = fuzzyRegexSearch.searchFolder(file, SampleFolder_root_Dir);

        if (Target_sample_Folder_path != null) {
            fuzzyRegexSearch.traverseDirectory(Target_sample_Folder_path, rootDirectory);
        } else {
            fuzzyRegexSearch.traverseDirectory(SampleFolder_root_Dir, rootDirectory);
        }

        // Store output in a single string variable
        StringBuilder output = new StringBuilder();
        output.append("MISSING FILES/FOLDERS:\n");

        for (int i = 0; i < fuzzyRegexSearch.missing_files.size(); i++) {
            output.append(fuzzyRegexSearch.missing_files.get(i))
                    .append("____ Expected in Folder: ")
                    .append(fuzzyRegexSearch.expectd_Dir_Of_mising_file.get(i))
                    .append("\n");
        }

        // Print or use the output string as needed
        System.out.println(output.toString());

        // You can store the result in a variable
        String finalOutput = output.toString();
        SwingUtilities.invokeLater(() -> outputTextArea.setText(output.toString()));

    }



    public static void main(String[] args) {

    }

}