package cn.edu.whu.zhuyuhan.scheduler.thread.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 16:02
 **/
public abstract class AbstractThreadFactory implements ThreadFactory {

    protected static final String TOTAL_PREFIX = "scheduler-";

    protected static final AtomicInteger POOL_NUM = new AtomicInteger(1);

    protected AtomicInteger threadNum = new AtomicInteger(1);

    protected abstract String getThreadGroup();

    protected final String NAME_PREFIX = TOTAL_PREFIX + getThreadGroup() + "-" + "pool" + "-" + POOL_NUM.getAndIncrement();

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(new ThreadGroup(getThreadGroup()), r,
                NAME_PREFIX + "-" + threadNum.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }

}
