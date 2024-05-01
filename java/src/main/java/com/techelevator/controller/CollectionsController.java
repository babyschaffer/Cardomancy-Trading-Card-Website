package com.techelevator.controller;

import com.techelevator.dao.CollectionsDao;
import com.techelevator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin
public class CollectionsController {

    @Autowired
    CollectionsDao cdao;
    private User userForCollectionId;

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/allCollections", method = RequestMethod.GET)
    public List<CollectionsDto> getAllCollections() {
        return cdao.getAllCollections();
    }

    @RequestMapping(path = "/myCollections", method = RequestMethod.GET)
    public List<Collection> getUserCollections(Principal principal) {
        return cdao.getAllUserCollections(principal.getName());
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path="/userCollections/{username}", method = RequestMethod.GET)
    public List<Collection> getCollectionsByUsername(@PathVariable String username){
        return cdao.getAllUserCollections(username);
    }

//    @PreAuthorize("permitAll")
//    @RequestMapping(path = "/allCollections/{tcgId}", method = RequestMethod.GET)
//    public List<Collection> getCollectionsByTCG(@PathVariable int tcgId) {
//        return cdao.getCollectionsByTCG(tcgId);
//    }

//    @RequestMapping(path = "/myCollections/{tcgId}", method = RequestMethod.GET)
//    public List<Collection> getUserCollectionsByTCG(Principal principal, @PathVariable int tcgId) {
//        return cdao.getUserCollectionsByTCG(principal.getName(), tcgId);
//    }

    @RequestMapping(path = "/myCollections/{collectionId}/add", method = RequestMethod.POST)
    public int addCardToCollection(@Valid @RequestBody Card card, @PathVariable int collectionId) {
        return cdao.addCardToCollection(card, collectionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/myCollections/add", method = RequestMethod.POST)
    public int createCollection(@Valid @RequestBody Collection collection, Principal principal) {
        return cdao.addCollection(collection, principal.getName());
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/collections/{collectionId}/cards", method = RequestMethod.GET)
    public List<CardDto> getCardsByCollection(@PathVariable int collectionId) {
        return cdao.getCardsByCollectionId(collectionId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/myCollections/{collectionId}", method = RequestMethod.DELETE)
    public int removeCollection(@PathVariable int collectionId) {
        return cdao.removeCollection(collectionId);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/collections/{collectionId}", method = RequestMethod.GET)
    public Collection getCollectionById(@PathVariable int collectionId) {
        return cdao.getCollectionById(collectionId);
    }


    @PreAuthorize("permitAll")
    @GetMapping(path = "/collections/{collectionId}/user")
    public User getUserByCollection(@PathVariable int collectionId) {
        return cdao.getUserForCollectionId(collectionId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/collections/{collectionId}/{cardId}/delete", method = RequestMethod.DELETE)
    public void deleteCardFromCollection(@PathVariable String cardId, @PathVariable int collectionId) {
        cdao.deleteCardFromCollection(cardId, collectionId);
    }
    @PreAuthorize("permitAll")
    @GetMapping(path = "/collections/allCollections/{collectionId}/stats")
    public int getCountOfCardsInCollection(@PathVariable int collectionId) {
        return cdao.getCountOfCardsInCollection(collectionId);
    }
    @RequestMapping(path = "/collections/{collectionId}/update", method = RequestMethod.PUT)
    public Collection updateCollection(@PathVariable int collectionId, @Valid @RequestBody Collection collection) {
        return cdao.updateCollection(collectionId, collection);
    }
}


