package test;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TestExcel {
	//标题
	private static int TITLE = 0;
	//表头
	private static int TH = 2;
	//表头单位
	private static String[] TH_CONTENT = {"品名","菜名","肉","销售单价","数量","销售份数","折合斤两","销售金额","进货均价","上月库存","本月库存"};

	private void createFile(String filename) throws Exception{
		WritableWorkbook book = Workbook.createWorkbook(new File(filename)); // 第一步
		/**
		 * 定义与设置Sheet
		 */
		WritableSheet sheet = book.createSheet("sheet", 0); // 创建Sheet
		for (int i = 0; i < 15; i++) {
			sheet.setColumnView(i, 15); // 设置列的宽度
		}
		WritableCellFormat boldCell = getBoldCell();
		WritableCellFormat normalCell = getNormalCell();
		//创建标题
		createTitle(sheet,boldCell);
		//创建表头
		createTHead(sheet,boldCell);
		
		book.write();
		book.close();
	}
	private WritableCellFormat getNormalCell() throws Exception{
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
		wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		return wcf;
	}
	private WritableCellFormat getBoldCell() throws Exception{
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
		wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		return wcf;
	}
	private void createTitle(WritableSheet sheet,WritableCellFormat wcf) throws RowsExceededException, WriteException{
		sheet.addCell(new Label(0, TITLE, "表头", wcf));
		sheet.mergeCells(0, 0, TH_CONTENT.length, TH-1);
	}
	
	private void createTHead(WritableSheet sheet,WritableCellFormat wcf) throws RowsExceededException, WriteException{
		for (int i = 0; i < TH_CONTENT.length; i++) {
			sheet.addCell(new Label(i, TH, TH_CONTENT[i], wcf));
		}
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestExcel t = new TestExcel();
		t.createFile("f:\\aa.xls");
	}
}
