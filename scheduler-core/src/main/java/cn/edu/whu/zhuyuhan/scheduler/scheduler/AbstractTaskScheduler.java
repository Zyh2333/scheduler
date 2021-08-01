package cn.edu.whu.zhuyuhan.scheduler.scheduler;

import cn.edu.whu.zhuyuhan.scheduler.scheduler.factory.TaskSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public abstract class AbstractTaskScheduler implements Scheduler {

    protected static final Logger log = LoggerFactory.getLogger(AbstractTaskScheduler.class);

    private Scheduler getTaskScheduler() throws Exception {
        Class<? extends Scheduler> taskScheduler = TaskSchedulerFactory.getTaskScheduler(kindMatch());
        // TODO NEED FIX NULL CONSTRUCTOR WITH ABSTRACT CLASS
        Scheduler scheduler = taskScheduler.getDeclaredConstructor().newInstance();
        return scheduler;
    }

    protected abstract String kindMatch();

    protected abstract ThreadFactory getThreadFactory();

}
