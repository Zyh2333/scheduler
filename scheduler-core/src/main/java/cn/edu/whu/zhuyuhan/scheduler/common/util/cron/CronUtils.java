package cn.edu.whu.zhuyuhan.scheduler.common.util.cron;

import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/1 12:20
 **/
public class CronUtils {

    public static boolean valid(String cron) {
        return CronSequenceGenerator.isValidExpression(cron);
    }
}
