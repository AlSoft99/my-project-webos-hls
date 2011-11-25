

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test test1 = new Test();
		int objNum = 7000;
        System.out.println("test object number : " + objNum);
        List jsObjects = new ArrayList(objNum);
        for (int i = 0; i < objNum; i++) {
        	JsonObject jo = new JsonObject();
            jo.setPint(1000);
            jo.setPstring("test string");
            jo.setPlong(0L);
            jo.setPboolean(false);
            jo.setPshort((short) 1);
            jo.setPbyte((byte) 50);
            jo.setPdate(new Date());
            Map map = new HashMap();
            for (int a = 0; a < 100; a++) {
                map.put(i, i);
            }
            List array = new ArrayList();
            for (int a = 0; a < 100; a++) {
                array.add(i);
            }
            jo.setPlist(array);
            jo.setPmap(map);
            jsObjects.add(jo);
        }
 
        Date gsonStart = new Date();
        Gson gs = new Gson();
        gs.toJson(jsObjects);
        Date gsonEnd = new Date();
        long end = gsonEnd.getTime() - gsonStart.getTime();
        System.out.println("gson cost:" + end+"ms");

	}
	
}
