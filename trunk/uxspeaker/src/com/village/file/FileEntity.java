package com.village.file;

import java.io.InputStream;

public class FileEntity {
	//上传修改的名字
	private String uploadname;
	//上传前的名字
	private String sourcename;
	//上传的路径
	private String uploadurl;
	//输入流
	private InputStream stream;
	public InputStream getStream() {
		return stream;
	}
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	public String getUploadname() {
		return uploadname;
	}
	public void setUploadname(String uploadname) {
		this.uploadname = uploadname;
	}
	public String getSourcename() {
		return sourcename;
	}
	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}
	public String getUploadurl() {
		return uploadurl;
	}
	public void setUploadurl(String uploadurl) {
		this.uploadurl = uploadurl;
	}
}
