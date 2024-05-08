package com.sejacha.server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocket {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from client: " + message);
        session.getAsyncRemote().sendText("Received your message: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket closed: " + session.getId());
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}