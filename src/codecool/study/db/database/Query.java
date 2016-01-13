package codecool.study.db.database;

import codecool.study.db.BC.IResultValidator;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public class Query {

    public String myName;
    public String myFileName;
    public ArrayList<String> myQueryList;
    public QueryResult myResult;
    public int myLimit;
    public IResultValidator myValidator;
}
