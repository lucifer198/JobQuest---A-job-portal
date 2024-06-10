package com.krish.jobquestbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> allJobApplications() {
        return jobApplicationRepository.findAll();
    }

    public Optional<JobApplication> singleJobApplication(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }

    public JobApplication createJobApplication(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication); // ✅ save() for JPA
    }

    public JobApplication updateStatus(Long applicationId, String newStatus) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Job application not found"));

        application.setStatus(newStatus);
        return jobApplicationRepository.save(application); // ✅ save() again for update
    }
}
