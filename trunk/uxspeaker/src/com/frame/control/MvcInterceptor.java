package com.frame.control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ux.entity.UserInfo;

public class MvcInterceptor extends HandlerInterceptorAdapter {

	//发向控制器之前执行的动作, 比如session判断,是否过期
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String uri = request.getServletPath();
		if(uri.equals("/register.do")){
			return true;
		}else{
			UserInfo userinfo = (UserInfo)request.getSession().getAttribute("userinfo");
			if(userinfo==null){
				throw new Exception("session missing, please login again!");
			}
			return true;
		}
		
	}
	//控制器执行完成后,生成视图之前执行的动作
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	//释放资源
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
