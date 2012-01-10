package com.frame.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Utils {
	/**
	 * 减少图片尺寸
	 * @param imgsrc 输入文件路径
	 * @param imgdist 输出新尺寸文件路径
	 * @param widthdist 输出尺寸宽
	 * @param heightdist 输出尺寸高
	 */
	public static void reduceImg(String imgsrc, String imgdist, int widthdist,   
	        int heightdist) {   
	    try {   
	        File srcfile = new File(imgsrc);   
	        if (!srcfile.exists()) {   
	            return;   
	        }   
	        Image src = javax.imageio.ImageIO.read(srcfile);   
	  
	        BufferedImage tag= new BufferedImage((int) widthdist, (int) heightdist,   
	                BufferedImage.TYPE_INT_RGB);   
	  
	        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_SMOOTH), 0, 0,  null);   
	///         tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_AREA_AVERAGING), 0, 0,  null);   
	           
	        FileOutputStream out = new FileOutputStream(imgdist);   
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
	        encoder.encode(tag);   
	        out.close();   
	  
	    } catch (IOException ex) {   
	        ex.printStackTrace();   
	    }   
	} 
	/**
	 * 减少图片尺寸
	 * @param stream 输入文件流
	 * @param imgdist 输出新尺寸文件路径
	 * @param widthdist 输出尺寸宽
	 * @param heightdist 输出尺寸高
	 */
	public static void reduceImg(InputStream stream, String imgdist, int widthdist, int heightdist) {   
	    try {   
	    	System.out.println("stream:"+stream);
	        Image src = javax.imageio.ImageIO.read(stream);   
	        BufferedImage tag= new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);   
	        System.out.println("src:"+src);
	        System.out.println("tag.getGraphics():"+tag.getGraphics());
	        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_SMOOTH), 0, 0,  null);   
	        FileOutputStream out = new FileOutputStream(imgdist);   
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
	        encoder.encode(tag);   
	        out.close();   
	  
	    } catch (IOException ex) {   
	        ex.printStackTrace();   
	    }   
	} 
	/**
	 * 减少图片尺寸
	 * @param stream 输入文件流
	 * @param imgdist 输出新尺寸文件路径
	 * @param widthdist 输出尺寸宽
	 * @param heightdist 输出尺寸高
	 */
	public static void reduceImg(Image src, String imgdist, int widthdist, int heightdist) {   
	    try {   
	        BufferedImage tag= new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);
	        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_SMOOTH), 0, 0,  null);
	        FileOutputStream out = new FileOutputStream(imgdist);
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	        encoder.encode(tag);
	        out.close();
	  
	    } catch (IOException ex) {   
	        ex.printStackTrace();   
	    }   
	} 
}
