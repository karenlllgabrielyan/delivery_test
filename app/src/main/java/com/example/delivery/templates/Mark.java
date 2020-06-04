package com.example.delivery.templates;

public class Mark {

    private String name;
    private String address;
    private String numbers;
    private String timeToDeliver;
    private String timeInterval;
    private String destination;
    private int type;

    public Mark(
            String name,
            String address,
            String numbers,
            String timeToDeliver,
            String timeInterval,
            String destination,
            int type
    ) {

        this.name = name;
        this.address = address;
        this.numbers = numbers;
        this.timeToDeliver = timeToDeliver;
        this.timeInterval = timeInterval;
        this.destination = destination;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getTimeToDeliver() {
        return timeToDeliver;
    }

    public void setTimeToDeliver(String timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
