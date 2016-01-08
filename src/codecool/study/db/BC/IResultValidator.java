package codecool.study.db.BC;

import codecool.study.db.database.*;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public interface IResultValidator {

    public void setViewName(String aName);
    public String getViewName();
    public boolean isMatch(Query aQuery) throws Exception;
}
