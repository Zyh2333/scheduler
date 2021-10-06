package cn.edu.whu.zhuyuhan.scheduler.scheduler.thread.pool;

import cn.edu.whu.zhuyuhan.scheduler.scheduler.thread.factory.support.SyncTaskThreadFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/10/6 10:41 上午
 **/
public class SyncCommonThreadPoolExecutor {

    public static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR;

    static {
        SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                new SyncTaskThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

}
