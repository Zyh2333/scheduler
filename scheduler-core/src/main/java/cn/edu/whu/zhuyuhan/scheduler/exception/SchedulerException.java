package cn.edu.whu.zhuyuhan.scheduler.exception;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import com.horsecoder.common.status.StatusException;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/3/6 1:33 下午
 **/
public class SchedulerException extends StatusException {

    private SchedulerDO schedulerDO;

    public SchedulerException(int code, String message, String detail) {
        super(code, message, detail);
    }

    public SchedulerException(int code, String message) {
        this(code, message, (String) null);
    }

    public SchedulerException(int code, String message, SchedulerDO schedulerDO) {
        this(code, message);
        this.schedulerDO = schedulerDO;
    }

    public SchedulerException(int code, String message, String detail, SchedulerDO schedulerDO) {
        this(code, message, detail);
        this.schedulerDO = schedulerDO;
    }

    @Override
    public String toString() {
        return "SchedulerException{" +
                "schedulerDO=" + schedulerDO +
                ", code=" + super.getCode() +
                ", message='" + super.getMessage() + '\'' +
                ", detail='" + super.getDetail() + '\'' +
                '}';
    }
}
