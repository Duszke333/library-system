package pap.helpers;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Properties;

public class Parameters {
    /**
    * A class that holds all parameters related to the library system.
    */
    @Getter
    @Setter
    private static Integer maxQueueLength;
    @Getter
    @Setter
    private static Double dailyPenalty;
    @Getter
    @Setter
    private static Double bookDamagePenalty;

    @Getter
    @Setter
    private static Double bookLossPenalty;

    // paths for reading and writing the parameters
    private final static String readPath = "parameters.properties";
    private final static String writePath = "src/main/resources/parameters.properties";

    public static void readParameters() {
        /*
          A method that reads the parameters from the parameters.properties file.
         */
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(readPath)) {
            // try to read all the parameters and store them in the class variables
            Properties prop = new Properties();
            prop.load(input);
            maxQueueLength = Integer.parseInt(prop.getProperty("maxQueueLength"));
            dailyPenalty = Double.parseDouble(prop.getProperty("dailyPenalty"));
            bookDamagePenalty = Double.parseDouble(prop.getProperty("bookDamagePenalty"));
            bookLossPenalty = Double.parseDouble(prop.getProperty("bookLossPenalty"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Parameters file corrupted");
        }
    }

    public static void writeParameters() {
        /*
          A method that writes the parameters to the parameters.properties file.
         */
        try {
            // set parameters values and try to store them in a file
            Properties prop = new Properties();
            prop.setProperty("maxQueueLength", maxQueueLength.toString());
            prop.setProperty("dailyPenalty", dailyPenalty.toString());
            prop.setProperty("bookDamagePenalty", bookDamagePenalty.toString());
            prop.setProperty("bookLossPenalty", bookLossPenalty.toString());
            var output = new FileOutputStream(writePath);
            prop.store(output, null);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
