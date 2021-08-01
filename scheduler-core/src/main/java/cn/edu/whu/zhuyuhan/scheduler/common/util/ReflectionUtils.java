package cn.edu.whu.zhuyuhan.scheduler.common.util;

import cn.edu.whu.zhuyuhan.scheduler.annotation.Task;
import cn.edu.whu.zhuyuhan.scheduler.registrar.model.ScheduleComponent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 12:25
 **/
public class ReflectionUtils {

    public static List<Method> collectScheduleTask(ScheduleComponent component) {
        Object bean = component.getBean();
        Method[] methods = bean.getClass().getDeclaredMethods();
        List<Method> methodList  = new ArrayList<>(methods.length);
        for (Method method : methods) {
            if (method.isAnnotationPresent(Task.class)) {
                methodList.add(method);
            }
        }
        return methodList;
    }

}
