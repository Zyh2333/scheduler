package cn.edu.whu.zhuyuhan.scheduler.scheduler.context.task;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDAO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.exception.SchedulerException;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContext;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContextHolder;
import cn.edu.whu.zhuyuhan.scheduler.util.SchedulerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/21 10:25 下午
 **/
public class SchedulerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerTask.class);

    private final Runnable delegate;

    private final ScheduleComponentTaskInstance taskInstance;

    private static SchedulerDAO schedulerDAO;

    public SchedulerTask(Runnable delegate, ScheduleComponentTaskInstance taskInstance) {
        this.delegate = delegate;
        this.taskInstance = taskInstance;
    }

    @Override
    public void run() {
        TaskContext taskContext = TaskContextHolder.get();
        taskContext.setTaskInstance(taskInstance);
        SchedulerDO schedulerDO = null;
        try {
            schedulerDO = schedulerDAO.getByName(taskInstance.getName());
        } catch (EmptyResultDataAccessException accessException) {
            LOGGER.info("init task:{} with record", taskInstance);
        }
        if (schedulerDO == null) {
            schedulerDO = SchedulerData.map(taskInstance);
            schedulerDO.setStatus(0);
            schedulerDO.setExecuteCount(1);
            Date date = new Date();
            schedulerDO.setGmtCreate(date);
            schedulerDO.setGmtUpdate(date);
        } else {
            schedulerDO.setExecuteCount(schedulerDO.getExecuteCount() + 1);
        }
        taskContext.setSchedulerDO(schedulerDO);
        taskContext.setBeginDate(System.currentTimeMillis());
        try {
            delegate.run();
            taskContext.setEndDate(System.currentTimeMillis());
            // record
            if (schedulerDO.getId() == null) {
                schedulerDAO.insert(schedulerDO);
            } else {
                schedulerDAO.updateExecuteCountByPrimaryKey(schedulerDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SchedulerException(744, "schedule error occurred", schedulerDO);
        } finally {
            // release context
            TaskContextHolder.remove();
        }

    }

    public static void setSchedulerDAO(SchedulerDAO schedulerDAO) {
        SchedulerTask.schedulerDAO = schedulerDAO;
    }

    @Override
    public String toString() {
        return "SchedulerTask{" +
                "delegate=" + delegate +
                '}';
    }
}
