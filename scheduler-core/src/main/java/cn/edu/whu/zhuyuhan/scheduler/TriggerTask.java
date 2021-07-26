package cn.edu.whu.zhuyuhan.scheduler;

import org.springframework.scheduling.Trigger;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:37
 **/
public interface TriggerTask extends Task{

    Trigger getTrigger();

    // TODO 时间管理
    @Override
    default Long period() {
        return null;
    }
}
