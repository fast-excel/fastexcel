package cn.idev.excel.test.temp.read;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import cn.idev.excel.EasyExcel;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.test.demo.read.Sample;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

public class WebStreamReadTest {

    private ClientAndServer mockServer;

    @BeforeEach
    public void startServer() {
        this.mockServer = ClientAndServer.startClientAndServer();
    }

    @Test
    public void urlLoadInputStreamTest() throws IOException {
        mockServer
                .when(request().withMethod("GET").withPath("/sample.xlsx"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                        .withBody(Files.readAllBytes(Paths.get("src/test/resources/web/io.xlsx"))));
        URL url = new URL("http://localhost:" + mockServer.getPort() + "/sample.xlsx");
        InputStream is = url.openStream();
        List<String> body = new LinkedList<>();
        EasyExcel.read(is, Sample.class, new ReadListener<Sample>() {
                    @Override
                    public void invoke(Sample data, AnalysisContext context) {
                        body.add(data.getHeader());
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {}
                })
                .sheet()
                .doRead();
        Assertions.assertTrue(body.contains("body"));
    }

    @AfterEach
    public void stopServer() {
        mockServer.stop();
    }
}
