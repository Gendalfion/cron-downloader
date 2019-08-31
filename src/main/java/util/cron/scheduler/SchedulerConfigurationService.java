package util.cron.scheduler;

import java.util.List;

public interface SchedulerConfigurationService {
    List<CronSchedule> getCronSchedules();
}
