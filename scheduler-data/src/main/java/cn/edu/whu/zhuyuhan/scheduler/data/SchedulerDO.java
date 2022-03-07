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

    // TODO: 2022/3/7 暂时为调度次数，分布式场景下作为执行次数有问题
    private Integer executeCount;

    private Integer status;

    private Integer special;

//    private Integer version;

    private Integer distributed;

    private Integer syncAsync;

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

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

//    public Integer getVersion() {
//        return version;
//    }
//
//    public void setVersion(Integer version) {
//        this.version = version;
//    }

    public Integer getDistributed() {
        return distributed;
    }

    public void setDistributed(Integer distributed) {
        this.distributed = distributed;
    }

    public Integer getSyncAsync() {
        return syncAsync;
    }

    public void setSyncAsync(Integer syncAsync) {
        this.syncAsync = syncAsync;
    }

    @Override
    public String toString() {
        return "SchedulerDO{" +
                "name='" + name + '\'' +
                ", cron='" + cron + '\'' +
                ", parentName='" + parentName + '\'' +
                ", executeCount=" + executeCount +
                ", status=" + (status == 0 ? "normal" : "error") +
                ", special=" + (special == 0 ? "no" : "yes") +
                ", distributed=" + (distributed == 0 ? "no" : "yes") +
                ", syncAsync=" + (syncAsync == 0 ? "sync" : "async") +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                '}';
    }
}
