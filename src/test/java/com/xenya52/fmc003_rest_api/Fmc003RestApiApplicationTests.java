package com.xenya52.fmc003_rest_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xenya52.fmc003_rest_api.service.scrape.TeltonikaIoPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TeltonikaIoPage.class)
class Fmc003RestApiApplicationTests {

    @Test
    public void getTeltonikaIoData() throws Exception {
        TeltonikaIoPage teltonikaIoData = new TeltonikaIoPage();
        teltonikaIoData.getIoData();
        assertNotNull(teltonikaIoData);
    }
}
