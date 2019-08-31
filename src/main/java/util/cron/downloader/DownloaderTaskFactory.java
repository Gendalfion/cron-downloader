package util.cron.downloader;

import util.cron.task.Task;

public interface DownloaderTaskFactory {
    Task createDownloadTask(String resource, String downloadDirectory);
}
