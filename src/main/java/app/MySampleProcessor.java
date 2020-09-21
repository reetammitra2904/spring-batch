package app;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import java.util.List;

/**
 * Processor to be used in the step function
 */
@StepScope
public class MySampleProcessor implements ItemProcessor<List<MySampleObject>, List<MySampleObject>> {

    @Override
    public List<MySampleObject> process(List<MySampleObject> dataList) throws Exception {
        System.out.println("Processing the data");
        for (MySampleObject sampleObject : dataList) {
            System.out.println("The object id is " + sampleObject.getId() + " and the name is " + sampleObject.getName());
            Thread.sleep(5000);
        }
        System.out.println("The data has been processed");
        return dataList;
    }
}
