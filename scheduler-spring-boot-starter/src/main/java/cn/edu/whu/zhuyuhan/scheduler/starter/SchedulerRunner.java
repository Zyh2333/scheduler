package cn.edu.whu.zhuyuhan.scheduler.starter;

import cn.edu.whu.zhuyuhan.scheduler.processor.TaskSchedulerBeanPostProcessor;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 14:30
 **/
@Configuration
public class SchedulerRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TaskSchedulerBean taskSchedulerBean = new TaskSchedulerBean();
        taskSchedulerBean.schedule();
    }

    @Bean
    public TaskSchedulerBeanPostProcessor taskSchedulerBeanPostProcessor() {
        return new TaskSchedulerBeanPostProcessor();
    }
}
