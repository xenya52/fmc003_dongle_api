package com.xenya52.fmc003_rest_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xenya52.fmc003_rest_api.service.ScrapeTeltonikaIoWiki;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ScrapeTeltonikaIoWiki.class)
class Fmc003RestApiApplicationTests {

    @Test
    public void getTeltonikaIoData() throws Exception {
        ScrapeTeltonikaIoWiki teltonikaIoData = new ScrapeTeltonikaIoWiki();
        teltonikaIoData.getDataSendingParameters();
        assertNotNull(teltonikaIoData);
    }
}
