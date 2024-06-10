package com.krish.jobquestbackend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;
    private String company;
    private String location;
    private String experience;
    private String description;

    @ElementCollection
    private List<String> skills;

    public Job(String position, String company, String location, String experience, String description, List<String> skills) {
        this.position = position;
        this.company = company;
        this.location = location;
        this.experience = experience;
        this.description = description;
        this.skills = skills;
    }
}
