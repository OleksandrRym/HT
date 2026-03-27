package com.rymar.ht.service.command;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Commands {
    Message execute(Long chatId, String[] cmd);
}
