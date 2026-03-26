package com.rymar.ht.config;

import com.rymar.ht.controller.telegram.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBot bot) throws Exception {
        TelegramBotsApi botsApi =
                new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        return botsApi;
    }
}