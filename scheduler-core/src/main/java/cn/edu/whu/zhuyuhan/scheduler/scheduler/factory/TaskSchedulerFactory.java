package cn.edu.whu.zhuyuhan.scheduler.scheduler.factory;

import cn.edu.whu.zhuyuhan.scheduler.scheduler.Scheduler;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerEnum;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 13:25
 **/
public class TaskSchedulerFactory {

    public static Class<? extends Scheduler> getTaskScheduler(String kind) {
        return TaskSchedulerEnum.getTaskSchedulerInstance(kind);
    }

}
