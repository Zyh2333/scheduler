package cn.edu.whu.scheduler.test;

import cn.edu.whu.zhuyuhan.scheduler.async.AbstractAsyncTriggerTaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 22:13
 **/
public class AsyncScheduleTaskServiceTest extends AbstractAsyncTriggerTaskScheduler {

    public static void main(String[] args) {

    }

    @Override
    public Runnable doAsyncTask() {
        return () -> {
            System.out.println("trigger task execute");
        };
    }

    @Override
    public Long period() {
        return null;
    }

    @Override
    public Trigger getTrigger() {
        return triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger("0/5 * * * * ?");
            return cronTrigger.nextExecutionTime(triggerContext);
        };
    }

}
