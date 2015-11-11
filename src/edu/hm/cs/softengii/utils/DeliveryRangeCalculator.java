/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 11-11-2015
*/

package edu.hm.cs.softengii.utils;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;

/**
 * Class to calculate the delivery ranges of a supplier
 * @author Kevin Beck
 */
public class DeliveryRangeCalculator {

	// Enums ----------------------------------------------------------------------------
	
	/**
	 * Enum to classify a range
	 */
	public enum Range {
		VERY_LATE, LATE, IN_TIME, EARLY, VERY_EARLY
	}
	
	// Consts ---------------------------------------------------------------------------
	
	/**
	 * Number of seconds per day (24 * 60 * 60)
	 */
	private static final long SECS_PER_DAY = 86400;
	
	// Ctor -----------------------------------------------------------------------------
	
	/**
	 * Default constructor to create a new instance
	 */
	public DeliveryRangeCalculator() {
		
	}
	
	// Public methods -------------------------------------------------------------------
	
	/**
	 * Calculate the rates for the various ranges
	 * @param supplier
	 * @return Ranges with their rates
	 */
	public Map<Range, Double> calculateDeliveryRanges(Supplier supplier) {
		
		Map<Range, Double> ranges = new HashMap<>();
		ranges.put(Range.VERY_EARLY, calculateRangeRate(supplier, Integer.MIN_VALUE, -8));
		ranges.put(Range.EARLY, calculateRangeRate(supplier, -7, -2));
		ranges.put(Range.IN_TIME, calculateRangeRate(supplier, -1, 0));
		ranges.put(Range.LATE, calculateRangeRate(supplier, 1, 14));
		ranges.put(Range.VERY_LATE, calculateRangeRate(supplier, 15, Integer.MAX_VALUE));
		
		return ranges;		
	}
	
	// Private methods ------------------------------------------------------------------
	
	/**
	 * Calculate the difference in days between two dates
	 * @param date1
	 * @param date2
	 * @return Difference in days
	 */
	private int calculateDayDiff(Date date1, Date date2) {
		
		long diffInSeconds = (date2.getTime() - date1.getTime()) / 1000;

	    int diffInDays = (int)(diffInSeconds / SECS_PER_DAY);
	    
	    return diffInDays;
	}
	
	/**
	 * Calculate the rate for this day period
	 * @param supplier
	 * @param dayDiffMin
	 * @param dayDiffMax
	 * @return Rate for this day period
	 */
	private double calculateRangeRate(Supplier supplier, int dayDiffMin, int dayDiffMax) {
		double deliveryCounter = 0;
		
		// Count all deliveries of this supplier which are in this day period
		for (Delivery delivery: supplier.getDeliveries()) {
			int dayDiff = calculateDayDiff(delivery.getPromisedDeliveryDate(), delivery.getActualDeliveryDate());
			
			// Check if this delivery is in the given day period
			if (dayDiff >= dayDiffMin && dayDiff <= dayDiffMax) {
				deliveryCounter++;
			}
		}
		
		// Prevent deviding by zero
		double rate = 0;
		if (supplier.getDeliveries().size() > 0) {
			// Calculate rate by deviding by all deliveries
			rate = deliveryCounter / supplier.getDeliveries().size();
		}
		
		return rate;
	}
}