package cn.edu.whu.zhuyuhan.scheduler.data;

import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/22 11:36 上午
 **/
public class SchedulerLockDO {

    private Long id;

    private Long schedulerId;

    private Date gmtCreate;

    private Date gmtUpdate;

    public SchedulerLockDO() {
    }

    public SchedulerLockDO(Long schedulerId) {
        this.schedulerId = schedulerId;
        Date date = new Date();
        this.gmtCreate = date;
        this.gmtUpdate = date;
    }

    public SchedulerLockDO(Long id, Long schedulerId, Date gmtCreate, Date gmtUpdate) {
        this.id = id;
        this.schedulerId = schedulerId;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
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
        return "SchedulerLockDO{" +
                "id=" + id +
                ", schedulerId=" + schedulerId +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                '}';
    }
}
