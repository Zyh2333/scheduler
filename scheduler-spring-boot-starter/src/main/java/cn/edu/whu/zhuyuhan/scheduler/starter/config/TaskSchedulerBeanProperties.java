package cn.edu.whu.zhuyuhan.scheduler.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/7 14:27
 **/
@ConfigurationProperties(prefix = "task.scheduler")
public class TaskSchedulerBeanProperties {

    // TODO NEED OPTIMIZE 同步任务串行貌似没用
    private Integer threadSize;

    private Boolean enabled = Boolean.TRUE;

    public TaskSchedulerBeanProperties() {
    }

    public Integer getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(Integer threadSize) {
        this.threadSize = threadSize;
    }

    public Boolean getOpen() {
        return enabled;
    }

    public void setOpen(Boolean open) {
        enabled = open;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
