package cn.edu.whu.zhuyuhan.scheduler.async;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/6/18 21:38
 **/
public interface AsyncCronTask extends AsyncTask {

    String getCron();

    // TODO 时间管理
    @Override
    default Long period() {
        return null;
    }
}
