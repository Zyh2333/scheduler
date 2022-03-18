package cn.edu.whu.zhuyuhan.scheduler.scheduler;

import cn.edu.whu.zhuyuhan.mq.rocketmq.producer.Producer;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Async;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Special;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.common.util.AnnotationUtils;
import cn.edu.whu.zhuyuhan.scheduler.common.util.ReflectionUtils;
import cn.edu.whu.zhuyuhan.scheduler.common.util.cron.CronUtils;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDAO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.exception.SchedulerException;
import cn.edu.whu.zhuyuhan.scheduler.registrar.ScheduleComponentRegistrar;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.factory.TaskSchedulerFactory;
import cn.edu.whu.zhuyuhan.scheduler.util.SchedulerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public class TaskSchedulerBean implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerBean.class);

    private static Producer producer;

    private static StringRedisTemplate stringRedisTemplate;

    public static final Integer DEFAULT_POOL_SIZE = 1;

    public static final Integer MAX_POOL_SIZE = 10;

    // TODO 同步任务用不着，异步任务不需要
    public static Integer CONFIG_POOL_SIZE = 0;

    private final SchedulerDAO schedulerDAO;

    public TaskSchedulerBean(SchedulerDAO schedulerDAO) {
        this.schedulerDAO = schedulerDAO;
    }

    public void schedule() throws Exception {
        for (ScheduleComponent value : ScheduleComponentRegistrar.componentMap.values()) {
            // TODO 动态降级 (结合配置中心)
            if (!value.isSchedule()) {
                LOGGER.warn("ScheduleComponent bean is not open:{}", value.getBean());
                continue;
            }
            if (!this.schedule(value)) {
                LOGGER.warn("ScheduleComponent bean init with empty task:{}", value.getBean());
            }
        }
    }

    private boolean schedule(ScheduleComponent scheduleComponent) throws Exception {
        Object bean = scheduleComponent.getBean();
        if (AnnotationUtils.isAnnotatedTaskSchedule(bean)) {
            List<Method> methods = ReflectionUtils.collectScheduleTask(scheduleComponent);
            if (CollectionUtils.isEmpty(methods)) return false;
            for (Method m : methods) {
                // 方法注解重新覆盖类注解
                Task taskAnno = m.getAnnotation(Task.class);
                Async asyncAnno = m.getAnnotation(Async.class);
                Distributed distributedAnno = m.getAnnotation(Distributed.class);
                Special specialAnno = m.getAnnotation(Special.class);
                scheduleComponent.addTask(m, (Runnable) m.invoke(bean), taskAnno, asyncAnno, distributedAnno, specialAnno);
                LOGGER.info("register task method {} in bean {} success", m, bean);
            }

            // TODO 线程池资源管控
            for (ScheduleComponentTaskInstance taskInstance : scheduleComponent.getTasks()) {
                this.schedule(taskInstance);
            }
        } else if (cn.edu.whu.zhuyuhan.scheduler.Task.class.isAssignableFrom(bean.getClass())) {
            cn.edu.whu.zhuyuhan.scheduler.Task task = (cn.edu.whu.zhuyuhan.scheduler.Task) bean;
            this.schedule(task.task());
        }
        return true;
    }

    private void schedule(ScheduleComponentTaskInstance taskInstance) throws Exception {
        String cron = taskInstance.getCron();
        if (StringUtils.isEmpty(cron) || !CronUtils.valid(cron)) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(taskInstance.getTask());
            record(taskInstance);
            return;
        }

        Class<? extends Scheduler> taskSchedulerClass = TaskSchedulerFactory.getTaskScheduler(taskInstance.map());
        // 默认newInstance会首先获取缓存的构造器
        Scheduler scheduler = taskSchedulerClass.newInstance();
        scheduler.schedule(taskInstance);
        record(taskInstance);
    }

    private void record(ScheduleComponentTaskInstance taskInstance) {
        SchedulerDO schedulerDO = null;
        try {
            schedulerDO = schedulerDAO.getByName(taskInstance.getName());
        } catch (EmptyResultDataAccessException accessException) {
            schedulerDO = SchedulerData.map(taskInstance);
            schedulerDO.setStatus(0);
            schedulerDO.setExecuteCount(0);
            Date date = new Date();
            schedulerDO.setGmtCreate(date);
            schedulerDO.setGmtUpdate(date);
            schedulerDAO.insert(schedulerDO);
            LOGGER.info("init task:{} with record", schedulerDO);
            return;
        }
        schedulerDO.setCron(taskInstance.getCron());
        schedulerDO.setDistributed(taskInstance.isDistributed() ? 1 : 0);
        schedulerDO.setSpecial(taskInstance.isSpecial() ? 1 : 0);
        schedulerDO.setSyncAsync(taskInstance.isAsync() ? 1 : 0);
        schedulerDAO.updateMetadataByPrimaryKey(schedulerDO);
        LOGGER.info("task:{} has been updated with record by name:{}", schedulerDO, schedulerDO.getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        try {
            producer = applicationContext.getBean(Producer.class);
            stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
        } catch (Exception e) {
            producer = null;
            stringRedisTemplate = null;
        }
    }

    public static Producer getProducer() {
        return producer;
    }

    public static RedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }
}
