package cn.edu.whu.zhuyuhan.scheduler.registrar.model;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 16:37
 **/
public class ScheduleComponentTaskInstance {

    private static Integer count = 1;

    private String parentName;

    private String name;

    private String cron;

    private boolean async;

    private Runnable task;

    public ScheduleComponentTaskInstance(String parentName, String name, String cron, boolean async, Runnable task) {
        this.parentName = parentName;
        this.name = name == null ? String.valueOf(count++) : name;
        this.cron = cron;
        this.async = async;
        this.task = task;
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

    public void setTask(Runnable task) {
        this.task = task;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
