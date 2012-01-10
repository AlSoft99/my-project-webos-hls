package com.frame.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.village.file.FileEntity;

public class FileUpload {
	private String filepath = "";
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	private String downloalpath = "";
	public List<FileEntity> upload(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		//upload.setHeaderEncoding("UTF-8");
		List<FileEntity> result = new ArrayList<FileEntity>();
		downloalpath = filepath+path;
		File file = new File(System.getProperty("webapp.root")+downloalpath);
		if(!file.exists()){
			file.mkdirs();
		}
		try {
			List items = upload.parseRequest(request);
			if (null != items) {
				Iterator itr = items.iterator();
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {
						continue;
					} else {
						java.util.UUID uuid = java.util.UUID.randomUUID();
						String type = item.getName().split("\\.")[1].toLowerCase();//获取文件类型
						//String file = root + path +"/"+uuid + "." + type;
						String name = uuid + "." + type;
						if(type.equals("jpg") || type.equals("gif") ||type.equals("png")){
							savePngImage(item.getInputStream(),name);
						}else{
							saveFile(item,name);
						}
						FileEntity entity = new FileEntity();
						entity.setSourcename(item.getName());
						entity.setUploadname(name);
						entity.setUploadurl(downloalpath);
						entity.setStream(item.getInputStream());
						result.add(entity);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void saveFile(FileItem item,String filename) throws Exception{
		String root = System.getProperty("webapp.root");
		File savedFile = new File(root + downloalpath ,filename);
		item.write(savedFile);
	}
	/**
	 * 穿入Inputstream图片IO流即可
	 * @param stream
	 */
	public void savePngImage(InputStream stream,String filename) {
        int w = 0;
        int h = 0;
        BufferedImage bufImg = null;
        try {
        	BufferedImage image = ImageIO.read(stream);
        	w = image.getWidth();
        	h = image.getHeight();
	        bufImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	        //bufImg = ImageUtil.createCompatibleImage(w, h, Transparency.OPAQUE);
	        Graphics2D g2d = bufImg.createGraphics();
	        //File file = new File("e:\\2.gif");
	        //javax.imageio.ImageIO.read(file);
        
			g2d.drawImage(image, 0, 0, null);
			g2d.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        saveImage(bufImg, filename);
    }
	// 保存图片
    public void saveImage(BufferedImage img, String filename) {
    	//String path = "upload/picture/";
		String root = System.getProperty("webapp.root");
    	String file = root + downloalpath +"/"+filename;
        try {
            String extension = file.substring(file.lastIndexOf('.') + 1);
            extension = isFormatSupported(extension) ? extension : "png";
            ImageIO.write(img, extension, new File(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 判断ImageIO是否支持指定的图片格式
    public static boolean isFormatSupported(String format) {
        for (String f : ImageIO.getWriterFormatNames()) {
            if (f.equalsIgnoreCase(format)) { return true; }
        }

        return false;
    }
    
    public FileEntity getInputStream(HttpServletRequest request){
    	FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			if (null != items) {
				Iterator itr = items.iterator();
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {
						continue;
					} else {
						FileEntity file = new FileEntity();
						String type = item.getName().split("\\.")[1].toLowerCase();//获取文件类型
						String name = UUID.randomUUID().toString();
						file.setStream(item.getInputStream());
						file.setUploadname(name+"."+type);
						return file;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
}
