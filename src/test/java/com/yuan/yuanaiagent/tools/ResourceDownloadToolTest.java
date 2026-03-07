package com.yuan.yuanaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceDownloadToolTest {

    @Test
    void testDownloadResource() {
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        String url = "https://pic.nximg.cn/20140117/10453194_101940593000_2.jpg";
        String fileName = "pic.jpg";
        String result = resourceDownloadTool.downloadResource(url, fileName);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }
}
