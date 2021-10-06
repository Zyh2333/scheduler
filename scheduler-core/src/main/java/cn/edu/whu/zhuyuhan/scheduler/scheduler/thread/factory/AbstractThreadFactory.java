package cn.edu.whu.zhuyuhan.scheduler.scheduler.thread.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 16:02
 **/
public abstract class AbstractThreadFactory implements ThreadFactory {

    protected static final String TOTAL_PREFIX = "scheduler-";

    protected static final String THREAD_PREFIX = "-thread";

    // 潜在隐患，大约138 * 2^32 次方年之后可能会出错（1个1s一次的定时任务，共用会几何倍数递减，但是应该也够用）
    protected static final AtomicLong POOL_NUM = new AtomicLong(1);

    protected AtomicInteger threadNum = new AtomicInteger(1);

    protected abstract String getThreadGroup();

    protected final String NAME_PREFIX;

    public AbstractThreadFactory() {
        NAME_PREFIX = new StringBuilder(TOTAL_PREFIX)
                .append(getThreadGroup())
                .append("-pool-")
                .append(POOL_NUM.getAndIncrement())
                .append(THREAD_PREFIX)
                .toString();
    }

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
