package com.village.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.frame.servlet.ServletFactory;
import com.frame.util.FileUpload;
import com.frame.util.Utils;
import com.google.gson.Gson;

public class PhotoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileUpload upload = ServletFactory.newInstant().getFactory().getBean("fileUpload", FileUpload.class);
		List<FileEntity> file = upload.upload(request, response,"guanrl/picture"+"/");
		String minpath = System.getProperty("webapp.root")+file.get(0).getUploadurl()+"min/";
		File min = new File(minpath);
		if(!min.exists()){
			min.mkdirs();
		}
		Utils.reduceImg(file.get(0).getStream(), minpath+file.get(0).getUploadname(), 100, 100);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<FileEntity>>() {}.getType();    
        String beanListToJson = gson.toJson(file,type);
		response.getWriter().print(beanListToJson);
	}
	

}
