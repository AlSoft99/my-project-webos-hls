package com.ux.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.frame.servlet.ServletFactory;
import com.frame.util.FileUpload;
import com.village.file.FileEntity;

public class KindeditorServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "tmp/head/";
		String path = System.getProperty("webapp.root")+url;
		FileUpload upload = ServletFactory.newInstant().getFactory().getBean("fileUpload", FileUpload.class);
		List<FileEntity> file = upload.upload(request, response,url);
		String responseUrl = file.get(0).getUploadurl()+file.get(0).getUploadname();
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", responseUrl);
		System.out.println("obj.toJSONString(): "+obj.toJSONString());
		response.getWriter().print(obj.toJSONString());
	}
}