package com.aon04.backend.controllers;

import com.aon04.backend.factories.ExamFactory;
import com.aon04.backend.models.Exam;
import com.aon04.backend.repository.IExamRepository;
import com.aon04.backend.services.Exceptions.StorageFileNotFoundException;
import com.aon04.backend.services.ILogService;
import com.aon04.backend.services.StorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/skelet/exam")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExamController {

    private final StorageService storageService;

    private final ILogService log;

    @Autowired
    private IExamRepository examRepository;

    @Autowired
    public ExamController(StorageService storageService, ILogService logService) {
        this.storageService = storageService;
        this.log = logService;
    }

    /**
     * Get all exams.
     *
     * @return Iterable<Exam>
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllExams() {
        Iterable<Exam> allUsers = examRepository.findAll();

        if (((List<Exam>) allUsers).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    /**
     * Get exam file by id.
     *
     * @param id
     * @return ResponseEntity<Resource>
     */
    @GetMapping("{id}")
    public byte[] getExambyId(@PathVariable int id) {
        Exam exam = new Exam();
        byte[] file;
        try {
            exam = examRepository.findById(id).get();
            file = IOUtils.toByteArray(new FileInputStream(storageService.load(exam.getSkelet()).toFile()));
        } catch (NoSuchElementException e) {
            log.error("Exam with id: " + id + " not found.", this.getClass());
            return null;
        } catch (StorageFileNotFoundException e) {
            log.error("Cannot find examfile: " + exam.getSkelet() + ".", this.getClass());
            return null;
        } catch (FileNotFoundException e) {
            log.error("Cannot find file with filestream: " + exam.getSkelet() + ".", this.getClass());
            return null;
        } catch (IOException e) {
            log.error("IoException on: " + exam.getSkelet() + ".", this.getClass());
            return null;
        }
        return file;
    }

    /**
     * Get exam as object by id.
     *
     * @param id
     * @return Exam
     */
    @GetMapping("/asobject/{id}")
    public Exam getExamAsObjectbyId(@PathVariable int id) {
        Exam exam = examRepository.findById(id).get();
        return exam;
    }

    /**
     * Create Exam object and save file.
     *
     * @param naam
     * @param file
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity CreateExam(@RequestParam("naam") String naam, @RequestParam("file") MultipartFile file)

    {
        Exam exam = ExamFactory.create(naam, file.getOriginalFilename());
        examRepository.save(exam);
        storageService.store(file);
        log.info("Exam " + exam.getNaam() + " created", this.getClass());
        return created(URI.create("/skelet/exam/create" + exam.getId())).build();
    }

    /**
     * Update an already existing exam.
     *
     * @param file = new exam file.
     * @param name = new exam name.
     * @param id   = id of exam.
     * @return
     */
    @PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
    public ResponseEntity UpdateExam(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @PathVariable("id") int id) {
        Exam exam = examRepository.findById(id).get();

        try {
            storageService.delete(exam.getSkelet());
            exam.setSkelet(file.getOriginalFilename());
            exam.setNaam(name);
            examRepository.save(exam);
            storageService.store(file);
        } catch (NoSuchElementException e) {
            log.error("Exam with id: " + id + " not found.", this.getClass());
            return notFound().header(HttpHeaders.CONTENT_DISPOSITION, "Exam " + exam.getSkelet() + " Not Found").build();
        } catch (StorageFileNotFoundException e) {
            log.error("Examfile with id: " + id + " not found.", this.getClass());
            return notFound().header(HttpHeaders.CONTENT_DISPOSITION, "Examfile " + exam.getSkelet() + " Not Found").build();
        }
        log.info("Exam with id: " + id + " Updated", this.getClass());
        return created(URI.create("/skelet/exam/update/" + exam.getId())).build();
    }

    /**
     * Delete a finished Exam
     *
     * @param id
     * @return ResponseEntity<Exam>
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Exam> deleteFinishedExam(@PathVariable("id") int id) {
        Exam exam = new Exam();
        try {
            exam = examRepository.findById(id).get();
            storageService.delete(exam.getSkelet());
            examRepository.delete(exam);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.error("Exam with id: " + id + " not found.", this.getClass());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (StorageFileNotFoundException e) {
            log.error("Examfile with id: " + id + " not found.", this.getClass());
            return notFound().header(HttpHeaders.CONTENT_DISPOSITION, "Examfile " + exam.getSkelet() + " Not Found").build();
        }

    }
}