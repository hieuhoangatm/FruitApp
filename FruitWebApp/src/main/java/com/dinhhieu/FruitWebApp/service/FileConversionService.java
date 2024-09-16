package com.dinhhieu.FruitWebApp.service;


import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileConversionService {
    private static final String LIBREOFFICE_PATH = "C:\\Program Files\\LibreOffice\\program\\soffice.exe";

    public void convertToPdf(String inputFilePath, String outputFilePath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                LIBREOFFICE_PATH,
                "--headless",
                "--convert-to", "pdf",
                "--outdir", new File(outputFilePath).getParent(),
                inputFilePath
        );
        Process process = processBuilder.start();


        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Conversion failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Conversion process was interrupted", e);
        }
    }

    public String convertToPdf(String inputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        String outputDir = inputFile.getParent();
        ProcessBuilder processBuilder = new ProcessBuilder(
                LIBREOFFICE_PATH,
                "--headless",
                "--convert-to", "pdf",
                "--outdir", outputDir,
                inputFilePath
        );
        Process process = processBuilder.start();

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Conversion failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Conversion process was interrupted", e);
        }

        // Trả về đường dẫn file PDF sau khi chuyển đổi
        return inputFilePath.replaceAll("\\.[^.]+$", ".pdf");
    }

//    public String viewFileContent(String inputFilePath) throws IOException {
//        // Chuyển đổi file thành định dạng văn bản (.txt)
//        File tempFile = new File(inputFilePath + ".txt");
//        convertToText(inputFilePath, tempFile.getAbsolutePath());
//
//        // Đọc nội dung từ file văn bản
//        StringBuilder content = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line).append("\n");
//            }
//        }
//
//        // Xóa file tạm sau khi đọc xong
//        if (!tempFile.delete()) {
//            System.err.println("Failed to delete temporary file: " + tempFile.getAbsolutePath());
//        }
//
//        return content.toString();
//    }
//
//    private void convertToText(String inputFilePath, String outputFilePath) throws IOException {
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                LIBREOFFICE_PATH,
//                "--headless",
//                "--convert-to", "txt",
//                "--outdir", new File(outputFilePath).getParent(),
//                inputFilePath
//        );
//        processBuilder.redirectErrorStream(true);
//
//        Process process = processBuilder.start();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line); // Log output for debugging
//            }
//        }

//        try {
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                throw new IOException("Conversion to text failed with exit code " + exitCode);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new IOException("Conversion process was interrupted", e);
//        }
//    }
}
