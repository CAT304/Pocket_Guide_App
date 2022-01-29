package com.example.pocket_guide.Model;

public class Display_Post {
    private String postid, imageurl,description, publisher, location_name, post_time;


    public Display_Post(String postid, String postimage, String caption, String publisher, String location_name, String post_time) {
        this.postid = postid;
        this.imageurl = postimage;
        this.description = caption;
        this.publisher = publisher;
        this.location_name = location_name;
        this.post_time = post_time;
    }

    public Display_Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String postimage) {
        this.imageurl = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String caption) {
        this.description = caption;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }
}
