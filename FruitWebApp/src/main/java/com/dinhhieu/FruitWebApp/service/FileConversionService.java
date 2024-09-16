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

        return inputFilePath.replaceAll("\\.[^.]+$", ".pdf");
    }
}
