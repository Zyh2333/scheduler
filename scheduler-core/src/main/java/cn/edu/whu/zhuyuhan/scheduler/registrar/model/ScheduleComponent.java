package cn.edu.whu.zhuyuhan.scheduler.registrar.model;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Async;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/31 23:09
 **/
public class ScheduleComponent {

    private static final Logger log = LoggerFactory.getLogger(ScheduleComponent.class);

    private static final String PREFIX = "schedule-component-";

    private static final Integer MAX_COMPONENT = 64;

    private static Integer count = 1;

    private static final Map<String, ScheduleComponentTaskInstance> scheduleComponentTaskInstanceMap = new HashMap<>(MAX_COMPONENT << 2);

    private String name;

    private String cron;

    private boolean async;

    private Object bean;

    private List<ScheduleComponentTaskInstance> tasks;

    private boolean schedule;

    private boolean distributed = false;

    public ScheduleComponent(String name, String cron, boolean async, Object bean, boolean schedule, boolean distributed) {
        this.name = StringUtils.isEmpty(name) ? PREFIX + count++ : name;
        this.cron = cron;
        this.async = async;
        this.bean = bean;
        this.schedule = schedule;
        this.tasks = new ArrayList<>(MAX_COMPONENT);
        this.distributed = distributed;
    }

    public ScheduleComponent(String name, String cron, boolean async, Object bean, boolean schedule) {
        this(name, cron, async, bean, schedule, false);
    }


    public ScheduleComponent(String cron, boolean async, Object bean, boolean schedule) {
        this(null, cron, async, bean, schedule);
    }

    public void addTask(Runnable task, Task taskAnno, Async asyncAnno, Distributed distributedAnno) {
        if (task != null) {
            ScheduleComponentTaskInstance taskInstance =
                    new ScheduleComponentTaskInstance(
                            name,
                            StringUtils.isEmpty(taskAnno.name()) ? null : taskAnno.name(),
                            StringUtils.isEmpty(taskAnno.cron()) ? cron : taskAnno.cron(),
                            this.parseAsync(taskAnno, asyncAnno),
                            task,
                            distributedAnno != null
                    );
            String taskInstanceName = taskInstance.getName();
            if (scheduleComponentTaskInstanceMap.putIfAbsent(taskInstanceName, taskInstance) != null) {
                log.warn("ScheduleComponentTaskInstance:{} register failed because of duplicated name:{}", taskInstance, taskInstanceName);
            }
            this.tasks.add(taskInstance);
        }
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public boolean isSchedule() {
        return schedule;
    }

    public void setSchedule(boolean schedule) {
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScheduleComponentTaskInstance> getTasks() {
        return tasks;
    }

    public void setTasks(List<ScheduleComponentTaskInstance> tasks) {
        this.tasks = tasks;
    }

    public boolean isDistributed() {
        return distributed;
    }

    public void setDistributed(boolean distributed) {
        this.distributed = distributed;
    }

    private boolean parseAsync(Task taskAnno, Async asyncAnno) {
        // 判断方法和类注解async字段值是否相等
        return (taskAnno.async() == async ? async : true) == true ? true : Objects.nonNull(asyncAnno);
    }
}
