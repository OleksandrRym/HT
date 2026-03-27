package com.rymar.ht.service;

import com.rymar.ht.service.command.AddCommand;
import com.rymar.ht.service.command.Commands;
import com.rymar.ht.service.command.DrawCommand;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TelegramHandler {
    private final DrawCommand drawCommand;
    private final AddCommand addCommand;

    private final Map<String,Commands> COMMANDS_MAP;

    @PostConstruct
    public void init(){
        COMMANDS_MAP.put("/draw",drawCommand);
        COMMANDS_MAP.put("/add",addCommand);
    }

    public Message process(Long chatId , String[] cmd){
        var exc = COMMANDS_MAP.get(cmd[0]);
        return exc.execute(chatId,cmd);
    }
}
