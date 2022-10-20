package com.example.springjdbctemplate.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Message {
    private String username;
    private Long millis;
    private String blabla;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getMillis() {
        return millis;
    }

    public void setMillis(Long millis) {
        this.millis = millis;
    }

    public String getBlabla() {
        return blabla;
    }

    public void setBlabla(String blabla) {
        this.blabla = blabla;
    }

    public LocalDateTime getLocalDateTime() {
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public Message() {
    }

    public Message(String username, Long millis, String blabla) {
        this.username = username;
        this.millis = millis;
        this.blabla = blabla;
    }
}
