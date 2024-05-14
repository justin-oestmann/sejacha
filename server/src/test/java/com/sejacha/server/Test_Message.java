package com.sejacha.server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class Test_Message {

    @Test
    @DisplayName("CreateTestMessage")
    public void createTestMessage() {
        Message msg = new Message();

        msg.setUserID("null");
        msg.setRoomID("null");
        msg.setTimeCreated();
        msg.setTimeCreated(LocalDateTime.now());
        msg.setMessage("null");
    }

}
