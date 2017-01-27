package com.boomerang.controller;

import com.boomerang.model.Opportunity;
import com.boomerang.repository.OpportunityRepository;
import com.boomerang.service.NearbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BoomerangController {

    private OpportunityRepository repository;
    private NearbyService nearbyService;

    @Autowired
    public BoomerangController(OpportunityRepository repository, NearbyService nearbyService) {

        this.repository = repository;
        this.nearbyService = nearbyService;
    }

    @GetMapping("/opportunities")
    public ResponseEntity<List<Opportunity>> getOpportunities() {
        List<Opportunity> opportunities = new ArrayList<>();
        opportunities = repository.findAll();

        return new ResponseEntity<>(opportunities, HttpStatus.OK);
    }

    @PostMapping("/opportunity")
    public ResponseEntity addOpportunity(@RequestBody Opportunity opportunity) {
        repository.save(opportunity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/nearby")
    public ResponseEntity<Opportunity> getNearbyOpportunity(@RequestParam Double latitude,
                                                            @RequestParam Double longitude,
                                                            @RequestParam Integer radius) {
        List<Opportunity> opportunities = repository.findAll();
        Opportunity nearbyOpportunity = nearbyService.getNearbyOpportunity(opportunities, latitude, longitude, radius);
        if (nearbyOpportunity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(nearbyOpportunity, HttpStatus.OK);
        }
    }

    @GetMapping("/opportunity/post")
    public ResponseEntity addOpportunity(@RequestParam String title,
                                         @RequestParam String hours,
                                         @RequestParam String description,
                                         @RequestParam String location) {
        Opportunity opportunity = new Opportunity();
        opportunity.setAddress(location);
        opportunity.setDescription(description);
        opportunity.setHours(hours);
        opportunity.setTitle(title);
        opportunity.setLatitude(43.650244);
        opportunity.setLongitude(-79.376564);
        repository.save(opportunity);
        return new ResponseEntity(HttpStatus.OK);
    }
}
