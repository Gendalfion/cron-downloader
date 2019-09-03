package util.cron.scheduler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import util.cron.downloader.DownloaderTaskFactory;
import util.cron.scheduler.CronSchedule;
import util.cron.scheduler.SchedulerConfigurationService;
import util.cron.task.Task;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class SchedulerConfigurationServiceImpl implements SchedulerConfigurationService {
    private static final Logger log = getLogger(SchedulerConfigurationServiceImpl.class);

    private final List<CronSchedule> cronSchedules;

    @SneakyThrows
    public SchedulerConfigurationServiceImpl(@Value("${util.cron.scheduler.config.path:classpath:default-scheduler-config.json}") String jsonConfigPath,
                                             @Value("${util.cron.download.directory:}") String downloadDirectory,
                                             ResourceLoader resourceLoader,
                                             ObjectMapper mapper,
                                             DownloaderTaskFactory downloaderTaskFactory) {
        log.info("Loading config from {}", jsonConfigPath);
        final var resource = resourceLoader.getResource(jsonConfigPath);

        final var configuration = mapper
                .readValue(resource.getInputStream(), util.cron.scheduler.dto.SchedulerConfiguration.class);
        log.debug("Got configuration {}", configuration);

        this.cronSchedules = configuration.getDownloaderList().stream()
                .map(downloaderDto -> new CronSchedule() {
                    private final Task task = downloaderTaskFactory
                            .createDownloadTask(downloaderDto.getResourcePath(), downloadDirectory, downloaderDto.getOutputFileName());

                    private final String cron = downloaderDto.getCron();

                    @Override
                    public String getCron() {
                        return cron;
                    }

                    @Override
                    public Task getTask() {
                        return task;
                    }
                })
                .collect(toList());
    }

    @Override
    public List<CronSchedule> getCronSchedules() {
        return cronSchedules;
    }
}
