package com.boomerang.service;

import com.boomerang.model.Opportunity;
import com.boomerang.model.external.DistanceResponse;
import com.boomerang.model.external.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NearbyService {

    public Opportunity getNearbyOpportunity(List<Opportunity> opportunities, Double latitude, Double longitude, Integer radius) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + latitude + "," + longitude + "&destinations=" + createQueryString(opportunities)
                + "&key=AIzaSyDTWmoUW1QMuMDacudevjwWpz0xL-nQJb0";
        ResponseEntity<DistanceResponse> googleDistance = restTemplate.getForEntity(url, DistanceResponse.class);
        List<Element> distanceElements = googleDistance.getBody().getRows().get(0).getElements();

        Opportunity nearestOpportunity = null;
        for(int i = 0; i < distanceElements.size(); i++) {
            if (distanceElements.get(i).getDistance().getValue() < radius) {
                nearestOpportunity = opportunities.get(i);
                radius = distanceElements.get(i).getDistance().getValue();
            }
        }
        return nearestOpportunity;
    }

    private String createQueryString(List<Opportunity> opportunities) {
        List<String> coordinates = opportunities.stream().map(opportunity -> opportunity.getLatitude() + "," + opportunity.getLongitude()).collect(Collectors.toList());
        String query = String.join("|", coordinates);
        return query;
    }
}
