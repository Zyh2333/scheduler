package cn.edu.whu.zhuyuhan.scheduler;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:37
 **/
public interface Task {

    Runnable task();

    // TODO 动态限流
    boolean isOpen();

}
