package util.cron.downloader.impl;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import util.cron.downloader.DownloadService;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class DownloadServiceImpl implements DownloadService {
    @SneakyThrows
    @Override
    public void download(Resource resource, Path downloadPath) {
        Files.copy(resource.getInputStream(), downloadPath, REPLACE_EXISTING);
    }
}
