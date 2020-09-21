package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBatchJobController {

    @Autowired
    @Qualifier("jobManager")
    JobManager jobManager;

    @GetMapping("/invokeJob/{jobName}")
    public String invokeJob(@PathVariable("jobName") String jobName) throws Exception {
        if (jobManager.isJobRunning(jobName)) {
            return "The job is already running. The status of the job is " + jobManager.getBatchStatus(jobName);
        }
        long jobId = jobManager.runNewJob(jobName);
        return "Batch job has been invoked. The job Id is " + jobId;
    }

    @GetMapping("/stopJob/{jobName}")
    public String stopJob(@PathVariable("jobName") String jobName) throws Exception {
        boolean result = jobManager.stopRunningJob(jobName);
        if (result) {
            return "The job has been stopped";
        }
        return "The job could not be stopped";
    }

    @GetMapping("/resumeJob/{jobName}")
    public String resumeJob(@PathVariable("jobName") String jobName) throws Exception {
        boolean result = jobManager.resumeRunningJob(jobName);
        if (result) {
            return "The job has been resumed";
        }
        return "The job could not be resumed";
    }


}
