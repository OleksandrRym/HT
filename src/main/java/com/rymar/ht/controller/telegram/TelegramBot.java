package com.rymar.ht.controller.telegram;

import com.rymar.ht.entity.dto.MessageDto;
import com.rymar.ht.service.ActivityService;
import com.rymar.ht.service.ChartGenerator;
import java.io.InputStream;

import com.rymar.ht.service.TelegramHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.util.Objects.isNull;
import static java.util.function.Predicate.isEqual;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

  @Value("${telegram.bot.token}")
  private String token;

  private final TelegramHandler telegramHandler;

  @Value("${telegram.bot.username}")
  private String username;

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String text = update.getMessage().getText();
      Long chatId = update.getMessage().getChatId();
      String[] strings = text.split(" ");
      MessageDto message = telegramHandler.process(chatId, strings);
      sendMessage(message);
    }
  }

  @SneakyThrows
  private void sendMessage(MessageDto message) {
    if (isNull(message.photo())) {
      execute(message.text());
    } else {
      execute(message.photo());
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
