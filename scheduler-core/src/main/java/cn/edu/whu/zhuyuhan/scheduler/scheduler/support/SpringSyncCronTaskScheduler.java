package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean.DEFAULT_POOL_SIZE;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public class SpringSyncCronTaskScheduler extends SpringSyncTaskScheduler {

    @Override
    public String kindMatch() {
        return TaskSchedulerKindConstant.SYNC_TASK_SCHEDULER_SPRING;
    }

    @Override
    public void schedule(ScheduleComponentTaskInstance taskInstance) {
        log.info("TaskScheduleComponent init success with {}", taskInstance);
        ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        org.springframework.scheduling.TaskScheduler taskScheduler;
        ScheduledExecutorService executorService = getTaskExecutor() == null ? new ScheduledThreadPoolExecutor(DEFAULT_POOL_SIZE, getThreadFactory()) : getTaskExecutor();
        taskScheduler = new ConcurrentTaskScheduler(executorService);
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
        scheduledTaskRegistrar.addCronTask(taskInstance.getTask(), taskInstance.getCron());
        scheduledTaskRegistrar.getCronTaskList().forEach(scheduledTaskRegistrar::scheduleCronTask);
    }

}
