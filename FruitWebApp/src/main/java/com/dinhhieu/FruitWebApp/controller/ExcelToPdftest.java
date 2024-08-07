package com.dinhhieu.FruitWebApp.controller;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ExcelToPdftest {
    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertExcelToPdf(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        // Đọc file Excel
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        // Tạo document PDF
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Tạo bảng PDF
        PdfPTable table = new PdfPTable(sheet.getRow(0).getLastCellNum());

        // Thêm dữ liệu từ Excel vào PDF
        for (Row row : sheet) {
            for (Cell cell : row) {
                table.addCell(new PdfPCell(new Phrase(cell.toString())));
            }
        }

        document.add(table);
        document.close();

        // Trả về file PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "converted.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(out.toByteArray());
    }
}
