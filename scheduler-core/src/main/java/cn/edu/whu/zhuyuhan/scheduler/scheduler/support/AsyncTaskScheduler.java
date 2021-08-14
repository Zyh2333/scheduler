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
public class AsyncTaskScheduler extends AbstractTaskScheduler {

    @Override
    protected String kindMatch() {
        return TaskSchedulerKindConstant.ASYNC_TASK_SCHEDULER;
    }

    @Override
    protected ThreadFactory getThreadFactory() {
        return new AsyncTaskThreadFactory();
    }

    @Override
    public void schedule(ScheduleComponentTaskInstance taskInstance) {
        Assert.isTrue(taskInstance.isAsync(), "schedule async switch is closed");
        // TODO NEED OPTIMIZE 异步定时任务间隔时间算法优化，现在时间不准
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(taskInstance.getCron());
        log.info("TaskScheduleComponent inited success with taskInstance{}", taskInstance);
        AsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.submit(() -> {
            Date start = new Date();
            Date submitTime = start;
            while (true) {
                // 如果提交任务时间与实际执行时间相差1s则继续循环
                if (Math.abs((submitTime.getTime() - new Date().getTime())) > CronConstant.MIN_SUBMIT_GAP) continue;
                AsyncTaskExecutor asyncTaskExecutorInner = new SimpleAsyncTaskExecutor(getThreadFactory());
                asyncTaskExecutorInner.submit(taskInstance.getTask());
                submitTime = cronSequenceGenerator.next(submitTime);
            }
        });
    }

}
