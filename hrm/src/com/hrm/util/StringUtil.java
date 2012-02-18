package com.hrm.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.hrm.control.Request;

public class StringUtil {
	private static StringUtil stringUtil = new StringUtil();
	private static Logger logger = Logger.getLogger(StringUtil.class);
	private StringUtil(){}
	public static StringUtil newInstance(){
		return stringUtil;
	}
	/**
	 * 根据List<String[]>转成List<List<String>>
	 * @param list
	 * @return
	 */
	public List<List<String>> trasnformList(List<String[]> list){
		List<List<String>> result = new ArrayList<List<String>>();
		for(String[] string : list){
			List<String> temp = new ArrayList<String>();
			for (int i = 0; i < string.length; i++) {
				temp.add("\""+string[i]+"\"");
			}
			result.add(temp);
		}
		return result;
	}
	/**
	 * 根据List<Object[]>转成List<List<String>>
	 * @param list
	 * @return
	 */
	public List<List<String>> trasnformObjectList(List<Object[]> list){
		List<List<String>> result = new ArrayList<List<String>>();
		for(Object[] string : list){
			List<String> temp = new ArrayList<String>();
			for (int i = 0; i < string.length; i++) {
				temp.add("\""+string[i].toString()+"\"");
			}
			result.add(temp);
		}
		return result;
	}
	/**
	 * 把source为null或者""的转化成target值
	 * @param source
	 * @param target
	 * @return
	 */
	public String filterData(Object source,String target){
		if(source==null || "".equals(source)){
			return target;
		}
		return String.valueOf(source);
	}
	/**
	 * 把Object转化成Json的String串
	 * @param obj
	 * @return
	 */
	public String transeObjectToJson(Object obj){
		String result = "{";
		PropertyDescriptor[] property = PropertyUtils.getPropertyDescriptors(obj);
		try {
			for (int i = 0; i < property.length; i++) {
				String fieldName = property[i].getName();
				Object fieldValue = PropertyUtils.getProperty(obj, fieldName);
				if (fieldValue == null) {
					String type = property[i].getPropertyType().getSimpleName();
					if("Double".equals(type) || "Integer".equals(type)){
						result += fieldName + ":0,";
					}else{
						result += fieldName + ":'',";
					}
				}else if(!fieldName.equals("class")){
					String type = property[i].getPropertyType().getName();
					if (type.indexOf("entity")==-1) {
						result += fieldName + ":'"+String.valueOf(fieldValue)+"',";
					}else{
						PropertyDescriptor[] childProperty = PropertyUtils.getPropertyDescriptors(fieldValue);
						for (int j = 0; j < childProperty.length; j++) {
							String childFieldName = childProperty[j].getName();
							if(!childFieldName.equals("class")){
								Object childFieldValue = PropertyUtils.getProperty(fieldValue, childFieldName);
								result += childFieldName+":'"+childFieldValue+"',";
							}
						}
					}
					
				}
			}
			result = result.substring(0, result.length()-1);
		} catch (IllegalAccessException e) {
			logger.error("StringUtil的transeObjectToJson方法错误:", e);
		} catch (InvocationTargetException e) {
			logger.error("StringUtil的transeObjectToJson方法错误:", e);
		} catch (NoSuchMethodException e) {
			logger.error("StringUtil的transeObjectToJson方法错误:", e);
		}
		result += "}";
		return result;
	}
	/**
	 * 格式化子节点,使得子节点可以为Map
	 * @param list
	 * @return
	 */
	public Map<String,List<Map<String,Object>>> formatTreeChild(List<Map<String,Object>> list){
		String currentNodeId = "";
		Map<String,List<Map<String,Object>>> childMap = new HashMap<String,List<Map<String,Object>>>();
		
		for(Map<String,Object> map : list){
			currentNodeId = (String)map.get("nodeId");
			if (childMap.get(currentNodeId)!=null) {
				childMap.get(currentNodeId).add(map);
			}else{
				List<Map<String,Object>> childList = new ArrayList<Map<String,Object>>();
				childList.add(map);
				childMap.put(currentNodeId, childList);
			}
		}
		return childMap;
	}
	/**
	 * 格式化Map,把Map的key和value输出为一个json字符串
	 */
	public String formatMapToJson(Map<String,Object> map){
		String result = "{";
		for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
			String key = it.next();
			result += key+":'"+map.get(key)+"',";
		}
		result = result.substring(0, result.length()-1);
		result += "}";
		return result;
	}
	/**
	 * 生成ID
	 * @return
	 */
	public String createId(String header){
		return header+System.currentTimeMillis()+(Math.random()+"").substring(5, 15)+"";
	}
	/**
	 * 创造账单号
	 * @param header
	 * @param number
	 * @param length
	 * @return
	 */
	public String createAccId(String header,int number,int length){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return header + sdf.format(new Date()) + numberToZero(number,length);
	}
	/**
	 * 补零数字
	 * @param number
	 * @param length
	 * @return
	 */
	public String numberToZero(int number,int length){
		String result = "";
		String numberString = String.valueOf(number);
		int zeroLength = length-numberString.length();
		while(zeroLength>0){
			result += "0";
			zeroLength --;
		}
		return result+number;
	}
	/**
	 * 补空格
	 * @param number
	 * @param length
	 * @return
	 * @throws Exception 
	 */
	public String stringToBlank(String numberString,int length,String str) throws Exception{
		String result = "";
		int zeroLength = length-numberString.getBytes().length;
		while(zeroLength>0){
			result += str;
			zeroLength --;
		}
		if(zeroLength<0){
			throw new Exception("数据长度校验不通过, 字段为["+numberString+"], 最大长度为["+length+"], 实际长度为["+numberString.getBytes().length+"]");
		}
		return numberString+result;
	}
	/**
	 * 得到当天日期,默认格式yyy-MM-dd
	 * @return
	 */
	public String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	/**
	 * 得到当天日期,format格式
	 * @return
	 */
	public String getCurrentDate(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	public Date getInputDate(String date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date redate = new Date();
		try {
			redate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redate;
	}
	/**
	 * 对输入的日期加天数
	 * @param currentdate
	 * @param day
	 * @return
	 */
	public String dayAddNumber(Date currentdate,int day){
		long dateMS = currentdate.getTime();  
		dateMS = dateMS   +   60   *   60   *   24   *   1000   *   day;  
		currentdate.setTime(dateMS);  
		SimpleDateFormat   formatter   =   new   SimpleDateFormat("yyyy-MM-dd");    
		String   dateString   =   formatter.format(currentdate);  
		return dateString;
	}
	/**
	 * 日期增加月数
	 * @param date
	 * @param number
	 * @return
	 */
	public String dateAddMonth(Date date,int number){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, number);
		Date newDate = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(newDate);
	}
	/**
	 * 日期增加天数
	 * @param date
	 * @param number
	 * @return
	 */
	public String dateAddNumber(Date date,int number){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, number);
		Date newDate = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(newDate);
	}
	/**
	 * 格式化日期,例如yyyy-MM-dd HH:mm:ss格式化为yyyy-MM-dd
	 * @param date		日期
	 * @param format	yyyy-MM-dd
	 * @return
	 */
	public String formatDate(String date,String format){
		String formatDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			formatDate = sdf.format(DateFormat.getDateInstance().parse(date));
		} catch (ParseException e) {
			logger.error("StringUtil的formatDate方法错误:", e);
		}
		return formatDate;
	}
	/**
	 * 两个日期相减,得到相隔月份
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int subDate(String startDate,String endDate,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		int year = 0;
		int month = 0;
		try {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(sdf.parse(startDate));
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(sdf.parse(endDate));
			year = cal1.get(Calendar.YEAR)-cal2.get(Calendar.YEAR);
			month = cal1.get(Calendar.MONTH)-cal2.get(Calendar.MONTH);
		} catch (ParseException e) {
			logger.error("StringUtil的subDate方法错误:", e);
		}
		return year*12+month;
	}
	/**
	 * 返回日期的当月最大值
	 * @param date
	 * @param format
	 * @return
	 */
	public String maxDate(String date,String format){
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
			result = cal.get(Calendar.YEAR)+"-"+((cal.get(Calendar.MONTH)+1)<10?("0"+(cal.get(Calendar.MONTH)+1)):(cal.get(Calendar.MONTH)+1))+"-"+cal.getActualMaximum(Calendar.DATE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 返回日期的当月最小值
	 * @param date
	 * @param format
	 * @return
	 */
	public String minDate(String date,String format){
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
			result = cal.get(Calendar.YEAR)+"-"+((cal.get(Calendar.MONTH)+1)<10?("0"+(cal.get(Calendar.MONTH)+1)):(cal.get(Calendar.MONTH)+1))+"-"+(cal.getActualMinimum(Calendar.DATE)<10?"0"+cal.getActualMinimum(Calendar.DATE):cal.getActualMinimum(Calendar.DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * date1-date2
	 * @param date1
	 * @param date2
	 * @param type  Calendar.MINUTE, Calendar.SECOND, Calendar.HOUR 
	 * @return
	 */
	public long subDate(Date date1,Date date2,int type){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		long time = cal1.getTimeInMillis()-cal2.getTimeInMillis();
		if(type==Calendar.HOUR){
			return time/1000/60/60;
		}else if(type==Calendar.MINUTE){
			return time/1000/60;
		}else if(type==Calendar.SECOND){
			return time/1000;
		}
		return 0L;
	}
	public Request toRequest(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,String[]> map = request.getParameterMap();
		Map<String,String> paramsMap = new HashMap<String,String>();
		String entity = request.getParameter("entity");
		Request req = new Request();
		try {
			Object classObj = null;
			if(entity!=null){
				classObj = Class.forName(entity).newInstance();
				for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
					String key = it.next();
					String[] value = map.get(key);
					Class properties = PropertyUtils.getPropertyType(classObj, key);
					paramsMap.put(key, value[0]);
					if(properties!=null){
						String type = properties.getSimpleName();
						if (type.equals("String")) {
							PropertyUtils.setProperty(classObj, key, value[0]);
						}else if (type.equals("Integer")){
							PropertyUtils.setProperty(classObj, key, Integer.valueOf(value[0]));
						}else if (type.equals("Double")){
							PropertyUtils.setProperty(classObj, key, Double.valueOf(value[0]));
						}else if (type.equals("Date")){
							if(!"".equals(value[0])) PropertyUtils.setProperty(classObj, key, DateFormat.getDateInstance().parse(value[0]));
						}else if (type.equals("BigDecimal")){
							PropertyUtils.setProperty(classObj, key, new BigDecimal(value[0]));
						}else if (type.equals("Float")){
							PropertyUtils.setProperty(classObj, key, Float.valueOf(value[0]));
						}else{
							throw new Exception("未知数据类型==>类型为["+type+"]");
						}
					}
				}
				//查看entity实体存放情况
//				toEntityString(classObj);
				
			}else{
				for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
					String key = it.next();
					String[] value = map.get(key);
					paramsMap.put(key, value[0]);
				}
			}
			req.setEntity(classObj);
			req.setParamsMap(paramsMap);
			req.setUserInfo(request.getSession().getAttribute("userInfo"));
			req.setHttpRequest(request);
			req.setHttpResponse(response);
		}catch (Exception e) {
			e.printStackTrace();
			if(!req.getResponse().equals("")){
				response.getWriter().write(req.getResponse().toString());
			}else if(req.getResponse()!=null && e.getMessage()!=null){
				response.getWriter().write(e.getMessage());
			}else{
				response.getWriter().write(e.toString());
			}
			logger.error("StringUtil的toRequest方法出错:", e);
		} 
		
		return req;
	}
	
	/**
	 * 转换定长字符串为JavaBean对象
	 * 	<bean id="testBean" class="test.TestBean">
  			<property name="a1" value="1@L2"></property>
  			<property name="a2" value="2@L5"></property>
  			<property name="a3" value="3@L3"></property>
  			<property name="teamBean" ref="teamBean"></property>
  		</bean>
	 * @param bean
	 * @param a
	 * @throws Exception 
	 */
	public String inputData(Object bean,String a) throws Exception{
		List<String> propertylist = new ArrayList<String>();
		getProperties(propertylist, bean);
		Collections.sort(propertylist, new Comparator<String>(){

			public int compare(String pro1, String pro2) {
				String[] tmp1 = pro1.split("@");
				String[] tmp2 = pro2.split("@");
				int t1 = Integer.parseInt(tmp1[0]);
				int t2 = Integer.parseInt(tmp2[0]);
				if (t1<t2) {
					return -1;
				}else if (t1==t2){
					return 0;
				}else{
					return 1;
				}
			}
		});
		Map<String,String> map = new HashMap<String, String>();
		int sub = 0;
		int allLen = 0;
		for(int i=0; i<propertylist.size();i++){
			String[] result = parseData(propertylist.get(i).split("@")[1]);
			allLen += Integer.parseInt(result[1]);
		}
		int reportLen = a.length();
		if(allLen>reportLen){
			throw new Exception("解析长度不符,需要解析长度为:["+allLen+"],实际报文长度为:["+reportLen+"]");
		}
		for(int i=0; i<propertylist.size();i++){
			String[] result = parseData(propertylist.get(i).split("@")[1]);
			if(result[0].equals("L")){
				int len = Integer.parseInt(result[1]);
				map.put(propertylist.get(i), a.substring(sub,sub+len));
				sub += len;
			}
		}
		setProperties(map, bean);
		return a.substring(allLen);
	}
	/**
	 * 返回list格式为[顺序@长度]
	 * @param list
	 * @param obj
	 * @param flag
	 */
	public void getProperties(List<String> list,Object obj){
		PropertyDescriptor[] property = PropertyUtils.getPropertyDescriptors(obj);
		for (int i = 0; i < property.length; i++) {
			String key = property[i].getName();
			try {
				Object propertyValue = PropertyUtils.getProperty(obj, key);
				if( propertyValue instanceof String){
					String ele = propertyValue.toString();
					if(ele.indexOf("@")!=-1){
						list.add(propertyValue.toString());
					}
				}else if (propertyValue instanceof Class){
				}else{
					getProperties(list, propertyValue);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 返回list格式为[字段@顺序@长度]
	 * @param list
	 * @param obj
	 * @param flag
	 */
	public void getProperties(List<String> list,Object obj,String flag){
		PropertyDescriptor[] property = PropertyUtils.getPropertyDescriptors(obj);
		for (int i = 0; i < property.length; i++) {
			String key = property[i].getName();
			try {
				Object propertyValue = PropertyUtils.getProperty(obj, key);
				if( propertyValue instanceof String){
					String ele = propertyValue.toString();
					if(ele.indexOf(flag)!=-1){
						list.add(key+flag+propertyValue.toString());
					}
				}else if (propertyValue instanceof Class){
				}else{
					getProperties(list, propertyValue);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	public void setProperties(Map<String,String> map,Object obj){
		PropertyDescriptor[] property = PropertyUtils.getPropertyDescriptors(obj);
		for (int i = 0; i < property.length; i++) {
			String key = property[i].getName();
			try {
				Object propertyValue = PropertyUtils.getProperty(obj, key);
				if( propertyValue instanceof String){
					String ele = propertyValue.toString();
					if(map.containsKey(ele)){
						PropertyUtils.setProperty(obj, key, map.get(ele));
					}
				}else if (propertyValue instanceof Class){
				}else{
					setProperties(map, propertyValue);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	public String[] parseData(String x){
		String[] result = new String[2];
		result[0] = x.substring(0, 1);
		result[1] = x.substring(1);
		return result;
	}
	/**
	 * 
	 * @param sbean	模版Bean
	 * @param tbean	需要转化的Bean
	 * @return	String定长
	 * @throws Exception
	 */
	public String outputData(Object sbean,Object tbean) throws Exception{
		List<String> propertylist = new ArrayList<String>();
		getProperties(propertylist, sbean,"@");
		Collections.sort(propertylist, new Comparator<String>(){

			public int compare(String pro1, String pro2) {
				String[] tmp1 = pro1.split("@");
				String[] tmp2 = pro2.split("@");
				int t1 = Integer.parseInt(tmp1[1]);
				int t2 = Integer.parseInt(tmp2[1]);
				if (t1<t2) {
					return -1;
				}else if (t1==t2){
					return 0;
				}else{
					return 1;
				}
			}
		});
		String response = "";
		for(int i=0; i<propertylist.size();i++){
			String[] splitStr = propertylist.get(i).split("@");
			String[] result = parseData(splitStr[2]);
			if(result[0].equals("L")){
				int len = Integer.parseInt(result[1]);
				String value = String.valueOf(PropertyUtils.getProperty(tbean, splitStr[0]));
				response += stringToBlank(value,len," ");
			}else if(result[0].equals("D")){
				response += String.valueOf(PropertyUtils.getProperty(tbean, splitStr[0]));
			}
		}
		return response;
	}
	/**====================================================**/
	public static void main(String[] args) {
		int a = StringUtil.newInstance().subDate("2010-05-07 12:00:00", "2010-03-02","yyyy-MM-dd");
		String b = StringUtil.newInstance().minDate("2010-9-07 12:00:00", "yyyy-MM-dd");
		String c = StringUtil.newInstance().dateAddMonth(StringUtil.newInstance().getInputDate("2010-05-07 12:00:00", "yyyy-MM-dd"), 10);
		System.out.println(c);
	}
}
