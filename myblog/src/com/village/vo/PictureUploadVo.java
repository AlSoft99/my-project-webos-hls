package com.village.vo;

import org.springframework.stereotype.Repository;

import com.frame.servlet.ServletFactory;
import com.frame.util.FileUpload;
import com.frame.vo.BaseVo;
import com.frame.vo.Request;

@Repository
public class PictureUploadVo implements BaseVo{

	@Override
	public Request execute(Request request) throws Exception {
		FileUpload file = ServletFactory.newInstant().getFactory().getBean("fileUpload", FileUpload.class);
		
		return request;
	}

}
