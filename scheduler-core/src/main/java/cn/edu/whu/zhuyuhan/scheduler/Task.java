package cn.edu.whu.zhuyuhan.scheduler;

import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:37
 **/
public interface Task {

    ScheduleComponentTaskInstance task();

    // TODO 动态限流
    boolean isOpen();

}
