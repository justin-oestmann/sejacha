package com.sejacha.server;

// import java.util.*; (。_。)
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The Contact class manages friendships between users. Both users must mutually
 * add each other to form a friendship.
 */
public class Contact {

    private static HashMap<String, Set<String>> pendingRequests = new HashMap<>();
    private static HashMap<String, Set<String>> friends = new HashMap<>();

    /**
     * Sends a friend request from one user to another.
     *
     * @param fromUser the user sending the friend request
     * @param toUser   the user receiving the friend request
     */
    public static void sendFriendRequest(String fromUser, String toUser) {
        pendingRequests.putIfAbsent(toUser, new HashSet<>());
        pendingRequests.get(toUser).add(fromUser);
    }

    /**
     * Accepts a friend request.
     *
     * @param fromUser the user who sent the friend request
     * @param toUser   the user accepting the friend request
     */
    public static void acceptFriendRequest(String fromUser, String toUser) {
        if (pendingRequests.containsKey(toUser) && pendingRequests.get(toUser).contains(fromUser)) {
            pendingRequests.get(toUser).remove(fromUser);
            if (pendingRequests.get(toUser).isEmpty()) {
                pendingRequests.remove(toUser);
            }
            addFriend(fromUser, toUser);
            addFriend(toUser, fromUser);
        }
    }

    /**
     * Adds a user to another user's friend list.
     *
     * @param user      the user whose friend list is being updated
     * @param newFriend the user being added to the friend list
     */
    private static void addFriend(String user, String newFriend) {
        friends.putIfAbsent(user, new HashSet<>());
        friends.get(user).add(newFriend);
    }

    /**
     * Checks if two users are friends.
     *
     * @param user1 the first user
     * @param user2 the second user
     * @return {@code true} if the users are friends, {@code false} otherwise
     */
    public static boolean areFriends(String user1, String user2) {
        return friends.containsKey(user1) && friends.get(user1).contains(user2);
    }

    /**
     * Retrieves the friend list of a user.
     *
     * @param user the user whose friend list is to be retrieved
     * @return the set of friends for the user
     */
    public static Set<String> getFriends(String user) {
        return friends.getOrDefault(user, new HashSet<>());
    }

    /**
     * Cancels a friend request.
     *
     * @param fromUser the user who sent the friend request
     * @param toUser   the user who received the friend request
     */
    public static void cancelFriendRequest(String fromUser, String toUser) {
        if (pendingRequests.containsKey(toUser)) {
            pendingRequests.get(toUser).remove(fromUser);
            if (pendingRequests.get(toUser).isEmpty()) {
                pendingRequests.remove(toUser);
            }
        }
    }
}
