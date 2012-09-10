package com.ux.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.frame.servlet.ServletFactory;
import com.frame.util.FileUpload;
import com.ux.entity.UserInfo;
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
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute("userinfo");
		if(userInfo==null){
			return ;
		}
		String email = userInfo.getEmail();
		String url = "upload/"+email+"/article/";
		FileUpload upload = ServletFactory.newInstant().getFactory().getBean("fileUpload", FileUpload.class);
		List<FileEntity> file = upload.upload(request, response,url);
		String responseUrl = file.get(0).getUploadurl()+file.get(0).getUploadname();
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", responseUrl);
		response.getWriter().print(obj.toJSONString());
	}
}