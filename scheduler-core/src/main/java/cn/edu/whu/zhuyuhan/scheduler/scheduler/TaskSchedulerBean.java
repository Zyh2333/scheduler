package cn.edu.whu.zhuyuhan.scheduler.scheduler;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Async;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.common.util.ReflectionUtils;
import cn.edu.whu.zhuyuhan.scheduler.common.util.cron.CronUtils;
import cn.edu.whu.zhuyuhan.scheduler.registrar.ScheduleComponentRegistrar;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.support.AsyncTaskScheduler;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.support.SpringSyncCronTaskScheduler;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.support.SyncTaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public class TaskSchedulerBean {

    private static final Logger log = LoggerFactory.getLogger(TaskSchedulerBean.class);

    // TODO 可配置
    public static final Integer DEFAULT_POOL_SIZE = 1;

    public void schedule() throws InvocationTargetException, IllegalAccessException {
        for (ScheduleComponent value : ScheduleComponentRegistrar.componentMap.values()) {
            // TODO 动态降级
            if (!value.isSchedule()) continue;
            if (!this.schedule(value)) {
                log.warn("ScheduleComponent bean inited with empty task: {}", value.getBean());
            }
        }
    }

    private boolean schedule(ScheduleComponent scheduleComponent) throws InvocationTargetException, IllegalAccessException {
        List<Method> methods = ReflectionUtils.collectScheduleTask(scheduleComponent);
        if (CollectionUtils.isEmpty(methods)) return false;
        for (Method m : methods) {
            // 方法注解重新覆盖类注解
            Task taskAnno = m.getAnnotation(Task.class);
            Async asyncAnno = m.getAnnotation(Async.class);
            scheduleComponent.addTask((Runnable) m.invoke(scheduleComponent.getBean()), taskAnno, asyncAnno);
            log.info("register task method {} in bean {} success", m, scheduleComponent.getBean());
        }
        for (ScheduleComponentTaskInstance taskInstance : scheduleComponent.getTasks()) {
            // async
            if (taskInstance.isAsync()) {
                AsyncTaskScheduler asyncTaskScheduler = new AsyncTaskScheduler();
                asyncTaskScheduler.schedule(taskInstance);
            } else {
                String cron = taskInstance.getCron();
                // not trigger
                if (StringUtils.isEmpty(cron) || !CronUtils.valid(cron)) {
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(taskInstance.getTask());
                } else {
                    SyncTaskScheduler syncTaskScheduler = new SpringSyncCronTaskScheduler();
                    syncTaskScheduler.schedule(taskInstance);
                }
            }
        }
        return true;
    }

}
