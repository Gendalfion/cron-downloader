package util.cron.downloader.impl;

import org.springframework.core.io.DefaultResourceLoader;
import util.cron.downloader.DownloadService;
import util.cron.task.Task;

import java.nio.file.Path;

public class DownloaderTask implements Task {
    private final String resource;
    private final Path downloadPath;
    private final DownloadService downloadService;

    public DownloaderTask(String resource, Path downloadPath, DownloadService downloadService) {
        this.resource = resource;
        this.downloadPath = downloadPath;
        this.downloadService = downloadService;
    }

    @Override
    public void schedule() {
        final var resourceLoader = new DefaultResourceLoader();
        final var resource = resourceLoader.getResource(this.resource);

        downloadService.download(resource, downloadPath);
    }

    public String getResource() {
        return resource;
    }

    public Path getDownloadPath() {
        return downloadPath;
    }
}
