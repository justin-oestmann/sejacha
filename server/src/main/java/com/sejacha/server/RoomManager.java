package com.sejacha.server;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private static List<RoomHandler> active_rooms = new ArrayList<>();

    private static void openRoom(RoomHandler roomHandler) {
        active_rooms.add(roomHandler);
        SysPrinter.println("RoomManager",
                "opened room " + roomHandler.getRoom().getName() + " (" + roomHandler.getRoom().getID() + ")");
    }

    public static boolean joinRoom(ServerClient user, Room room) throws Exception {

        if (!isRoomActive(room)) {
            RoomHandler roomHandler = new RoomHandler(room);
            openRoom(roomHandler);
        }

        for (RoomHandler roomHandler : active_rooms) {
            if (StringModify.areStringsEqual(roomHandler.getRoom().getID(), room.getID())) {
                roomHandler.subscribeRoom(user);
                roomHandler.notifyAll(user.getUser().getName() + " joined the room!");
                return true;
            }
        }
        return false;

    }

    public static boolean isRoomActive(Room room) {
        for (RoomHandler roomHandler : active_rooms) {
            SysPrinter.println(roomHandler.getRoom().getID(), room.getID());

            if (StringModify.areStringsEqual(roomHandler.getRoom().getID(), room.getID())) {
                SysPrinter.println("debug", "true");
                return true;
            }
            SysPrinter.println("debug", "false");
        }
        return false;
    }

    public static RoomHandler getRoomHandler(String room_id) {
        for (RoomHandler roomHandler : active_rooms) {
            if (StringModify.areStringsEqual(roomHandler.getRoom().getID(), room_id)) {
                return roomHandler;
            }
        }
        return null;
    }

    public static boolean isRoomActive(RoomHandler room) {
        return active_rooms.contains(room);
    }

    public static boolean isRoomActive(String room_id) {
        for (RoomHandler roomHandler : active_rooms) {
            if (StringModify.areStringsEqual(roomHandler.getRoom().getID(), room_id)) {
                return true;
            }
        }
        return false;
    }

}
