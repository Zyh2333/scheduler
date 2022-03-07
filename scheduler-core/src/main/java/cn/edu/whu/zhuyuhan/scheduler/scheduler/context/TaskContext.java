package cn.edu.whu.zhuyuhan.scheduler.scheduler.context;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;

import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/21 10:26 下午
 **/
public class TaskContext {

    private Long beginDate;

    private Long endDate;

    private ScheduleComponentTaskInstance taskInstance;

    private Map<String, Object> metadata;

    private SchedulerDO schedulerDO;

    private Boolean isLocked = false;

    public TaskContext() {
    }

    public SchedulerDO getSchedulerDO() {
        return schedulerDO;
    }

    public void setSchedulerDO(SchedulerDO schedulerDO) {
        this.schedulerDO = schedulerDO;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public ScheduleComponentTaskInstance getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(ScheduleComponentTaskInstance taskInstance) {
        this.taskInstance = taskInstance;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }
}
