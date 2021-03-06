package edu.hm.cs.softengii.utils;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.DeliveryRange;
import edu.hm.cs.softengii.db.dataStorage.DeliveryRangeThresholdEntity;
import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to calculate the delivery ranges of a supplier.
 */
public class DeliveryRangeCalculator {

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
	public Map<DeliveryRange, Double> calculateDeliveryRanges(Supplier supplier) {

		Map<DeliveryRange, Double> ranges = new HashMap<>();

		// Get range thrsholds from database
		ArrayList<DeliveryRangeThresholdEntity> rangeEntities =
				DatabaseDataStorage.getInstance().getDeliveryRangeThresholds();
		
		for (DeliveryRangeThresholdEntity entity: rangeEntities) {
			
			ranges.put(entity.getDeliveryRangeName(),
					calculateRangeRate(supplier, entity.getDaysMin(), entity.getDaysMax()));
		}

		return ranges;
	}

	/**
	 * Returns the rate for a specific period of time.
	 * @param supplier The supplier to calculate the rates for
	 * @param dayDiffMin The minimum date difference
	 * @param dayDiffMax The maximum date difference
	 */
	private double calculateRangeRate(Supplier supplier, int dayDiffMin, int dayDiffMax) {

		double deliveryCounter = 0;

		List<Delivery> filteredDels = supplier.getDeliveries().stream()
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

		if (filteredDels.size() > 0) {
			rate = deliveryCounter / filteredDels.size(); // calculate rate by dividing by all deliveries
		}

		return rate;
	}
}