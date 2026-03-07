package com.yuan.yuanaiagent.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeToolTest {

    private DateTimeTool dateTimeTool;

    @BeforeEach
    void setUp() {
        dateTimeTool = new DateTimeTool();
    }

    @Test
    void testGetCurrentDateTime() {
        // Test with default format
        String result = dateTimeTool.getCurrentDateTime("");
        System.out.println(result);
        
        // Test with custom format
        String customResult = dateTimeTool.getCurrentDateTime("yyyy/MM/dd");
        System.out.println(customResult);
    }

}
