package app;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@StepScope
public class MySampleReader implements ItemReader<List<MySampleObject>> {

    @Value("#{jobParameters['testParameter']}")
    private String testParameter;

    private int count = 0;

    @Override
    public List<MySampleObject> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        count++;
        if (count == 1) {
            System.out.println("Reading the data");
            return DataGenerator.generateData();
        }
        return null;
    }

}
