/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/
package edu.hm.cs.softengii.db.sap;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.SupplierClass;
import edu.hm.cs.softengii.db.dataStorage.SupplierClassificationThresholdEntity;
import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;

/**
 * Represents the connection to NoKlooBoutIT's SAP database.
 */
public class Database implements IDatabase {

    private static Database instance = null;

    /** Database URL */
    private final static String DB_URL = SettingsPropertiesHelper.getInstance().getSapDbUrl();

    /** Credentials for connection to the database */
    private final static String USER = SettingsPropertiesHelper.getInstance().getSapDbUser();
    private final static String PASSWORD = SettingsPropertiesHelper.getInstance().getSapDbPswd();

    /** Connection to the database. */
    private Connection connection;

    /** Contains the supplier along with their deliveries during runtime. */
    private List<Supplier> supplierData = new ArrayList<>();

    /**
     * Ctor, loading supplier data upon creation of database instance.
     */
    private Database() {
        Runnable dataLoader = () -> loadSupplierData();
        new Thread(dataLoader).start();
    }

    /**
     * Returns a db instance, if existant.
     * Creates and returns a new instance of the db otherwise.
     * @return A db instance.
     */
    public static Database getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    public static void createInstance() {
        instance = new Database();
    }

    @Override
    public boolean establishConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                //TODO PW vor finaler Abgabe aendern
                connection = DriverManager.getConnection(DB_URL, USER, "2N682Gsa");//decryptPassword(PASSWORD));
            }
        } catch (SQLException e) {
            ErrorMessage.show(e);
            return false;
        }
        return true;
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            ErrorMessage.show(e);
        }
    }

    @Override
    public List<String> getSuppliersList() {

    	List<String> suppliers = new ArrayList<>();
    	
        if (establishConnection()) {
        	
        	try {
        		String query = "SELECT * FROM lfa1";
        		
        		Statement statement = connection.createStatement();
        		ResultSet set = statement.executeQuery(query);
        		
        		while (set.next()) {
        			suppliers.add(set.getString("name1"));
        		}
        		
        	} catch (SQLException e) {
        		ErrorMessage.show(e);
        	}
        	
        	closeConnection();
        	
        }

        return suppliers;
    }

    @Override
    public List<Supplier> getSupplierData() {
        return supplierData;
    }

    /**
     * Loads all necessary data, i.e. all suppliers with their deliveries,
     * and stores it in a local List.
     */
    private void loadSupplierData() {

        List<Supplier> suppliers = new ArrayList<>();
        if (establishConnection()) {
        	try {
        		ResultSet set = getSupplierDataFromDB();
        		
        		while (set.next()) {
        			
        			Supplier supplier = findSupplierInList(suppliers, set.getString("lfa1.LIFNR"));
        			
        			if (supplier == null) {
        				supplier = new Supplier(set.getString("lfa1.LIFNR"), set.getString("NAME1"));
        				suppliers.add(supplier);
        			}
        			
        			// generate delivery entry
        			Date actualDate = set.getDate("BUDAT");
        			Date promisedDate = set.getDate("SLFDT");
        			boolean isRelevantDelivery = actualDate != null && promisedDate != null;
        			
        			if (isRelevantDelivery) {
        				Delivery delivery = generateDelivery(set, actualDate, promisedDate);
        				supplier.getDeliveries().add(delivery);
        			}
        		}
        	} catch (SQLException e) {
        		ErrorMessage.show(e);
        	}
        	
        	supplierData = suppliers;
        	closeConnection();
        }

        assignSupplierClasses(suppliers);

    }

    /**
     * Assigns classes based on suppliers' total delivery count
     * @param suppliers List of suppliers.
     */
    private void assignSupplierClasses(List<Supplier> suppliers) {

    	ArrayList<SupplierClassificationThresholdEntity> thresholds =
    			DatabaseDataStorage.getInstance().getSupplierClassificationThresholds();
    	
        suppliers.stream().forEach(supplier -> {
            supplier.setSupplierClass(this.getClassforSupplier(supplier, thresholds));
        });
    }
    
    /**
     * Get the classification for a supplier
     * @param supplier Supplier
     * @param thresholds Classification thresholds
     * @return Classification
     */
    private SupplierClass getClassforSupplier(Supplier supplier,
    		ArrayList<SupplierClassificationThresholdEntity> thresholds) {
    	
    	int deliveryCount = supplier.getDeliveries().size();
        SupplierClass suppClass = SupplierClass.NORMAL;
    	
    	// Search for classification
        for (SupplierClassificationThresholdEntity threshold: thresholds) {
        	
        	if (deliveryCount >= threshold.getDeliveriesMin() &&
        		deliveryCount <= threshold.getDeliveriesMax()) {
        		
        		// found!!
        		suppClass = threshold.getClassName();
        		break;
        	}
        }
        
        return suppClass;
    }

    /**
     * Loads all data from the database that is relevant for the analytics application.
     * @return The result set as received from the database.
     * @throws SQLException
     */
    private ResultSet getSupplierDataFromDB() throws SQLException {

        String query = "SELECT DISTINCT "
            + "eket.EBELN, SLFDT, BUDAT, lfa1.LIFNR, NAME1, DATEDIFF(BUDAT, SLFDT) AS DIFF "
            + "FROM eket, ekbe, ekko, lfa1 "
            + "WHERE ekko.EBELN = ekbe.EBELN "
            + "AND eket.ebeln = ekbe.ebeln "
            + "AND lfa1.lifnr = ekko.lifnr "
            + "AND VGABE = 1";

        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }

    /**
     * Retrieves a supplier from the suppliers' list by his ID.
     * @param suppliers The list containing all suppliers up to now as retrieved from the db.
     * @param id Suppliers' id as in SAP db --> lfa1.lifnr
     * @return The desired supplier.
     */
    private static Supplier findSupplierInList(List<Supplier> suppliers, String id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(id)) {
                return supplier;
            }
        }
        return null;
    }

    /**
     * Generates a new delivery with the given data from the database.
     * @param set The result set from the database query.
     * @param actualDate The actual goods receipt date for a delivery.
     * @param promisedDate The delivery date as initially promised by the supplier.
     * @return A delivery object.
     * @throws SQLException
     */
    private Delivery generateDelivery(ResultSet set, Date actualDate, Date promisedDate) throws SQLException {

        String deliveryID = set.getString("eket.EBELN");
        LocalDate actual = actualDate.toLocalDate();
        LocalDate promised = promisedDate.toLocalDate();

        Delivery delivery = new Delivery(deliveryID, promised, actual);

        delivery.setDelay(set.getInt("DIFF"));

        return delivery;
    }

    /**
     * Decrypts the password for the SAP database connection.
     * @param cryptedPassword The encrypted password retrieved from the application's settings
     * @return The password.
     */
    private String decryptPassword(final String cryptedPassword) {

        byte[] keyBytes = new byte[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        String password = "";

            Cipher cipher;
			try {
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, key);
				byte[] decryptedPwd = cipher.doFinal(cryptedPassword.getBytes());
				password = new String(decryptedPwd);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}

        return password;
    }
}
