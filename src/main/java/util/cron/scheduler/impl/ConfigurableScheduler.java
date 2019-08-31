package util.cron.scheduler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import static java.util.TimeZone.getDefault;

@Service
public class ConfigurableScheduler implements SchedulingConfigurer {
    private final TaskScheduler taskScheduler;

    public ConfigurableScheduler(@Qualifier("poolScheduler") TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(taskScheduler);
        }

        // TODO: place dynamic configuration instead
        taskRegistrar.getScheduler().schedule(() -> System.out.println("Scheduler 1 ping..."),
                new CronTrigger("0/10 * * * * ?", getDefault()));

        taskRegistrar.getScheduler().schedule(() -> System.out.println("Scheduler 2 ping..."),
                new CronTrigger("0/5 * * * * ?", getDefault()));
    }
}

