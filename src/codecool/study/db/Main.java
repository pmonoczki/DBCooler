package codecool.study.db;

import codecool.study.db.CLI.CLAParser;
import codecool.study.db.CLI.Option;
import codecool.study.db.database.DatabaseConnection;
import codecool.study.db.database.*;

import java.io.File;
import java.util.ArrayList;

public class Main {

    static ArrayList<String> aFileList = new ArrayList<String>();
    static int myLimit = 5;

    public static void main(String[] args) throws Exception {

        if (args.length == 1 ){
            printHelp();
            return;
        }

        CLAParser p = new CLAParser();
        QueryProcess qp = new QueryProcess();

        for(Option o: p.Parse(args)){

            if (o.flag.equals( "-f")){
                aFileList.add(o.opt);
            }
            if (o.flag.equals( "-d")){
                setFilesForFolder(new File(o.opt));
            }

            if (o.flag.equals( "-l")){
                myLimit = Integer.parseInt( o.opt);
            }
        }

        qp.handleScripts(aFileList, myLimit);
        System.out.println("READY");

    }

    // TODO: ONLY SQL!!!
    private static void setFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                setFilesForFolder(fileEntry);
            } else {
                aFileList.add(fileEntry.getName());
            }
        }
    }


    private static void printHelp(){
        System.out.println("Codecool Kft. All rights are reserved");
        System.out.println("Usage of the tool");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
}
