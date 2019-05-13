package com.aon04.backend.controllers;

import com.aon04.backend.factories.UserFactory;
import com.aon04.backend.models.User;
import com.aon04.backend.models.input.UserInput;
import com.aon04.backend.repository.IUserRepository;
import com.aon04.backend.services.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ILogService log;

    @Autowired
    private IUserRepository userRepository;

    public UserController(ILogService log) {
        this.log = log;
    }

    /**
     * Get all users from the database.
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        Iterable<User> allUsers = userRepository.findAll();

        if (((List<User>) allUsers).isEmpty()) {
            log.error("No users found.", this.getClass());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    /**
     * Get all students from the database.
     * @return
     */
    @GetMapping("/all/student")
    public ResponseEntity<?> getAllStudents() {
        Iterable<User> allStudentUsers = userRepository.findAllStudents();

        if (((List<User>) allStudentUsers).isEmpty()) {
           log.error("No students found.", this.getClass());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allStudentUsers, HttpStatus.OK);
    }

    /**
     * Get A user by id.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            User user = userRepository.findById(id).get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
           log.error("User with id: " + id + " not found.", this.getClass());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Get user by firstname & lastname.
     * @param firstName
     * @param lastName
     * @return
     */
    @GetMapping("/name")
    public ResponseEntity<?> getUserByName(@RequestParam("FirstName") String firstName, @RequestParam("LastName") String lastName) {
        try {
            User user = userRepository.findUserByName(firstName, lastName);
            if(user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            log.error("User with name: " + firstName + " " + lastName + " not found.", this.getClass());
        } catch (Exception e) {
            log.error("Error finding User with name: " + firstName + " " + lastName + " not found: " + e.getMessage(), this.getClass());
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    /**
     * Get user by studentNumber
     * @param number
     * @return
     */
    @GetMapping("/number/{number}")
    public ResponseEntity<?> getUserByNumber(@PathVariable String number) {
        User user = new User();
        try {
            user = userRepository.findUserByStudentNumber(number);
            if(user != null)
            {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            log.error("User with number: " + number + " not found.", this.getClass());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.error("Error finding User with number: " + number + " not found: " + e.getMessage(), this.getClass());

            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Add a user to database.
     * @param userInput = firstName, lastName, studentNumber
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody UserInput userInput) {
        try {
            User newUser = UserFactory.createFromUserInput(userInput);
            userRepository.save(newUser);
            log.info("User " + userInput.getFirstName() + " " + userInput.getLastName() + " added.", this.getClass());
            return new ResponseEntity<>(created(URI.create("/add/create/" + newUser.getId())).build(), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            log.error("User with studentnumber: " + userInput.getStudentNumber() + " cannot be created. ElementNotFound", this.getClass());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Delete user by Id.
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        try {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            log.info("User " + user.getFirstName() + " " + user.getLastName() + " deleted.", this.getClass());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(LocalDateTime.now().toString() + "  ERROR " + e.getMessage() + " in UserController.deleteUser: user with id: " + id + " Not found.");
           log.error("User with id: " + id + " Not found.", this.getClass());

            return notFound().header(HttpHeaders.CONTENT_DISPOSITION, "User with id: " + id + " Not Found").build();
        }

    }
}