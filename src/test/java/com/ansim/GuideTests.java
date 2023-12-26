package com.ansim;

import com.ansim.mapper.GuideMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
public class GuideTests {

    @Autowired
    private GuideMapper mapper;

    @Test
    public void testSelect() {
        System.out.println();
    }

}
