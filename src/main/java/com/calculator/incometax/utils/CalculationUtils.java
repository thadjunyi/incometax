package com.calculator.incometax.utils;

import com.calculator.incometax.model.Tax;

public class CalculationUtils {
	
	public static double getTaxAmount(double taxableIncome, Tax tax) {
		
		Integer from = Integer.valueOf(tax.getFrom());
		Integer to = Integer.valueOf(tax.getTo());
		Double rate = Double.valueOf(tax.getRate());
		
		Double result;
		
		if (taxableIncome < from) {
			result = 0.00;
		} else if (taxableIncome < to) {
			result = (taxableIncome - from) * rate / 100;
		} else {
			result = (to - from) * rate / 100;
		}
		
		return result;
	}
	
	public static double calculateDeduction(double amount, String rate) {
		return amount * Double.valueOf(rate);
	}
	
	public static double calculateTaxRate(double taxableIncome, double totalTax) {
		return 1 / (taxableIncome / totalTax) * 100;
	}
}
