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
        schedulerDO.setSpecial(taskInstance.isSpecial() ? 1 : 0);
        schedulerDO.setSyncAsync(taskInstance.isAsync() ? 1 : 0);
        schedulerDO.setDistributed(taskInstance.isDistributed() ? 1 : 0);
        return schedulerDO;
    }
}
