package util.cron.scheduler;

import util.cron.task.Task;

public interface CronSchedule {
    String getCron();
    Task getTask();
}
