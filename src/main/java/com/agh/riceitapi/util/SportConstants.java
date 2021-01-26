package com.agh.riceitapi.util;

public enum SportConstants {

    //these are provided with mph (not kmh) !!!
    RUNNING_4(6.0),
    RUNNING_5(8.3),
    RUNNING_5_2(9.0),
    RUNNING_6(9.8),
    RUNNING_6_7(10.5),
    RUNNING_7(11.0),
    RUNNING_7_5(11.8),
    RUNNING_8(11.8),
    RUNNING_8_6(12.3),
    RUNNING_9(12.8),
    RUNNING_10(14.5),
    RUNNING_11(16.0),
    RUNNING_12(19.0),
    RUNNING_13(19.8),
    RUNNING_14(23.0),
    BICYCLING_10_TO_12(6.8),
    BICYCLING_12_TO_14(8.0),
    BICYCLING_14_TO_16(10.0),
    BICYCLING_16_TO_19(12.0),
    BICYCLING_MORE_THAN_20(15.8),
    SWIMMING_GENERAL(9.8),
    SWIMMING_BACKSTROKE(9.5),
    SWIMMING_CRAWL(10.0),
    SWIMMING_BREASTSTROKE(10.3),
    SWIMMING_BUTTERFLY(13.8);

    public final double MET;

    private SportConstants(double MET){
        this.MET = MET;
    }

}
