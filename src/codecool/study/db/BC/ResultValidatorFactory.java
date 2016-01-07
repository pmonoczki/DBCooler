package codecool.study.db.BC;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public class ResultValidatorFactory  {

    private static Map<String, String> myMap = new HashMap<String, String>();

    public static IResultValidator createResultValidator(String aName){

        return getValidatorNameByExercise(aName).isEmpty() ? new DefaultResultValidator() : null;

    }

    private static String getValidatorNameByExercise(String aName){
        if (myMap.containsKey(aName)) return myMap.get(aName);
        return "";
    }
}
