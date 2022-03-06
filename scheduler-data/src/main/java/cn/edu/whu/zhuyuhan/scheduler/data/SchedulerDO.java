package cn.edu.whu.zhuyuhan.scheduler.data;

import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/22 11:36 上午
 **/
public class SchedulerDO {

    private Long id;

    private String name;

    private String cron;

    private String parentName;

    private Integer executeCount;

    private Integer status;

    private Date gmtCreate;

    private Date gmtUpdate;

    public SchedulerDO() {
    }

    public SchedulerDO(Long id, String name, String cron, String parentName, Integer executeCount, Integer status, Date gmtCreate, Date gmtUpdate) {
        this.id = id;
        this.name = name;
        this.cron = cron;
        this.parentName = parentName;
        this.executeCount = executeCount;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    @Override
    public String toString() {
        return "SchedulerDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cron='" + cron + '\'' +
                ", parentName='" + parentName + '\'' +
                ", executeCount=" + executeCount +
                ", status=" + status +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                '}';
    }
}
