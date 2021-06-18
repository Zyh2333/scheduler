package cn.edu.whu.zhuyuhan.scheduler.registrar;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public abstract class AbstractTaskScheduler implements InitializingBean, Ordered {

    private TaskScheduler taskScheduler;

    private ScheduledExecutorService executorService;

    protected ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();

    @Override
    public void afterPropertiesSet() throws Exception {
        initTaskRegistrar();
        schedule();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private void initTaskRegistrar() {
        if (getTaskExecutor() == null) {
            this.executorService = new ScheduledThreadPoolExecutor(1, getThreadFactory());
        }
        this.taskScheduler = new ConcurrentTaskScheduler(executorService);
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }

    protected ScheduledExecutorService getTaskExecutor() {
        return null;
    }

    protected ThreadFactory getThreadFactory() {
        return Executors.defaultThreadFactory();
    }

    protected abstract void schedule();

}
