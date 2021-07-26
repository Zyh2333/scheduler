package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.Task;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.Scheduler;
import org.springframework.lang.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public abstract class TaskScheduler implements Scheduler, Task {

    protected Task task;

    public static final Integer DEFAULT_POOL_SIZE = 1;

    @Override
    public void schedule(@Nullable Long period) {
        // async
        if (task.async()) {
            AsyncTaskScheduler asyncTaskScheduler = (AsyncTaskScheduler) this;
            asyncTaskScheduler.schedule(period);
        } else {
            // not trigger
            if (period == null || period.longValue() == -1L) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(task.doTask());
            } else {
                SyncTaskScheduler syncTaskScheduler = (SyncTaskScheduler) this;
                syncTaskScheduler.schedule(period);
            }
        }
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    protected ThreadFactory getThreadFactory() {
        return Executors.defaultThreadFactory();
    }

    public abstract String kindMatch();

}
