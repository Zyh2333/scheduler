package cn.edu.whu.zhuyuhan.scheduler.starter;

import cn.edu.whu.zhuyuhan.mq.rocketmq.producer.RocketMqProperties;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.distributed.DistributedConsumer;
import cn.edu.whu.zhuyuhan.scheduler.starter.config.TaskSchedulerBeanProperties;
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

    @Autowired
    TaskSchedulerBeanProperties taskSchedulerBeanProperties;

    @Autowired
    RocketMqProperties rocketMqProperties;

    @Autowired
    TaskSchedulerBean taskSchedulerBean;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!taskSchedulerBeanProperties.isEnabled()) return;
        this.initProperties(this.taskSchedulerBeanProperties);
        taskSchedulerBean.schedule();
    }

    private void initProperties(TaskSchedulerBeanProperties taskSchedulerBeanProperties) {
        TaskSchedulerBean.setConfigPoolSize(taskSchedulerBeanProperties.getThreadSize());
        DistributedConsumer.setMqProperties(rocketMqProperties.getMqProperties());
    }
}
