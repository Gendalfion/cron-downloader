package util.cron.downloader.impl;

import org.springframework.stereotype.Service;
import util.cron.downloader.DownloadService;
import util.cron.downloader.DownloaderTaskFactory;
import util.cron.task.Task;

import java.nio.file.Path;

@Service
public class DownloaderTaskFactoryImpl implements DownloaderTaskFactory {
    private final DownloadService downloadService;

    public DownloaderTaskFactoryImpl(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @Override
    public Task createDownloadTask(String resource, String downloadDirectory, String fileName) {
        return new DownloaderTask(resource, Path.of(downloadDirectory, fileName), downloadService);
    }
}
