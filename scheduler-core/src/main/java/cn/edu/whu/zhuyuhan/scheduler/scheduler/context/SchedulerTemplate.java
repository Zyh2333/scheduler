package cn.edu.whu.zhuyuhan.scheduler.scheduler.context;

import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDAO;
import cn.edu.whu.zhuyuhan.scheduler.data.SchedulerDO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/22 12:27 下午
 **/
public class SchedulerTemplate {

    private SchedulerDAO schedulerDAO;

    public SchedulerTemplate(SchedulerDAO schedulerDAO) {
        this.schedulerDAO = schedulerDAO;
    }

    public SchedulerDO getByName(String name) {
        return schedulerDAO.getByName(name);
    }

//    public int insert(SchedulerDO schedulerDO) {
//        return schedulerDAO.insert(schedulerDO);
//    }

    public Long getLastScheduleTime() {
        return TaskContextHolder.get().getSchedulerDO().getGmtUpdate().getTime();
    }

}
