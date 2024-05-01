package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.dao.UserFriendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RestController
public class UserFriendController {

    @Autowired
    UserFriendDao userFriendDao;
    @Autowired
    UserDao userDao;

    @RequestMapping(path = "/addFriend/{username}", method = RequestMethod.POST)
    public int addFriend(@PathVariable String username, Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        int friendId = userDao.findIdByUsername(username);
        return userFriendDao.isFriended(userId, friendId)?
                -1:
                userFriendDao.friendUser(userId, friendId);
    }

    @RequestMapping(path = "/unFriend/{username}", method = RequestMethod.DELETE)
    public int deleteFriend(@PathVariable String username, Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        int friendId = userDao.findIdByUsername(username);
        return userFriendDao.isFriended(userId, friendId)?
                userFriendDao.unfriendUser(userId, friendId):
                -1;
    }


    @RequestMapping(path = "/isFriend/{username}", method = RequestMethod.GET)
    public boolean isFriend(@PathVariable String username, Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        int friendId = userDao.findIdByUsername(username);
        return userFriendDao.isFriended(userId, friendId);
    }

}
