package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.CronConstant;
import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.AbstractTaskScheduler;
import cn.edu.whu.zhuyuhan.scheduler.thread.factory.support.AsyncTaskThreadFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.concurrent.ThreadFactory;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public class AsyncTaskScheduler<P> extends AbstractTaskScheduler {

    @Override
    protected ThreadFactory getThreadFactory() {
        return new AsyncTaskThreadFactory();
    }

    @Override
    public void schedule(ScheduleComponentTaskInstance taskInstance) {
        Assert.isTrue(taskInstance.isAsync(), "schedule async switch is closed");
        // TODO NEED OPTIMIZE 异步定时任务间隔时间算法优化
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(taskInstance.getCron());
        log.info("TaskScheduleComponent inited success with taskInstance{}", taskInstance);
        AsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.submit(() -> {
            Date start = new Date();
            Date submitTime = start;
            while (true) {
                if (Math.abs((submitTime.getTime() - new Date().getTime())) > CronConstant.MIN_SUBMIT_GAP) continue;
                Date next = cronSequenceGenerator.next(start);
                start = next;
                submitTime = next;
                AsyncTaskExecutor asyncTaskExecutorInner = new SimpleAsyncTaskExecutor(getThreadFactory());
                asyncTaskExecutorInner.submit(taskInstance.getTask());
            }
        });
    }

    @Override
    protected String kindMatch() {
        return TaskSchedulerKindConstant.ASYNC_TASK_SCHEDULER;
    }
}
