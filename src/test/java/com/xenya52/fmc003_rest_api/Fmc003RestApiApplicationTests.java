package com.xenya52.fmc003_rest_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xenya52.fmc003_rest_api.service.IoWikiByFile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = IoWikiByFile.class)
class Fmc003RestApiApplicationTests {

    @Test
    public void getTeltonikaIoData() throws Exception {
        IoWikiByFile teltonikaIoData = new IoWikiByFile();
        teltonikaIoData.getIdsAndNames();
        assertNotNull(teltonikaIoData);
    }
}
