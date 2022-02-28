package com.airfrance.userApp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airfrance.userApp.dto.request.CreateAccountRequest;
import com.airfrance.userApp.dto.response.ApiResponse;
import com.airfrance.userApp.entity.User;
import com.airfrance.userApp.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAll() {

        List<User> userList = userService.getAll();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getById(@PathVariable("id") Long id) {

        Optional<User> userById = userService.getUserById(id);
        ApiResponse<User> apiResponse = new ApiResponse<>();

        if(!(userById.isPresent())) {
            apiResponse.setMessage("user is not available");
            apiResponse.setTime(LocalDateTime.now().toString());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

        apiResponse.setMessage("user is present");
        apiResponse.setTime(LocalDateTime.now().toString());
        apiResponse.setData(userById.get());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<User>> create(@Valid @RequestBody CreateAccountRequest request) {

        ApiResponse<User> apiResponse = new ApiResponse<>();

        if(request.getCountry() == null || request.getCountry().isEmpty()){
            apiResponse.setMessage("country is mandatory");
            apiResponse.setTime(LocalDateTime.now().toString());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        if(!request.getCountry().equals("French")){
            apiResponse.setMessage("You cannot create account reason - you are not from French");
            apiResponse.setTime(LocalDateTime.now().toString());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        if(request.getUsername() == null || request.getUsername().isEmpty()){
            apiResponse.setMessage("username is mandatory");
            apiResponse.setTime(LocalDateTime.now().toString());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        if(request.getBirthday() == null){
            apiResponse.setMessage("birthday is mandatory");
            apiResponse.setTime(LocalDateTime.now().toString());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        Period period = Period.between(request.getBirthday(), LocalDate.now());

        if(period.getYears() < 18){
            apiResponse.setMessage("You cannot create account reason - you are not an adult");
            apiResponse.setTime(LocalDateTime.now().toString());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        
        user.setUsername(request.getUsername());
        user.setBirthday(request.getBirthday());
        user.setCountry(request.getCountry());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
      
        User response = userService.createUser(user);

        apiResponse.setMessage("Successfully created !");
        apiResponse.setTime(LocalDateTime.now().toString());
        apiResponse.setData(response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
