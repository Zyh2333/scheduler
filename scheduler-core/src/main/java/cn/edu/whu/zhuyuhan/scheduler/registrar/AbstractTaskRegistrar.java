package cn.edu.whu.zhuyuhan.scheduler.registrar;

import cn.edu.whu.zhuyuhan.scheduler.scheduler.Scheduler;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.factory.TaskSchedulerFactory;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.support.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public abstract class AbstractTaskRegistrar extends TaskScheduler implements InitializingBean, Ordered {

    protected static final Logger log = LoggerFactory.getLogger(AbstractTaskRegistrar.class);

//    private TaskScheduler taskScheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("task scheduler init success with task {}", this);
        Assert.notNull(this, "task can't be null");
        schedule(this);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private Scheduler getTaskScheduler() throws Exception {
        Class<? extends Scheduler> taskScheduler = TaskSchedulerFactory.getTaskScheduler(kindMatch());
        // TODO NEED FIX NULL CONSTRUCTOR WITH ABSTRACT CLASS
        Scheduler scheduler = taskScheduler.getDeclaredConstructor().newInstance();
        return scheduler;
    }

}
