package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	public ReadExcel() {

	}

	public void readExcel(String filepath) {
		try {
			InputStream is = new FileInputStream(filepath);
			Workbook wb = Workbook.getWorkbook(is);
			int sheet_size = wb.getNumberOfSheets();
			for (int index = 0; index < sheet_size; index++) {
				Sheet sheet = wb.getSheet(index);
				Range[] ranges = sheet.getMergedCells();
				System.out.println("sheet" + index + "包含" + ranges.length + "个区域");
				for (Range space : ranges) {
					System.out.print(space.getTopLeft().getRow() + 1 + "行,");
					System.out.print(space.getTopLeft().getColumn() + 1 + "列\t");
					System.out.print(space.getBottomRight().getRow() + 1 + "行,");
					System.out.print(space.getBottomRight().getColumn() + 1 + "列\n");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}