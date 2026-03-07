package com.yuan.yuanaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "yuan.txt";
        String result = fileOperationTool.readFile(fileName);
        Assertions.assertNotNull(result);
        System.out.println(result);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "yuan.txt";
        String content = "Hello, World!";
        String result = fileOperationTool.writeFile(fileName, content);
        Assertions.assertNotNull(result);
        System.out.println(result);
    }
}
