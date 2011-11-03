package test;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Test1 extends QuartzJobBean{
	public void handle() {
		System.out.println("Test1");
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		handle();
	}
}
