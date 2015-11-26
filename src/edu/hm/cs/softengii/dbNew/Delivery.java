
/**Class representing the necessary data for each delivery.
 * @author Maximilian Renk
 */
public class Delivery {
	
	private final String EBELN;
	private final int EBELP;
	private final String EINDT;
	private final String SLFDT;

    public Delivery(final String EBELN, final int EBELP, final String EINDT, final String SLFDT) {
		this.EBELN = EBELN;
		this.EBELP = EBELP;
		this.EINDT = EINDT;
		this.SLFDT = SLFDT;
	}

	public String getEBELN() {
		return EBELN;
	}

	public int getEBELP() {
		return EBELP;
	}

	public String getEINDT() {
		return EINDT;
	}

	public String getSLFDT() {
		return SLFDT;
	}
}