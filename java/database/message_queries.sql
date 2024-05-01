

--- CREATE A MESSAGE

INSERT INTO messages (message_sender_user_id, message_receiver_user_id, message_text, message_timestamp, message_read_status)
VALUES ( (SELECT user_id FROM users WHERE username = 'admin'),
	   (SELECT user_id FROM users WHERE username = 'fireman'),
	   'Hi fireman, this is just a test message from Admin',
	   CURRENT_TIMESTAMP(0),
	   false)
RETURNING message_id;

--- SELECT ALL MESSAGES BASE ON A USER

SELECT message_id, u1.username AS sender, u2.username AS receiver, message_text, message_timestamp, message_read_status FROM messages 
	JOIN users AS u1 ON messages.message_sender_user_id = u1.user_id
	JOIN users AS u2 ON messages.message_receiver_user_id = u2.user_id
WHERE sender = 'admin' OR receiver = 'admin'
ORDER BY message_timestamp DESC;


--- DELETE A MESSAGE BASED ON MESSAGE ID

DELETE * FROM messages
WHERE message_id = 1;

