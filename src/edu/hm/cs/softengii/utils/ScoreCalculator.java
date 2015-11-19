/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 30-10-2015
*/

package edu.hm.cs.softengii.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.ScoreThresholdEntity;
import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;

/**
 * Class to calculate the score of a supplier
 * @author Kevin Beck
 */
public class ScoreCalculator {

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
	public ScoreCalculator() {

	}

	// Public methods -------------------------------------------------------------------

	/**
	 * Determines from which date on deliveries are to be taken into account for
	 * calculating a supplier's score.
	 * @param start
	 */
	public void setRangeStart(LocalDate start) {
		rangeStart = start;
	}

	/**
	 * Determines until which date deliveries are to be taken into account for
	 * calculating a supplier's score.
	 * @param end
	 */
	public void setRangeEnd(LocalDate end) {
		rangeEnd = end;
	}

	/**
	 * Calculate a score to rate this supplier
	 * @param supplier
	 * @return Score calculated over all deliveries of this supplier
	 */
	public double calculateScore(Supplier supplier) {

		double scoreSum = 0;

		List<Delivery> filteredDels = supplier.getDeliveries().stream()
				.filter(delivery -> {
					boolean isBefore = delivery.getActualDeliveryDate().isBefore(rangeEnd);
					boolean isAfter = delivery.getActualDeliveryDate().isAfter(rangeStart);
					return isBefore && isAfter;
				})
				.collect(Collectors.toList());

		// Get all thresholds from db
		ArrayList<ScoreThresholdEntity> thresholds = DatabaseDataStorage.getInstance().getScoreThresholds();
		
		for (Delivery delivery: filteredDels) {

			try {
				// Calculate difference of actual and promised delivery date in days
				int diffInDays = delivery.getDelay();

				// Calculate single score
				int singleScore = calculateSingleScore(diffInDays, thresholds);

				scoreSum += singleScore;
			}
			catch (Exception e) {
				// couldn't calculate score for this delivery
			}
		}

		// Calculate averange
		double score = 0;
		if (filteredDels.size() > 0) {
			score = scoreSum / filteredDels.size();
		}

		return score;
	}

	// Private methods ------------------------------------------------------------------

	/**
	 * Calculate score for a single delivery by a specific algorithm
	 * @param delayedDays
	 * @return Single score value
	 */
	private int calculateSingleScore(int delayedDays, ArrayList<ScoreThresholdEntity> thresholds) {

		// Default score is 0%
		int singleScore = 0;

		

		for (ScoreThresholdEntity threshold: thresholds) {

			if (delayedDays >= threshold.getEarlyMin() && delayedDays <= threshold.getEarlyMax() ||
				delayedDays >= threshold.getLateMin() && delayedDays <= threshold.getLateMax()) {

				// threshold found
				singleScore = threshold.getScoreValue();
				break;
			}
		}

		return singleScore;
	}
}