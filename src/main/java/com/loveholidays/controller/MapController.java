package com.loveholidays.controller;

import com.google.maps.model.GeocodingResult;
import com.loveholidays.component.GoogleMapApi;
import com.loveholidays.model.Location;
import com.loveholidays.model.MapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class MapController {

    @Autowired
    private GoogleMapApi googleMapApi;

    @RequestMapping(method=RequestMethod.GET, value={"/map/{postcode}"})
    public List<Location> map(@PathVariable("postcode") String postcode) throws Exception {
        return googleMapApi.getLocationAddressByPostcode(postcode);
    }

    @ExceptionHandler(Exception.class)
    public MapException handleError(HttpServletRequest req, Exception ex) {
        MapException mapException = new MapException();
        mapException.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        mapException.setMessage(ex.getMessage());
        return mapException;
    }

}
