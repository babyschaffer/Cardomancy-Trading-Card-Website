package com.techelevator.dao;

import com.techelevator.model.UserProfile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcUserProfileDao implements UserProfileDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserProfileDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public UserProfile findUserProfileByName(String username) {
        // queried profile initially set to null in case no results are returned
        UserProfile queriedProfile = null;
        // friends list started as an empty list
        List<String> friendsList = new ArrayList<>();
        // query command to select profile using an exact username using parameterized input
        String sql = "SELECT u1.username, img_loc, users_profile.about_me, u2.username as friend " +
                     "FROM users_friends " +
                     "FULL JOIN users AS u1 ON users_friends.user_id = u1.user_id " +
                     "FULL JOIN users AS u2 ON users_friends.friend_id = u2.user_id " +
                     "FULL JOIN users_profile ON u1.user_id = users_profile.user_id " +
                     "FULL JOIN default_profile_img ON users_profile.pic_id = default_profile_img.pic_id " +
                     "WHERE u1.username = ?";

        // setting up jdbc call inside of a try block to catch any errors
        try{
            // send SQL command and return the results as a SQL Row Set
            SqlRowSet results = jdbcTemplate.queryForRowSet( sql, username );
            // while there are results....
            while(results.next()){
                // use helper method to map sql row to profile object and update the friends list
                queriedProfile = mapRowSetToUserProfile(results,friendsList);
            }
            // after going through the row set update the profile' friend list to updated list
            queriedProfile.setFriends(friendsList);

        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        }catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        }catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }catch (NullPointerException e){
            // catch any null pointer due to no results being found in database
            throw new RuntimeException("No results were found!",e);
        }

        return queriedProfile;
    }

    @Override
    public List<String> findProfilesByUsername(String username) {
        String parameter = "%" + username + "%";

        String sql ="SELECT DISTINCT u1.username " +
                "FROM users_friends " +
                "FULL JOIN users AS u1 ON users_friends.user_id = u1.user_id " +
                "FULL JOIN users AS u2 ON users_friends.friend_id = u2.user_id " +
                "FULL JOIN users_profile ON u1.user_id = users_profile.user_id " +
                "FULL JOIN default_profile_img ON users_profile.pic_id = default_profile_img.pic_id " +
                "WHERE u1.username ILIKE ? " +
                "ORDER BY u1.username;" ;

        List<String> queriedProfiles = new ArrayList<>();

        try{
            SqlRowSet result= jdbcTemplate.queryForRowSet(sql,parameter);
            while(result.next()){
                queriedProfiles.add(result.getString("username")) ;
            }

        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        }catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        }catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }catch (NullPointerException e){
            // catch any null pointer due to no results being found in database
            throw new RuntimeException("No results were found!",e);
        }


        return queriedProfiles;
    }

    /**
     * Method that maps a sql row set to a UserProfile object and updates
     * a friends list to be added to the user profile at th
     *
     * @param results sql row set returned by database
     * @param friendsList list of friends to be updated from database
     * @return UserProfile object.
     */
    private UserProfile mapRowSetToUserProfile( SqlRowSet results, List<String> friendsList){
        UserProfile mappedProfile = new UserProfile();
        mappedProfile.setUsername(results.getString("username"));
        mappedProfile.setProfilePic(results.getString("img_loc"));
        mappedProfile.setAboutMe(results.getString("about_me"));
        friendsList.add(results.getString("friend"));

        return mappedProfile;
    }


}
