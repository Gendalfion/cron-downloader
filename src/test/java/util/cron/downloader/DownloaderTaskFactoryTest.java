package util.cron.downloader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import util.cron.SpringContextBasedTest;

import java.io.IOException;

import static java.nio.file.Files.readString;
import static java.nio.file.Path.of;
import static org.assertj.core.util.Files.temporaryFolderPath;

class DownloaderTaskFactoryTest extends SpringContextBasedTest {
    @Autowired
    private DownloaderTaskFactory downloaderTaskFactory;

    @Test
    void testDownloadTask() throws IOException {
        final var downloadTask = downloaderTaskFactory
                .createDownloadTask("https://jsonplaceholder.typicode.com/posts/1", temporaryFolderPath(), "test.json");

        downloadTask.schedule();

        final var downloadedContent = readString(of(temporaryFolderPath(), "test.json"));
        Assertions.assertThat(downloadedContent)
                .isEqualTo("{\n" +
                        "  \"userId\": 1,\n" +
                        "  \"id\": 1,\n" +
                        "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                        "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                        "}");
    }
}