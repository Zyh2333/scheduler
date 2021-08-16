package cn.edu.whu.zhuyuhan.scheduler.scheduler.distributed;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.DistributedTaskSchedulerConstant;
import cn.edu.whu.zhuyuhan.scheduler.common.util.TaskSchedulerUtils;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/15 11:34
 **/
public class DistributedConsumer {

    private static Properties mqProperties;

    private ScheduleComponentTaskInstance taskInstance;

    private MessageListener messageListener;

    private Consumer consumer;

    public DistributedConsumer(ScheduleComponentTaskInstance taskInstance, MessageListener messageListener) {
        this.taskInstance = taskInstance;
        this.messageListener = messageListener;
    }

    public String consumerGroup() {
        return TaskSchedulerUtils.createGroup(taskInstance.getName());
    }

    public void initMq() {
        mqProperties.setProperty(PropertyKeyConst.GROUP_ID, this.consumerGroup());
        this.consumer = ONSFactory.createConsumer(mqProperties);
        this.subscribe();
    }

    private void subscribe() {
        this.consumer.subscribe(DistributedTaskSchedulerConstant.SCHEDULER_DEFAULT_TOPIC, TaskSchedulerUtils.createTag(taskInstance.getName()), messageListener);
    }

    public static void setMqProperties(Properties mqProperties) {
        DistributedConsumer.mqProperties = mqProperties;
    }
}
