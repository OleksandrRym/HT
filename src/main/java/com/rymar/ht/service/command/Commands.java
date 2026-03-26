package com.rymar.ht.service.command;

public interface Commands {
    void execute(Long chatId, String[] cmd);
}
