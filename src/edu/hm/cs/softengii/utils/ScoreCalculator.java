package edu.hm.cs.softengii.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.ScoreThresholdEntity;
import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;

/**
 * Class to calculate the score for a supplier.
 */
public class ScoreCalculator {

	private static final long SECS_PER_DAY = 86400; // Number of seconds per day (24 * 60 * 60)

	private LocalDate rangeStart = LocalDate.MIN;
	private LocalDate rangeEnd = LocalDate.now();

	public ScoreCalculator() {}

	/**
	 * Determines from which date on deliveries should be taken to calculate a supplier's score.
	 *
	 * @param start The start date for the score calculation
	 */
	public void setRangeStart(LocalDate start) {
		rangeStart = start;
	}

	/**
	 * Determines until which date deliveries should taken to calculate a supplier's score.
	 *
	 * @param end The end date for the score calculation
	 */
	public void setRangeEnd(LocalDate end) {
		rangeEnd = end;
	}

	/**
	 * Calculates a score to rate a given supplier.
	 * Returns the score calculated over all deliveries of a given supplier.
	 *
	 * @param supplier The supplier to calculate the score for
	 */
	public double calculateScore(Supplier supplier) {

		double scoreSum = 0;
        double score = 0;

		List<Delivery> filteredDels = supplier.getDeliveries().stream()
				.filter(delivery -> {
					boolean isBefore = delivery.getActualDeliveryDate().isBefore(rangeEnd);
					boolean isAfter = delivery.getActualDeliveryDate().isAfter(rangeStart);
					return isBefore && isAfter;
				})
				.collect(Collectors.toList());

		// get all thresholds from db
		ArrayList<ScoreThresholdEntity> thresholds = DatabaseDataStorage.getInstance().getScoreThresholds();
		
		for (Delivery delivery: filteredDels) {

			try {

				// calculate difference of actual and promised delivery date in days
				int diffInDays = delivery.getDelay();

				// calculate single score
				int singleScore = calculateSingleScore(diffInDays, thresholds);

				scoreSum += singleScore;

			} catch (Exception ex) {

				// couldn't calculate score for this delivery
                Logger.getLogger(ScoreCalculator.class.getName()).log(Level.WARNING, null, ex);
            }
		}

		if (filteredDels.size() > 0) {
			score = scoreSum / filteredDels.size(); // calculate average score
		}

		return score;
	}

	/**
	 * Calculate score for a single delivery by a specific algorithm
     * Returns a single score value.
     *
	 * @param delayedDays The umber of delayed days
     * @param thresholds A list containing the thresholds
	 */
	private int calculateSingleScore(int delayedDays, ArrayList<ScoreThresholdEntity> thresholds) {

		int singleScore = 0; // default score is 0%

		for (ScoreThresholdEntity threshold: thresholds) {

			if (delayedDays >= threshold.getEarlyMin() && delayedDays <= threshold.getEarlyMax()
                    || delayedDays >= threshold.getLateMin() && delayedDays <= threshold.getLateMax()) {

				singleScore = threshold.getScoreValue(); // threshold found
				break;
			}
		}

		return singleScore;
	}
}