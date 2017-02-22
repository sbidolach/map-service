package com.loveholidays.component;

import com.loveholidays.model.Location;

import java.util.List;

public interface MapApi {

    List<Location> getLocationAddressByPostcode(String postcode) throws Exception;

}
