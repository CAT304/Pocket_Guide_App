package com.example.pocket_guide.Model;

public class User {
    private String id, username, fullname, imagetoken, bio;

    public User(String id, String username, String fullname, String imagetoken, String bio) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imagetoken = imagetoken;
        this.bio = bio;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getimagetoken() {
        return imagetoken;
    }

    public void setimagetoken(String imagetoken) {
        this.imagetoken = imagetoken;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
