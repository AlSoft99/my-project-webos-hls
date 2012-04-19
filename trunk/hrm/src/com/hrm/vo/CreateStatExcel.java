package com.hrm.vo;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CreateStatExcel {
	//标题
	public static int TITLE = 0;
	//表头
	public static int TH = 2;
	//表头单位
	private static String[] TH_CONTENT = {"品名","菜名","肉","销售单价","数量","销售份数","折合斤两","销售金额","进货均价","上月库存","本月库存"};
	private String folder = System.getProperty("webapp.root")+"\\stat\\";
	
	public WritableWorkbook createFile(String filename) throws Exception{
		File file = new File(folder);
		if(!file.exists()){
			file.mkdirs();
		}
		return Workbook.createWorkbook(new File( folder + filename)); // 第一步
	}
	public WritableSheet createSheet(WritableWorkbook book) throws RowsExceededException{
		/**
		 * 定义与设置Sheet
		 */
		WritableSheet sheet = book.createSheet("sheet", 0); // 创建Sheet
		for (int i = 0; i < 15; i++) {
			sheet.setColumnView(i, 17); // 设置列的宽度
		}
		return sheet;
	}
	public WritableCellFormat getNormalCell(int size) throws Exception{
		WritableFont wf = new WritableFont(WritableFont.ARIAL, size, WritableFont.NO_BOLD); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
		wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		return wcf;
	}
	public WritableCellFormat getBoldCell(int size) throws Exception{
		WritableFont wf = new WritableFont(WritableFont.ARIAL, size, WritableFont.BOLD); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
		wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
		wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		return wcf;
	}
	public void createTitle(WritableSheet sheet,WritableCellFormat wcf, String title) throws RowsExceededException, WriteException{
		sheet.addCell(new Label(0, TITLE, title, wcf));
		sheet.mergeCells(0, 0, TH_CONTENT.length, TH-1);
	}
	
	public void createTHead(WritableSheet sheet,WritableCellFormat wcf, String[] th) throws RowsExceededException, WriteException{
		for (int i = 0; i < th.length; i++) {
			sheet.addCell(new Label(i, TH, th[i], wcf));
		}
	}
	public void write(WritableWorkbook book) throws WriteException, IOException{
		book.write();
	}
	public void close(WritableWorkbook book) throws WriteException, IOException{
		book.close();
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String th[] = {"品名","菜名","肉","销售单价","数量","销售份数","折合斤两","销售金额","进货均价","上月库存","本月库存"};
		CreateStatExcel t = new CreateStatExcel();
		WritableWorkbook book = t.createFile("f:\\aa.xls");
		WritableCellFormat boldCell = t.getBoldCell(12);
		WritableCellFormat normalCell = t.getNormalCell(12);
		WritableSheet sheet = t.createSheet(book);
		//创建标题
		t.createTitle(sheet,boldCell,"标题");
		//创建表头
		t.createTHead(sheet,boldCell, th);
		t.write(book);
		t.close(book);
	}
}
