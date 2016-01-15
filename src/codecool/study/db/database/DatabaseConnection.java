package codecool.study.db.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the DB connection. Only one connection is used, which is stored by
 * this class. If the connection doesn't exist it is created.
 *
 * @author pmonoczki
 */
public class DatabaseConnection {


    /**
     * Stores the DB connection.
     */
    private Connection myConnection;

    /**
     * Stores the path of the config XML.
     */
    private static InputStream myConfig;

    /**
     * The filename of the config XML.
     */
    private static final String CONFIG_FILENAME = "config.xml";

    private static final String VALIDATOR_CONFIG_FILENAME = "validator-config.xml";
    

    /**
     * Private constructor ensures to use this class only in static way.
     */
    private DatabaseConnection(Connection p_oConnetcion) {
        myConnection = p_oConnetcion;
    }

    /**
     * Opens a new DB connection using the parameters in the congig XML.
     *
     * @throws Exception if an error occurs
     */
    private static DatabaseConnection openDbConnection(boolean isValidatorConn)
            throws Exception {

        // ISSUE: cooldrs cannot edit the config
        isValidatorConn = true;
        if (isValidatorConn){
            myConfig = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream(
                            VALIDATOR_CONFIG_FILENAME);

        }
        else{
            myConfig = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream(CONFIG_FILENAME);

        }
        // parses the config file
        Document oConfigDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(myConfig);
        oConfigDocument.normalize();

        Element oConfigRoot = oConfigDocument.getDocumentElement();

        // gets the parameters of the DB connenction
        Element oDatabaseConnection = (Element) oConfigRoot
                .getElementsByTagName("DatabaseConnection").item(0);

        String sDriver = oDatabaseConnection.getElementsByTagName("Driver")
                .item(0).getChildNodes().item(0).getNodeValue();

        String sURL = oDatabaseConnection.getElementsByTagName("URL").item(0)
                .getChildNodes().item(0).getNodeValue();

        String sUsername = oDatabaseConnection.getElementsByTagName("Username")
                .item(0).getChildNodes().item(0).getNodeValue();

        String sPassword = oDatabaseConnection.getElementsByTagName("Password")
                .item(0).getChildNodes().item(0).getNodeValue();

        // sets the driver of the DB connetcion
        Class.forName(sDriver);

        // opens the new connection
        Connection oConnection = DriverManager.getConnection(sURL, sUsername, sPassword);

        DatabaseConnection oDBConnection = new DatabaseConnection(oConnection);


        return oDBConnection;

    }

    /**
     * Returns the DB connection. If there is an existing connection, returns
     * that, else creates a new one.
     *
     * @return the DB connection.
     * @throws Exception if an error occurs.
     */
    public static Connection getDbConnection(boolean isValidatorConn) throws Exception {
        return openDbConnection(isValidatorConn).myConnection;

    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void valueBound() {

    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void valueUnbound() {

        try {
            myConnection.close();
        } catch (SQLException anSQLE) {
            anSQLE.printStackTrace();
        }
    }
}
