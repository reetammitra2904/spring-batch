package app;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.print.attribute.standard.JobName;
import java.util.List;

public class JobInstantiator {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("jobExecutionListener")
    JobExecutionListener jobExecutionListener;

    public Job createNewJob(String jobName) {
        return jobBuilderFactory.get(jobName)
                .incrementer(new RunIdIncrementer()).listener(jobExecutionListener)
                .flow(createStep(jobName)).end().build();
    }

    public JobParameters createJobParameters() {
        return new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .addString("testParameter", "hello world")
                .toJobParameters();
    }

    private Step createStep(String stepName) {
        return stepBuilderFactory.get(stepName). <List<MySampleObject>, List<MySampleObject> > chunk(1)
                .reader(new MySampleReader()).processor(new MySampleProcessor())
                .writer(new MySampleWriter()).build();
    }
}
