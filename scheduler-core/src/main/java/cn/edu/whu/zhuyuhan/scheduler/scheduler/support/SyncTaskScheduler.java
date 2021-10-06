package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.AbstractTaskScheduler;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.thread.factory.support.SyncTaskThreadFactory;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.thread.pool.SyncCommonThreadPoolExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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

    /**
     * sync task common thread pool
     *
     * @return if null, new a thread pool for each task, or use this common thread pool
     */
    protected ScheduledExecutorService getTaskExecutor(ScheduleComponentTaskInstance componentTaskInstance) {
        if (componentTaskInstance.isSpecial()) return null;
        return SyncCommonThreadPoolExecutor.SCHEDULED_THREAD_POOL_EXECUTOR;
    }

    @Override
    protected ThreadFactory getThreadFactory() {
        return new SyncTaskThreadFactory();
    }


}
