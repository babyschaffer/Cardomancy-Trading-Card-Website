BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, tcg, cards, collections, collections_user, collections_cards, default_profile_img, users_profile, users_friends, messages CASCADE;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,	
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE tcg(
	tcg_id SERIAL,
	tcg_name varchar(50) NOT NULL UNIQUE,
	CONSTRAINT PK_tcg PRIMARY KEY (tcg_id)
);

CREATE TABLE cards(
	card_id varchar(128) NOT NULL UNIQUE, 
	tcg_id integer NOT NULL,
	card_title varchar(256) NOT NULL,
	card_small_image_url varchar(256),
	card_normal_image_url varchar(256),
	card_reverse_image_url varchar(256),
	card_reverse_small_image_url varchar(256),
	card_colors varchar(10),
	card_color_identity varchar(256) NOT NULL,
	card_set_code varchar(256) NOT NULL,
	card_set_name varchar(256) NOT NULL,
	card_details_url varchar(256),
	card_collector_number varchar(256) NOT NULL,
	card_legalities varchar(256) NOT NULL,
	card_layout varchar(256),
	card_cmc varchar(256),
	card_edhrec_rank varchar(256),
	CONSTRAINT PK_cards PRIMARY KEY(card_id),
	CONSTRAINT FK_cards_tcg FOREIGN KEY (tcg_id) REFERENCES tcg (tcg_id)
);

CREATE TABLE collections(
	collection_id SERIAL,
	collection_name varchar(50) NOT NULL,
	tcg_id integer NOT NULL,
	CONSTRAINT PK_collections PRIMARY KEY(collection_id),
	CONSTRAINT FK_collections_tcg FOREIGN KEY (tcg_id) REFERENCES tcg (tcg_id)
);

CREATE TABLE collections_user(
	collection_id integer NOT NULL,
	user_id integer NOT NULL,
	CONSTRAINT PK_collections_user PRIMARY KEY (collection_id, user_id),
	CONSTRAINT FK_collections_user_cID FOREIGN KEY (collection_id) REFERENCES collections (collection_id),
	CONSTRAINT FK_collections_user_uID FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE collections_cards(
	collection_id integer NOT NULL,
	card_id varchar(128) NOT NULL,
	quantity integer CHECK (quantity > -1),
	CONSTRAINT PK_collections_cards PRIMARY KEY (collection_id, card_id),
	CONSTRAINT FK_collections_cards_cID FOREIGN KEY (collection_id) REFERENCES collections (collection_id),
	CONSTRAINT FK_collections_cards_cardID FOREIGN KEY (card_id) REFERENCES cards (card_id)
);

CREATE TABLE default_profile_img(
	pic_id serial NOT NULL,
	img_loc varchar(256) NOT NULL,
	CONSTRAINT PK_profile_pic PRIMARY KEY (pic_id)	
);

CREATE TABLE users_profile(
	user_id int UNIQUE NOT NULL ,
	pic_id int NOT NULL,
	about_me varchar(1000),
	CONSTRAINT PK_users_profile PRIMARY KEY(user_id),
	CONSTRAINT FK_users_profile_user FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_users_profile_pic FOREIGN KEY (pic_id) REFERENCES default_profile_img (pic_id)
);

CREATE TABLE users_friends(
	user_id int NOT NULL,
	friend_id int NOT NULL,
	CONSTRAINT PK_users_friends PRIMARY KEY (user_id, friend_id),
	CONSTRAINT FK_users_friends_users FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_users_friends_users2 FOREIGN KEY (friend_id) REFERENCES users(user_id)
);

CREATE TABLE messages (
    message_id SERIAL,
    message_text varchar(250) NOT NULL,
    message_sender_user_id int NOT NULL,
    message_receiver_user_id int NOT NULL,
    message_timestamp TIMESTAMP NOT NULL,
    message_read_status boolean,
    CONSTRAINT PK_messages_user_message PRIMARY KEY (message_id),
    CONSTRAINT FK_messages_user_sender FOREIGN KEY (message_sender_user_id) REFERENCES users(user_id),
    CONSTRAINT FK_messages_user_receiver FOREIGN KEY (message_receiver_user_id) REFERENCES users(user_id)
);

COMMIT TRANSACTION;
