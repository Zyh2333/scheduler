package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.Scheduler;
import org.springframework.util.StringUtils;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 13:27
 **/
public enum TaskSchedulerEnum {

    ASYNC_TASK_SCHEDULER(AsyncTaskScheduler.class, TaskSchedulerKindConstant.ASYNC_TASK_SCHEDULER),

    SYNC_TASK_SCHEDULER(SyncTaskScheduler.class, TaskSchedulerKindConstant.SYNC_TASK_SCHEDULER);

    private Class<? extends Scheduler> taskScheduler;

    private String kind;

    TaskSchedulerEnum(Class<? extends Scheduler> taskScheduler, String kind) {
        this.taskScheduler = taskScheduler;
        this.kind = kind;
    }

    public static Class<? extends Scheduler> getTaskSchedulerInstance(String kind) {
        if (StringUtils.isEmpty(kind)) {
            return SyncTaskScheduler.class;
        }
        for (TaskSchedulerEnum value : TaskSchedulerEnum.values()) {
            if (kind.equals(value.kind)) {
                return value.taskScheduler;
            }
        }
        return SyncTaskScheduler.class;
    }

}
