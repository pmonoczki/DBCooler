package codecool.study.db;

import codecool.study.db.CLI.CLAParser;
import codecool.study.db.database.DatabaseConnection;
import codecool.study.db.database.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        //CLAParser p = new CLAParser();
        //p.Parse(args);



        QueryProcess qp = new QueryProcess();
        ArrayList<String> aFileList = new ArrayList<String>();
        aFileList.add("c:\\WS\\test.sql.txt");
        qp.handleScripts(aFileList);

        System.out.println("Stop");

    }
}
