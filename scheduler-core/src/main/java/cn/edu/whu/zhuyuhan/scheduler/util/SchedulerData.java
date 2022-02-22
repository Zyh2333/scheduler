package cn.edu.whu.zhuyuhan.scheduler.util;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/22 4:28 下午
 **/
public class SchedulerData {

    public static SchedulerDO map(ScheduleComponentTaskInstance taskInstance) {
        SchedulerDO schedulerDO = new SchedulerDO();
        schedulerDO.setCron(taskInstance.getCron());
        schedulerDO.setName(taskInstance.getName());
        schedulerDO.setParentName(taskInstance.getParentName());
        return schedulerDO;
    }
}
