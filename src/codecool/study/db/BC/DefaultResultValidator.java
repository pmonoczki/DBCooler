package codecool.study.db.BC;

import java.sql.ResultSet;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
class DefaultResultValidator implements IResultValidator {
    @Override
    public boolean isMatch(ResultSet aSet) {
        return true;
    }
}
