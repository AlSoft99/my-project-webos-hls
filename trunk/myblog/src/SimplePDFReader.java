import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class SimplePDFReader {
	/**
	 * simply reader all the text from a pdf file. 
	 * You have to deal with the format of the output text by yourself.
	 * 2008-2-25
	 * @param pdfFilePath file path
	 * @return all text in the pdf file
	 */
	public static Map<String,PdfEntity> getTextFromPDF(String pdfFilePath) {
		String result = null;
		FileInputStream is = null;
		PDDocument document = null;
		SimplePDFReader s = new SimplePDFReader();
		Map<String,PdfEntity> resultData = new HashMap<String,PdfEntity>();
		try {
			Map<String,String> type = s.createType();
			Map<String,String> tag = s.createTag();
			is = new FileInputStream(pdfFilePath);
			PDFParser parser = new PDFParser(is);
			parser.parse();
			document = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			result = stripper.getText(document);
			String[] catalog = result.split("\n");
			List<String> title = new ArrayList<String>();
			int lastindex = 0;
			for (int i = 0; i < catalog.length; i++) {
				String temp = catalog[i];
				if(temp.indexOf(". . .")>=0){
					String t = temp.substring(0, temp.indexOf(". . ."));
					if(t.equals("毁的时候触发  ")){
						t = "12.9 init-method是初始化spring配置文件的对象执行的初始化方法, destory-method方法是ioc容器销"+t;
					}
					title.add(t);
					lastindex = i;
				}
			}
			int calalogindex = 0;
			
			for (int i = lastindex+2; i < 500; i++) {
				String titleTmp = title.get(calalogindex);
				if(catalog[i].indexOf("第")>=0 && catalog[i].indexOf("页")>=0){
					continue;
				}
				String contentTmp = catalog[i].replace("\r", "");
				//TODO
				//System.out.println("===="+titleTmp+"  catalog[i]:"+catalog[i].replace("\r", "")+"  index:"+titleTmp.indexOf(catalog[i].replace("\r", "")));
				if(titleTmp.indexOf(contentTmp)>=0){
					calalogindex++;
				}else if(contentTmp.indexOf("http://rayln.iteye.com")<0 && contentTmp.indexOf("发表时间")<0){
					String nowTitle = title.get(calalogindex-1);
					if(resultData.containsKey(nowTitle)){
						PdfEntity entity = resultData.get(nowTitle);
						entity.setContent(entity.getContent()+"<br>"+contentTmp);
					}else{
						String number = nowTitle.substring(0, nowTitle.indexOf("."));
						PdfEntity entity = new PdfEntity();
						entity.setContent(contentTmp);
						entity.setTitle(nowTitle);
						entity.setType(type.get(number));
						entity.setTag(tag.get(number));
						resultData.put(nowTitle, entity);
					}
				}
			}
			System.out.println(resultData);
			System.out.println(resultData.get("2.2 Android 简单Activity和Service的交互代码  ").getContent());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultData;
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
	private Map<String,String> createTag(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "aix");
		map.put("2", "android");
		map.put("3", "database,mysql,sqlserver");
		map.put("4", "ecplise");
		map.put("5", "flex");
		map.put("6", "hibernate");
		map.put("7", "html4,css2");
		map.put("8", "html5,css3");
		map.put("9", "java");
		map.put("10", "js,jquery");
		map.put("11", "mq");
		map.put("12", "spring,mvc");
		map.put("13", "window");
		map.put("14", "weblogic");
		map.put("15", "webservice");
		return map;
	}
	
	public static void main(String[] args) {
		SimplePDFReader s = new SimplePDFReader();
		Map<String,PdfEntity> map = s.getTextFromPDF("rayln的博客文章.pdf");
		
	}
}
