package cn.edu.whu.zhuyuhan.scheduler.registrar.model;

import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.task.SchedulerTask;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 16:37
 **/
public class ScheduleComponentTaskInstance {

    private static final String PREFIX = "-task-instance-";

    private Integer count = 1;

    private String parentName;

    private String name;

    private String cron;

    private boolean async;

    private SchedulerTask task;

    private boolean distributed = false;

    private boolean isSpecial = false;

    public ScheduleComponentTaskInstance(String parentName, String name, String cron, boolean async, Runnable task, boolean distributed, boolean isSpecial) {
        this.parentName = parentName;
        this.name = parentName + "-" + name;
        this.cron = cron;
        this.async = async;
        this.distributed = distributed;
        this.isSpecial = isSpecial;
        this.task = new SchedulerTask(task, this);
    }

    public ScheduleComponentTaskInstance(String parentName, String name, String cron, boolean async, Runnable task) {
        this(parentName, name, cron, async, task, false, false);
    }

    public ScheduleComponentTaskInstance(String parentName, String cron, boolean async, Runnable task) {
        this(parentName, null, cron, async, task);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Runnable getTask() {
        return task;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    public void setTask(SchedulerTask task) {
        this.task = task;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public boolean isDistributed() {
        return distributed;
    }

    public void setDistributed(boolean distributed) {
        this.distributed = distributed;
    }

    @Override
    public String toString() {
        return "ScheduleComponentTaskInstance{" +
                "name='" + name + '\'' +
                ", cron='" + cron + '\'' +
                ", async=" + async +
                ", distributed=" + distributed +
                ", task=" + task +
                '}';
    }

    /**
     * 映射到具体调度器
     *
     * @return
     */
    public String map() {
        return new StringBuilder()
                .append(async ? "async" : "sync")
                .append(distributed ? "distributed" : "")
                .toString();
    }
}
