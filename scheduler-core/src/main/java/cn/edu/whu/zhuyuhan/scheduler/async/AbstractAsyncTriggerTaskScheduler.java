package cn.edu.whu.zhuyuhan.scheduler.async;

import cn.edu.whu.zhuyuhan.scheduler.TriggerTask;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.support.AsyncTaskScheduler;
import org.springframework.scheduling.Trigger;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public abstract class AbstractAsyncTriggerTaskScheduler extends AsyncTaskScheduler<Trigger> implements TriggerTask, AsyncTask {

    @Override
    protected void schedule() {

    }

    @Override
    public Long period() {
        return this.getPeriod(getTrigger());
    }

    @Override
    public Long getPeriod(Trigger trigger) {
        // TODO
        return 1L;
    }
}
