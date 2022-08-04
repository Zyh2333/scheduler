package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.AbstractTaskScheduler;

import java.util.concurrent.ThreadFactory;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public abstract class AbstractDistributedTaskScheduler extends AbstractTaskScheduler {

    public AbstractDistributedTaskScheduler() {
    }

    @Override
    public void schedule(ScheduleComponentTaskInstance taskInstance) {
        scheduleInner(taskInstance);
    }

    public abstract void scheduleInner(ScheduleComponentTaskInstance taskInstance);

    @Override
    protected ThreadFactory getThreadFactory() {
        return null;
    }

}
