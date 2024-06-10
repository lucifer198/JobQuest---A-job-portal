package com.krish.jobquestbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/applications")
@CrossOrigin(origins = {"http://localhost:5173", "https://job-quest-client.vercel.app"})
public class JobApplicationController {

    private static final List<String> VALID_STATUS_OPTIONS = Arrays.asList("Pending", "Accepted", "Rejected");

    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobApplications() {
        return new ResponseEntity<>(jobApplicationService.allJobApplications(), HttpStatus.OK);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Optional<JobApplication>> getSingleJobApplication(@PathVariable Long jobId) {
        return new ResponseEntity<>(jobApplicationService.singleJobApplication(jobId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobApplication> applyForJob(@RequestBody JobApplication jobApplication) {
        return new ResponseEntity<>(jobApplicationService.createJobApplication(jobApplication), HttpStatus.CREATED);
    }

    @PostMapping("/{applicationId}")
    public ResponseEntity<?> updateJobApplicationStatus(@PathVariable Long applicationId, @RequestBody String newStatus) {
        if (VALID_STATUS_OPTIONS.contains(newStatus)) {
            return new ResponseEntity<>(jobApplicationService.updateStatus(applicationId, newStatus), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid option", HttpStatus.BAD_REQUEST);
        }
    }
}
