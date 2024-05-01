package com.techelevator.dao;

import com.techelevator.model.Message;
import java.util.List;

public interface MessageDao {
    //CREATE messages

    /**
     * Method that allows for creation of a message
     *
     * @param messageToSend Message object without an id to submit
     * @return a complete message object
     */
    Message sendNewMessage (Message messageToSend);

    //READ messages

    /**
     * Method that retrieves a list of messages associated
     * with a specific username
     *
     * @param username username to search for
     * @return a list of message objects
     */
    List<Message> retrieveMessages(String username);

    //DELETE Messages

    /**
     * Method to delete a message
     *
     * @param messageId id of message to read
     * @return true if successful or false otherwise
     */
    boolean deleteMessage(int messageId);

    /**
     * Method to retrieve a single message based on a
     * provided ID
     *
     * @param messageID ID of message to retrieve
     * @return message object queried by ID
     */
    Message getMessageByID (int messageID);

    //UPDATE Message

    /**
     * Method that updates the read status of a
     * message
     *
     * @param messageID message ID to update the read status of
     * @return boolean that returns true if a successful update was made
     */

    boolean updateReadStatus (int messageID);

}
