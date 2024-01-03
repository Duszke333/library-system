package pap.helpers;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Properties;

public class Parameters {
    @Getter
    @Setter
    private static Integer maxQueueLength;
    @Getter
    @Setter
    private static Double dailyPenalty;
    @Getter
    @Setter
    private static Double bookDamagePenalty;
    private final static String readPath = "parameters.properties";
    private final static String writePath = "src/main/resources/parameters.properties";

    public static void readParameters() {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(readPath)) {
            Properties prop = new Properties();
            prop.load(input);
            maxQueueLength = Integer.parseInt(prop.getProperty("maxQueueLength"));
            dailyPenalty = Double.parseDouble(prop.getProperty("dailyPenalty"));
            bookDamagePenalty = Double.parseDouble(prop.getProperty("bookDamagePenalty"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Parameters file corrupted");
        }
    }

    public static void writeParameters() {
        try {
            Properties prop = new Properties();
            prop.setProperty("maxQueueLength", maxQueueLength.toString());
            prop.setProperty("dailyPenalty", dailyPenalty.toString());
            prop.setProperty("bookDamagePenalty", bookDamagePenalty.toString());
            var output = new FileOutputStream(writePath);
            prop.store(output, null);
            output.close();
            System.out.println("Parameters saved "+prop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
