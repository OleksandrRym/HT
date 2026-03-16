package com.rymar.ht.domain.service.command;

import com.rymar.ht.domain.service.ActivityService;
import com.rymar.ht.domain.service.ChartGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class DrawCommand implements Commands {
    private final ActivityService activityService;
    private final ChartGenerator chartGenerator;
    private final AbsSender absSender;

    @Override
    public void execute(Long chatId, String activityName) {
        var l = activityService.getWeekActivityCount_ByName(activityName);
        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId.toString());
        InputStream chartStream = chartGenerator.generateChart(l, activityName);
        photo.setPhoto(new InputFile(chartStream, "chart.png"));
        try {
            absSender.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
