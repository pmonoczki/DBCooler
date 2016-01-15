package codecool.study.db.BC;

import codecool.study.db.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormatSymbols;

import codecool.study.db.database.*;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public class ValidatorBase implements IResultValidator {

    private String myViewName = "";

    @Override
    public void setViewName(String aName) {
        this.myViewName = aName;
    }

    @Override
    public String getViewName() {
        return this.myViewName;
    }

    private boolean compareResultSets(
            ResultSet resultSet1,
            ResultSet resultSet2,
            int aLimit) throws SQLException {
        int counter = 0;
        resultSet1.first();
        resultSet2.first();
        while (resultSet1.next()) {
            resultSet2.next();
            ResultSetMetaData resultSetMetaData = resultSet1.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= count; i++) {

                if (resultSet1.getObject(i) == null &&
                        resultSet2.getObject(i) != null){
                    return false;
                }
                if (resultSet1.getObject(i) != null &&
                        resultSet2.getObject(i) == null){
                    return false;
                }

                if (resultSet1.getObject(i) == null &&
                        resultSet2.getObject(i) == null){
                    continue;
                }

                // Number type
                if (resultSet1.getType() == resultSet1.getType())
                {
                    if (ValidatorBase.isStringNumeric(resultSet1.getObject(i).toString()) &&
                            ValidatorBase.isStringNumeric(resultSet2.getObject(i).toString())){
                        if (((Number)resultSet1.getObject(i)).doubleValue() ==
                                ((Number)resultSet2.getObject(i)).doubleValue()){
                            continue;
                        }

                    }

                }
                // Any other type
                if (!resultSet1.getObject(i).toString().toLowerCase().
                        equals(resultSet2.getObject(i).toString().toLowerCase())) {


                    System.out.println("Difference:");
                    System.out.println(resultSet1.getObject(i).toString().toLowerCase());
                    System.out.println(resultSet2.getObject(i).toString().toLowerCase());

                    return false;
                }
            }
            counter++;
            if (counter == aLimit) break;
        }
        return true;
    }

    @Override
    public boolean isMatch(Query aQuery) throws Exception {
        return compareResultSets(
                aQuery.myResult.myResultList.get(0),
                getValidatedResult(aQuery.myValidator.getViewName()),
                aQuery.myLimit


                );
    }

    private static boolean isStringNumeric( String str )
    {
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if ( !Character.isDigit( str.charAt( 0 ) ) && str.charAt( 0 ) != localeMinusSign ) return false;

        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for ( char c : str.substring( 1 ).toCharArray() )
        {
            if ( !Character.isDigit( c ) )
            {
                if ( c == localeDecimalSeparator && !isDecimalSeparatorFound )
                {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    private ResultSet getValidatedResult(String aViewName) throws Exception {
        String query = "SELECT * FROM " + aViewName + " ;";
        Statement st = DatabaseConnection.
                getDbConnection(true).
                createStatement();

        return st.executeQuery(query);

    }
}
