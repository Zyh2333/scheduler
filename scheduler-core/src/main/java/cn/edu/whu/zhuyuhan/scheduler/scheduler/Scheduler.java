package cn.edu.whu.zhuyuhan.scheduler.scheduler;

import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:02
 **/
// TODO 资源监控
public interface Scheduler {

    void schedule(ScheduleComponentTaskInstance taskInstance);

}
