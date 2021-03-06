package edu.hm.cs.softengii.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.ScoreThresholdEntity;
import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;

/**
 * Class to calculate the score for a supplier.
 */
public class ScoreCalculator {

	/**
	 * Date pickers range start date
	 */
	private LocalDate rangeStart = LocalDate.MIN;

	/**
	 * Date pickers range end date
	 */
	private LocalDate rangeEnd = LocalDate.now();

	/**
	 * Thresholds to score a delivery
	 */
	List<ScoreThresholdEntity> thresholds;

	/**
	 * Create a new instance
	 */
	public ScoreCalculator()
	{
		this.thresholds = DatabaseDataStorage.getInstance().getScoreThresholds();
	}

	public ScoreCalculator(List<ScoreThresholdEntity> scoreTresholds) {
		thresholds = scoreTresholds;
	}

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
	 * Calculates a score to rate a given supplier.
	 * Returns the score calculated over all deliveries of a given supplier.
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

		for (Delivery delivery: filteredDels) {

			// calculate difference of actual and promised delivery date in days
			int diffInDays = delivery.getDelay();

			// calculate single score
			int singleScore = calculateSingleScore(diffInDays);

			scoreSum += singleScore;
		}

		if (filteredDels.size() > 0) {
			score = scoreSum / filteredDels.size(); // calculate average score
		}

		return score;
	}

	/**
	 * Calculate score for a single delivery by a specific algorithm
     * Returns a single score value.
	 * @param delayedDays The umber of delayed days
	 */
	private int calculateSingleScore(int delayedDays) {

		int singleScore = 0; // default score is 0%

		for (ScoreThresholdEntity threshold: this.thresholds) {

			if (delayedDays >= threshold.getEarlyMin() && delayedDays <= threshold.getEarlyMax()
                    || delayedDays >= threshold.getLateMin() && delayedDays <= threshold.getLateMax()) {

				singleScore = threshold.getScoreValue(); // threshold found
				break;
			}
		}

		return singleScore;
	}
}
