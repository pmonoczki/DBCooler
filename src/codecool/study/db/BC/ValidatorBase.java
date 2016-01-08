package codecool.study.db.BC;

import codecool.study.db.database.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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

    private boolean compareResultSets (
            ResultSet resultSet1,
            ResultSet resultSet2,
            int aLimit)  throws SQLException{
        int counter = 0;
        while (resultSet1.next()) {
            resultSet2.next();
            ResultSetMetaData resultSetMetaData = resultSet1.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                if (!resultSet1.getObject(i).equals(resultSet2.getObject(i))) {
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
                5

        );
    }

    private ResultSet getValidatedResult(String aViewName) throws Exception {
        String query = "SELECT * FROM "+ aViewName + " ;";
        Statement st = DatabaseConnection.
                getDbConnection(true).
                createStatement();

        return st.executeQuery(query);

    }
}
