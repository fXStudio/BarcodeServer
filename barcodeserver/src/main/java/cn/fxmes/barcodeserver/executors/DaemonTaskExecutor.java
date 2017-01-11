package cn.fxmes.barcodeserver.executors;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

/**
 * 后台作业执行器
 * 
 * @author Administrator
 */
final class DaemonTaskExecutor {
	private Logger log = Logger.getLogger(DaemonTaskExecutor.class);

	DaemonTaskExecutor(TaskExecutor taskExecutor, List<Runnable> tasks) {
		log.debug("DaemonTaskExecutor Created.");
		log.debug("Task list size: " + tasks.size());

		for (Runnable task : tasks) {
			taskExecutor.execute(task);
		}
	}
}
