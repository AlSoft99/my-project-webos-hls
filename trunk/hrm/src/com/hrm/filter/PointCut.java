package com.hrm.filter;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrm.entity.UserInfo;
import com.hrm.util.Constant;
import com.hrm.vo.BaseVo;

/**
 * 切面控制类,before方式用于组件调用前控制
 * @author guanrl
 *
 */
public class PointCut { 
	private List<BaseVo> proxyList;
	public void filter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfo info = (UserInfo)request.getSession().getAttribute(Constant.USER_INFO);
		if(info==null){
			throw new Exception("用户session过期!");
		}
	}
	public List<BaseVo> getProxyList() {
		return proxyList;
	}
	public void setProxyList(List<BaseVo> proxyList) {
		this.proxyList = proxyList;
	}
}
