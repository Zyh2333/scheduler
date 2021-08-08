package cn.edu.whu.zhuyuhan.scheduler.common.util;

import cn.edu.whu.zhuyuhan.scheduler.scheduler.TaskSchedulerBean;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/7 15:05
 **/
public class TaskSchedulerUtils {

    public static boolean isThreadSizeValid(Integer threadSize) {
        return threadSize <= TaskSchedulerBean.MAX_POOL_SIZE;
    }

}
