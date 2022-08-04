package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.scheduler.common.constant.TaskSchedulerKindConstant;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public class DistributedAsyncTaskScheduler extends AbstractDistributedTaskScheduler {

    @Override
    protected String kindMatch() {
        return TaskSchedulerKindConstant.DISTRIBUTED_ASYNC_TASK_SCHEDULER;
    }

    @Override
    public void scheduleInner(ScheduleComponentTaskInstance taskInstance) {
//        String taskInstanceName = taskInstance.getName();
//        Message message = new Message();
//        message.setTopic(TaskSchedulerUtils.createTopic(taskInstanceName));
////        message.setBody(JSON.toJSONBytes(taskInstance));
//
//        String cron = taskInstance.getCron();
//        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
//        Date start = cronSequenceGenerator.next(new Date());
//
//        DistributedConsumer distributedConsumer = new DistributedConsumer(taskInstance, new MessageListener() {
//            @Override
//            public Action consume(Message message, ConsumeContext context) {
////                ScheduleComponentTaskInstance taskInstance1 = JSONObject.parseObject(message.getBody(), ScheduleComponentTaskInstance.class);
////                taskInstance1.getTask().run();
//                taskInstance.getTask().run();
//                Date next = cronSequenceGenerator.next(new Date(message.getStartDeliverTime()));
//                producer.sendAtFixedTimeAsync(message, next.getTime());
//                return Action.CommitMessage;
//            }
//        });
//
//        distributedConsumer.subscribe();
//
//        producer.sendAtFixedTimeAsync(message, start.getTime());
    }

}
