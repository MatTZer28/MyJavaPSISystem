package excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class CreateWarehouseExcelFile {
	
	public CreateWarehouseExcelFile(ArrayList<WarehouseDataItems> data) {
		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("庫存報表");
			
			String[] columnHeadings = {"倉庫編號", "倉庫名稱", "產品編號", "產品名稱", "產品規格", "產品類型", "產品單位", "庫存數量", "安全量", "供應商名稱"};
			
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setColor(IndexedColors.BLACK.index);
			
			CellStyle headerStyle = workbook.createCellStyle();	
			headerStyle.setFont(headerFont);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			
			Row headerRow = sheet.createRow(0);
			for(int i = 0; i < columnHeadings.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columnHeadings[i]);
				cell.setCellStyle(headerStyle);
			}
			
			int rowCount = 1;
			for (WarehouseDataItems wItem : data) {
				Row row = sheet.createRow(rowCount++);
				row.createCell(0).setCellValue(wItem.getWarehouseId());
				row.createCell(1).setCellValue(wItem.getWarehouseName());
				row.createCell(2).setCellValue(wItem.getProductId());
				row.createCell(3).setCellValue(wItem.getProductName());
				row.createCell(4).setCellValue(wItem.getProductSpecification());
				row.createCell(5).setCellValue(wItem.getProductType());
				row.createCell(6).setCellValue(wItem.getProductUnit());
				row.createCell(7).setCellValue(wItem.getProductTotal());
				row.createCell(8).setCellValue(wItem.getProductSafeAmount());
				row.createCell(9).setCellValue(wItem.getProductVendorName());
			}
			
			for (int i = 0; i < columnHeadings.length; i++) {
				sheet.autoSizeColumn(i);
			}
			
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("報表輸出");
			File selectedDirectory = chooser.showDialog(new Stage());
			
			FileOutputStream fileOutputStream = new FileOutputStream(selectedDirectory.getAbsolutePath() + "\\庫存報表.xlsx");
			workbook.write(fileOutputStream);
			fileOutputStream.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
