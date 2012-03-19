package com.hrm.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.hrm.server.FileUploadServer;

public class ExcelUtil {

	/**
	 * 
	 * @param file  文件名
	 * @param isRow 是否按行放入List
	 * @throws BiffException
	 * @throws IOException
	 */
	public static String[][] parse(String file) throws BiffException, IOException{
        //通过Workbook的静态方法getWorkbook选取Excel文件
        Workbook workbook = Workbook.getWorkbook(new File(file));
        //通过Workbook的getSheet方法选择第一个工作簿（从0开始）
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();
        Cell cells[][] = new Cell[cols][rows];
        String[][] result = new String[cols][rows];
    	for(int i = 0;i < cols ; i ++){
            for(int j = 0;j < rows; j ++){
                cells[i][j] = sheet.getCell(i,j);
                String text = cells[i][j].getContents();
                result[i][j] = text;
            }
        }
        
        
        /*for(int c=0;c<cols;++c){
            for(int r=0;r<rows;++r){
                cells[c][r] = sheet.getCell(c,r);
                String value = "";
                if(cells != null){
                    if(cells[c][r].getType()==CellType.DATE_FORMULA) {
                        value += "\t";
                    }else if(cells[c][r].getType()==CellType.NUMBER_FORMULA){
                         value += cells[c][r].getContents()
                                     + "\t";
                    }else if(cells[c][r].getType()==CellType.STRING_FORMULA){
                        value += cells[c][r].getContents() + "\t";
                    }else if(cells[c][r].getType()==CellType.BOOLEAN_FORMULA){
                        value += cells[c][r].getContents() + "\t";
                    }
                    else{
                        value += "\t";
                    }
                 }
                System.out.println("c:"+c+" r:"+r+"  "+cells[c][r].getContents());
            }
        }
        //通过Sheet方法的getCell方法选择位置为C2的单元格（两个参数都从0开始）
        Cell c2 = sheet.getCell(2,0); //(列，行)
        //通过Cell的getContents方法把单元格中的信息以字符的形式读取出来
        String stringc2 = c2.getContents();
        System.out.println(stringc2);
        //我们都知道Excel单元格是有格式的，那么这些信息如何取得。
        //Cell提供了一个getType方法能够返回单元格的类型信息，同时JXL提供了一个CellType类用来预设Excel中的类型信息，而且JXL提供了一些Cell类的子类用来分别用来表示各种类型的单元格，如LabelCell，NumberCell，DateCell分别表示字符、数值、日期类型的单元格。
        //修改原来日期这样用　write
        if (c2.getType() == CellType.DATE){
             DateTime dt = (DateTime) c2;
             Calendar cal = Calendar.getInstance();
             cal.set(1998, 1, 18, 11, 23, 28);
             Date d = cal.getTime();
             dt.setDate(d);
        }*/
        workbook.close();
        return result;
	}
	public static void main(String[] args) throws Exception {
		String[][] excel = ExcelUtil.parse("db\\menu.xls");
		System.out.println(excel.length+"  "+excel[0].length);
		ExcelUtil.parseExcel(excel);
	}
	public static void parseExcel(String[][] content){
		int col = content.length;
		int row = content[0].length;
		List<Integer> rowInt = new ArrayList<Integer>();
		for(int i = 0; i < row; i++){
			String text = content[0][i];
			if(!"".equals(text) && text!=null && StringUtil.newInstance().isNumeric1(text)){
				rowInt.add(i);
			}
		}
		System.out.println(rowInt);
		List<FileUploadServer.FootEntity> list = new ArrayList<FileUploadServer.FootEntity>();
		FileUploadServer util = new FileUploadServer();
		for(int i = 0; i< rowInt.size(); i++){
			FileUploadServer.FootEntity entity = util.new FootEntity();
			for(int j = 0 ; j < col ; j++ ){
				String rowContent = content[j][rowInt.get(i)];
				if(!"".equals(rowContent)){
					if(j==0){
						entity.setSerial(Integer.valueOf(rowContent));
					}else if(j==2){
						entity.setName(rowContent);
					}else if(j==8){
						entity.setSellnumber(Float.valueOf(rowContent));
					}else if(j==11){
						entity.setSellamount(Float.valueOf(rowContent.replace(",", "")));
					}else if(j==14){
						entity.setCostamount(Float.valueOf(rowContent));
					}else if(j==18){
						entity.setGrossamount(Float.valueOf(rowContent.replace(",", "")));
					}else if(j==22){
						entity.setGrossrate(Float.valueOf(rowContent.replace(",", "")));
					}else if(j==25){
						entity.setType(rowContent.replace(",", ""));
					}
				}
			}
			list.add(entity);
		}
		System.out.println(list.get(10).getName()+"  "+list.get(10).getCostamount()+"  "+list.get(10).getGrossamount()+"  "+list.get(10).getGrossrate()+"  "+list.get(10).getSellamount()+"  "+list.get(10).getSellnumber()+"  "+list.get(10).getSerial()+"  "+list.get(10).getType());
	}
	
}
