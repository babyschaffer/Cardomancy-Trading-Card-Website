package com.techelevator.dao;

import com.techelevator.model.UserProfile;
import java.util.List;

public interface UserProfileDao {

    /**
     * Retrieves a single user profile
     * by using a username as a search
     * parameter
     *
     * @param username username to search by
     * @return single user profile associated to
     * the username
     */
    UserProfile findUserProfileByName(String username);


    /**
     * Retrieves a list of user profiles
     * by using a username as a search
     * parameter
     *
     * @param username username to search by
     * @return list of user profiles that match
     * the query parameter
     */
    List<String> findProfilesByUsername(String username);


}
