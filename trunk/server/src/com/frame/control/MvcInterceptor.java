package com.frame.control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MvcInterceptor implements HandlerInterceptor {

	
	//发向控制器之前执行的动作, 比如session判断,是否过期
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		/*if(request.getSession().getAttribute("userInfo")==null){
			throw new Exception("session已过期,请重新登陆");
		}*/
		System.out.println("preHandle");
		response.getWriter().println("fanhui");
		return true;
	}
	//控制器执行完成后,生成视图之前执行的动作
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
	}
	//释放资源
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
	}

}
