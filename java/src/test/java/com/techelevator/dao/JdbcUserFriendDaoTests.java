package com.techelevator.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcUserFriendDaoTests extends BaseDaoTests {
    private JdbcUserFriendDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserFriendDao(jdbcTemplate);
    }

    @Test
    public void isFriendedHappyPath(){
        boolean hasUser1FriendedUser2 = sut.isFriended(1, 2);
        Assert.assertTrue(hasUser1FriendedUser2);
        boolean hasUser2FriendedUser1 = sut.isFriended(1,1);
        Assert.assertTrue(hasUser2FriendedUser1);
        boolean hasUser1FriendedUser3 = sut.isFriended(1,3);
        Assert.assertTrue(hasUser1FriendedUser3);
        boolean hasUser3FriendedUser1 = sut.isFriended(3, 1);
        Assert.assertFalse(hasUser3FriendedUser1);
    }

}
