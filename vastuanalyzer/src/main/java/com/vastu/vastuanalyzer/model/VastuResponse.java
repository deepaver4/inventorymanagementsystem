package com.vastu.vastuanalyzer.model;

public class VastuResponse {

        private String direction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    private String zone;
        private String status;
        private String tip;

    public VastuResponse(String direction, String zone, String status, String tip) {
        this.direction = direction;
        this.zone = zone;
        this.status = status;
        this.tip = tip;
    }


}
