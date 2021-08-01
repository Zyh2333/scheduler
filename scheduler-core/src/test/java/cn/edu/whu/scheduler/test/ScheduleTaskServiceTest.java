package cn.edu.whu.scheduler.test;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Async;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 22:13
 **/
@TaskScheduleComponent(cron = "0/5 * * * * ?")
public class ScheduleTaskServiceTest {

    @Task(cron = "0/3 * * * * ?")
    public Runnable syncTask1() {
        return () -> {
            System.out.println(Thread.currentThread().getName() + ":" + "sync task1 execute");
        };
    }

    @Async
    @Task
    public Runnable asyncTask2() {
        return () -> {
            System.out.println(Thread.currentThread().getName() + ":" + "async task2 execute");
        };
    }

}
