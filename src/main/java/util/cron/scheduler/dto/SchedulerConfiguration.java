package util.cron.scheduler.dto;

import lombok.Data;

import java.util.List;

import static java.util.Collections.emptyList;

@Data
public class SchedulerConfiguration {
    private String downloadDirectory = "";
    private List<Downloader> downloaderList = emptyList();

    @Data
    public static class Downloader {
        private String cron;
        private String resourcePath;
    }
}
