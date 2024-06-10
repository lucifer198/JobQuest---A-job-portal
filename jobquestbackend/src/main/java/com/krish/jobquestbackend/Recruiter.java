package com.krish.jobquestbackend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recruiters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String company;

    private String location;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> jobIds = new ArrayList<>();

    public Recruiter(String name, String email, String password, String company, String location, List<String> jobIds) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.company = company;
        this.location = location;
        this.jobIds = jobIds != null ? jobIds : new ArrayList<>();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addJobId(String jobId) {
        this.jobIds.add(jobId);
    }

    public void removeJobId(String jobId) {
        this.jobIds.remove(jobId);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<String> jobIds) {
        this.jobIds = jobIds != null ? jobIds : new ArrayList<>();
    }
}
