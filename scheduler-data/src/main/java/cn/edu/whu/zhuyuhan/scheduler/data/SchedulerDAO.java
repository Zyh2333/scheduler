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

    public int updateExecuteByPrimaryKey(SchedulerDO schedulerDO) {
        return jdbcTemplate.update("update scheduler set execute_count = ?, status = ? where id = ?",
                schedulerDO.getExecuteCount(), schedulerDO.getStatus(), schedulerDO.getId());
    }

//    public int updateVersion(SchedulerDO schedulerDO) {
//        return jdbcTemplate.update("update scheduler set version = ? where id = ? and version = ?",
//                schedulerDO.getVersion() + 1, schedulerDO.getId(), schedulerDO.getVersion());
//    }

    public int insert(SchedulerDO schedulerDO) {
        return jdbcTemplate.update("insert into scheduler (name, cron, parent_name, execute_count, status, special, distributed, sync_async) values (?, ?, ?, ?, ?, ?, ?, ?)",
                schedulerDO.getName(), schedulerDO.getCron(),
                schedulerDO.getParentName(), schedulerDO.getExecuteCount(),
                schedulerDO.getStatus(), schedulerDO.getSpecial(),
                schedulerDO.getDistributed(), schedulerDO.getSyncAsync());
    }

    public int updateMetadataByPrimaryKey(SchedulerDO schedulerDO) {
        return jdbcTemplate.update("update scheduler set cron = ?, special = ?, distributed = ?, sync_async = ? where id = ?",
                schedulerDO.getCron(), schedulerDO.getSpecial(),
                schedulerDO.getDistributed(), schedulerDO.getSyncAsync(),
                schedulerDO.getId());
    }

}
