package cn.edu.whu.zhuyuhan.scheduler.data;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/22 12:27 下午
 **/
public class SchedulerLockDAO {

    private JdbcTemplate jdbcTemplate;

    public SchedulerLockDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(SchedulerLockDO schedulerLockDO) {
        return jdbcTemplate.update("insert into scheduler_lock (scheduler_id) values (?)",
                schedulerLockDO.getSchedulerId());
    }

    public int delete(Long schedulerId) {
        return jdbcTemplate.update("delete from scheduler_lock where scheduler_id = ?", schedulerId);
    }

    public int deleteLock() {
        return jdbcTemplate.update("delete from scheduler_lock");
    }

}
