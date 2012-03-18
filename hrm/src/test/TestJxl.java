package test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.DateTime;

public class TestJxl {

	public static void main(String[] args) throws Exception {
        //通过Workbook的静态方法getWorkbook选取Excel文件
        Workbook workbook = Workbook.getWorkbook(new File("db\\menu.xls"));
        //通过Workbook的getSheet方法选择第一个工作簿（从0开始）
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();
        Cell cells[][] = new Cell[cols][rows];
        String[] content = new String[cols];
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < cols ; i ++){
            sb.delete(0,sb.length());
            for(int j = 0;j < rows; j ++){
                cells[i][j] = sheet.getCell(i,j);
                sb.append(cells[i][j].getContents()).append(",");
            }
            content[i] = sb.toString();
        }
        System.out.println("content =========================="+content);
        for(int i = 0 ;i < content.length; i ++){
            System.out.print("ddddddddddddddddd"+content[i]);
        }
        
        
        for(int c=0;c<cols;++c){
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
        }
        workbook.close();
    }

}
