package com.loveholidays.component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.loveholidays.model.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleMapApi implements MapApi {

    @Value("${map.google.api_key}")
    private String GOOGLE_API_KEY;

    @Override
    public List<Location> getLocationAddressByPostcode(String postcode) throws Exception {
        GeocodingResult[] results = getGeocodeFromGoogleMap(postcode);

        List<Location> locations = new ArrayList<Location>();
        for (GeocodingResult result : results) {
            locations.add(new Location(postcode, result.formattedAddress));
        }

        return locations;
    }

    GeocodingResult[] getGeocodeFromGoogleMap(String postcode) throws Exception {
        GeoApiContext geoApiContext = new GeoApiContext().setApiKey(GOOGLE_API_KEY);
        return GeocodingApi.geocode(geoApiContext, postcode).await();
    }

}
