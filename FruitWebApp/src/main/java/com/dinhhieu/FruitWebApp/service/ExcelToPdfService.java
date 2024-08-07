package com.dinhhieu.FruitWebApp.service;

import com.itextpdf.text.Font;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.util.Iterator;
@Service
@Slf4j
public class ExcelToPdfService {

    public void convertExcelToPdf(InputStream excelInputStream, OutputStream pdfOutputStream) throws IOException, DocumentException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelInputStream);
        Document document = new Document();
        PdfWriter.getInstance(document, pdfOutputStream);
        document.open();

        XSSFSheet sheet = workbook.getSheetAt(0);
        Row firstRow = sheet.getRow(0);  // Lấy hàng đầu tiên để kiểm tra số lượng cột
        int numColumns = (firstRow != null && firstRow.getLastCellNum() > 0) ? firstRow.getLastCellNum() : 1;  // Kiểm tra null và số cột > 0

        PdfPTable table = new PdfPTable(numColumns);
        addTableData(table, sheet, numColumns);
        document.add(table);
        document.close();
        workbook.close();
    }

    private void addTableData(PdfPTable table, XSSFSheet sheet, int numColumns) throws IOException, DocumentException {
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            for (int i = 0; i < numColumns; i++) {
                Cell cell = row.getCell(i);
                String cellValue = "";

                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            cellValue = String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()));
                            break;
                        case BLANK:
                        default:
                            cellValue = "";
                            break;
                    }

                    PdfPCell cellPdf = new PdfPCell(new Phrase(cellValue, getCellStyle(cell)));
                    setBackgroundColor(cell, cellPdf);
                    setCellAlignment(cell, cellPdf);
                    table.addCell(cellPdf);
                } else {
                    // trường hợp ô là null
                    PdfPCell cellPdf = new PdfPCell(new Phrase(""));
                    table.addCell(cellPdf);
                }
            }
        }
    }

    Font getCellStyle(Cell cell) throws DocumentException, IOException {
        Font font = new Font();
        CellStyle cellStyle = cell.getCellStyle();
        org.apache.poi.ss.usermodel.Font cellFont = cell.getSheet()
                .getWorkbook()
                .getFontAt(cellStyle.getFontIndexAsInt());

        if (cellFont.getItalic()) {
            font.setStyle(Font.ITALIC);
        }

        if (cellFont.getStrikeout()) {
            font.setStyle(Font.STRIKETHRU);
        }

        if (cellFont.getUnderline() == 1) {
            font.setStyle(Font.UNDERLINE);
        }

        short fontSize = cellFont.getFontHeightInPoints();
        font.setSize(fontSize);

        if (cellFont.getBold()) {
            font.setStyle(Font.BOLD);
        }

        String fontName = cellFont.getFontName();
        if (FontFactory.isRegistered(fontName)) {
            font.setFamily(fontName);
        } else {
            log.warn("Unsupported font type: {}", fontName);
            font.setFamily("Helvetica");
        }

        return font;
    }

    private void setBackgroundColor(Cell cell, PdfPCell cellPdf) {
        short bgColorIndex = cell.getCellStyle()
                .getFillForegroundColor();
        if (bgColorIndex != IndexedColors.AUTOMATIC.getIndex()) {
            XSSFColor bgColor = (XSSFColor) cell.getCellStyle()
                    .getFillForegroundColorColor();
            if (bgColor != null) {
                byte[] rgb = bgColor.getRGB();
                if (rgb != null && rgb.length == 3) {
                    cellPdf.setBackgroundColor(new BaseColor(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF));
                }
            }
        }
    }

    private void setCellAlignment(Cell cell, PdfPCell cellPdf) {
        CellStyle cellStyle = cell.getCellStyle();

        HorizontalAlignment horizontalAlignment = cellStyle.getAlignment();

        switch (horizontalAlignment) {
            case LEFT:
                cellPdf.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case CENTER:
                cellPdf.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
            case JUSTIFY:
            case FILL:
                cellPdf.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                break;
            case RIGHT:
                cellPdf.setHorizontalAlignment(Element.ALIGN_RIGHT);
                break;
        }
    }
}
