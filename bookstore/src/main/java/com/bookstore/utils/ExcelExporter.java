package com.bookstore.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ExcelExporter {

    public static <T> void exportToExcel(List<T> list, String filePath) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách rỗng!");
            return;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Dữ liệu");

        try {
            // Lấy class của đối tượng đầu tiên để đọc field
            Class<?> clazz = list.get(0).getClass();
            Field[] fields = clazz.getDeclaredFields();

            // Ghi tiêu đề (header)
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(fields[i].getName());
            }

            // Ghi dữ liệu từng dòng
            for (int i = 0; i < list.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                T obj = list.get(i);

                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    Object value = fields[j].get(obj);
                    Cell cell = dataRow.createCell(j);
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }

            // Ghi ra file
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            workbook.close();
            out.close();
            System.out.println("Xuất file Excel thành công: " + filePath);
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi xuất Excel!");
        }
    }

    public static void exportToExcel(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách");

        TableModel model = table.getModel();

        // Ghi tiêu đề cột
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < model.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(model.getColumnName(i));
        }

        // Ghi dữ liệu từng dòng
        for (int i = 0; i < model.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < model.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                Object value = model.getValueAt(i, j);
                cell.setCellValue(value != null ? value.toString() : "");
            }
        }

        // Ghi file
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            workbook.write(out);
            workbook.close();
            JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel!");
        }
    }
}