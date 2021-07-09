//todo
/*
package parser;

import entity.Cone;
import exception.ParseException;
import factory.ConeFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.ConeValidator;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final Logger logger = LogManager.getLogger();

    public static List findCones(String str) {
        ArrayList<Cone> list = new ArrayList<>();

        str = str.replaceAll("\r", "");

        String[] splitted = str.split("\n");
        double[] coneParams = new double[5];
        long id;

        for (String string : splitted) {

            String[] splittedAgain = string.split(" ");

            if (splittedAgain.length == 6) {
                try {
                    if (splittedAgain[0].matches("\\d{8}")) {
                        id = Long.parseLong(splittedAgain[0]);
                    } else {
                        throw new ParseException("Wrong line " + splittedAgain[0]);
                    }

                    for (int i = 1; i < 6; i++) {
                        if (splittedAgain[i].matches("[-+]?\\d+")) {
                            coneParams[i - 1] = Double.parseDouble(splittedAgain[i]);
                        } else {
                            throw new ParseException("Wrong line " + splittedAgain[i]);
                        }
                    }
                    ConeFactory coneFactory = new ConeFactory();
                    Cone customCone = coneFactory.createCone(id, coneParams);
                    ConeValidator validator = new ConeValidator();
                    if (validator.validate(customCone)) {
                        list.add(coneFactory.createCone(id, coneParams));
                    } else {
                        logger.log(Level.WARN,customCone);
                        logger.log(Level.WARN,"contains wrong parameters");
                    }
                } catch (ParseException exception) {
                    exception.printStackTrace();
                    logger.error(exception);
                }
            }
        }
        return list;
    }
}
*/
