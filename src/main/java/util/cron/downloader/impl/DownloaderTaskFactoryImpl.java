package util.cron.downloader.impl;

import org.springframework.stereotype.Service;
import util.cron.downloader.DownloaderTaskFactory;
import util.cron.task.Task;

@Service
public class DownloaderTaskFactoryImpl implements DownloaderTaskFactory {
    @Override
    public Task createDownloadTask(String resource, String downloadDirectory) {
        return new DownloaderTask(resource, downloadDirectory);
    }
}
