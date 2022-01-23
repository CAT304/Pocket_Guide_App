package com.example.pocket_guide.Model;

public class Location {
    private String LocationDetails;
    private String LocationId;
    private String LocationImageUri;
    private String LocationName;
    private String LocationRatings;
    private String LocationReviews;

    public Location(){

    }

    public Location(String locationDetails, String locationId, String locationImageUri, String locationName, String locationRatings, String locationReviews) {
        LocationDetails = locationDetails;
        LocationId = locationId;
        LocationImageUri = locationImageUri;
        LocationName = locationName;
        LocationRatings = locationRatings;
        LocationReviews = locationReviews;
    }

    public String getLocationDetails() {
        return LocationDetails;
    }

    public void setLocationDetails(String locationDetails) {
        LocationDetails = locationDetails;
    }

    public String getLocationId() {
        return LocationId;
    }

    public void setLocationId(String locationId) {
        LocationId = locationId;
    }

    public String getLocationImageUri() {
        return LocationImageUri;
    }

    public void setLocationImageUri(String locationImageUri) {
        LocationImageUri = locationImageUri;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getLocationRatings() {
        return LocationRatings;
    }

    public void setLocationRatings(String locationRatings) {
        LocationRatings = locationRatings;
    }

    public String getLocationReviews() {
        return LocationReviews;
    }

    public void setLocationReviews(String locationReviews) {
        LocationReviews = locationReviews;
    }
}
