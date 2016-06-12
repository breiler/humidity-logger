package se.nimlab.hl.repository;

import se.nimlab.hl.model.Reading;

import java.util.Date;
import java.util.List;

public interface ReadingRepositoryCustom {
    List<Reading> getStatisticsReadingsByTheHour(Long deviceId, Date from, Date to);
}
