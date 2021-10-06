package cn.edu.whu.zhuyuhan.scheduler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否需要业务独立
 *
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/10/6 1:26 下午
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Special {
}
