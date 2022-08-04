package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public class DistributedSyncTaskScheduler extends AbstractDistributedTaskScheduler {

    @Override
    protected String kindMatch() {
        return TaskSchedulerKindConstant.DISTRIBUTED_SYNC_TASK_SCHEDULER;
    }

    @Override
    public void scheduleInner(ScheduleComponentTaskInstance taskInstance) {
    }

}
