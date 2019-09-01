package util.cron.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import util.cron.SpringContextBasedTest;
import util.cron.downloader.impl.DownloaderTask;

import java.nio.file.Path;

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
        assertEquals(Path.of("/download", "directory", "outFileName1.json"), firstTask.getDownloadPath());
        assertEquals("https://download.example.com/json/doc1", firstTask.getResource());

        var secondSchedule = cronSchedules.get(1);
        assertEquals("*/5 * * * * ?", secondSchedule.getCron());

        assertTrue(secondSchedule.getTask() instanceof DownloaderTask);
        var secondTask = (DownloaderTask) secondSchedule.getTask();
        assertEquals(Path.of("/download", "directory", "outFileName2.json"), secondTask.getDownloadPath());
        assertEquals("https://download.example.com/pdf/doc2", secondTask.getResource());
    }
}