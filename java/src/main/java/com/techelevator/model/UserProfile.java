package com.techelevator.model;

import java.util.List;

public class UserProfile {

    private String username;
    private String profilePic;
    private String aboutMe;
    private List<String> friends;

    public UserProfile() {
    }

    public UserProfile(String username, String profilePic, String aboutMe, List<String> friends) {
        this.username = username;
        this.profilePic = profilePic;
        this.aboutMe = aboutMe;
        this.friends = friends;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
