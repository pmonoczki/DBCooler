package codecool.study.db;

import codecool.study.db.CLI.CLAParser;
import codecool.study.db.CLI.Option;
import codecool.study.db.database.*;

import java.io.File;
import java.util.ArrayList;

public class Main {

    static ArrayList<String> aFileList = new ArrayList<String>();
    static int myLimit = 5;

    public static void main(String[] args) throws Exception {

        if (args.length == 0 ){
            printHelp();
            return ;
        }

        CLAParser p = new CLAParser();
        QueryProcessor qp = new QueryProcessor();

        for(Option o: p.Parse(args)){

            if (o.flag.equals( "-f")){
                aFileList.add(o.opt);
            }
            if (o.flag.equals( "-d")){
                setFilesForFolder(new File(o.opt));
            }

            if (o.flag.equals( "-l")){
                if (o.opt.endsWith(".sql"))
                    myLimit = Integer.parseInt( o.opt);
            }
        }

        qp.handleScripts(aFileList, myLimit);
        System.out.println("Program has been finished");

    }

    private static void setFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles( )) {
            if (fileEntry.isDirectory()) {
                setFilesForFolder(fileEntry);
            }
            else {
                if (fileEntry.getName().endsWith(".sql")) {
                    aFileList.add(fileEntry.getPath());
                }
            }
        }
    }


    private static void printHelp(){
        System.out.println("Codecool Kft. All rights are reserved");
        System.out.println("This tool is validates complex queries you are writing");
        System.out.println("The name of the scriptfile is defined.");
        System.out.println("The connection can be configured.");
        System.out.println("Precondition is the NOERTHWIND DB schema is already exist.");
        System.out.println("Usage of the tool");
        System.out.println("java -jar DBCooler.jar [flag] [option]");
        System.out.println("-f filepath");
        System.out.println("-d folderpath");
        System.out.println("Without argument the help will be displayed.");
    }
}
