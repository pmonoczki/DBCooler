package codecool.study.db.BC;

import codecool.study.db.database.*;
import java.sql.ResultSet;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
class DefaultResultValidator implements IResultValidator {

    private String myViewName = "";

    @Override
    public void setViewName(String aName) {
        this.myViewName = aName;
    }

    @Override
    public String getViewName() {
        return this.myViewName;
    }

    @Override
    public boolean isMatch(Query aQuery) {
        System.out.println("The exercise name is not valid. Please check your script name.");
        return false;
    }
}
