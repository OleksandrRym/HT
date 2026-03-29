package com.rymar.ht.entity.dto;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Builder
public record MessageDto(SendMessage text, SendPhoto photo) {}
