package cn.edu.whu.zhuyuhan.scheduler.data;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/2/22 12:27 下午
 **/
public class SchedulerDAO {

    private JdbcTemplate jdbcTemplate;

    public SchedulerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SchedulerDO getByName(String name) {
        return jdbcTemplate.queryForObject(
                "select * from scheduler where name = ? order by gmt_create desc limit 1",
                new Object[]{name},
                new BeanPropertyRowMapper<>(SchedulerDO.class));
    }

    public int updateExecuteCountByPrimaryKey(SchedulerDO schedulerDO) {
        return jdbcTemplate.update("update scheduler set execute_count = ? where id = ?",
                new Object[]{schedulerDO.getExecuteCount() + 1, schedulerDO.getId()});
    }

    public int insert(SchedulerDO schedulerDO) {
        return jdbcTemplate.update("insert into scheduler (name, cron, parent_name) values (?, ?, ?)",
                new Object[]{schedulerDO.getName(), schedulerDO.getCron(), schedulerDO.getParentName()});
    }

}
