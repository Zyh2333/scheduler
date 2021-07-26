package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.async.AsyncTask;
import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.AbstractTaskRegistrar;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.Assert;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public abstract class AsyncTaskScheduler<P> extends AbstractTaskRegistrar implements AsyncTask {

    @Override
    public String kindMatch() {
        return TaskSchedulerKindConstant.ASYNC_TASK_SCHEDULER;
    }

    @Override
    public void schedule(Long period) {
        Assert.isTrue(async(), "schedule async switch is closed");
        this.scheduleAsyncInner();
    }

    private void scheduleAsyncInner() {
        // TODO NEED FIX 单次异步任务 -> 异步定时任务
        AsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor(getThreadFactory());
        asyncTaskExecutor.submit(doTask());

//        org.springframework.scheduling.TaskScheduler taskScheduler = new ConcurrentTaskScheduler(asyncTaskExecutor, new ScheduledThreadPoolExecutor(DEFAULT_POOL_SIZE, getThreadFactory()));
//        taskScheduler.schedule(doTask(), new Date(new Date().getTime() + period()));
    }

    @Override
    public Runnable doTask() {
        return doAsyncTask();
    }

    protected abstract void schedule();

    protected abstract Runnable doAsyncTask();

    protected abstract Long getPeriod(P period);

}
