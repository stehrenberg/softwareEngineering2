package edu.hm.cs.softengii.test;

import edu.hm.cs.softengii.db.dataStorage.ScoreThresholdEntity;
import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.ScoreCalculator;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreCalculatorTest {

	/**
	 * Test the calculateScore method with a mocked supplier
	 */
    @Test
    public void testCalculateScore() {

        List<ScoreThresholdEntity> scoreThresholds = createScoreTresholds();
        ScoreCalculator calc = new ScoreCalculator(scoreThresholds);
        Supplier testSupplier = createSampleSupplier();

        double calculatedScore = calc.calculateScore(testSupplier);
        double expectedScore = (80 + 100 + 100) / 3.0;
        assertEquals(expectedScore, calculatedScore, 0.001);
    }
    
    /**
     * Test the calculateScore method with a mocked supplier in a specific date range (start & end)
     */
    @Test
    public void testCalculateScoreDateRange() {
    	
    	List<ScoreThresholdEntity> scoreThresholds = createScoreTresholds();
        ScoreCalculator calc = new ScoreCalculator(scoreThresholds);
        
        // take date range when first two deliveris were delivered
        calc.setRangeStart(LocalDate.of(2015, 10, 4));
        calc.setRangeEnd(LocalDate.of(2015, 10, 7));
        
        Supplier testSupplier = createSampleSupplier();

        double calculatedScore = calc.calculateScore(testSupplier);
        double expectedScore = (80 + 100) / 2.0;
        assertEquals(expectedScore, calculatedScore, 0.001);
    }
    
    /**
     * Test the calculateScore method with a mocked supplier and just a start date
     */
    @Test
    public void testCalculateScoreDateRangeStart() {
    	
    	List<ScoreThresholdEntity> scoreThresholds = createScoreTresholds();
        ScoreCalculator calc = new ScoreCalculator(scoreThresholds);
        
        // take date range when last two deliveris were delivered
        // end date should be now
        calc.setRangeStart(LocalDate.of(2015, 10, 5));
        
        Supplier testSupplier = createSampleSupplier();

        double calculatedScore = calc.calculateScore(testSupplier);
        double expectedScore = (80 + 100) / 2.0;
        assertEquals(expectedScore, calculatedScore, 0.001);
    }
    
    /**
     * Test the calculateScore method with a mocked supplier and just an end date
     */
    @Test
    public void testCalculateScoreDateRangeEnd() {
    	
    	List<ScoreThresholdEntity> scoreThresholds = createScoreTresholds();
        ScoreCalculator calc = new ScoreCalculator(scoreThresholds);
        
        // take date range when first delivery was delivered
        // start date should be now
        calc.setRangeEnd(LocalDate.of(2015, 10, 6));
        
        Supplier testSupplier = createSampleSupplier();

        double calculatedScore = calc.calculateScore(testSupplier);
        double expectedScore = 100 / 1.0;
        assertEquals(expectedScore, calculatedScore, 0.001);
    }

    private List<ScoreThresholdEntity> createScoreTresholds() {

        List<ScoreThresholdEntity> scoreThresholds = new ArrayList<>();

        scoreThresholds.add(new ScoreThresholdEntity(100, -1, -1, 0, 0));
        scoreThresholds.add(new ScoreThresholdEntity(90, -2, -2, 1, 3));
        scoreThresholds.add(new ScoreThresholdEntity(80, -3, -3, 4, 7));
        scoreThresholds.add(new ScoreThresholdEntity(60, -7, -4, 8, 14));
        scoreThresholds.add(new ScoreThresholdEntity(40, -10, -8, 15, 38));
        scoreThresholds.add(new ScoreThresholdEntity(0, Integer.MIN_VALUE, -11, 39, Integer.MAX_VALUE));

        return scoreThresholds;
    }

    private Supplier createSampleSupplier() {

        Supplier supplier = new Supplier("001", "AwesomeSupplier");
        // delay of 9 days equals a scoring of 80 per cent
        Delivery delivery1 = new Delivery("001", LocalDate.of(2015, 10, 01), LocalDate.of(2015, 10, 06));
        delivery1.setDelay(5);
        // delay of 0 days equals 100 per cent
        Delivery delivery2 = new Delivery("002", LocalDate.of(2015,10,05), LocalDate.of(2015,10,05));
        delivery2.setDelay(0);
        Delivery delivery3 = new Delivery("003", LocalDate.of(2015,10,10), LocalDate.of(2015,10,10));
        delivery3.setDelay(0);

        List<Delivery> deliveries = supplier.getDeliveries();
        deliveries.add(delivery1);
        deliveries.add(delivery2);
        deliveries.add(delivery3);

        return supplier;
    }
}
