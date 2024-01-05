package com.sequenceiq.periscope.service;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import com.sequenceiq.periscope.domain.TimeAlert;
import com.sequenceiq.periscope.utils.TimeUtil;

@Service
public class DateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateService.class);

    @Inject
    private DateTimeService dateTimeService;

    public boolean isTrigger(TimeAlert alert, long monitorUpdateRate) {
        return isTrigger(alert, monitorUpdateRate, dateTimeService.getDefaultZonedDateTime());
    }

    public boolean isTrigger(TimeAlert alert, long monitorUpdateRate, ZonedDateTime currentTime) {
        try {
            String alertTimeZone = alert.getTimeZone();
            CronExpression cronExpression = getCronExpression(alert.getCron());
            ZonedDateTime currentTimeAtAlertZone = currentTime.withZoneSameInstant(ZoneId.of(alertTimeZone));
            ZonedDateTime startTimeOfTheMonitorInterval = currentTimeAtAlertZone.minus(monitorUpdateRate, ChronoUnit.MILLIS);
            ZonedDateTime nextTime = cronExpression.next(startTimeOfTheMonitorInterval);
            long interval = (currentTimeAtAlertZone.toEpochSecond() - nextTime.toEpochSecond()) * TimeUtil.SECOND_TO_MILLISEC;

            boolean triggerReady = interval >= 0L && interval < monitorUpdateRate;
            if (triggerReady) {
                LOGGER.info("Time alert '{}', cluster '{}' firing at '{}' compared to current time '{}' in timezone '{}'",
                        alert.getName(), alert.getCluster().getStackCrn(), nextTime, currentTimeAtAlertZone, alertTimeZone);
            }
            return triggerReady;
        } catch (ParseException e) {
            LOGGER.error("Invalid cron expression '{}', cluster '{}'", e.getMessage(), alert.getCluster().getStackCrn());
            return false;
        }
    }

    public CronExpression getCronExpression(String cron) throws ParseException {
        try {
            return CronExpression.parse(cron);
        } catch (Exception ex) {
            throw new ParseException(ex.getMessage(), 0);
        }
    }

    public void validateTimeZone(String timeZone) throws ParseException {
        try {
            if (timeZone != null) {
                ZoneId.of(timeZone);
            }
        } catch (Exception ex) {
            throw new ParseException(ex.getMessage(), 0);
        }
    }
}
