package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.AbstractTaskScheduler;
import cn.edu.whu.zhuyuhan.scheduler.thread.factory.support.DistributedAsyncTaskThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public class DistributedAsyncTaskScheduler extends AbstractTaskScheduler {

    @Override
    protected String kindMatch() {
        return TaskSchedulerKindConstant.DISTRIBUTED_ASYNC_TASK_SCHEDULER;
    }

    @Override
    protected ThreadFactory getThreadFactory() {
        return new DistributedAsyncTaskThreadFactory();
    }

    @Override
    public void schedule(ScheduleComponentTaskInstance taskInstance) {

    }

}
