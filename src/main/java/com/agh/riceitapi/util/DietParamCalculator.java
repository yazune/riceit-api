package com.agh.riceitapi.util;

import com.agh.riceitapi.exception.InternalServerException;

public final class DietParamCalculator {

    private static double protPercentage = 15;
    private static double fatPercentage = 30;
    private static double carbPercentage = 55;

    public static double calculateBmr(double height, double weight, int age, Gender gender){
        double bmr;

        if (gender.equals(Gender.MALE)){
            bmr = 66.4730 + (13.7516 * weight) + (5.0033 * height) - (6.7750 * age);
        } else if (gender.equals(Gender.FEMALE)){
            bmr = 665.0955 + (9.5634 * weight) + (1.8496 * height) - (4.6756 * age);
        } else throw new InternalServerException("Goal.calculateParameters: wrong Gender format!");

        return bmr;
    }



    public static double[] calculatePFC(double bmr){
        double prot = bmr * protPercentage / 100 / 4;
        double fat =  bmr * fatPercentage / 100 / 9;
        double carb = bmr * carbPercentage / 100 / 4;

        return new double[]{prot,fat,carb};
    }

    public static double[] calculateMacro(double bmr, double k, double difference){
        bmr *= k;
        bmr += difference;

        double[] array = new double[4];
        double[] macro = calculatePFC(bmr);

        return new double[]{bmr, macro[0], macro[1], macro[2]};
    }

}
