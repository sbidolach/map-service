package com.loveholidays.component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.loveholidays.component.MapApi;
import com.loveholidays.model.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class GoogleMapApiTest {

    private GoogleMapApi googleMapApi;

    final String searchPostcode = "TW8 TG5";

    @Before
    public void setup() throws Exception {
        googleMapApi = new GoogleMapApi();
    }

    @Test
    public void getLocationAddressByPostcodeAddressSuccess() throws Exception {
        GoogleMapApi googleMapApiSpy = Mockito.spy(googleMapApi);
        GeocodingResult[] result = new GeocodingResult[1];
        GeocodingResult geocodingResult = new GeocodingResult();
        geocodingResult.formattedAddress = "Wall Street";
        result[0] = geocodingResult;
        Mockito.doReturn(result).when(googleMapApiSpy).getGeocodeFromGoogleMap(searchPostcode);

        List<Location> addressList = googleMapApiSpy.getLocationAddressByPostcode(searchPostcode);
        Assert.assertEquals("Wall Street", addressList.get(0).getAddress());
    }

    @Test
    public void getLocationAddressByPostcodePostcodeSuccess() throws Exception {
        GoogleMapApi googleMapApiSpy = Mockito.spy(googleMapApi);
        GeocodingResult[] result = new GeocodingResult[1];
        GeocodingResult geocodingResult = new GeocodingResult();
        geocodingResult.formattedAddress = "Wall Street";
        result[0] = geocodingResult;
        Mockito.doReturn(result).when(googleMapApiSpy).getGeocodeFromGoogleMap(searchPostcode);

        List<Location> addressList = googleMapApiSpy.getLocationAddressByPostcode(searchPostcode);
        Assert.assertEquals(searchPostcode, addressList.get(0).getPostcode());
    }

    @Test
    public void getLocationAddressByPostcodeReturnMoreThanOne() throws Exception {
        GoogleMapApi googleMapApiSpy = Mockito.spy(googleMapApi);
        GeocodingResult[] result = new GeocodingResult[2];
        result[0] = new GeocodingResult();
        result[1] = new GeocodingResult();
        Mockito.doReturn(result).when(googleMapApiSpy).getGeocodeFromGoogleMap(searchPostcode);

        List<Location> addressList = googleMapApiSpy.getLocationAddressByPostcode(searchPostcode);
        Assert.assertEquals(2, addressList.size());
    }

    @Test
    public void getLocationAddressByPostcodeReturnEmptyArray() throws Exception {
        GoogleMapApi googleMapApiSpy = Mockito.spy(googleMapApi);
        Mockito.doReturn(new GeocodingResult[0]).when(googleMapApiSpy).getGeocodeFromGoogleMap(searchPostcode);

        List<Location> addressList = googleMapApiSpy.getLocationAddressByPostcode(searchPostcode);
        Assert.assertEquals(0, addressList.size());
    }

}
