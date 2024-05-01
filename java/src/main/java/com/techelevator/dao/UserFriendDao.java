package com.techelevator.dao;

public interface UserFriendDao {

    /**
     * This method checks if the user with userId is already friends with another user
     *
     * @param userId the sender of the friend request
     * @param friendId the reciever of the friend request
     * @return whether there's already a friend request
     */
    boolean isFriended (int userId, int friendId);

    /**
     * This method adds someone as a friend
     * @param userId user that is sending the friend request
     * @param friendId user that is recieving the friend request
     */
    int friendUser (int userId, int friendId);

    /**
     * this method removes a friend
     * @param userId user that is removing a friend
     * @param friendId the friend
     */
    int unfriendUser (int userId, int friendId);

    /**
     * this method reports of two users added each other
     * @param user1 any one of the two users
     * @param user2 the other of the two users
     * @return
     */
    boolean isBothFriends (int user1, int user2);

}
