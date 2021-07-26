package cn.edu.whu.zhuyuhan.scheduler.async;

import cn.edu.whu.zhuyuhan.scheduler.CronTask;
import cn.edu.whu.zhuyuhan.scheduler.scheduler.support.AsyncTaskScheduler;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:44
 **/
public abstract class AbstractAsyncCronTaskScheduler extends AsyncTaskScheduler<String> implements CronTask, AsyncTask {

    @Override
    protected void schedule() {

    }

    @Override
    public Long period() {
        return this.getPeriod(getCron());
    }

    @Override
    public Long getPeriod(String cron) {
        // TODO
        return 1L;
    }
}
