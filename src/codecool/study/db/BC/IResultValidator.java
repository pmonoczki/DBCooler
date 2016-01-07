package codecool.study.db.BC;

import java.sql.ResultSet;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public interface IResultValidator {

    public boolean isMatch(ResultSet aSet);
}
