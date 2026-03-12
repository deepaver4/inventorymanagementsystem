package com.vastu.vastuanalyzer.model;

public class VastuRequest {

        private String room;

    public VastuRequest(String room, double degree) {
        this.room = room;
        this.degree = degree;
    }

    private double degree;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
}
