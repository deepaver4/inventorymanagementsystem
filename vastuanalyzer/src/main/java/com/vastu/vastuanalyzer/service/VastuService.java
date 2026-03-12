package com.vastu.vastuanalyzer.service;

import com.vastu.vastuanalyzer.util.DirectionUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VastuService {
     Map<String, String> vastuRules = Map.of(
            "Kitchen", "South-East",
            "Bedroom", "South-West",
            "Pooja Room", "North-East",
            "Living Room", "North",
            "Study Room", "East"
    );


    public String evaluateVastu(String room, double degree) {

        String direction = DirectionUtil.getDirection(degree);
        String idealDirection = vastuRules.get(room);
        if (direction.equalsIgnoreCase(idealDirection)) {
            return "Good placement as per Vastu";
        } else {

            return "Consider placing " + room + " in " + idealDirection + " direction";
        }
    }
}