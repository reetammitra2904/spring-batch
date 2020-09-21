package app;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Set;

public class JobManager {

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobOperator jobOperator;

    @Autowired
    @Qualifier("simpleJobLauncher")
    JobLauncher jobLauncher;

    @Autowired
    JobRegistry jobRegistry;

    @Autowired
    JobInstantiator jobInstantiator;

    public BatchStatus getBatchStatus(String jobName) {
        Set<JobExecution> jobExecutionSet = jobExplorer.findRunningJobExecutions(jobName);
        for (JobExecution jobExecution : jobExecutionSet) {
            return jobExecution.getStatus();
        }
        return null;
    }

    public Long runNewJob(String jobName) throws DuplicateJobException, JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Job job = jobInstantiator.createNewJob(jobName);
        JobExecution jobExecution = jobLauncher.run(jobInstantiator.createNewJob(jobName),
                jobInstantiator.createJobParameters());
        jobRegistry.register(new ReferenceJobFactory(job));
        return jobExecution.getId();
    }

    public boolean isJobRunning(String jobName) {
        if (jobExplorer.findRunningJobExecutions(jobName).size() > 0) {
            return true;
        }
        return false;
    }

    public BatchStatus getJobStatus(String jobName) {
        JobInstance jobInstance = jobExplorer.getLastJobInstance(jobName);
        if (jobInstance != null) {
            //have to sort the job executions by timestamp
            List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
            for (JobExecution jobExecution : jobExecutions) {
                return jobExecution.getStatus();
            }
        }
        return null;
    }

    public boolean stopRunningJob(String jobName) throws NoSuchJobExecutionException, JobExecutionNotRunningException,
            JobExecutionAlreadyRunningException {
        if (isJobRunning(jobName)) {
            JobExecution jobExecution = getJobExecution(jobName);
            System.out.println("The job execution id is " + jobExecution.getId());
            jobOperator.stop(jobExecution.getId());
            return true;
        }
        return false;
    }

    public boolean resumeRunningJob(String jobName) throws JobParametersInvalidException, JobRestartException,
            JobInstanceAlreadyCompleteException, NoSuchJobExecutionException, NoSuchJobException {
        if (getJobStatus(jobName).equals(BatchStatus.STOPPED)) {
            JobExecution jobExecution = getJobExecution(jobName);
            jobOperator.restart(jobExecution.getId());
            return true;
        }
        return false;
    }

    private JobExecution getJobExecution(String jobName) {
        //have to sort the list by timestamp
        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobExplorer.getLastJobInstance(jobName));
        for (JobExecution jobExecution : jobExecutions) {
            return jobExecution;
        }
        return null;
    }
}
