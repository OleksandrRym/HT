package com.rymar.ht.config;

import com.rymar.ht.service.ActivityService;
import com.rymar.ht.service.ChartGenerator;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyTelegramBot extends TelegramLongPollingBot {

  @Value("${telegram.bot.token}")
  private String token;

  @Value("${telegram.bot.username}")
  private String username;

  private final ChartGenerator chartGenerator;
  private final ActivityService activityService;

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {

      String text = update.getMessage().getText();
      Long chatId = update.getMessage().getChatId();

      if (text.equals("/chart")) {
        sendChart(chatId);
      } else {
        sendText(chatId, "Ти написав: " + text);
      }
    }
  }

  private void sendChart(Long chatId) {
    double[] x = {5, 12, 2, 5, 5};
    double[] y = {1, 2, 3, 4, 5};
     var l =  activityService.getActivityByWeek();
    SendPhoto photo = new SendPhoto();
    photo.setChatId(chatId.toString());

    InputStream chartStream = chartGenerator.generateChart( l , "RUN");
    System.out.println(chartStream);
    photo.setPhoto(new InputFile(chartStream, "chart.png"));

    try {
      execute(photo);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private void sendText(Long chatId, String text) {
    SendMessage message = SendMessage.builder().chatId(chatId.toString()).text(text).build();
    try {
      execute(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }
}
