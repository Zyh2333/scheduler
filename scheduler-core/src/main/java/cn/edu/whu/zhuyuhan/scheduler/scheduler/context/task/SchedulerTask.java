package cn.edu.whu.zhuyuhan.scheduler.scheduler.context.task;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerTemplate;
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

    private static SchedulerTemplate schedulerTemplate;

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
            schedulerDO = schedulerTemplate.getByName(taskInstance.getName());
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
        }
        taskContext.setBeginDate(System.currentTimeMillis());
        delegate.run();
        taskContext.setEndDate(System.currentTimeMillis());

        // record
        if (schedulerDO.getId() == null) {
            schedulerTemplate.insert(schedulerDO);
        } else {
            schedulerTemplate.updateExecuteCountByPrimaryKey(schedulerDO);
        }

        // release context
        TaskContextHolder.remove();
    }

    public static void setSchedulerTemplate(SchedulerTemplate schedulerTemplate) {
        SchedulerTask.schedulerTemplate = schedulerTemplate;
    }

    @Override
    public String toString() {
        return "SchedulerTask{" +
                "delegate=" + delegate +
                '}';
    }
}
