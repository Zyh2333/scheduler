package cn.edu.whu.zhuyuhan.scheduler.common.util;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Async;
import cn.edu.whu.zhuyuhan.scheduler.annotation.TaskScheduleComponent;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/31 22:11
 **/
public class AnnotationUtils {

    public static boolean isAnnotatedSpecificAnnotation(Object bean, Class<? extends Annotation> annotation) {
        if (Objects.isNull(bean) || Objects.isNull(annotation)) {
            return false;
        }
        Annotation anno = bean.getClass().getAnnotation(annotation);
        return Objects.nonNull(anno);
    }

    public static boolean isAnnotatedTaskSchedule(Object bean) {
        return isAnnotatedSpecificAnnotation(bean, TaskScheduleComponent.class);
    }

    public static boolean isAnnotatedAsync(Object bean) {
        return isAnnotatedSpecificAnnotation(bean, Async.class);
    }

}
