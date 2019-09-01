package util.cron.downloader;

import org.springframework.core.io.Resource;

import java.nio.file.Path;

public interface DownloadService {
    void download(Resource resource, Path downloadPath);
}
