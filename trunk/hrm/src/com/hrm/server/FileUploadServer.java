package com.hrm.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrm.util.FileUpload;

public class FileUploadServer extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileUpload file = new FileUpload();
		file.upload(request, response, "file");
		System.out.println("success!!!!");
		response.setContentType("text/html");
		response.getWriter().println("{success:true,msg:''}");
	}

}
