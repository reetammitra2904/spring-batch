package app;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

    @Autowired
    JobRepository jobRepository;

    @Bean(name = "simpleJobLauncher")
    public JobLauncher simpleJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean(name = "jobExecutionListener")
    public JobExecutionListener jobExecutionListener() throws Exception {
        return new SpringBatchJobCompletionListener();
    }

    @Bean(name="jobManager")
    public JobManager jobManager() {
        return new JobManager();
    }

    @Bean(name="jobInstantiator")
    public JobInstantiator jobInstantiator() {
        return new JobInstantiator();
    }
}
