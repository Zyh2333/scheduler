package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.AbstractTaskScheduler;
import cn.edu.whu.zhuyuhan.scheduler.thread.factory.support.SyncTaskThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public abstract class SyncTaskScheduler extends AbstractTaskScheduler {

    @Override
    public String kindMatch() {
        return TaskSchedulerKindConstant.SYNC_TASK_SCHEDULER;
    }

    protected ScheduledExecutorService getTaskExecutor() {
        return null;
    }

    @Override
    protected ThreadFactory getThreadFactory() {
        return new SyncTaskThreadFactory();
    }


}
