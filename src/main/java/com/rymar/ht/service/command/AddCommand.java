package com.rymar.ht.service.command;

import com.rymar.ht.entity.dto.MessageDto;
import com.rymar.ht.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class AddCommand implements Commands{
    private final ActivityService activityService;
    @Override
    public MessageDto execute(Long chatId, String[] cmd) {
        Double count = new Double(cmd[2]);
        activityService.addActivity(cmd[1],count);
        return new MessageDto(SendMessage.builder().chatId(chatId.toString()).text("Updated").build(), null);
    }
}
