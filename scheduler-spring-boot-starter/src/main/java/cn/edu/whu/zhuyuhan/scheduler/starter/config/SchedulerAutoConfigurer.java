package cn.edu.whu.zhuyuhan.scheduler.starter.config;

import cn.edu.whu.zhuyuhan.scheduler.processor.TaskSchedulerBeanPostProcessor;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 14:30
 **/
@Configuration
public class SchedulerAutoConfigurer {

    @Bean
    public TaskSchedulerBeanPostProcessor taskSchedulerBeanPostProcessor() {
        return new TaskSchedulerBeanPostProcessor();
    }

    @Bean
    public TaskSchedulerBean taskSchedulerBean() {
        return new TaskSchedulerBean();
    }

}
