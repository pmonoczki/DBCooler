package codecool.study.db.BC;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by monoc_000 on 2016. 01. 07..
 */
public class ResultValidatorFactory {

    static {
        myMap = new HashMap<String, String>();
        try {
            setMapping();
        } catch
                (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> myMap;

    public static IResultValidator createResultValidator(String aName) {

        return getValidatorNameByExercise(aName).isEmpty() ? new DefaultResultValidator() : new ValidatorBase();

    }

    public static String getValidatorNameByExercise(String aName) {

        if (myMap.containsKey(aName)) return myMap.get(aName.toLowerCase());
        return "";
    }

    private static void setMapping() throws Exception {
        InputStream myConfig = ResultValidatorFactory.class.getClassLoader()
                .getResourceAsStream("validator-mapping.xml");

        Document oConfigDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(myConfig);
        oConfigDocument.normalize();

        Element oConfigRoot = oConfigDocument.getDocumentElement();

        for (int u = 0; u < oConfigRoot
                .getElementsByTagName("validator").getLength(); u++)

        {

            Element cd = (Element) oConfigRoot
                    .getElementsByTagName("validator").item(u);
            String aValue = cd.getElementsByTagName("view").item(0)
                    .getChildNodes().item(0).getNodeValue();
            String aKey = cd.getElementsByTagName("exercise").item(0)
                    .getChildNodes().item(0).getNodeValue();

            myMap.put(aKey.toLowerCase(), aValue);
        }
    }
}
