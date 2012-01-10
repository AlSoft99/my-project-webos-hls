package com.ux.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.frame.servlet.ServletFactory;
import com.frame.util.FileUpload;
import com.frame.util.Utils;
import com.google.gson.Gson;
import com.village.file.FileEntity;

public class HeadPhotoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileUpload upload = ServletFactory.newInstant().getFactory().getBean("fileUpload", FileUpload.class);
		List<FileEntity> file = upload.upload(request, response,"head"+"/");
		/*String minpath = System.getProperty("webapp.root")+file.get(0).getUploadurl()+"min/";
		File min = new File(minpath);
		if(!min.exists()){
			min.mkdirs();
		}*/
		BufferedImage image = ImageIO.read(file.get(0).getStream());
    	float w = image.getWidth();
    	float h = image.getHeight();

    	if(w<h){
    		w = (300/h)*w;
    		h = 300;
    	}else{
    		h = (300*h)/w;
    		w = 300;
    	}
    	System.out.println("11w:"+w+"  h:"+h+"");
		Utils.reduceImg(file.get(0).getStream(), System.getProperty("webapp.root")+file.get(0).getUploadurl()+file.get(0).getUploadname(), Integer.parseInt(w+""), Integer.parseInt(h+""));
		Gson gson = new Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<FileEntity>>() {}.getType();    
        String beanListToJson = gson.toJson(file,type);
		response.getWriter().print(beanListToJson);
	}
	

}
