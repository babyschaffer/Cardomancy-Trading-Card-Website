package com.techelevator.controller;


import com.techelevator.dao.CardDao;
import com.techelevator.model.Card;
import com.techelevator.model.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class CardController{

    private final CardDao cardDao;


    //Constructor needed for Rest Controller
    public CardController( CardDao cardDao){
        this.cardDao = cardDao;
    }

    @GetMapping(path= "/cards/search/id")
    public Card getCardByID (String searchID){
       try{
           return cardDao.getCardById(searchID);
       }catch (RuntimeException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to locate card!");
       }
    }

    @GetMapping(path= "/cards/search/title")
    public List<Card> getMultipleCardsByTitle (String searchTerm, @RequestParam (defaultValue= "false")  boolean isExactMatch ){
        try {
            return cardDao.getCardsByTitle(searchTerm, isExactMatch);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to locate any cards that match!");
        }
    }
    @GetMapping(path= "/search-cards")
    public List<Card> getAllCards() {
        try {
            return cardDao.getCards();
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to load cards!");
        }
    }

    @PreAuthorize("permitAll")
    @GetMapping(path= "/search-cards/free")
    public List<Card> getAllCardsNoLogin() {
        try {
            return cardDao.getCards();
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to load cards!");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/collections/:id/add", method = RequestMethod.POST)
    public Card addCardToCollection (Card card){
        return cardDao.addCard(card);
    }



}
