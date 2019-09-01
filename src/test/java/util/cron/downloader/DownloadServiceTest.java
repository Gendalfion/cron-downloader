package util.cron.downloader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import util.cron.SpringContextBasedTest;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.readAllLines;
import static org.assertj.core.api.Assertions.assertThat;

class DownloadServiceTest extends SpringContextBasedTest {
    @Autowired
    private DownloadService downloadService;

    @Test
    void testDownload() throws IOException {
        final var resource = new ByteArrayResource("Hello".getBytes(UTF_8));
        final var outPath = createTempFile("", ".txt");

        downloadService.download(resource, outPath);

        assertThat(readAllLines(outPath, UTF_8))
                .hasSize(1)
                .containsExactly("Hello");
    }
}