package cn.edu.whu.zhuyuhan.scheduler.starter;

import cn.edu.whu.zhuyuhan.scheduler.processor.TaskSchedulerBeanPostProcessor;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;
import cn.edu.whu.zhuyuhan.scheduler.starter.config.TaskSchedulerBeanProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 14:30
 **/
@Configuration
@EnableConfigurationProperties({TaskSchedulerBeanProperties.class})
public class SchedulerRunner implements ApplicationRunner {

    @Autowired
    TaskSchedulerBeanProperties taskSchedulerBeanProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initProperties(this.taskSchedulerBeanProperties);
        TaskSchedulerBean taskSchedulerBean = new TaskSchedulerBean();
        taskSchedulerBean.schedule();
    }

    @Bean
    public TaskSchedulerBeanPostProcessor taskSchedulerBeanPostProcessor() {
        return new TaskSchedulerBeanPostProcessor();
    }

    private void initProperties(TaskSchedulerBeanProperties taskSchedulerBeanProperties) {
        if (!taskSchedulerBeanProperties.getOpen()) return;
        TaskSchedulerBean.CONFIG_POOL_SIZE = taskSchedulerBeanProperties.getThreadSize();
    }
}
