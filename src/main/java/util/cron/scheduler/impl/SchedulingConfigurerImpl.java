package util.cron.scheduler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import util.cron.scheduler.SchedulerConfigurationService;

import static java.util.TimeZone.getDefault;

@Service
public class SchedulingConfigurerImpl implements SchedulingConfigurer {
    private final TaskScheduler taskScheduler;
    private final SchedulerConfigurationService configurationService;

    public SchedulingConfigurerImpl(@Qualifier("poolScheduler") TaskScheduler taskScheduler,
                                    SchedulerConfigurationService configurationService) {
        this.taskScheduler = taskScheduler;
        this.configurationService = configurationService;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(taskScheduler);
        }

        configurationService.getCronSchedules()
                .forEach(cronSchedule ->
                        taskRegistrar.getScheduler().schedule(
                                cronSchedule.getTask()::schedule,
                                new CronTrigger(cronSchedule.getCron(), getDefault())
                        )
                );
    }
}

