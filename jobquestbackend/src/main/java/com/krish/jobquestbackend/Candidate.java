package com.krish.jobquestbackend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "candidates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection
    private List<String> skills;

    public Candidate(String name, String email, String password, List<String> skills) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.skills = skills;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword() {
        return password;
    }

}
