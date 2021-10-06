package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public class SpringSyncCronTaskScheduler extends SpringSyncTaskScheduler {

    @Override
    public void schedule(ScheduleComponentTaskInstance taskInstance) {
        log.info("TaskScheduleComponent init success with {}", taskInstance);
        ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        org.springframework.scheduling.TaskScheduler taskScheduler;
        ScheduledExecutorService taskExecutor = getTaskExecutor(taskInstance);
        ScheduledExecutorService executorService = taskExecutor == null ? new ScheduledThreadPoolExecutor(getThreadSize(), getThreadFactory()) : taskExecutor;
        taskScheduler = new ConcurrentTaskScheduler(executorService);
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
        scheduledTaskRegistrar.addCronTask(taskInstance.getTask(), taskInstance.getCron());
        scheduledTaskRegistrar.getCronTaskList().forEach(scheduledTaskRegistrar::scheduleCronTask);
    }

}
