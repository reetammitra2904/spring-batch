package app;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Sample writer to write the output
 */
@StepScope
public class MySampleWriter implements ItemWriter<List<MySampleObject>> {

    @Override
    public void write(List<? extends List<MySampleObject>> items) throws Exception {
        System.out.println("Writing the data");
        System.out.println("Finished writing the data");
    }
}
