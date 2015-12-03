package edu.hm.cs.softengii.utils;

import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to calculate the delivery ranges of a supplier.
 */
public class DeliveryRangeCalculator {

	/**
	 * Enum to classify a range
	 * @author Kevin Beck
	 */
	public enum Range { VERY_LATE, LATE, IN_TIME, EARLY, VERY_EARLY } 
	
	/**
	 * Number of seconds per day (24 * 60 * 60)
	 */
	private static final long SECS_PER_DAY = 86400;

	/**
	 * Date pickers range start date
	 */
	private LocalDate rangeStart = LocalDate.MIN;
	
	/**
	 * Date pickers range end date
	 */
	private LocalDate rangeEnd = LocalDate.now();

	/** 
	 * Create a new empty instance
	 */
	public DeliveryRangeCalculator() {}

    /**
     * Determines from which date on deliveries should be taken to calculate a supplier's score.
     * @param start The start date for the score calculation
     */
	public void setRangeStart(LocalDate start) {
		rangeStart = start;
	}

    /**
     * Determines until which date deliveries should taken to calculate a supplier's score.
     * @param end The end date for the score calculation
     */
	public void setRangeEnd(LocalDate end) {
		rangeEnd = end;
	}

    /**
     * Calculates the rates for a supplier in specific date ranges.
     * Returns a map of the date ranges with their rates.
     * @param supplier Supplier to calculate rates for.
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

	/**
	 * Returns the difference in days between two given dates.
	 * @param start The start date
	 * @param end The end date
	 */
	private int calculateDayDiff(Date start, Date end) {

		long diffInSeconds = (end.getTime() - start.getTime()) / 1000;

        return (int)(diffInSeconds / SECS_PER_DAY);
	}

	/**
	 * Returns the rate for a specific period of time.
	 * @param supplier The supplier to calculate the rates for
	 * @param dayDiffMin The minimum date difference
	 * @param dayDiffMax The maximum date difference
	 */
	private double calculateRangeRate(Supplier supplier, int dayDiffMin, int dayDiffMax) {

		double deliveryCounter = 0;
		List<Delivery> deliveries = supplier.getDeliveries();

		List<Delivery> filteredDels = deliveries.stream()
				.filter(delivery -> {

					boolean isBefore = delivery.getActualDeliveryDate().isBefore(rangeEnd);
					boolean isAfter = delivery.getActualDeliveryDate().isAfter(rangeStart);
					return isBefore && isAfter;

				}).collect(Collectors.toList());

		// count all deliveries of this supplier which are in this day period
		for (Delivery delivery: filteredDels) {

			int dayDiff = delivery.getDelay();

			// check if this delivery is in the given day period
			if (dayDiff >= dayDiffMin && dayDiff <= dayDiffMax) {
				deliveryCounter++;
			}
		}

		double rate = 0; // prevent dividing by zero

		if (supplier.getDeliveries().size() > 0) {
			rate = deliveryCounter / supplier.getDeliveries().size(); // calculate rate by dividing by all deliveries
		}

		return rate;
	}
}