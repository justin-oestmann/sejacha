package com.sejacha.server;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private static List<RoomHandler> active_rooms = new ArrayList<>();

    public static void openRoom(RoomHandler roomHandler) {
        active_rooms.add(roomHandler);
    }

    public static boolean joinRoom(ServerClient user, Room room) throws Exception {

        if (!isRoomActive(room)) {
            RoomHandler roomHandler = new RoomHandler(room);
            openRoom(roomHandler);
        }

        for (RoomHandler roomHandler : active_rooms) {
            if (roomHandler.getRoom() == room) {
                roomHandler.subscribeRoom(user);
                return true;
            }
        }
        return false;

    }

    public static boolean isRoomActive(Room room) {
        for (RoomHandler roomHandler : active_rooms) {
            if (roomHandler.getRoom() == room) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRoomActive(RoomHandler room) {
        return active_rooms.contains(room);
    }

    public static boolean isRoomActive(String room_id) {
        for (RoomHandler roomHandler : active_rooms) {
            if (roomHandler.getRoom().getID() == room_id) {
                return true;
            }
        }
        return false;
    }

}
