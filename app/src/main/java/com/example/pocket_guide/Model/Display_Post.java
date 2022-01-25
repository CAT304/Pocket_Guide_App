package com.example.pocket_guide.Model;

public class Display_Post {
    private String postid, imageurl,description,publisher;

    public Display_Post(String postid, String postimage, String caption, String publisher) {
        this.postid = postid;
        this.imageurl = postimage;
        this.description = caption;
        this.publisher = publisher;
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
}
