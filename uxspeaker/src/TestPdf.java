import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.faceless.pdf2.PDF;
import org.faceless.pdf2.PDFParser;
import org.faceless.pdf2.PDFReader;
import org.faceless.pdf2.PageExtractor;


public class TestPdf {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		TestPdf t = new TestPdf();
		Map<String,PdfEntity> map = new HashMap<String,PdfEntity>();
		// PDF文档路径  
        String filepath = "rayln.pdf";  
        // PDFReader对象建立  
        PDFReader reader = new PDFReader(new File(filepath));  
        // 建立PDF文档对象  
        PDF pdf = new PDF(reader);  
        // 建立文档解析对象  
        PDFParser parser = new PDFParser(pdf);  
        Map<String,String> type = t.createType();
        for (int i = 0; i < pdf.getNumberOfPages(); i++) {  
            PageExtractor extractor = parser.getPageExtractor(i);  
            String first = extractor.getTextInDisplayOrder().toArray()[1].toString();
            if(first.indexOf(". . . ")<0 && first.indexOf("做最棒的软件开发交流社区")<0 && first.indexOf("目录")<0 && i>10){
            	String title = first.substring(2, first.lastIndexOf("\""));
            	System.out.println(extractor.getTextAsStringBuffer().toString());
            	if(!map.containsKey(title)){
            		PdfEntity e = new PdfEntity();
                	String number = title.substring(0, title.indexOf("."));
                	String types = type.get(number);
                	String content = extractor.getTextAsStringBuffer().toString();
                	int start = content.indexOf("\n");
                	int end = content.lastIndexOf("\n")-15;
                	content = content.substring(start,end);
                	e.setTitle(title);
                	e.setType(types);
                	e.setContent(content);
                	map.put(title, e);
            	}else{
            		PdfEntity e = map.get(title);
                	String number = title.substring(0, title.indexOf("."));
                	String types = type.get(number);
                	String content = extractor.getTextAsStringBuffer().toString();
                	int start = content.indexOf("\n");
                	int end = content.lastIndexOf("\n")-15;
                	content = content.substring(start,end);
                	e.setTitle(title);
                	e.setType(types);
                	e.setContent(e.getContent()+content);
                	map.put(title, e);
            	}
            }
            
            //System.out.println(extractor.getTextInDisplayOrder().toArray()[1]);  
            //System.out.println(extractor.getTextAsStringBuffer());  
        }
        System.out.println(map);  
        System.out.println(map.get("2.73 WabSarvica利用KSoap2调用接口"));
	}
	private Map<String,String> createType(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "Aix");
		map.put("2", "Android");
		map.put("3", "Database");
		map.put("4", "Ecplise");
		map.put("5", "Flex");
		map.put("6", "Hibernate");
		map.put("7", "Html4&Css2");
		map.put("8", "Html5&Css3");
		map.put("9", "Java");
		map.put("10", "Js&JQuery");
		map.put("11", "MQ");
		map.put("12", "Spring");
		map.put("13", "Window");
		map.put("14", "Weblogic");
		map.put("15", "WebService");
		return map;
	}
}
