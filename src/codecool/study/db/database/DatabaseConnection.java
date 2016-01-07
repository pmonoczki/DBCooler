package codecool.study.db.database;

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
    private static String myConfigPath;

    /**
     * The filename of the config XML.
     */
    private static final String CONFIG_FILENAME = "config.xml";

    static {
        // process the config XML and initialize static fields
        try {

            // finds the path of the config file
            myConfigPath = DatabaseConnection.class.getClassLoader()
                    .getResource(CONFIG_FILENAME).getFile();
            myConfigPath = myConfigPath.substring(1);
            System.out.println(myConfigPath);
            // the document object of config XML
            Document oConfigDocument = null;

            // parses the config file
            oConfigDocument = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(myConfigPath);

            oConfigDocument.normalize();

            Element oConfigRoot = oConfigDocument.getDocumentElement();

        } catch (Exception p_eException) {
			/*
			 * throws an Error because throwing Exceptions are not allowed in
			 * static init blocks. The name of the thrown Exception with its
			 * message can be read in the message of the Error.
			 */

            //throws exception
            ExceptionInInitializerError eError = new ExceptionInInitializerError("Forwarded: "
                    + p_eException.getClass().getName() + " : "
                    + p_eException.getMessage());
            eError.setStackTrace(p_eException.getStackTrace());
            throw eError;
        }

    }

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
    private static DatabaseConnection openDbConnection()
            throws Exception {

        // parses the config file
        Document oConfigDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(myConfigPath);
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
    public static Connection getDbConnection() throws Exception {

        return openDbConnection().myConnection;
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
