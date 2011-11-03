package com.hrm.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.hrm.util.ClsFactory;
import com.hrm.vo.StatQuzrtzVo;

public class StatQuzrtzBean extends QuartzJobBean {
	private String startDate = "";
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		com.hrm.util.ClsFactory.newInstance().info("StatQuzrtzBean触发器开启statQuzrtzVo");
		StatQuzrtzVo base = ClsFactory.newInstance().getFactory().getBean("statQuzrtzVo", StatQuzrtzVo.class);
		try {
			base.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		com.hrm.util.ClsFactory.newInstance().info("StatQuzrtzBean触发器关闭");
	}
	
}
