package com.rymar.ht.domain.service;

import com.rymar.ht.domain.service.command.Commands;
import com.rymar.ht.domain.service.command.DrawCommand;

import java.util.HashMap;
import java.util.Map;

public class TelegramHandler {
    private final static Map<String,Commands> COMMANDS_MAP = Map.ofEntries(
            Map.entry("/draw", 1),
            Map.entry("/start", 2),
            Map.entry("c", 3)
    );
    private void process(Long chatId ,String cmd){
        var exc = COMMANDS_MAP.get(cmd);
        exc.execute(chatId,cmd);
    }
}
