package codecool.study.db.database;


import codecool.study.db.BC.ResultValidatorFactory;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public class QueryProcess {
    public void handleScripts(ArrayList<String> aFileList) throws SQLException {
        ArrayList<Query> aQueryList = new ArrayList<Query>();

        for (String aFile : aFileList) {
            Query aQuery = new Query();
            aQuery.myFileName = aFile;
            aQuery.myName = new File(aFile).getName().replaceFirst("[.][^.]+$", "");

            aQuery.myValidator = ResultValidatorFactory.createResultValidator(aQuery.myName);

            aQuery.myResult = new QueryExecutor().getExecutedQueryResult(aQuery);
            aQueryList.add(aQuery);
        }
        printQuery(aQueryList);

    }

    private void printQuery(ArrayList<Query> aListOfQueries) throws SQLException {

        for (Query q : aListOfQueries) {

            System.out.println("Name: " + q.myName);
            System.out.println("File: " + q.myFileName);
            System.out.println("Result of execution: ");

            for (ResultSet r : q.myResult.myResultList) {
                ResultSetMetaData rsmd = r.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (r.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = r.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }
            }

            System.out.println(
                    "Result validity: " +
                            String.valueOf(q.myValidator.isMatch(
                                    q.myResult.myResultList.get(0)
                            ))
            );
        }

    }
}
