package cn.edu.whu.zhuyuhan.scheduler.scheduler;

import cn.edu.whu.zhuyuhan.scheduler.common.util.TaskSchedulerUtils;
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

    protected abstract String kindMatch();

    protected abstract ThreadFactory getThreadFactory();

    protected Integer getThreadSize() {
        if (TaskSchedulerUtils.isThreadSizeValid(TaskSchedulerBean.CONFIG_POOL_SIZE)) {
            return TaskSchedulerBean.CONFIG_POOL_SIZE;
        }
        return TaskSchedulerBean.DEFAULT_POOL_SIZE;
    }

}
