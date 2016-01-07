package codecool.study.db.database;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
class QueryExecutor {

    public QueryResult getExecutedQueryResult(Query aQuery) {

        ArrayList<String> aQueries = new SQLReader().createQueries(aQuery.myFileName);

        ArrayList<ResultSet> aRSList = new ArrayList<ResultSet>();

        try {
            Statement aST =
                    DatabaseConnection.getDbConnection().createStatement();
            for (String query : aQueries) {

                aRSList.add(aST.executeQuery(query));
            }

            QueryResult aResult = new QueryResult();
            aResult.myResultList = aRSList;
            return aResult;

        } catch (Exception e) {
            // TODO: NEM JO A QUERY EZT KEZELNI KELL
            e.printStackTrace();
        }

        return null;
    }
}


