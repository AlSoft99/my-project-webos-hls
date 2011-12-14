package com.frame.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileServlet extends HttpServlet{

	String file ;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "upload/picture/";
		String root = System.getProperty("webapp.root");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		//upload.setHeaderEncoding("UTF-8");
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
						String type = item.getName().split("\\.")[1];//获取文件类型
						file = root + path +"/"+uuid + "." + type;
						SavePngImage(item.getInputStream());
						//File savedFile = new File(root + path ,uuid + "." + type);
						//item.write(savedFile);
					}
				}
			}
			response.getWriter().print("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 穿入Inputstream图片IO流即可
	 * @param stream
	 */
	public void SavePngImage(InputStream stream) {
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
        
        
        saveImage(bufImg, file);
    }
	// 保存图片
    public void saveImage(BufferedImage img, String path) {
        try {
            String extension = path.substring(path.lastIndexOf('.') + 1);
            extension = isFormatSupported(extension) ? extension : "png";
            ImageIO.write(img, extension, new File(path));
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

}
