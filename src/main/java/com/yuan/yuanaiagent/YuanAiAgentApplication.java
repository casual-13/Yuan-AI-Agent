package com.yuan.yuanaiagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
public class YuanAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuanAiAgentApplication.class, args);
    }

}
