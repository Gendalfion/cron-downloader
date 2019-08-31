package util.cron.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import util.cron.SpringContextBasedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestPropertySource(properties = {
        "util.cron.scheduler.config.path=classpath:scheduler-test-config.json"
})
class SchedulerConfigurationServiceTest extends SpringContextBasedTest {
    @Autowired
    private SchedulerConfigurationService schedulerConfigurationService;

    @Test
    void testGetConfig() {
        var config = schedulerConfigurationService.getConfig();

        assertNotNull(config);
        assertEquals(".", config.getDownloadDirectory());
        assertEquals(2, config.getDownloaderList().size());

        var firstDownloader = config.getDownloaderList().get(0);
        assertEquals("*/10 * * * * ?", firstDownloader.getCron());
        assertEquals("TEST_PATH_1", firstDownloader.getResourcePath());

        var secondDownloader = config.getDownloaderList().get(1);
        assertEquals("*/5 * * * * ?", secondDownloader.getCron());
        assertEquals("TEST_PATH_2", secondDownloader.getResourcePath());
    }
}