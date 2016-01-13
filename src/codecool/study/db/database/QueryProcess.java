package codecool.study.db.database;

import codecool.study.db.BC.ResultValidatorFactory;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;


/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public class QueryProcess {
    public void handleScripts(ArrayList<String> aFileList, int aLimit) throws Exception {
        ArrayList<Query> aQueryList = new ArrayList<Query>();

        for (String aFile : aFileList) {
            Query aQuery = new Query();
            aQuery.myFileName = aFile;
            aQuery.myName = new File(aFile).getName().replaceFirst("[.][^.]+$", "");
            aQuery.myLimit = aLimit;
            aQuery.myValidator = ResultValidatorFactory.
                    createResultValidator(
                            aQuery.myName
                    );
            aQuery.myValidator.setViewName(
                    ResultValidatorFactory.
                            getValidatorNameByExercise(aQuery.myName)
            );

            aQuery.myResult = new QueryExecutor().
                    getExecutedQueryResult(aQuery);
            aQueryList.add(aQuery);
        }
        printQuery(aQueryList);

    }

    private void printQuery(ArrayList<Query> aListOfQueries) throws Exception {

        for (Query q : aListOfQueries) {

            System.out.println("Name: " + q.myName);
            System.out.println("==========================");
            System.out.println("File: " + q.myFileName);
            System.out.println("==========================");
            System.out.println("Result of execution (Limit " +
                    String.valueOf(q.myLimit) + ") : ");
            System.out.println("==========================");

            for (ResultSet r : q.myResult.myResultList) {
                ResultSetMetaData rsmd = r.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                int counter = 0;
                while (r.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = r.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                    counter++;
                    if (counter == q.myLimit)
                        break;

                }
            }

            System.out.println("==========================");
            if (q.myQueryList.size() != 0) {
                System.out.println(
                        "Result validity: " +
                                String.valueOf(
                                        q.myValidator.isMatch(q)
                                )
                );
            }
            else{
                System.out.println("No script to run in file");
            }
        }

    }
}
