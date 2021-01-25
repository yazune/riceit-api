package com.agh.riceitapi.util;

import com.agh.riceitapi.exception.InternalServerException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DietParamCalculator {

    private static double protPercentage = 15.0;
    private static double fatPercentage = 30.0;
    private static double carbPercentage = 55.0;

    public static double calculateBmr(double height, double weight, int age, Gender gender){
        double bmr;

        if (gender.equals(Gender.MALE)){
            bmr = 66.4730 + (13.7516 * weight) + (5.0033 * height) - (6.7750 * (double)age);
        } else if (gender.equals(Gender.FEMALE)){
            bmr = 665.0955 + (9.5634 * weight) + (1.8496 * height) - (4.6756 * (double)age);
        } else throw new InternalServerException("Goal.calculateParameters: wrong Gender format!");

        return DecimalOperator.round(bmr,2);
    }



    public static double[] calculatePFC(double bmr){
        double prot = DecimalOperator.round((bmr * protPercentage / 100.0 / 4.0),2);
        double fat =  DecimalOperator.round((bmr * fatPercentage / 100.0 / 9.0), 2);
        double carb = DecimalOperator.round((bmr - carbPercentage / 100.0 / 4.0), 2);

        return new double[]{prot,fat,carb};
    }

    public static double[] calculateMacro(double bmr, double k, double difference){
        bmr *= k;
        bmr += difference;

        double[] macro = calculatePFC(bmr);

        return new double[]{bmr, macro[0], macro[1], macro[2]};
    }
}
