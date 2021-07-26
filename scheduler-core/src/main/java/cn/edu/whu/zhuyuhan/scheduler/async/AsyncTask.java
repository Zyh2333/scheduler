package cn.edu.whu.zhuyuhan.scheduler.async;

import cn.edu.whu.zhuyuhan.scheduler.Task;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:38
 **/
public interface AsyncTask extends Task {

    @Override
    default Boolean async() {
        return true;
    }

}
