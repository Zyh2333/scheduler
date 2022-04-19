package cn.edu.whu.zhuyuhan.scheduler.starter;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDAO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerLockDAO;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;
import cn.edu.whu.zhuyuhan.scheduler.starter.config.TaskSchedulerBeanProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 14:30
 **/
@Configuration
@EnableConfigurationProperties({TaskSchedulerBeanProperties.class})
public class SchedulerRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerRunner.class);

    @Autowired
    TaskSchedulerBeanProperties taskSchedulerBeanProperties;

    @Autowired
    SchedulerLockDAO schedulerLockDAO;

    @Autowired
    SchedulerDAO schedulerDAO;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // release lock when reboot
        schedulerLockDAO.deleteLock();
        this.initProperties(this.taskSchedulerBeanProperties);
        TaskSchedulerBean taskSchedulerBean = new TaskSchedulerBean(schedulerDAO);
        taskSchedulerBean.schedule();
        safeShutdown();
    }

    private void initProperties(TaskSchedulerBeanProperties taskSchedulerBeanProperties) {
        if (!taskSchedulerBeanProperties.getOpen()) return;
        TaskSchedulerBean.CONFIG_POOL_SIZE = taskSchedulerBeanProperties.getThreadSize();
    }

    private void safeShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            schedulerLockDAO.deleteLock();
            LOGGER.info("[SCHEDULER] success to release all lock before shutdown ");
        }));
    }
}
