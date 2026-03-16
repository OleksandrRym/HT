package com.rymar.ht.adapter.in.tg;

import com.rymar.ht.domain.service.ActivityService;
import com.rymar.ht.domain.service.ChartGenerator;
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
public class TelegramBot extends TelegramLongPollingBot {

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
      String[] strings = text.split(" ");
      if (strings[0].equals("/chart")) {
        sendChart(chatId , strings[1]);
      } else {
        sendText(chatId, "Ти написав: " + text);
      }
    }
  }

  private void sendChart(Long chatId, String activity) {
      var l =  activityService.getWeekActivityCount_ByName(activity);
    SendPhoto photo = new SendPhoto();
    photo.setChatId(chatId.toString());
    InputStream chartStream = chartGenerator.generateChart( l , activity);
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
