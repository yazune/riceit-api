package com.agh.riceitapi.util;

import com.agh.riceitapi.exception.InternalServerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DietParamCalculator {

    private static double protPercentage = 30.0;
    private static double fatPercentage = 15.0;
    private static double carbPercentage = 55.0;
    private static double difference = 500.0;

    public static double calculateBmr(double height, double weight, int age, Gender gender){
        double bmr;
        double height_m, weight_m, age_m;

         // Harris-Benedict method
//        if (gender.equals(Gender.MALE)){
//            height_m = DecimalOperator.round(5.0033 * height);
//            weight_m = DecimalOperator.round(13.7516 * weight);
//            age_m = DecimalOperator.round(6.7750 * (double)age);
//            bmr = 66.4730 + weight_m + height_m - age_m;
//
//        } else if (gender.equals(Gender.FEMALE)){
//            height_m = DecimalOperator.round(1.8496 * height);
//            weight_m = DecimalOperator.round(9.5634 * weight);
//            age_m = DecimalOperator.round(4.6756 * (double)age);
//            bmr = 655.0955 + weight_m + height_m - age_m;
//        } else throw new InternalServerException("Goal.calculateParameters: wrong Gender format!");

        // Mifflin - St Jeor method
        weight_m = DecimalOperator.round(10.0 * weight);
        height_m = DecimalOperator.round(6.25 * height);
        age_m = DecimalOperator.round (5.0 * age);

        if (gender.equals(Gender.MALE)){
            bmr = weight_m + height_m - age_m + 5.0;
        } else if (gender.equals(Gender.FEMALE)){
            bmr = weight_m + height_m - age_m - 161.0;
        } else throw new InternalServerException("Goal.calculateParameters: wrong Gender format!");

        return DecimalOperator.round(bmr);
    }

    public static double[] calculatePFC(double bmr){
        double prot = DecimalOperator.round((bmr * protPercentage / 100.0 / 4.0));
        double fat =  DecimalOperator.round((bmr * fatPercentage / 100.0 / 9.0));
        double carb = DecimalOperator.round((bmr * carbPercentage / 100.0 / 4.0));

        return new double[]{prot,fat,carb};
    }

    public static double[] calculateMacro(double bmr, double pal, DietType dietType){

        if(dietType.equals(DietType.GAIN)){
            bmr += difference;
        } else if(dietType.equals(DietType.REDUCTION)){
            bmr -= difference;
        }
        bmr *= pal;

        double[] macro = calculatePFC(bmr);

        return new double[]{bmr, macro[0], macro[1], macro[2]};
    }

    public static double calculateCorrectedMET(double MET, double bmr, double weight){

        double bmrInL = DecimalOperator.round(bmr / 7200.0);
        double bmrInMl = DecimalOperator.round(bmrInL * 1000.0 / weight);
        double bmrModified = DecimalOperator.round(3.5 / bmrInMl);

        return DecimalOperator.round(MET * bmrModified);
    }

    public static double calculateKcalBurnt(double MET, double bmr, double weight, int duration){
        double correctedMET = calculateCorrectedMET(MET, bmr, weight);
        double timeInH = DecimalOperator.round((double)duration / 60.0);
        double kcalBurntPerKg = DecimalOperator.round(correctedMET * timeInH);
        return DecimalOperator.round(kcalBurntPerKg * weight);
    }

}
