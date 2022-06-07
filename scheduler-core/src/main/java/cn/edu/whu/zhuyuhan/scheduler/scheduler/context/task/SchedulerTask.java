package cn.edu.whu.zhuyuhan.scheduler.scheduler.context.task;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDAO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerLockDAO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerLockDO;
import cn.edu.whu.zhuyuhan.scheduler.exception.SchedulerException;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContext;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.TaskContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.support.CronSequenceGenerator;

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

    private static SchedulerLockDAO schedulerLockDAO;

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
            if (schedulerDO.getExecuteCount() == 0) {
                schedulerDO.setGmtUpdate(new Date());
            }
        } catch (EmptyResultDataAccessException accessException) {
            LOGGER.error("init task:{} failed because task is absent in db", taskInstance);
            throw new SchedulerException(745, "task init error occurred", taskInstance.toString());
        }

        // 这里使用悲观锁，在分布式任务场景下乐观锁不保险，网络延时/具体执行实例的线程阻塞都会影响调度结果
        Long schedulerId = schedulerDO.getId();
        try {
            if (!taskInstance.isDistributed() || schedulerLockDAO.insert(new SchedulerLockDO(schedulerId)) > 0) {
                taskContext.setLocked(true);
                runInner(schedulerDO, taskContext);
            }
        } catch (DuplicateKeyException duplicateKeyException) {
            LOGGER.info("task:{} get lock failed with schedulerId:{}", schedulerDO, schedulerId);
        } finally {
            if (taskInstance.isDistributed() && taskContext.getLocked()) {
                final SchedulerDO temp = schedulerDO;
                // 需要保持一段时间再release锁，否则如果任务执行时间太短还是有很大冲突概率，但是也不能太久，否则影响下一个周期
                new Thread(() -> {
                    try {
                        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(taskInstance.getCron());
                        Date next = cronSequenceGenerator.next(taskContext.getSchedulerDO().getGmtUpdate());
                        Date date = new Date();
                        if (next.before(date)) {
                            throw new SchedulerException(746, "schedule too frequent to release the distributed lock", temp);
                        }
                        // 有 bug 查不出来是为啥，始终不释放锁
//                        Thread.sleep((next.getTime() - date.getTime()) / 2);
                        Thread.sleep(10000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        schedulerLockDAO.delete(schedulerId);
                    }
                }).start();
                taskContext.setLocked(false);
            }
        }
    }

    private void runInner(SchedulerDO schedulerDO, TaskContext taskContext) {
        schedulerDO.setExecuteCount(schedulerDO.getExecuteCount() + 1);
        taskContext.setSchedulerDO(schedulerDO);
        taskContext.setBeginDate(System.currentTimeMillis());
        try {
            delegate.run();
            taskContext.setEndDate(System.currentTimeMillis());
        } catch (Exception e) {
            throw handleException(schedulerDO, e);
        } finally {
            // record
            handleRecord(schedulerDO);
            // release context
            TaskContextHolder.remove();
        }
    }

    private SchedulerException handleException(SchedulerDO schedulerDO, Exception e) {
        e.printStackTrace();
        // 暂时为失败不计入执行次数，并修改状态
        schedulerDO.setStatus(1);
        schedulerDO.setExecuteCount(schedulerDO.getExecuteCount() - 1);
        LOGGER.warn("schedule task:{} status has been set with:{}", schedulerDO, ScheduleTaskStatus.STATUS_FAIL);
        throw new SchedulerException(744, "schedule error occurred", schedulerDO);
    }

    private void handleRecord(SchedulerDO schedulerDO) {
        // schedule success
        if (TaskContextHolder.get().getEndDate() != null) {
            // error before
            if (schedulerDO.getStatus() == 1) {
                schedulerDO.setStatus(0);
                LOGGER.info("schedule task:{} status has been set with:{}", schedulerDO, ScheduleTaskStatus.STATUS_SUCCESS);
            }
        }
        schedulerDAO.updateExecuteByPrimaryKey(schedulerDO);
    }

    public enum ScheduleTaskStatus {
        STATUS_SUCCESS,
        STATUS_FAIL;
    }

    public static void setSchedulerDAO(SchedulerDAO schedulerDAO) {
        SchedulerTask.schedulerDAO = schedulerDAO;
    }

    public static void setSchedulerLockDAO(SchedulerLockDAO schedulerLockDAO) {
        SchedulerTask.schedulerLockDAO = schedulerLockDAO;
    }

    @Override
    public String toString() {
        return "SchedulerTask{" +
                "delegate=" + delegate +
                '}';
    }
}
