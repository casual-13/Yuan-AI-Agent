package com.yuan.yuanaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class executeTerminalCommand {

    @Test
    void executeTerminalCommand() {
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        String command = "calc";    //打开计算器
        String result = terminalOperationTool.executeTerminalCommand(command);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }
}
