package com.vastu.vastuanalyzer.util;

public class DirectionUtil {

    public static String getDirection(double degree){

        if(degree >= 337.5 || degree < 22.5)
            return "North";

        else if(degree < 67.5)
            return "North-East";

        else if(degree < 112.5)
            return "East";

        else if(degree < 157.5)
            return "South-East";

        else if(degree < 202.5)
            return "South";

        else if(degree < 247.5)
            return "South-West";

        else if(degree < 292.5)
            return "West";

        else
            return "North-West";
    }
}