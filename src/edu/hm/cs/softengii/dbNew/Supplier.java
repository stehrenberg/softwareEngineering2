

import java.util.ArrayList;

/**Class representing the necessary data for each supplier.
 * @author Maximilian Renk
 */
public class Supplier {

    /**Name of the supplier.*/
    private final String supplier;

    /**Deliveries from this supplier.*/
    private final ArrayList<Delivery> deliveries = new ArrayList<>();

    public Supplier(final String supplier) {
        this.supplier = supplier;
    }

	public String getSupplier() {
		return supplier;
	}
	
	public void addDelivery(final Delivery delivery) {
		deliveries.add(delivery);
	}

	public ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}
}