package se.nimlab.hl.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.nimlab.hl.model.Reading;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReadingRepositoryImpl implements ReadingRepositoryCustom {

    private static final String SEARCH_BY_THE_HOUR =
            "SELECT " +
                    "  MAX(id) as id,\n" +
                    "  sum(humidity) / count(*) as humidity,\n" +
                    "  sum(temperature) / count(*) as temperature,\n" +
                    "  TO_CHAR(created, 'yyyy-mm-dd hh24') || ':00:00' as created,\n" +
                    "  device_id\n" +
                    "FROM\n" +
                    "  reading\n" +
                    "WHERE\n" +
                    "  created > :from AND\n" +
                    "  created < :to AND\n" +
                    "  device_id < :deviceId\n" +
                    "GROUP BY\n" +
                    "  TO_CHAR(created, 'yyyy-mm-dd hh24') || ':00:00',\n" +
                    "  device_id\n" +
                    "ORDER BY created DESC";

    private static final String SEARCH_ALL =
            "SELECT * FROM reading ORDER BY created DESC";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Reading> getStatisticsReadingsByTheHour(Long deviceId, Date from, Date to) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("deviceId", deviceId);
        queryParams.put("from", from);
        queryParams.put("to", to);

        return jdbcTemplate.query(SEARCH_BY_THE_HOUR,
                queryParams,
                new BeanPropertyRowMapper<>(Reading.class)
        );
    }
}
