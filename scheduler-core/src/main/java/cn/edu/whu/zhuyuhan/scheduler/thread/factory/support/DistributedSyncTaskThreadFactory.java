package cn.edu.whu.zhuyuhan.scheduler.thread.factory.support;

import cn.edu.whu.zhuyuhan.scheduler.thread.factory.AbstractThreadFactory;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 16:07
 **/
public class DistributedSyncTaskThreadFactory extends AbstractThreadFactory {

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("distributedSyncThreadGroup");

    @Override
    protected String getThreadGroup() {
        return THREAD_GROUP.getName();
    }

}
