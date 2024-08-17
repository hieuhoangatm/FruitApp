package com.dinhhieu.FruitWebApp.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;

@Service
@Slf4j
public class ExcelToPdfService {

    public void convertExcelToPdf(InputStream excelInputStream, OutputStream pdfOutputStream) throws IOException, DocumentException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelInputStream);
        Document document = new Document();
        PdfWriter.getInstance(document, pdfOutputStream);
        document.open();


        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            if (sheet == null) continue;

            if (i > 0) {
                document.newPage();
            }

            int numColumns = 0;
            int lastRowIndex = sheet.getLastRowNum();

            // Find the number of columns in the sheet
            for (int rowIndex = 0; rowIndex <= lastRowIndex; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    int lastCellNum = row.getLastCellNum();
                    if (lastCellNum > numColumns) {
                        numColumns = lastCellNum;
                    }
                }
            }

            PdfPTable table = new PdfPTable(numColumns);

            // Add table data with customization
            addTableData(table, sheet, numColumns);

            document.add(table);
        }

        document.close();
        workbook.close();
    }



    private void addTableData(PdfPTable table, XSSFSheet sheet, int numColumns) throws IOException, DocumentException {

        for (Row row : sheet) {
            for (int i = 0; i < numColumns; i++) {
                Cell cell = row.getCell(i);
                String cellValue = "";

                if (cell != null) {
                    cellValue = switch (cell.getCellType()) {
                        case STRING -> cell.getStringCellValue();
//                        case NUMERIC -> String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()));
                        case NUMERIC -> {
                            double numericValue = cell.getNumericCellValue();
                            if (numericValue == Math.floor(numericValue)) {
                                // Số nguyên, không có phần thập phân
                                yield String.valueOf((long) numericValue);
                            } else {
                                // Số thực, có phần thập phân
                                yield String.valueOf(BigDecimal.valueOf(numericValue));
                            }
                        }
                        case FORMULA -> evaluateFormula(cell);
                        default -> "";
                    };

                    PdfPCell cellPdf = new PdfPCell(new Phrase(cellValue, getCellStyle(cell)));
                    cellPdf.setRotation(0); // Ensure no rotation
                    setBackgroundColor(cell, cellPdf);
                    setCellAlignment(cell, cellPdf);
                    table.addCell(cellPdf);
                } else {
                    // Handle null cell case
                    PdfPCell cellPdf = new PdfPCell(new Phrase(""));
                    cellPdf.setRotation(0); // Ensure no rotation
                    table.addCell(cellPdf);
                }
            }
        }
    }

    private String evaluateFormula(Cell cell) {
        FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);

        return switch (cellValue.getCellType()) {
            case STRING -> cellValue.getStringValue();
            case NUMERIC -> String.valueOf(BigDecimal.valueOf(cellValue.getNumberValue()));
            case BOOLEAN -> String.valueOf(cellValue.getBooleanValue());
            default -> "";
        };
    }

    Font getCellStyle(Cell cell) throws DocumentException, IOException {
        Font font = new Font();
        CellStyle cellStyle = cell.getCellStyle();
        org.apache.poi.ss.usermodel.Font cellFont = cell.getSheet().getWorkbook().getFontAt(cellStyle.getFontIndexAsInt());

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
//            font.setFamily("Times-Roman");
        }

        return font;
    }

    private void setBackgroundColor(Cell cell, PdfPCell cellPdf) {
        short bgColorIndex = cell.getCellStyle().getFillForegroundColor();
        if (bgColorIndex != IndexedColors.AUTOMATIC.getIndex()) {
            XSSFColor bgColor = (XSSFColor) cell.getCellStyle().getFillForegroundColorColor();
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

        // Horizontal alignment
        switch (cellStyle.getAlignment()) {
            case LEFT:
                cellPdf.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case CENTER:
                cellPdf.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
            case RIGHT:
                cellPdf.setHorizontalAlignment(Element.ALIGN_RIGHT);
                break;
            default:
                cellPdf.setHorizontalAlignment(Element.ALIGN_LEFT); // Default to left alignment if unspecified
                break;
        }

        // Vertical alignment
        switch (cellStyle.getVerticalAlignment()) {
            case TOP:
                cellPdf.setVerticalAlignment(Element.ALIGN_TOP);
                break;
            case CENTER:
                cellPdf.setVerticalAlignment(Element.ALIGN_MIDDLE);
                break;
            case BOTTOM:
                cellPdf.setVerticalAlignment(Element.ALIGN_BOTTOM);
                break;
            default:
                cellPdf.setVerticalAlignment(Element.ALIGN_MIDDLE); // Default to middle alignment if unspecified
                break;
        }
    }

}

