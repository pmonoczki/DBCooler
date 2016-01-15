package codecool.study.db.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
class QueryExecutor {

    public QueryResult getExecutedQueryResult(Query aQuery) {

        aQuery.myQueryList = new SQLReader().createQueries(aQuery.myFileName);
        ArrayList<ResultSet> aResultSetList = new ArrayList<ResultSet>();
        int aScriptNum = 0;
        try {
            Statement aQueryStatement =
                    DatabaseConnection.
                            getDbConnection(false).
                            createStatement();
            for (String query : aQuery.myQueryList) {

                aResultSetList.add(aQueryStatement.executeQuery(query));
            }

            aScriptNum++;

            QueryResult aResult = new QueryResult();
            aResult.myResultList = aResultSetList;
            return aResult;

        } catch (Exception e) {

            System.out.println("Not runnable script");
            System.out.println(e.getMessage());
            System.out.println("TAKE CARE OF THE TABLE AND COLUMNS ARE CASE SENSITIVE!!!");
            System.out.println("Error in file: " + aQuery.myFileName);
            System.out.println("Error at script: " + aQuery.myQueryList.get(aScriptNum));
        }

        return null;
    }
}


