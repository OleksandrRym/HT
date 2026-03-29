package com.rymar.ht.service.command;

import com.rymar.ht.entity.dto.MessageDto;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Commands {
    MessageDto execute(Long chatId, String[] cmd);
}
