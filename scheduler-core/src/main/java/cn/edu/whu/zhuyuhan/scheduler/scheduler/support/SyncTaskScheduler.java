package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.AbstractTaskRegistrar;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public abstract class SyncTaskScheduler extends AbstractTaskRegistrar {

    private org.springframework.scheduling.TaskScheduler taskScheduler;

    private ScheduledExecutorService executorService;

    protected ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();

    @Override
    public String kindMatch() {
        return TaskSchedulerKindConstant.SYNC_TASK_SCHEDULER;
    }

    @Override
    public void schedule(@Nullable Long period) {
        initTaskRegistrar();
        schedule();
    }

    private void initTaskRegistrar() {
        if (getTaskExecutor() == null) {
            this.executorService = new ScheduledThreadPoolExecutor(DEFAULT_POOL_SIZE, getThreadFactory());
        }
        this.taskScheduler = new ConcurrentTaskScheduler(executorService);
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }

    protected ScheduledExecutorService getTaskExecutor() {
        return null;
    }

    protected abstract void schedule();

}
