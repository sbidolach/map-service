package com.loveholidays.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.loveholidays.component.GoogleMapApi;
import com.loveholidays.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(MapController.class)
public class MapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GoogleMapApi googleMapApiMock;

    private String mapPath = "/map/";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void getMapByPostcodeSuccess() throws Exception {

        final String searchPostcode = "TW8 FDS";
        final String expectedAddress = "Orchard Road";

        List<Location> returnMockLocationList = new ArrayList<Location>();
        returnMockLocationList.add(new Location(searchPostcode, expectedAddress));

        given(googleMapApiMock.getLocationAddressByPostcode(searchPostcode)).willReturn(returnMockLocationList);

        mockMvc.perform(MockMvcRequestBuilders.get(mapPath + searchPostcode).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].postcode", is(searchPostcode)))
                .andExpect(jsonPath("$[0].address", is(expectedAddress)));
    }



    @Test
    public void getMapByPostcodeWhenIsNotFounded() throws Exception {

        final String searchPostcode = "TW8 FDS";

        given(googleMapApiMock.getLocationAddressByPostcode(searchPostcode)).willReturn(new ArrayList<Location>());

        mockMvc.perform(MockMvcRequestBuilders.get(mapPath + searchPostcode).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void getMapByPostcodeAndThrowException() throws Exception {

        final String searchPostcode = "TW8 FDS";

        given(googleMapApiMock.getLocationAddressByPostcode(searchPostcode)).willThrow(new Exception("Internal Exception"));

        mockMvc.perform(MockMvcRequestBuilders.get(mapPath + searchPostcode).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.code", is(500)))
                .andExpect(jsonPath("$.message", is("Internal Exception")));

    }

}
