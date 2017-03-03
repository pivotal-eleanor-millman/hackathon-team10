package com.boomerang.model;

import javax.persistence.*;

@Entity
public class Opportunity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String address;

    @Column(name="HOURS", length=512*4)
    private String hours;

    @Lob
    @Column(name="DESCRIPTION", length=512*4)
    private String description;
    private Double latitude;
    private Double longitude;
    private String requester;
    private Boolean accepted;

    public Opportunity() {
    }

    public Opportunity(String title, String address, String hours, String description, Double latitude, Double longitude, String requester, Boolean accepted) {
        this.title = title;
        this.address = address;
        this.hours = hours;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requester = requester;
        this.accepted = accepted;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
