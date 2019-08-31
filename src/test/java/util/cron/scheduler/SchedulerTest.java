package util.cron.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import util.cron.SpringContextBasedTest;
import util.cron.task.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@EnableScheduling
class SchedulerTest extends SpringContextBasedTest {
    @TestConfiguration
    static class MockSchedulerConfigurationService {
        private static AtomicInteger everySecondCounter = new AtomicInteger(0);
        private static AtomicInteger everyTwoSecondsCounter = new AtomicInteger(0);

        @Bean
        @Primary
        public SchedulerConfigurationService schedulerConfigurationService () {
            return new SchedulerConfigurationService() {
                @Override
                public List<CronSchedule> getCronSchedules() {
                    return List.of(
                            new CronSchedule() {
                                @Override
                                public String getCron() {
                                    return "*/1 * * * * ?";
                                }

                                @Override
                                public Task getTask() {
                                    return everySecondCounter::incrementAndGet;
                                }
                            },
                            new CronSchedule() {
                                @Override
                                public String getCron() {
                                    return "*/2 * * * * ?";
                                }

                                @Override
                                public Task getTask() {
                                    return everyTwoSecondsCounter::incrementAndGet;
                                }
                            }
                    );
                }
            };
        }
    }


    @Test
    void testCronScheduling() throws InterruptedException {
        MockSchedulerConfigurationService.everySecondCounter.set(0);
        MockSchedulerConfigurationService.everyTwoSecondsCounter.set(0);

        Thread.sleep(10_000);

        final var secondsCounterValue = MockSchedulerConfigurationService.everySecondCounter.get();
        final var everyTwoSecondsCounterValue = MockSchedulerConfigurationService.everyTwoSecondsCounter.get();

        assertThat(secondsCounterValue).isBetween(8, 12);
        assertThat(everyTwoSecondsCounterValue).isBetween(3, 7);
    }
}