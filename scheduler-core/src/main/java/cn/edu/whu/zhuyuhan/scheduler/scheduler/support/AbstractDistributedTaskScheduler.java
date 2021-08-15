package cn.edu.whu.zhuyuhan.scheduler.scheduler.support;

import cn.edu.whu.zhuyuhan.mq.rocketmq.producer.Producer;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponentTaskInstance;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.AbstractTaskScheduler;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ThreadFactory;

/**
 * sync task
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/26 11:09
 **/
public abstract class AbstractDistributedTaskScheduler extends AbstractTaskScheduler {

    protected Producer producer;

    protected RedisTemplate redisTemplate;

    public AbstractDistributedTaskScheduler() {
        this.producer = TaskSchedulerBean.getProducer();
        this.redisTemplate = TaskSchedulerBean.getStringRedisTemplate();
    }

    @Override
    public void schedule(ScheduleComponentTaskInstance taskInstance) {
        String taskInstanceName = taskInstance.getName();
        try {
            if (redisTemplate.opsForValue().setIfAbsent(taskInstanceName, "0")) {
                scheduleInner(taskInstance);
            }
        } catch (Exception e) {
            log.error("redis lock fail with task:{}", taskInstance);
        } finally {
            // TODO 程序全部停止时需要将redis全部释放
//            try {
//                redisTemplate.delete(taskInstanceName);
//            } catch (Exception e) {
//                log.error("redis lock release fail with task:{}", taskInstance);
//            }
        }

    }

    public abstract void scheduleInner(ScheduleComponentTaskInstance taskInstance);

    @Override
    protected ThreadFactory getThreadFactory() {
        return null;
    }

}
