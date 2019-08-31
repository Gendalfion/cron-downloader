package util.cron.scheduler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import util.cron.scheduler.SchedulerConfiguration;
import util.cron.scheduler.SchedulerConfigurationService;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class SchedulerConfigurationServiceImpl implements SchedulerConfigurationService {
    private static final Logger log = getLogger(SchedulerConfigurationServiceImpl.class);

    private final SchedulerConfiguration configuration;

    @SneakyThrows
    public SchedulerConfigurationServiceImpl(@Value("${util.cron.scheduler.config.path:classpath:default-scheduler-config.json}") String jsonConfigPath,
                                             ResourceLoader resourceLoader,
                                             ObjectMapper mapper) {
        log.info("Loading config from {}", jsonConfigPath);
        final var resource = resourceLoader.getResource(jsonConfigPath);

        final var configuration = mapper.readValue(resource.getInputStream(), SchedulerConfiguration.class);
        log.debug("Got configuration {}", configuration);

        this.configuration = configuration;
    }

    @Override
    public SchedulerConfiguration getConfig() {
        return configuration;
    }
}
