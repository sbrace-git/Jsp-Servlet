package com.example.jstl;

public class MessageService {

    private static Message[] messages;

    static {
        messages = new Message[]{
                new Message("1", "test1"),
                new Message("2", "test2"),
                new Message("3", "test3"),
                new Message("4", "test4"),
                new Message("5", "test5"),
                new Message("6", "test6"),
        };
    }

    public Message[] getMessages() {
        return messages;
    }
}
