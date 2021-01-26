package com.agh.riceitapi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalOperator {

    public static double round (double value){
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
