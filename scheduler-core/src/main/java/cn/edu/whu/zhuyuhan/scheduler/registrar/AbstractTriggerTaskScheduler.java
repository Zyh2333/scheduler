package cn.edu.whu.zhuyuhan.scheduler.registrar;

import cn.edu.whu.zhuyuhan.scheduler.TriggerTask;
import org.springframework.util.Assert;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public abstract class AbstractTriggerTaskScheduler extends AbstractTaskScheduler implements TriggerTask {

    public void schedule() {
        Assert.notNull(doTask(), "trigger task can't be null");
        scheduledTaskRegistrar.addTriggerTask(doTask(), getTrigger());
        scheduledTaskRegistrar.getTriggerTaskList().forEach(scheduledTaskRegistrar::scheduleTriggerTask);
    }

}
