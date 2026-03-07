package com.yuan.yuanaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        String fileName = "test.pdf";
        String content = "This is a test PDF file.";
        String result = pdfGenerationTool.generatePDF(fileName, content);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }
}
