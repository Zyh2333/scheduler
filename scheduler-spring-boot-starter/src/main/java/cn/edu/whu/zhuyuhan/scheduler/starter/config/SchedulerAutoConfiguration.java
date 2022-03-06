package cn.edu.whu.zhuyuhan.scheduler.starter.config;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDAO;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.SchedulerTemplate;
import cn.edu.whu.zhuyuhan.scheduler.processor.TaskSchedulerBeanPostProcessor;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.context.task.SchedulerTask;
import cn.edu.whu.zhuyuhan.scheduler.starter.SchedulerRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 14:30
 **/
@Configuration
@Import({TaskSchedulerBeanPostProcessor.class, SchedulerRunner.class, BatchTaskDataSourceInitializer.class})
public class SchedulerAutoConfiguration {

    @Bean
    public TaskSchedulerBean taskSchedulerBean() {
        return new TaskSchedulerBean();
    }

    @Bean
    public SchedulerTemplate schedulerTemplate(JdbcTemplate jdbcTemplate) {
        SchedulerDAO schedulerDAO = new SchedulerDAO(jdbcTemplate);
        SchedulerTemplate schedulerTemplate = new SchedulerTemplate(schedulerDAO);
        SchedulerTask.setSchedulerDAO(schedulerDAO);
        return schedulerTemplate;
    }

}
