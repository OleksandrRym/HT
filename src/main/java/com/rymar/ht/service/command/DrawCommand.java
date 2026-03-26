package com.rymar.ht.service.command;

import com.rymar.ht.service.ActivityService;
import com.rymar.ht.service.ChartGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class DrawCommand implements Commands {
    private final ActivityService activityService;
    private final ChartGenerator chartGenerator;
    private final AbsSender absSender;

    @Override
    public void execute(Long chatId, String[] cmd) {
        var l = activityService.getWeekActivityCount_ByName(cmd[0]);
        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId.toString());
        InputStream chartStream = chartGenerator.generateChart(l, cmd[0]);
        photo.setPhoto(new InputFile(chartStream, "chart.png"));
        try {
            absSender.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
