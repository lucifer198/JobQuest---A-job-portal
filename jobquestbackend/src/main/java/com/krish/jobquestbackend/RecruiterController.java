package com.krish.jobquestbackend;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/recruiters")
@CrossOrigin(origins = {"http://localhost:5173", "https://job-quest-client.vercel.app"})
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity<List<Recruiter>> getAllRecruiters() {
        return new ResponseEntity<>(recruiterService.allRecruiters(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Optional<Recruiter>> getSingleRecruiter(@PathVariable String email) {
        return new ResponseEntity<>(recruiterService.singleRecruiter(email), HttpStatus.OK);
    }

    @PostMapping("/{email}/appendjob")
    public ResponseEntity<?> appendJob(@PathVariable String email, @RequestBody String jobId) {
        try {
            return new ResponseEntity<>(recruiterService.addJobToRecruiter(email, jobId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{email}/removejob")
    public ResponseEntity<Recruiter> removeJob(@PathVariable String email, @RequestBody String jobId) {
        return new ResponseEntity<>(recruiterService.removeJobFromRecruiter(email, jobId), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Recruiter recruiter) {
        Optional<Recruiter> existingRecruiter = recruiterService.singleRecruiter(recruiter.getEmail());
        if (existingRecruiter.isPresent()) {
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(recruiterService.createRecruiter(recruiter), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        String email = payload.get("email");
        String password = payload.get("password");

        try {
            Optional<Recruiter> recruiterOpt = recruiterService.singleRecruiter(email);
            if (recruiterOpt.isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "Email not found"), HttpStatus.NOT_FOUND);
            }

            Recruiter recruiter = recruiterOpt.get();

            if (!passwordEncoder.matches(password, recruiter.getPassword())) {
                return new ResponseEntity<>(Map.of("error", "Wrong password"), HttpStatus.UNAUTHORIZED);
            }

            // ✅ Ensure jobIds is never null
            if (recruiter.getJobIds() == null) {
                recruiter.setJobIds(new ArrayList<>());
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            HttpSession session = request.getSession(true);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", session.getId());
            System.out.println("Recruiter on login: " + recruiter);
            System.out.println("Recruiter jobIds: " + recruiter.getJobIds());
            responseBody.put("recruiter", recruiter);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(Map.of("error", "Authentication error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}
