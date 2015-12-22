package edu.hm.cs.softengii.test;

import edu.hm.cs.softengii.db.dataStorage.DeliveryRange;
import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.DeliveryRangeCalculator;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DeliveryRangeCalculatorTest {

    
    /**
	 * Test the calculateDeliveryRanges method with a mocked supplier
	 */
    @Test
    public void testCalculateDeliveryRanges() {

        DeliveryRangeCalculator calc = new DeliveryRangeCalculator();
        Supplier testSupplier = createSampleSupplier();

        Map<DeliveryRange, Double> calculatedRanges = calc.calculateDeliveryRanges(testSupplier);

        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_EARLY), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.EARLY), 0.001);
        assertEquals(2.0 / 3, calculatedRanges.get(DeliveryRange.IN_TIME), 0.001);
        assertEquals(1.0 / 3, calculatedRanges.get(DeliveryRange.LATE), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_LATE), 0.001);
    }
	
	/**
	 * Test the calculateDeliveryRanges method with a mocked supplier
	 */
    @Test
    public void testCalculateDeliveryRangesDateRange() {

        DeliveryRangeCalculator calc = new DeliveryRangeCalculator();
        
        // take date range when first two deliveris were delivered
        calc.setRangeStart(LocalDate.of(2015, 10, 4));
        calc.setRangeEnd(LocalDate.of(2015, 10, 7));
        
        Supplier testSupplier = createSampleSupplier();

        Map<DeliveryRange, Double> calculatedRanges = calc.calculateDeliveryRanges(testSupplier);

        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_EARLY), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.EARLY), 0.001);
        assertEquals(0.5, calculatedRanges.get(DeliveryRange.IN_TIME), 0.001);
        assertEquals(0.5, calculatedRanges.get(DeliveryRange.LATE), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_LATE), 0.001);
    }
    
    /**
	 * Test the calculateDeliveryRanges method with a mocked supplier and just a start date
	 */
    @Test
    public void testCalculateDeliveryRangesDateRangeStart() {

        DeliveryRangeCalculator calc = new DeliveryRangeCalculator();
        
        // take date range when last two deliveris were delivered
        // end date should be now
        calc.setRangeStart(LocalDate.of(2015, 10, 5));
        
        Supplier testSupplier = createSampleSupplier();

        Map<DeliveryRange, Double> calculatedRanges = calc.calculateDeliveryRanges(testSupplier);

        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_EARLY), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.EARLY), 0.001);
        assertEquals(0.5, calculatedRanges.get(DeliveryRange.IN_TIME), 0.001);
        assertEquals(0.5, calculatedRanges.get(DeliveryRange.LATE), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_LATE), 0.001);
    }
    
    /**
	 * Test the calculateDeliveryRanges method with a mocked supplier and just an end date
	 */
    @Test
    public void testCalculateDeliveryRangesDateRangeEnd() {

        DeliveryRangeCalculator calc = new DeliveryRangeCalculator();
        
        // take date range when first delivery was delivered
        // start date should be now
        calc.setRangeEnd(LocalDate.of(2015, 10, 6));
        
        Supplier testSupplier = createSampleSupplier();

        Map<DeliveryRange, Double> calculatedRanges = calc.calculateDeliveryRanges(testSupplier);

        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_EARLY), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.EARLY), 0.001);
        assertEquals(1.0, calculatedRanges.get(DeliveryRange.IN_TIME), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.LATE), 0.001);
        assertEquals(0.0, calculatedRanges.get(DeliveryRange.VERY_LATE), 0.001);
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
