package cn.edu.whu.scheduler.test;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 22:13
 **/
@TaskScheduleComponent(cron = "0/5 * * * * ?", async = true)
public class AsyncScheduleTaskServiceTest {

    public static void main(String[] args) {

    }

    @Task
    public Runnable asyncTask() {
        return () -> {
            System.out.println("async trigger task execute");
        };
    }

}
