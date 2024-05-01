package com.techelevator.dao;

import com.techelevator.model.Message;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcMessageDao implements MessageDao
{
    private final JdbcTemplate jdbcTemplate;

    public JdbcMessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Message sendNewMessage(Message messageToSend) {
        Message createdMessage = null;
        String sql = "INSERT INTO messages (message_sender_user_id, message_receiver_user_id, " +
                     "message_text, message_timestamp, message_read_status) " +
                     "VALUES ( (SELECT user_id FROM users WHERE username = ? ), " +
                     "(SELECT user_id FROM users WHERE username = ?), " +
                     "?, " +
                     "CURRENT_TIMESTAMP(0), " +
                     "false) " +
                     "RETURNING message_id;";
        try {
            String sender = messageToSend.getMessageSender();
            String receiver = messageToSend.getMessageReceiver();
            String messageBody = messageToSend.getMessageText();

            int messageID = jdbcTemplate.queryForObject(sql, int.class, sender, receiver, messageBody);

            // need a method to retrieve a single message from the database when given an ID #
            createdMessage = getMessageByID(messageID);

        }  catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        } catch (NullPointerException e){
            throw new RuntimeException("Null value encountered or produced",e);
        }

        return createdMessage;
    }

    @Override
    public Message getMessageByID (int messageID){

        Message messageToRetrieve = null;

        String sql = "SELECT message_id, user1.username AS sender, user2.username AS receiver, " +
                     "message_text, message_timestamp, message_read_status " +
                     "FROM messages " +
                     "JOIN users AS user1 ON messages.message_sender_user_id = user1.user_id " +
                     "JOIN users AS user2 ON messages.message_receiver_user_id = user2.user_id " +
                     "WHERE message_id = ?;";

        try {
            SqlRowSet message = jdbcTemplate.queryForRowSet(sql, messageID);

            if (message.next()) {
                messageToRetrieve = mapRowToMessage(message);
            }

        } catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        } catch (NullPointerException e){
            throw new RuntimeException("Null value encountered or produced", e);
        }

        return messageToRetrieve;
    }

    @Override
    public boolean updateReadStatus(int messageID) {
        String sql = "UPDATE messages SET message_read_status = true " +
                     "WHERE message_id = ?;";
        boolean readStatusChanged = false;
        try{
            int rowsUpdated = jdbcTemplate.update(sql, messageID);
            if(rowsUpdated == 1){
                readStatusChanged = true;
            }else{
                throw new RuntimeException("Unexpected number of rows updated! " + rowsUpdated + "row(s)");
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!" + e.getMessage(), e);
        }  catch (NullPointerException e){
            throw new RuntimeException("Null value encountered in mapping", e);
        }
        return readStatusChanged;
    }


    @Override
    public List<Message> retrieveMessages(String username) {

        List<Message> allMessages = new ArrayList<>();

        String sql = "SELECT message_id, user1.username AS sender, user2.username AS receiver, " +
                     "message_text, message_timestamp, message_read_status " +
                     "FROM messages " +
                     "JOIN users AS user1 ON messages.message_sender_user_id = user1.user_id " +
                     "JOIN users AS user2 ON messages.message_receiver_user_id = user2.user_id " +
                     "WHERE user1.username = ? OR user2.username = ? " +
                     "ORDER BY message_id;";

        try {
            SqlRowSet messages = jdbcTemplate.queryForRowSet(sql, username, username);
            while (messages.next()) {
                allMessages.add(mapRowToMessage(messages));
            }
        } catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }  catch (NullPointerException e){
            throw new RuntimeException("Null value encountered in mapping", e);
        }

        return allMessages;
    }



    @Override
    public boolean deleteMessage(int messageId) {

        int numberOfRows = 0;
        boolean deleteConfirmed = false;
        String sql = "DELETE FROM messages WHERE message_id = ?;";

        try {
            numberOfRows = jdbcTemplate.update(sql, messageId);
            if (numberOfRows == 1){
                deleteConfirmed = true;
            } else {
                throw new RuntimeException("Unexpected number of rows deleted: " + numberOfRows + " rows.");

            }

        } catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }

        return deleteConfirmed;

    }


    private Message mapRowToMessage(SqlRowSet results){
        Message message = new Message();
        message.setMessageID(results.getInt("message_id"));
        message.setMessageText(results.getString("message_text"));
        message.setMessageSender(results.getString("sender"));
        message.setMessageReceiver(results.getString("receiver"));
        message.setMessageTimestamp(results.getTimestamp("message_timestamp").toLocalDateTime());
        message.setRead(results.getBoolean("message_read_status"));

        return message;
    }
}
