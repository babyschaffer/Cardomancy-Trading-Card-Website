package com.techelevator.dao;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.techelevator.model.Message;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//This class runs the testing for the JDBC MessageDAO methods
public class JdbcMessageDaoTests extends BaseDaoTests {

    // sut => System Under Test
    private JdbcMessageDao sut;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcMessageDao(jdbcTemplate);
    }

    /**
     * Method that checks the creation of a new message
     */
    @Test
    public void send_message_returns_new_message_with_all_fields_completed(){
        // Test 1 of 2
        // create expected test message that to check returned message against
        Message expected = new Message();
        // setting various properties
        expected.setMessageText("Test Message #1");
        expected.setMessageSender("user1");
        expected.setMessageReceiver("user2");
        expected.setRead(false);
        // create message to feed into database
        Message messageToSend = new Message();
        // setting various properties
        messageToSend.setMessageText("Test Message #1");
        messageToSend.setMessageSender("user1");
        messageToSend.setMessageReceiver("user2");
        expected.setRead(false);
        // send message to the database and set returned object to actual Message
        Message actual = sut.sendNewMessage(messageToSend);
        // compare the two message object for equality
        assertEqualMessage(expected,actual);
        // Test 2 of 2
        // create expected test message that to check returned message against
        expected = new Message();
        // setting various properties
        expected.setMessageText("Test Message #2");
        expected.setMessageSender("user3");
        expected.setMessageReceiver("user2");
        // create message to feed into database
        messageToSend = new Message();
        // setting various properties
        messageToSend.setMessageText("Test Message #2");
        messageToSend.setMessageSender("user3");
        messageToSend.setMessageReceiver("user2");
        // send message to the database and set returned object to actual Message
        actual = sut.sendNewMessage(messageToSend);
        //compare the two message objects
        assertEqualMessage(expected,actual);
    }

    /**
     * Method that checks if the DAO retrieves the correct message
     * when provided an ID
     */
    @Test
    public void get_message_by_id_returns_expected_message(){
        // Test 1 of 2
        // create expected test message that to check returned message against
        Message expected = new Message();
        // setting various properties
        expected.setMessageText("Wow youre really cool!");
        expected.setMessageSender("user2");
        expected.setMessageReceiver("user1");
        expected.setRead(false);
        // retrieve actual message from the database
        Message actual = sut.getMessageByID(1);
        // compare the two message object for equality
        assertEqualMessage(expected,actual);
        // verify the message id matches
        Assert.assertEquals("Message id should be 1", 1, actual.getMessageID());
        //verify the timestamp matches
        Assert.assertEquals("Timestamp should match",LocalDateTime.parse("2023-10-04T12:23:34"),actual.getMessageTimestamp());
        // Test 2of 2
        // create expected test message that to check returned message against
        expected = new Message();
        // setting various properties
        expected.setMessageText("Can we trade cards? You have some interesting pieces!");
        expected.setMessageSender("user1");
        expected.setMessageReceiver("user2");
        expected.setRead(true);
        // retrieve actual message from the database
        actual = sut.getMessageByID(2);
        // compare the two message object for equality
        assertEqualMessage(expected,actual);
        // verify the message id matches
        Assert.assertEquals("Message id should be 2", 2, actual.getMessageID());
        //verify the timestamp matches 2023-09-10 04:35:31
        Assert.assertEquals("Timestamp should match",LocalDateTime.parse("2023-09-10T04:35:31"),actual.getMessageTimestamp());

    }

    /**
     * Method to check whether SUT updates read status
     */
    @Test
    public void update_read_status_changed_read_status(){
        // update the message read status using SUT
        sut.updateReadStatus(1);
        // message returned by SUT should have true read status
        Assert.assertTrue("Message read status should be set to true",sut.getMessageByID(1).isRead());
        // update the message read status using SUT
        sut.updateReadStatus(3);
        // message returned by SUT should have true read status
        Assert.assertTrue("Message read status should be set to true",sut.getMessageByID(3).isRead());
    }

    /**
     * Method to check whether SUT returns a list of message objects
     * associated with a given username
     *
     */
    @Test
    public void retrieve_messages_should_return_a_list_of_messages(){
        // retrieve a list using SUT
        List<Message> actual = sut.retrieveMessages("user1");
        // assert that the list size matches the expected results
        Assert.assertEquals("List size should be 3",3, actual.size());
        // spot check content
        Message messageToBeChecked = actual.get(0);
        Assert.assertEquals("Receiver should be user1", "user1",messageToBeChecked.getMessageReceiver() );
        // retrieve a list using SUT
        actual = sut.retrieveMessages("user3");
        // assert that the list size matches the expected results
        Assert.assertEquals("List size should be 1 ",1,actual.size());
        // spot check content
        messageToBeChecked = actual.get(0);
        Assert.assertEquals("Message body should be the same","Lets connect!", messageToBeChecked.getMessageText() );

    }

    /**
     * Method that tests whether SUT returns a valid response to
     * a deletion request
      */
    @Test
    public void delete_messages_should_delete_existing_messages(){
        // assert that the SUT returns true upon deletion
        Assert.assertTrue("Value returned should be true if successfully deleted",sut.deleteMessage(1));
        // assert that the SUT returns true upon deletion
        Assert.assertTrue("Value returned should be true if successfully deleted",sut.deleteMessage(2));
    }




    /**
     * Helper method that compares two message objects
     *
     * @param expected message that is given to SUT
     * @param actual message that is returned by SUT
     */
    private void assertEqualMessage(Message expected, Message actual){
        Assert.assertTrue("Message ID should be provided by the database", actual.getMessageID()>0);
        Assert.assertEquals("Message text bodies should be the same", expected.getMessageText(), actual.getMessageText());
        Assert.assertEquals("Message sender names should be the same", expected.getMessageSender(), actual.getMessageSender());
        Assert.assertEquals("Message receiver names should be the same",expected.getMessageReceiver(),actual.getMessageReceiver());
        Assert.assertNotNull("Timestamp should not be null",actual.getMessageTimestamp());
        Assert.assertEquals("Message read status should match",expected.isRead(),actual.isRead());

    }












}
