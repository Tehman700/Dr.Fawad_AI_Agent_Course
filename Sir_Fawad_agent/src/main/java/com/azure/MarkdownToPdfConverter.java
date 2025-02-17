package com.azure;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import javax.swing.*;

public class MarkdownToPdfConverter {
    // This will change the file
    AutoShowingDialog obb = new AutoShowingDialog();
    private static final String API_URL = "https://md-to-pdf.fly.dev/";

    public void changerss(String savePath) {
        String markdownFilePath = "Analysis Report.md";
        String pdfOutput = savePath + File.separator + "final_PDF_Report.pdf"; // Use the provided save path

        try {
            String markdownContent = new String(Files.readAllBytes(Paths.get(markdownFilePath)));
            convertMarkdownToPdf(markdownContent, pdfOutput);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void convertMarkdownToPdf(String markdownContent, String outputPdfPath) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create a POST request
            HttpPost postRequest = new HttpPost(API_URL);
            postRequest.setEntity(new StringEntity("markdown=" + markdownContent, ContentType.APPLICATION_FORM_URLENCODED));

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                if (response.getCode() == 200) {
                    byte[] pdfData = response.getEntity().getContent().readAllBytes();
                    try (FileOutputStream fos = new FileOutputStream(outputPdfPath)) {
                        fos.write(pdfData);
                        obb.showAutoCloseDialog("PDF FILE CREATED SUCCESSFULLY!!! " + outputPdfPath, "Final Step Completed", JOptionPane.INFORMATION_MESSAGE);

                        File pdfFile = new File(outputPdfPath);
                        if(pdfFile.exists()){
                            Desktop.getDesktop().open(pdfFile);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Error: PDF file not found!", "File Not Found", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    System.out.println("Error: " + response.getCode());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}