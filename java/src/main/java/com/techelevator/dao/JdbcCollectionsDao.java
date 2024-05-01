package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcCollectionsDao implements CollectionsDao{


    private UserDao userDao;
    private JdbcTemplate jdbcTemplate;
    private CardDao cardDao;

    public JdbcCollectionsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userDao = new JdbcUserDao(jdbcTemplate);
        cardDao = new JdbcCardDao(jdbcTemplate);
    }

    /**
     *
     * @return A list of all collections in the database
     */

    @Override
    public List<CollectionsDto> getAllCollections() {
        //new list
        List<CollectionsDto> collectionList = new ArrayList<>();
        Collection collection = new Collection();
        CollectionsDto dto = new CollectionsDto();
        //grab all collections from database
        String sql = "select * from collections;";
        String userSql = "select username from users join collections_user on users.user_id = collections_user.user_id where collection_id = ?;";
        String quansql = "select sum(quantity) as quantity from collections_cards where collection_id = ?;";
        try{
            SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql);
            while(rowset.next()){
                //if no database issues, map the rowset properties to a collection obj and add the obj to the list
                collection = (mapRowToCollection(rowset));
                String username = jdbcTemplate.queryForObject(userSql, String.class, collection.getId());
                // set initial quantity to zero in case there are no cards in the collection after query is performed
                int quantity=0;
                // if the query does not return null...
                if(jdbcTemplate.queryForObject(quansql,int.class,collection.getId())!=null){
                    // update the quantity with the returned sum
                    quantity = jdbcTemplate.queryForObject(quansql,int.class,collection.getId());
                }
                dto = mapRowToCollectionsDto(collection,username,quantity);
                collectionList.add(dto);
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //return the whole collections data base
        return collectionList;
    }

    @Override
    public List<Collection> getCollectionsByTCG(int tcgId) {
        //new list
        List<Collection> collectionList = new ArrayList<>();
        //collections who carry the specified game id
        String sql = "select * from collections where tcg_id = ?;";
        try{
            //no issues, maps the rowset to a collection
            SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql,tcgId);
            while(rowset.next()){
                //adds collection to list
                collectionList.add(mapRowToCollection(rowset));
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //list of collections who belong to specified game
        return collectionList;
    }

    @Override
    public List<Collection> getAllUserCollections(String username) {
        //new list
        List<Collection> collectionList = new ArrayList<>();
        //all collections associated with a specified user
        String sql = "select * from collections join collections_user on " +
                "collections.collection_id = collections_user.collection_id where user_id = ?;";
        try{
            //user id grabbed
          int userId = userDao.findIdByUsername(username);
          SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
           while(rowSet.next()){
               //maps rowset to collection and adds to list
               collectionList.add(mapRowToCollection(rowSet));
           }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //returns list of user collections
        return collectionList;
    }


    @Override
    public List<Collection> getUserCollectionsByTCG(String username, int tcgId) {
        //new list
        List<Collection> collectionList = new ArrayList<>();
        //List of user collections for a specific game
        String sql = "select * from collections join collections_user on " +
                "collections.collection_id = collections_user.collection_id where user_id = ? and tcg_id = ?;";
        try{
            //user id grabbed
            int userId = userDao.findIdByUsername(username);
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId, tcgId);
            while(rowSet.next()){
                //maps rowset to collection and adds to list
                collectionList.add(mapRowToCollection(rowSet));
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //returns list of user collections filtered by a specific game
        return collectionList;
    }


    @Override
    public List<CardDto> getCardsByCollectionId(int collectionId) {
        //new card and carddto
        Card card;
        CardDto cardDto;
        //new list
        List<CardDto> cards = new ArrayList<>();
        //grabs the card id and quantity properties
        String sql = "select card_id, quantity from collections_cards where collection_id = ?;";
        try{
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,collectionId);
            while(rowSet.next()){
                //initializes
                card = new Card();
                cardDto = new CardDto();
                //sets two requested fields as usable data types
                String id = rowSet.getString("card_id");
                int qty = rowSet.getInt("quantity");
                //grabs one card obj
                String cardsql = "select * from cards where card_id = ?;";
                SqlRowSet result = jdbcTemplate.queryForRowSet(cardsql,id);
                if(result.next()){
                    //maps returned sqlrowset to a card obj
                    card = cardDao.mapResultsToCard(result);
                }
                //sets all fields of card to the cardDto, adding quantity
                cardDto.setQty(qty);
                cardDto.setCmc(card.getCmc());
                cardDto.setCollectorNumber(card.getCollectorNumber());
                cardDto.setColorIdentity(card.getColorIdentity());
                cardDto.setColors(card.getColors());
                cardDto.setEdhrecRank(card.getEdhrecRank());
                cardDto.setId(id);
                cardDto.setImageUrl(card.getImageUrl());
                cardDto.setLayout(card.getLayout());
                cardDto.setLegalities(card.getLegalities());
                cardDto.setName(card.getName());
                cardDto.setReverseImgUrl(card.getReverseImgUrl());
                cardDto.setScryfallUrl(card.getScryfallUrl());
                cardDto.setSetCode(card.getSetCode());
                cardDto.setSetName(card.getSetName());
                cardDto.setSmallImgUrl(card.getSmallImgUrl());
                cardDto.setSmallReverseImgUrl(card.getSmallReverseImgUrl());
                cardDto.setTcgId(card.getTcgId());
                //adds cardDto to list
                cards.add(cardDto);
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //returns list of cardDto's
        return cards;
    }

    @Override
    public int addCollection(Collection collection, String username) {
        String sql = "insert into collections (collection_name, tcg_id) values (?, ?) returning collection_id;";
        String connectingSql = "insert into collections_user (collection_id, user_id) values (?,?);";
        //sql to add a collection and connect it to a user
        int newCollectionId;
        try{
            //grabs user id
            int userId = userDao.findIdByUsername(username);
            //newly inserted
            newCollectionId = jdbcTemplate.queryForObject(sql, int.class,collection.getName(),collection.getTcgId());
            int check = jdbcTemplate.update(connectingSql,newCollectionId,userId);
            if(check != 1){
                throw new RuntimeException("Failed to manipulate the database!");
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        return newCollectionId;
    }

    @Override
    public int removeCollection(int collectionId) {
        //cascading delete
        String CCsql = "delete from collections_cards where collection_id = ?;";
        String CUsql = "delete from collections_user where collection_id = ?;";
        String Csql = "delete from collections where collection_id = ?;";
        int check =-1;
        try{
            //remove cards
            check = jdbcTemplate.update(CCsql,collectionId);
            if(check == -1){
                throw new RuntimeException("Failed to manipulate the database!");
            }else{
                //remove user association
                check = jdbcTemplate.update(CUsql,collectionId);
                if(check ==0){
                    throw new RuntimeException("Failed to manipulate the database!");
                }else{
                    //remove collection
                    check = jdbcTemplate.update(Csql,collectionId);
                    if(check != 1){
                        throw new RuntimeException("Failed to manipulate the database!");
                    }
                }
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //returns final num of collections deleted-should always be one.
        return check;
    }

    @Override
    public int addCardToCollection(Card card, int collectionId) {
        //manual initial card set
        int qty =1;
        String sql = "insert into collections_cards (collection_id, card_id, quantity) values(?,?,?);";
        String update = "update collections_cards set quantity = ? where card_id =? and collection_id =?";
        String quansql = "select quantity from collections_cards where card_id =? and collection_id =?";
        int check = -1;
        try{
            //set quantity to quantity +1
            check = jdbcTemplate.update(sql,collectionId,card.getId(),qty);
            if(check == -1){
                throw new RuntimeException("Failed to manipulate the database!");
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            //increments the amount of cards
            qty = jdbcTemplate.queryForObject(quansql,int.class,card.getId(),collectionId);
            qty++;
            jdbcTemplate.update(update,qty,card.getId(),collectionId);
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //returns final num of collections added -should always be one.
        return check;
    }


        public void deleteCardFromCollection(String cardId, int collectionId){
        //removes one card from the connected database
            String sql ="delete from collections_cards where card_id = ? and collection_id =? ;";
            try{
                //deletes
                int check = jdbcTemplate.update(sql, cardId,collectionId);
                if(check!=1){
                    throw new RuntimeException("Failed to manipulate the database!");
                }
            }catch (CannotGetJdbcConnectionException e) {
                // catch any database connection errors and throw a new error to be caught at next level
                throw new RuntimeException("Unable to connect to the database!", e);
            } catch (BadSqlGrammarException e) {
                // catch any SQL command errors and throw a new error to be caught at next level
                throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
            } catch (DataIntegrityViolationException e) {
                // catch any database connection errors and throw a new error to be caught at next level
                throw new RuntimeException("Database Integrity Violation!", e);
            }
        }

        @Override
        public Collection getCollectionById(int collectionId) {
        //new collection
        Collection collection = new Collection();
        //grab one collection
        String sql ="select * from collections where collection_id = ?";
        try{
            //
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,collectionId);
            if(result.next()){
                //maps the rowset to a collection
                collection = mapRowToCollection(result);
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }//returns created collection
        return collection;
    }
    public User getUserForCollectionId(int collectionID){
        //initializes a new user obj
        User user = new User();
        //returns user associated with the collection
        String sql = "select user_id from collections_user where collection_id = ?;";
        try{
            //gets user
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,collectionID);
            if(result.next()){
                //gets user id
                int userId = result.getInt("user_id");
                //returns user associated with user_id
                user = userDao.getUserById(userId);
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }//returns users
        return user;
    }

    public int getCountOfCardsInCollection(int collectionId) {
        int count = 0;
        //adds the quantity from each card together and returns that
        String sql = "Select Count (Distinct card_id) AS card_count From collections_cards Where collection_id = ?;";

        try{
            //returns count and casts to int
            count = jdbcTemplate.queryForObject(sql, int.class, collectionId);

        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        //returns quantity of cards
        return  count;
    }

    /////updates collection name//////
    public Collection updateCollection(int collectionId, Collection collection) {
//        Collection result = new Collection();
        String sql = "UPDATE collections SET collection_name = ? WHERE collection_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, collection.getName(), collectionId);

            if (rowsAffected != 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }catch (CannotGetJdbcConnectionException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Unable to connect to the database!", e);
        } catch (BadSqlGrammarException e) {
            // catch any SQL command errors and throw a new error to be caught at next level
            throw new RuntimeException("Bad SQL grammar: " + e.getSql() + "\n" + e.getSQLException(), e);
        } catch (DataIntegrityViolationException e) {
            // catch any database connection errors and throw a new error to be caught at next level
            throw new RuntimeException("Database Integrity Violation!", e);
        }
        return collection;
    }


    private Collection mapRowToCollection(SqlRowSet set){
        Collection collection = new Collection();
        collection.setId(set.getInt("collection_id"));
        collection.setName(set.getString("collection_name"));
        collection.setTcgId(set.getInt("tcg_id"));
        return collection;
    }

    private CollectionsDto mapRowToCollectionsDto(Collection col, String username, int quantity){
        CollectionsDto collection = new CollectionsDto();
        collection.setCollectionId(col.getId());
        collection.setCollectionName(col.getName());
        collection.setTcgId(col.getTcgId());
        collection.setUsername(username);
        collection.setQuantity(quantity);
        return collection;
    }

}
