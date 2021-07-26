package cn.edu.whu.scheduler.test;

import cn.edu.whu.zhuyuhan.scheduler.registrar.AbstractTriggerTaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 22:13
 **/
public class ScheduleTaskServiceTest extends AbstractTriggerTaskScheduler {

    public static void main(String[] args) {

    }

    @Override
    public Runnable doTask() {
        return () -> {
            System.out.println("trigger task execute");
        };
    }

    @Override
    public Trigger getTrigger() {
        return triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger("0/5 * * * * ?");
            return cronTrigger.nextExecutionTime(triggerContext);
        };
    }
}
