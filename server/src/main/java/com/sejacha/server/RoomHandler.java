package com.sejacha.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class RoomHandler {
    private Room room;

    private List<ServerClient> clients = new ArrayList<>();;

    public RoomHandler(Room room) throws Exception {
        if (room.getID() == null) {
            throw new Exception("room is not a valid, loaded room!");
        }
        this.room = room;
    }

    public void subscribeRoom(ServerClient user) throws Exception {
        if (this.clients.contains(user)) {
            throw new Exception("client already joined the room!");
        }
        this.clients.add(user);
    }

    public void unSubscribeRoom(ServerClient user) throws Exception {
        if (!this.clients.contains(user)) {
            throw new Exception("client not in the room!");
        }
        this.clients.remove(user);

    }

    public Room getRoom() {
        return room;
    }

    public void sendMessage(Message msg) throws Exception {
        if (!StringModify.areStringsEqual(msg.getRoomID(), this.room.getID())) {
            throw new Exception("room_id invalid!");
        }

        for (ServerClient serverClient : clients) {

            if (!StringModify.areStringsEqual(serverClient.getUser().getID(), msg.getUserID())) {
                JSONObject messageContent = new JSONObject();

                messageContent.put("msg", msg.getMessage());

                String author_name = "null";

                for (ServerClient serverClient2 : clients) {
                    if (StringModify.areStringsEqual(serverClient2.getUser().getID(), msg.getUserID())) {
                        author_name = serverClient2.getUser().getName();
                        break;
                    }
                }

                messageContent.put("author", author_name);
                messageContent.put("room", msg.getRoomID());

                SocketMessage socketMessage = new SocketMessage(serverClient.getUser().getAuthKey(),
                        SocketMessageType.NEWMESSAGE, messageContent);

                serverClient.sendMessage(socketMessage);
            }
        }
    }

    public void notifyAll(String msg) {
        for (ServerClient serverClient : clients) {
            JSONObject messageContent = new JSONObject();

            messageContent.put("msg", msg);
            messageContent.put("author", "Server");
            messageContent.put("room", this.room.getID());

            SocketMessage socketMessage = new SocketMessage(serverClient.getUser().getAuthKey(),
                    SocketMessageType.NEWMESSAGE, messageContent);

            serverClient.sendMessage(socketMessage);
        }
    }
}
