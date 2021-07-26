package cn.edu.whu.zhuyuhan.scheduler.registrar;

import cn.edu.whu.zhuyuhan.scheduler.CronTask;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.support.SyncTaskScheduler;
import org.springframework.util.Assert;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public abstract class AbstractCronTaskScheduler extends SyncTaskScheduler implements CronTask {

    @Override
    public Boolean async() {
        return false;
    }

    @Override
    public void schedule() {
        Assert.notNull(doTask(), "cron task can't be null");
        scheduledTaskRegistrar.addCronTask(doTask(), getCron());
        scheduledTaskRegistrar.getCronTaskList().forEach(scheduledTaskRegistrar::scheduleCronTask);
    }

}
