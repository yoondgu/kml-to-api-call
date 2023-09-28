package com.example.demo;

public class PinCreateRequest {

    public PinCreateRequest() {
    }

    private Long topicId;
    private String name;
    private String description;
    private String address;
    private String legalDongCode;
    private double latitude;
    private double longitude;

    public PinCreateRequest(Long topicId, String name, String description, String address, String legalDongCode, double latitude, double longitude) {
        this.topicId = topicId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.legalDongCode = legalDongCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getTopicId() {
        return topicId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getLegalDongCode() {
        return legalDongCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "PinCreateRequest{" +
                "topicId=" + topicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", legalDongCode='" + legalDongCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
