package com.techelevator.dao;

import com.techelevator.model.Card;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

//This interface will define the methods needed for any class to be identified as a Card data access object
public interface CardDao {


    /**
     * Method to query a card object using a card's ID
     * as a search parameter
     *
     * @param cardID card ID to search by
     * @return Card object that exactly matches the search parameter
     */
    Card getCardById(String cardID);


    //ToDO remove this method in subsequent sprint
//    /**
//     * Method to query a card object using a card's
//     * title as a search parameter
//     *
//     * @param cardTitle card title to search by
//     * @return Card object that exactly matches the search parameter
//     */
//    Card getCardByTitle(String cardTitle);


    /**
     * Method to query a list of cards using a card
     * title as a search parameter.
     * @param cardTitle card title to search by
     * @param isExactMatch boolean determining if an exact match is needed
     * @return List of card objects that may match the search parameter
     */
    List<Card> getCardsByTitle(String cardTitle, boolean isExactMatch);


    /**
     * Method to get cards by color/color identity
     * Search using preset option of Strings (Red, Black, White, Green, Blue)
     * Must be an exact match as options will not be user entered.
     * Stored in color object in API using Upper Case single letter abbreviations.
     * W - White | U - Blue | B - Black | R - Red | G - Green
     */

    List<Card> getCardsByColor(String color);


    List<Card> getCardsByColorIdentity(String cardColorIdentity);

    /**
     * Methods to get cards by set id and code. Set name can be displayed but does not need to be searchable.
     * Codes are three letter abbreviations of set names
     * Must be an exact match as options will be (possibly) searchable but preset.
     */

    List<Card> getCardsBySetId(String id);

    List<Card> getCardsBySetCode(String setCode);


    /**
     * Method to add a new card to source.
     *
     * @param card card object to be added
     * @return returns the card object directly queried from the source
     */
    Card addCard(Card card);

    /** Method to view all cards in database. Will be limited by filtered list on front end
     *
     * @return returns all cards
     */

    List<Card> getCards();

    /**
     * Method that maps sql results to card object
     *
     * @param results SQL row set returned by database
     * @return Card object
     */
    Card mapResultsToCard(SqlRowSet results);



}
