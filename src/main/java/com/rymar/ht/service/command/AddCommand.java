package com.rymar.ht.service.command;

import com.rymar.ht.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class AddCommand implements Commands{
    private final ActivityService activityService;
    @Override
    public Message execute(Long chatId, String[] cmd) {
        Double count = new Double(cmd[2]);
        activityService.addActivity(cmd[1],count);
    }
}
