package com.techelevator.dao;

import com.techelevator.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JdbcCollectionsDaoTest extends BaseDaoTests{

    private final Collection TEST_COLLECTION1 = new Collection(1,"test4",1);
    private final Collection TEST_COLLECTION2 = new Collection(2,"test5",1);
    private final Map<String,String> TEST_MAP = new HashMap<>();
    private final List<String> TEST_LIST = new ArrayList<>();

    private final Card TEST_CARD3 = new Card("7", 1, "Nut Collector","Test","Test","Test",TEST_LIST,"Test","Test:Test,Test:Test","Test",TEST_MAP,TEST_LIST,"Test","Test","Test",1.5,1);
    private final Card TEST_CARD2 = new Card("14", 1, "Helm of the Host","Test","Test","Test",TEST_LIST,"Test","Test:Test,Test:Test","Test",TEST_MAP,TEST_LIST,"Test","Test","Test",1.5,1);
    private final Card TEST_CARD = new Card("15", 1, "Sword of Hearth and Home","Test","Test","Test",TEST_LIST,"Test","Test:Test,Test:Test","Test",TEST_MAP,TEST_LIST,"Test","Test","Test",1.5,1);
    CollectionsDao cdao;
    UserDao udao;
    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        cdao = new JdbcCollectionsDao(jdbcTemplate);
        udao = new JdbcUserDao(jdbcTemplate);
        //dummy card data
        TEST_MAP.put("test","test");
        TEST_MAP.put("test2","test");
        TEST_LIST.add("Test");
        TEST_LIST.add("Test2");

    }

    @Test
    public void getAllCollections() {
        List<CollectionsDto> actual = cdao.getAllCollections();
        assertEquals("List should contain all Collections.",3,actual.size());
        assertFalse("List size should not be less than 3.",actual.size()==2);
    }

    @Test
    public void getAllUserCollections() {
        ///tests only user-specific collections returned
        List<Collection> actual = cdao.getAllUserCollections("user1");
        assertEquals("List size should not be greater than 1.",1,actual.size());
        List<Collection> actual2 = cdao.getAllUserCollections("user2");
        assertEquals("List size should not be greater than 1.",1,actual.size());
    }

    @Test
    public void getCardsByCollectionId() {
        List<CardDto> actual = cdao.getCardsByCollectionId(100);
        assertEquals("List size should return 5.",5,actual.size());
    }

    @Test
    public void addCollection() {
        //returns collection id of created collection
       int expectedId =  cdao.addCollection(TEST_COLLECTION1,"user1");
       //returns newly created collection
        Collection expected =cdao.getCollectionById(expectedId);
        //checks that the collections match
       assertCollectionsMatch(expected,TEST_COLLECTION1);
    }

    @Test
    public void removeCollection() {
        //returns users collections
        List<Collection> actual1 = cdao.getAllUserCollections("user1");
        //removes collection
        cdao.removeCollection(101);
        //returns the affected users collections
        List<Collection> actual2 = cdao.getAllUserCollections("user1");

        assertNotEquals("Amount of user-collections should not remain the same.",actual1.size(),actual2.size());
    }

    @Test
    public void addCardToCollection() {
        //num of inserted cards
        int actual = cdao.addCardToCollection(TEST_CARD,100);
        assertEquals(1,actual);
        //list of cards from the parent collection
        List<CardDto> actual2 = cdao.getCardsByCollectionId(100);
        //assert that the length of the list has changed
        assertNotEquals(5,actual2.size());
        //test adding multiple cards
        actual += cdao.addCardToCollection(TEST_CARD2,100);
        actual += cdao.addCardToCollection(TEST_CARD3,100);
        //reset the parent list length
        actual2 = cdao.getCardsByCollectionId(100);
        //assert the added cards counter has increased
        assertEquals(3,actual);
        //assert that the length of the parent list has incremented
        assertEquals(8,actual2.size());
    }

    @Test
    public void deleteCardFromCollection() {
        //adds test card to collection
        cdao.addCardToCollection(TEST_CARD,100);
        //gets card list from associated collection
        List<CardDto> firstList = cdao.getCardsByCollectionId(100);
        //removes the previously added card
        cdao.deleteCardFromCollection(TEST_CARD.getId(),100);
        //resets card list from associated collection
        List<CardDto> actualList = cdao.getCardsByCollectionId(100);
        //asserts that the size of the list before and after deletion has changed
        assertNotEquals("A card was added, but not deleted.",6,actualList.size());
        assertNotEquals(firstList.size(),actualList.size());
        assertNotEquals("A card was deleted, but not added",4,actualList.size());
        assertEquals(5,actualList.size());
    }

    @Test
    public void getCollectionById() {
        //creates the expected resultant collection
        Collection expected = new Collection(100,"test1",1);
        //returns the collection from the matching id
        Collection actual =cdao.getCollectionById(100);
        //asserts the expected collection and the returned collection match
        assertCollectionsMatch(expected,actual);
        //adds a collection and returns it's id
        int id = cdao.addCollection(TEST_COLLECTION2,"user1");
        //sets the checked collection to the new test properties
        expected = new Collection(1,"test5",1);
        //gets the newly created collection
        actual = cdao.getCollectionById(id);
        //asserts the data collection matches the pulled collection
        assertCollectionsMatch(expected,actual);
    }

    @Test
    public void getUserForCollectionId() {
        //models the expected returned user
        User expected = new User(1, "user1", "user1", "ROLE_USER");
        //grabs the user obj associated with the collection
        User acutal = cdao.getUserForCollectionId(101);
        //asserts user match
        assertEquals(expected.getUsername(),acutal.getUsername());
        assertEquals(expected.getPassword(),acutal.getPassword());
        assertEquals(expected.getId(),acutal.getId());
        assertEquals(expected.getAuthorities(),acutal.getAuthorities());
    }

    @Test
    public void getCountOfCardsInCollection() {
        int actual = cdao.getCountOfCardsInCollection(100);
        assertEquals(5,actual);
        actual = cdao.getCountOfCardsInCollection(101);
        assertEquals(5,actual);
        actual = cdao.getCountOfCardsInCollection(102);
        assertEquals(5,actual);
    }

    @Test
    public void updateCollection() {
        Collection expected = new Collection(100,"NAMETEST",1);
        Collection actual = cdao.updateCollection(100,expected);
        assertCollectionsMatch(expected,actual);
    }

    private void assertCollectionsMatch(Collection expected, Collection actual){
        assertEquals( "Ids should match.",expected.getId(), actual.getId());
        assertEquals("TcgIds should match.", expected.getTcgId(), actual.getTcgId());
        assertEquals("Names should match.", expected.getName(), actual.getName());
    }
}