package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Random data generator for reading, processing and writing jobs
 */
public class DataGenerator {

    private static Random random = new Random();

    public static List<MySampleObject> generateData() {
        System.out.println("Generating data");
        List<MySampleObject> dataList = new ArrayList<>();
        int size = 10;
        System.out.println("The size of the list will be " + size);
        int temp = 0;
        while (temp < size) {
            dataList.add(new MySampleObject(random.nextInt(), RandomStringUtils.random(10)));
            temp++;
        }
        System.out.println("The data list has been generated");
        return dataList;
    }
}
