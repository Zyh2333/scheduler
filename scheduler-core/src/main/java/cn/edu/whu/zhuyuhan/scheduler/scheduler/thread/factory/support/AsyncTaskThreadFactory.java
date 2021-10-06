package cn.edu.whu.zhuyuhan.scheduler.scheduler.thread.factory.support;

import cn.edu.whu.zhuyuhan.scheduler.scheduler.thread.factory.AbstractThreadFactory;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 16:07
 **/
public class AsyncTaskThreadFactory extends AbstractThreadFactory {

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("asyncThreadGroup");

    @Override
    protected String getThreadGroup() {
        return THREAD_GROUP.getName();
    }

}
