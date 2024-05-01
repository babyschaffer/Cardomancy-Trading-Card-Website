package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CollectionsDao {

    /**
     * Retrieve all collections regardless of trading
     * card game
     *
     * @return list of Collections{id,name,tcg}
     */
    List<CollectionsDto> getAllCollections();


    /**
     * Retrieve all collections associated with a specific
     * trading card game
     *
     * @param tcgId parameter that specifies the Trading Card Game
     * @return list of Collections{id,name,tcg} filtered by tcgId
     */
    List<Collection> getCollectionsByTCG(int tcgId);

    /**
     * Retrieve specific collections associated with a username
     *
     * @param username
     * @return list of Collections associated with the username
     */
    List<Collection> getAllUserCollections(String username);



    /**
     * Retrieve all collections associated with a
     * specific username for specified trading card game
     * id
     *
     * @param username
     * @param tcgId parameter that specifies the Trading Card Game
     * @return list of collections associated with the username filtered by the tcgId
     */
    List<Collection> getUserCollectionsByTCG(String username, int tcgId);

    /*
    returns a list of card objects by collectionId
     */

    /**
     * Retrieves all cards associated with a specified
     * collection id
     *
     * @param collectionId
     * @return list of cards
     */
    List<CardDto> getCardsByCollectionId(int collectionId);


    /**
     *Method that adds a collection to a specific username
     *
     * @param collection collection object to be added
     * @param username collection owner
     * @return ID of the newly created collection
     */


    int addCollection(Collection collection, String username);

    /**
     * Method to remove a collection using a
     * specified collection ID
     *
     * @param collectionId
     * @return boolean status of successful removal
     */

    //toDO change to boolean returns
    int removeCollection(int collectionId);

    /**
     * Method to add a card object to a collection
     * specified by a collection id
     *
     * @param card card object to be added
     * @param collectionId collection identifier
     * @return boolean status of successful insertion
     */

    //toDO change to boolean returns
    int addCardToCollection(Card card, int collectionId);

    Collection getCollectionById(int collectionId);

    User getUserForCollectionId(int collectionID);

    void deleteCardFromCollection(String cardId, int collectionId);

    int getCountOfCardsInCollection(int collectionId);

    Collection updateCollection(int collectionId, Collection collection);


}
