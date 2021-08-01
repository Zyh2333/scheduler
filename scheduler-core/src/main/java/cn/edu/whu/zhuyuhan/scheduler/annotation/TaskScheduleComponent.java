package cn.edu.whu.zhuyuhan.scheduler.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定时任务申明注解
 *
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/7/31 21:53
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface TaskScheduleComponent {

    String cron() default "";

    String name() default "";

    boolean async() default false;

    boolean schedule() default true;

}
