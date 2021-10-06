package cn.edu.whu.zhuyuhan.scheduler.processor;

import cn.edu.whu.zhuyuhan.scheduler.Task;
import cn.edu.whu.zhuyuhan.scheduler.common.util.AnnotationUtils;
import cn.edu.whu.zhuyuhan.scheduler.registrar.ScheduleComponentRegistrar;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/31 22:08
 **/
public class TaskSchedulerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ScheduleComponentRegistrar.register(bean);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
