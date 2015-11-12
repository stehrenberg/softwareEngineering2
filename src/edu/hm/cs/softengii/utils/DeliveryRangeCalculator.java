/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 11-11-2015
*/

package edu.hm.cs.softengii.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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

	// Fields ---------------------------------------------------------------------------

	/** For selecting deliveries after a certain date. */
	private LocalDate rangeStart = LocalDate.MIN;
	/** For selecting deliveries before a certain date. */
	private LocalDate rangeEnd = LocalDate.now();

	// Ctor -----------------------------------------------------------------------------

	/**
	 * Default constructor to create a new instance
	 */
	public DeliveryRangeCalculator() {

	}

	// Public methods -------------------------------------------------------------------

	/**
	 * Set start range
	 * @param start
	 */
	public void setRangeStart(LocalDate start) {
		rangeStart = start;
	}

	/**
	 * Set end range
	 * @param end
	 */
	public void setRangeEnd(LocalDate end) {
		rangeEnd = end;
	}
	/**
	 * Calculate the rates for the various ranges
	 * @param supplier
	 * @return Ranges with their rates
	 */
	public Map<Range, Double> calculateDeliveryRanges(Supplier supplier) {

		Map<Range, Double> ranges = new HashMap<>();
		ranges.put(Range.VERY_EARLY, calculateRangeRate(supplier, Integer.MIN_VALUE, -6));
		ranges.put(Range.EARLY, calculateRangeRate(supplier, -5, -2));
		ranges.put(Range.IN_TIME, calculateRangeRate(supplier, -1, 0));
		ranges.put(Range.LATE, calculateRangeRate(supplier, 1, 5));
		ranges.put(Range.VERY_LATE, calculateRangeRate(supplier, 6, Integer.MAX_VALUE));

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
		List<Delivery> deliveries = supplier.getDeliveries();
		List<Delivery> filteredDels = deliveries.stream()
				.filter(delivery -> {
					boolean isBefore = delivery.getActualDeliveryDate().isBefore(rangeEnd);
					boolean isAfter = delivery.getActualDeliveryDate().isAfter(rangeStart);
					return isBefore && isAfter;
				})
				.collect(Collectors.toList());

		// Count all deliveries of this supplier which are in this day period
		for (Delivery delivery: filteredDels) {
			int dayDiff = delivery.getDelay();

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

	private java.util.Date asUtilDate(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
}