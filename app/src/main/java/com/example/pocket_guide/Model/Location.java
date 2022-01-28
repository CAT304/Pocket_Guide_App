package com.example.pocket_guide.Model;

public class Location {
    private String imageUri;
    private String Name;

    public Location(){

    }

    public Location(String imageUri, String name) {
        this.imageUri = imageUri;
        Name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
