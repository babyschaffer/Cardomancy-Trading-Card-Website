package com.techelevator.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserFriendDao implements UserFriendDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserFriendDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public boolean isFriended(int userId, int friendId){
        String sql = "SELECT friend_id " +
                "FROM users_friends " +
                "WHERE user_id = ? " +
                "AND friend_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, friendId);
            return results.next();
        }catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database!", e);
        }catch (BadSqlGrammarException e) {
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Database Integrity Violation!", e);
        }catch (NullPointerException e){
            throw new RuntimeException("No results were found!",e);
        }
    }

    @Override
    public int friendUser(int userId, int friendId){
        String sql = "INSERT INTO users_friends (user_id, friend_id) VALUES " +
                "(?, ?);";
        try{
            int rowsCreated = jdbcTemplate.update(sql, userId, friendId);
            if (rowsCreated < 1)
                throw new RuntimeException("Failed to manipulate the database!");
            return rowsCreated;
        }catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database!", e);
        }catch (BadSqlGrammarException e) {
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Database Integrity Violation!", e);
        }catch (NullPointerException e){
            throw new RuntimeException("No results were found!",e);
        }
    }

    @Override
    public int unfriendUser (int userId, int friendId){
        String sql = "DELETE FROM users_friends " +
                "WHERE user_id = ? " +
                "AND friend_id = ?;";
        try{
            int rowsDeleted = jdbcTemplate.update(sql, userId, friendId);
            if (rowsDeleted < 1)
                throw new RuntimeException("Failed to manipulate the database!");
            return rowsDeleted;
        }catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to the database!", e);
        }catch (BadSqlGrammarException e) {
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Database Integrity Violation!", e);
        }catch (NullPointerException e){
            throw new RuntimeException("No results were found!",e);
        }
    }

    @Override
    public boolean isBothFriends(int user1Id, int user2Id){
        return isFriended(user1Id, user2Id) && isFriended(user2Id, user1Id);
    }
}
