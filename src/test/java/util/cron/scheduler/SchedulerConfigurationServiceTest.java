package util.cron.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import util.cron.SpringContextBasedTest;
import util.cron.downloader.impl.DownloaderTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(properties = {
        "util.cron.scheduler.config.path=classpath:scheduler-test-config.json"
})
class SchedulerConfigurationServiceTest extends SpringContextBasedTest {
    @Autowired
    private SchedulerConfigurationService schedulerConfigurationService;

    @Test
    void testGetConfig() {
        var cronSchedules = schedulerConfigurationService.getCronSchedules();
        assertNotNull(cronSchedules);
        assertEquals(2, cronSchedules.size());

        var firstSchedule = cronSchedules.get(0);
        assertEquals("*/10 * * * * ?", firstSchedule.getCron());

        assertTrue(firstSchedule.getTask() instanceof DownloaderTask);
        var firstTask = (DownloaderTask) firstSchedule.getTask();
        assertEquals(".", firstTask.getDownloadDirectory());
        assertEquals("TEST_PATH_1", firstTask.getResource());

        var secondSchedule = cronSchedules.get(1);
        assertEquals("*/5 * * * * ?", secondSchedule.getCron());

        assertTrue(secondSchedule.getTask() instanceof DownloaderTask);
        var secondTask = (DownloaderTask) secondSchedule.getTask();
        assertEquals(".", secondTask.getDownloadDirectory());
        assertEquals("TEST_PATH_2", secondTask.getResource());
    }
}