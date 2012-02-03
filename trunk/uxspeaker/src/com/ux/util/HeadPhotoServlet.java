package com.ux.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

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
		/*List<FileEntity> file = upload.upload(request, response,"head"+"/");*/
		String url = "tmp/head/";
		String path = "/"+url;
		String reduce = request.getParameter("reduce");
		FileEntity entity = null;
		File min = new File(url);
		if(!min.exists()){
			min.mkdirs();
		}
		if(!"false".equals(reduce)){
			entity = upload.getInputStream(request);
			entity.setUploadurl(url);
			BufferedImage image = ImageIO.read(entity.getStream());
	    	float w = image.getWidth();
	    	float h = image.getHeight();

	    	if(w<h){
	    		w = (300/h)*w;
	    		h = 300;
	    	}else{
	    		h = (300*h)/w;
	    		w = 300;
	    	}
	    	int intW = (int)w;
	    	int intH = (int)h;
			Utils.reduceImg(image, path+entity.getUploadname(), intW, intH);
		}else{
			List<FileEntity> file = upload.upload(request, response,url);
			if(file.size()>0){
				entity = file.get(0);
			}
		}
		
		Gson gson = new Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<FileEntity>() {}.getType();    
        String beanListToJson = gson.toJson(entity,type);
		response.getWriter().print(beanListToJson);
	}
	

}
