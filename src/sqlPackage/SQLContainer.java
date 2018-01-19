/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlPackage;

/**
 * @author antizaiac
 */
public class SQLContainer {

    public String getId() {
        return "select get_id()";
    }

    public String selectPoints() {
        return "select id, x, y\n" +
                "  from points\n" +
                " order by id";
    }

    public String selectLines() {
        return "select start_point, end_point\n" +
                "  from lines";
    }

    public String selectDevices() {
        return "select name, x, y, p.value\n" +
                "  from devices d\n" +
                "  left join params p\n" +
                "    on d.id = p.device_id\n" +
                "   and p.get_date = (\n" +
                "     select max(get_date)\n" +
                "       from params p2\n" +
                "      where p2.device_id = d.id\n" +
                "   )";
    }

    public String insertSession() {
        return "insert into sessions(id, ip_address, init_date, additional_info, status, last_visit)" +
                "values(?, ?, current_timestamp, ?, ?, current_timestamp)";
    }

    public String selectSessions() {
        return "select id, ip_address, init_date, additional_info, last_visit \n" +
                "  from sessions \n" +
                " where status = 1\n" +
                " order by init_date desc";
    }

    public String selectFailedSessions() {
        return "select se.id, se.ip_address, se.init_date, se.additional_info, st.name\n" +
                "  from sessions se, statuses st \n" +
                " where se.status in (3,4)\n" +
                "   and se.status = st.id\n" +
                " order by se.init_date desc";
    }

    public String updateSession() {
        return "update sessions" +
                "   set last_visit = current_timestamp" +
                " where id = ?";
    }

    public String selectSessionByIdAndStatus() {
        return "select *\n" +
                "  from sessions \n" +
                " where id = ?\n" +
                "   and status = 1";
    }

    public String deleteSession() {
        return "update sessions" +
                "  set status = 2" +
                " where id = ?";
    }

    public String checkIpAddress() {
        return "select * \n" +
                "  from ip_black_list\n" +
                " where ip = ?";
    }

    public String countFailedAttempts() {
        return "select count(*)\n" +
                "  from sessions\n" +
                " where ip_address = ?\n" +
                "   and status in (3)";
    }

    public String insertIpInBlackList() {
        return "insert into ip_black_list(ip)" +
                "values(?)";
    }

    public String dropErrorSessionsByIp() {
        return "delete from sessions \n" +
                " where status in (3)\n" +
                "   and ip_address = ?";
    }

    public String findSessionForUser() {
        return "select id" +
                "  from sessions" +
                " where ip_address = ?" +
                "   and additional_info = ?" +
                "   and status = 1";
    }

    public String selectAllJobs() {
        return "select id, name, body, timeout_minutes, last_execute, status, type\n" +
                "  from jobs";
    }

    public String checkJobTimeOut() {
        return "select \n" +
                "  case \n" +
                "    when extract(epoch FROM current_timestamp - last_execute) / 60 >= timeout_minutes then 'true'\n" +
                "    when last_execute is null then 'true'\n" +
                "    else 'false'\n" +
                "  end\n" +
                "  from jobs\n" +
                " where id = ?";
    }

    public String updateJobLastExecute() {
        return "update jobs" +
                "   set last_execute = current_timestamp" +
                " where id = ?";
    }

    public String insertErrorRecord() {
        return "insert into job_errors(job_id, name, error_message, error_date)" +
                "values(?,?,?,current_timestamp)";
    }

    public String selectAllJobsFullInfo() {
        return "select j.id, \n" +
                "       j.name, \n" +
                "       j.body, \n" +
                "       j.timeout_minutes,\n" +
                "       j.timeout_minutes - trunc(extract(epoch FROM current_timestamp - last_execute) / 60) as time_to_next_execute,\n" +
                "       j.status,\n" +
                "       case\n" +
                "         when j.last_execute is null then 'never'\n" +
                "         else to_char(j.last_execute, 'YYYY-MM-DD HH24-MI-SS:SSSS')\n" +
                "       end,\n" +
                "       count(e.*) as errors,\n" +
                "       j.type\n" +
                "  from jobs j \n" +
                "  left join job_errors e \n" +
                "    on j.id = e.job_id\n" +
                " group by j.id, \n" +
                "          j.name, \n" +
                "          j.body, \n" +
                "          j.timeout_minutes,\n" +
                "          j.timeout_minutes - trunc(extract(epoch FROM current_timestamp - last_execute) / 60),\n" +
                "          j.status,\n" +
                "          j.last_execute,\n" +
                "          j.type\n" +
                " order by j.id desc";
    }

    public String updateJobStatus() {
        return "update jobs" +
                "   set status = ?" +
                " where id = ?";
    }

    public String insertNewJob() {
        return "insert into jobs(id, name, body, timeout_minutes, status, type)\n" +
                "values(?, 'New job', '', 1 , 2, 'SQL')";
    }

    public String deleteJob() {
        return "delete from jobs\n" +
                " where id = ?";
    }

    public String selectJob() {
        return "select name,\n" +
                "      body,\n" +
                "      timeout_minutes,\n" +
                "      last_execute,\n" +
                "      status,\n" +
                "      type,\n" +
                "      timeout_minutes - trunc(extract(epoch FROM current_timestamp - last_execute) / 60)\n" +
                "  from jobs\n" +
                " where id = ?";
    }

    public String updateJob() {
        return "update jobs\n" +
                "   set name = ?,\n" +
                "       body = ?,\n" +
                "       timeout_minutes = ?,\n" +
                "       status = ?,\n" +
                "       type = ?\n" +
                " where id = ?";
    }

    public String selectError() {
        return "select e.id, e.job_id, e.error_message, e.name, e.error_date, j.name\n" +
                "  from job_errors e, jobs j\n" +
                " where e.id = ?\n" +
                "   and e.job_id = j.id";
    }

    public String selectJobErrors() {
        return "select e.id, e.job_id, e.error_message, e.name, e.error_date, j.name\n" +
                "  from job_errors e, jobs j\n" +
                " where e.job_id = ?\n" +
                "   and e.job_id = j.id\n" +
                " order by e.error_date desc";
    }

    public String selectAllStatistics() {
        return "select id,name,query,width,height,type_id\n" +
                "  from statistics\n" +
                " where hidden is null\n" +
                " order by creation_date";
    }

    public String selectStatistic() {
        return "select name,query,width,height,type_id\n" +
                "  from statistics\n" +
                " where id = ?";
    }

    public String updateStatistic() {
        return "update statistics\n" +
                "   set name = ?,\n" +
                "       query = ?,\n" +
                "       width = ?,\n" +
                "       height = ?,\n" +
                "       type_id = ?\n" +
                " where id = ?";
    }

    public String insertNewStatistic() {
        return "insert into statistics(id, name, query, width, height, creation_date, type_id)\n" +
                "values(?, 'empty stat', 'select', 300, 100, current_timestamp, ?)";
    }

    public String selectDeviceById() {
        return "select name, x, y, type_id\n" +
                "  from devices\n" +
                " where id = ?";
    }

    public String selectProcessorClassByDeviceId() {
        return "select dt.processor_class\n" +
                "  from devices d, device_types dt\n" +
                " where d.id = ?\n" +
                "   and d.type_id = dt.id";
    }

    public String insertParam() {
        return "insert into params(device_id, value, get_date)\n" +
                "values(?, ?, current_timestamp)";
    }

    public String selectStatisticName() {
        return "select name from statistic_types where id = ?";
    }

    public String selectStatisticType() {
        return "select t.name\n" +
                "  from statistic_types t, statistics s\n" +
                " where t.id = s.type_id\n" +
                "   and s.id = ?";
    }
}
