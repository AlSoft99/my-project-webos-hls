package com.frame.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/*@Controller  
@RequestMapping("/village/swfupload")  */
public class FileUploadController extends MultiActionController {  
  
    /** 
     * 1、文件上传 
     * @param request 
     * @param response 
     * @return 
     */  
    public ModelAndView uploadFiles(HttpServletRequest request, HttpServletResponse response) {  
        ModelAndView mav = new ModelAndView();  
        System.out.println("++++++++++++++++++++++");
        
        return mav;  
    }  
  
    /** 
     *  
     * @param name 
     * @param file 
     * @param session 
     * @return 
     */  
   /* @RequestMapping(value="/pictureUploadVo.file1", method=RequestMethod.GET)     
    public String uploadFile(@RequestParam("fileName") String fileName,     
            @RequestParam("clientFile") MultipartFile clientFile, HttpSession session){     
    	System.out.println("=================");   
        return "";     
    }   */ 
  
}