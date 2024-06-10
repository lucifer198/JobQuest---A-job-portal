package com.krish.jobquestbackend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "job_applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String qualification;
    private String resumeLink;
    private String status;

    @ElementCollection
    private List<String> skills;

    private Long jobId;  // referencing Job.id (type: Long)

    public JobApplication(String name, String email, String phone, String qualification, String resumeLink,
                          String status, List<String> skills, Long jobId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.qualification = qualification;
        this.resumeLink = resumeLink;
        this.status = status;
        this.skills = skills;
        this.jobId = jobId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
