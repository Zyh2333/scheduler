package cn.edu.whu.zhuyuhan.scheduler.registrar;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Distributed;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;
import cn.edu.whu.zhuyuhan.scheduler.common.constant.CronConstant;
import cn.edu.whu.zhuyuhan.scheduler.common.util.AnnotationUtils;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponent;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/31 23:11
 **/
public class ScheduleComponentRegistrar {

    public static Map<Object, ScheduleComponent> componentMap = new ConcurrentHashMap<>();

    public static void register(Object bean) {
        TaskScheduleComponent annotation = bean.getClass().getAnnotation(TaskScheduleComponent.class);
        if (!annotation.schedule()) return;
        String cron = annotation.cron();
        boolean async = annotation.async();
        String name = annotation.name();
        boolean distributed = annotation.distributed();
        if (StringUtils.isEmpty(cron)) {
            cron = CronConstant.EMPTY_CRON;
        }
        if (!async) {
            async = AnnotationUtils.isAnnotatedAsync(bean);
        }
        if (!distributed) {
            distributed = AnnotationUtils.isAnnotatedSpecificAnnotation(bean, Distributed.class);
        }
        ScheduleComponent scheduleComponent = new ScheduleComponent(name, cron, async, bean, annotation.schedule(), distributed);
        componentMap.put(bean, scheduleComponent);
    }

    public static void remove(Object bean) {
        if (!componentMap.containsKey(bean)) return;
        ScheduleComponent scheduleComponent = componentMap.get(bean);
        scheduleComponent.setSchedule(false);
        componentMap.put(bean, scheduleComponent);
    }

    public static void get() {

    }
}
