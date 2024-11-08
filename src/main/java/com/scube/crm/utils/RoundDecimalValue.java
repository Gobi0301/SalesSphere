package com.scube.crm.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundDecimalValue {
	
	 public static Double roundToDecimalPlaces(Double value) {
		 int decimalPlaces=4;
	        if (value == null) {
	            return null; // Or throw an exception, depending on your requirements
	        }

	        // Use BigDecimal for rounding
	        BigDecimal bd = new BigDecimal(value.toString());
	        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);

	        return bd.doubleValue();
	    }

}
