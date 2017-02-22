package com.loveholidays.model;

public class Location {

    private final String postcode;
    private final String address;

    public Location(String postcode, String address) {
        this.postcode = postcode;
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }

}
