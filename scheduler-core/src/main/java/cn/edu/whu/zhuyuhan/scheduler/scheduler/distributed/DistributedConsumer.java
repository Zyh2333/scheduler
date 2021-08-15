package cn.edu.whu.zhuyuhan.scheduler.scheduler.distributed;

import cn.edu.whu.zhuyuhan.mq.rocketmq.consumer.AbstractMqConsumer;
import cn.edu.whu.zhuyuhan.scheduler.common.constant.DistributedTaskSchedulerConstant;
import cn.edu.whu.zhuyuhan.scheduler.common.util.TaskSchedulerUtils;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;

import java.util.Properties;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/15 11:34
 **/
public class DistributedConsumer extends AbstractMqConsumer {

    private ScheduleComponentTaskInstance taskInstance;

    private MessageListener messageListener;

    public DistributedConsumer(ScheduleComponentTaskInstance taskInstance, MessageListener messageListener) {
        this.taskInstance = taskInstance;
        this.messageListener = messageListener;
        this.initMqProperties();
    }

    @Override
    public String consumerGroup() {
        return TaskSchedulerUtils.createGroup(taskInstance.getName());
    }

    @Override
    public void subscribe() {
        this.consumer.subscribe(TaskSchedulerUtils.createTopic(taskInstance.getName()), DistributedTaskSchedulerConstant.DEFAULT_TAG, messageListener);
    }

    private void initMqProperties() {
        Properties properties = new Properties();
        properties.setProperty("GROUP_ID", this.consumerGroup());
        // TODO NEED INJECT MQ PROPERTY
//        properties.setProperty("AccessKey", this.ak);
//        properties.setProperty("SecretKey", this.sk);
//        properties.setProperty("OnsChannel", this.onsChannel);
//        properties.setProperty("INSTANCE_ID", this.instanceId);
//        properties.setProperty("SendMsgTimeoutMillis", this.timeoutMillis);
//        properties.setProperty("NAMESRV_ADDR", this.nameServerDomain);
        this.consumer = ONSFactory.createConsumer(properties);
    }

}
