package cn.edu.whu.zhuyuhan.scheduler.scheduler;

import cn.edu.whu.zhuyuhan.mq.rocketmq.producer.Producer;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Async;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.common.util.ReflectionUtils;
import cn.edu.whu.zhuyuhan.scheduler.common.util.cron.CronUtils;
import cn.edu.whu.zhuyuhan.scheduler.registrar.ScheduleComponentRegistrar;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.factory.TaskSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public class TaskSchedulerBean implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(TaskSchedulerBean.class);

    private static Producer producer;

    private static StringRedisTemplate stringRedisTemplate;

    public static final Integer DEFAULT_POOL_SIZE = 1;

    public static final Integer MAX_POOL_SIZE = 10;

    // TODO 同步任务用不着，异步任务不需要
    public static Integer CONFIG_POOL_SIZE = 0;

    public void schedule() throws Exception {
        for (ScheduleComponent value : ScheduleComponentRegistrar.componentMap.values()) {
            // TODO 动态降级 (结合配置中心)
            if (!value.isSchedule()) continue;
            if (!this.schedule(value)) {
                log.warn("ScheduleComponent bean inited with empty task: {}", value.getBean());
            }
        }
    }

    private boolean schedule(ScheduleComponent scheduleComponent) throws Exception {
        List<Method> methods = ReflectionUtils.collectScheduleTask(scheduleComponent);
        if (CollectionUtils.isEmpty(methods)) return false;
        for (Method m : methods) {
            // 方法注解重新覆盖类注解
            Task taskAnno = m.getAnnotation(Task.class);
            Async asyncAnno = m.getAnnotation(Async.class);
            Distributed distributedAnno = m.getAnnotation(Distributed.class);
            scheduleComponent.addTask((Runnable) m.invoke(scheduleComponent.getBean()), taskAnno, asyncAnno, distributedAnno);
            log.info("register task method {} in bean {} success", m, scheduleComponent.getBean());
        }

        // TODO 线程池资源管控
        for (ScheduleComponentTaskInstance taskInstance : scheduleComponent.getTasks()) {
            String cron = taskInstance.getCron();
            if (StringUtils.isEmpty(cron) || !CronUtils.valid(cron)) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(taskInstance.getTask());
                continue;
            }

            Class<? extends Scheduler> taskSchedulerClass = TaskSchedulerFactory.getTaskScheduler(taskInstance.map());
            // 默认newInstance会首先获取缓存的构造器
            Scheduler scheduler = taskSchedulerClass.newInstance();
            scheduler.schedule(taskInstance);
        }
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        producer = applicationContext.getBean(Producer.class);
        stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }

    public static Producer getProducer() {
        return producer;
    }

    public static RedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public static void setConfigPoolSize(Integer configPoolSize) {
        CONFIG_POOL_SIZE = configPoolSize;
    }
}
