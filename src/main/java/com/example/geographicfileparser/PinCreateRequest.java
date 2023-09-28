package com.example.geographicfileparser;

public class PinCreateRequest {

    public PinCreateRequest() {
    }

    private Long topicId;
    private String name;
    private String description;
    private String address;
    private String legalDongCode;
    private double latitude;
    private double longitud;

    public PinCreateRequest(Long topicId, String name, String description, String address, String legalDongCode, double latitude, double longitud) {
        this.topicId = topicId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.legalDongCode = legalDongCode;
        this.latitude = latitude;
        this.longitud = longitud;
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

    public double getLongitud() {
        return longitud;
    }
}
