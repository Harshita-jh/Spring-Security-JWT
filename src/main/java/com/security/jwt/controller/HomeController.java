package com.security.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.model.JwtRequest;
import com.security.jwt.model.JwtResponse;
import com.security.jwt.model.Student;
import com.security.jwt.repository.StudentRepository;
import com.security.jwt.service.UserService;
import com.security.jwt.utility.JWTUtility;

@RestController
public class HomeController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    
    @Autowired
	StudentRepository studentRepository;

    @GetMapping("/")
    public String home() {
        return "Welcome to Spring Security with JWT!!";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }
    
  //Get all the Student
  	//localhost:8081/students
  	@GetMapping("/students")
  	public List<Student> getAllStuednt() {
  		List<Student> students = studentRepository.findAll();
  		return students;
  	}
  	
  	//id passed as pathvariable
  	@GetMapping("/student/{id}")
  	public Student getStudent(@PathVariable int id) {
  		Student student = studentRepository.findById(id).get();
  		return student;
  		
  	}
  	
  	@PostMapping("/student/add")
  	@ResponseStatus(code = HttpStatus.CREATED)
  	public void createStudent(@RequestBody Student student) {
  		studentRepository.save(student);
  		
  	}
  	
  	@PutMapping("/student/update/{id}")
  	public Student updateStudents(@PathVariable int id) {
  		Student student = studentRepository.findById(id).get();
  		student.setName("Harsh");
  		student.setPercentage(98);
  		student.setBranch("ECE");
  		studentRepository.save(student);
  		return student;
  	}
  	
  	@DeleteMapping("/student/delete/{id}")
  	public void removeStudent(@PathVariable int id) {
  		Student student = studentRepository.findById(id).get();
  		studentRepository.delete(student);
  	}
}
