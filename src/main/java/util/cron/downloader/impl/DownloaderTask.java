package util.cron.downloader.impl;

import util.cron.task.Task;

public class DownloaderTask implements Task {
    private final String resource;
    private final String downloadDirectory;

    public DownloaderTask(String resource, String downloadDirectory) {
        this.resource = resource;
        this.downloadDirectory = downloadDirectory;
    }

    @Override
    public void schedule() {
        // TODO: implement downloader task
    }

    public String getResource() {
        return resource;
    }

    public String getDownloadDirectory() {
        return downloadDirectory;
    }
}
