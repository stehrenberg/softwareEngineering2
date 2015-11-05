/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 30-10-2015
*/

package edu.hm.cs.softengii.utils;

import java.util.Date;

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
	
	// Ctor -----------------------------------------------------------------------------
	
	/**
	 * Default constructor to create a new instance
	 */
	public ScoreCalculator() {
		
	}
	
	// Public methods -------------------------------------------------------------------
	
	/**
	 * Calculate a score to rate this supplier
	 * @param supplier
	 * @return Score calculated over all deliveries of this supplier
	 */
	public double calculateScore(Supplier supplier) {
		
		double scoreSum = 0;
		
		for (Delivery delivery: supplier.getDeliveries()) {
			
			try {
				// Calculate difference of actual and promised delivery date in days
				int diffInDays = calculateDayDiff(delivery.getPromisedDeliveryDate(),
						delivery.getActualDeliveryDate());
				
				// Calculate single score
				int singleScore = calculateSingleScore(diffInDays);
				
				scoreSum += singleScore;
			}
			catch (Exception e) {
				// couldn't calculate score for this delivery
			}
		}
		
		// Calculate averange
		double score = scoreSum / supplier.getDeliveries().size();
		
		return score;
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
	 * Calculate score for a single delivery by a specific algorithm
	 * @param delayedDays
	 * @return Single score value
	 */
	private int calculateSingleScore(int delayedDays) {
		
		// Default score is 0%
		int singleScore = 0;
		
		if (delayedDays == 0 || delayedDays == -1) {
			singleScore = 100;
		}
		else if (delayedDays >= 1 && delayedDays <= 3 || delayedDays == -2) {
			singleScore = 90;
		}
		else if (delayedDays >= 4 && delayedDays <= 7 || delayedDays == -3) {
			singleScore = 80;
		}
		else if (delayedDays >= 8 && delayedDays <= 14 || delayedDays <= -4 && delayedDays >= -7) {
			singleScore = 60;
		}
		else if (delayedDays >= 15 && delayedDays <= 38 || delayedDays <= -8 && delayedDays >= -10) {
			singleScore = 40;
		}
		
		return singleScore;
	}
}