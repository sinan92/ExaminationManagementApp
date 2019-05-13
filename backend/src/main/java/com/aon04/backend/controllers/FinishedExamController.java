package com.aon04.backend.controllers;

import com.aon04.backend.factories.FinishedExamFactory;
import com.aon04.backend.models.Exam;
import com.aon04.backend.models.FinishedExam;
import com.aon04.backend.models.User;
import com.aon04.backend.repository.IExamRepository;
import com.aon04.backend.repository.IFinishedExamRepository;
import com.aon04.backend.repository.IUserRepository;
import com.aon04.backend.services.Exceptions.StorageFileNotFoundException;
import com.aon04.backend.services.ILogService;
import com.aon04.backend.services.IZipService;
import com.aon04.backend.services.StorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/finished/exam")
public class FinishedExamController {
    private final StorageService storageService;

    private final ILogService log;

    private final IZipService zipService;

    @Autowired
    private IFinishedExamRepository finishedExamRepository;

    @Autowired
    private IExamRepository examRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    public FinishedExamController(StorageService storageService,
                                  ILogService logService,
                                  IZipService zipService) {
        this.storageService = storageService;
        this.log = logService;
        this.zipService = zipService;
    }

    /**
     * Get all finished exam objects.
     *
     * @return Iterable<FinishedExam>
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllFinishedExams() {
        Iterable<FinishedExam> allFinishedExams = finishedExamRepository.findAll();

        if (((List<FinishedExam>) allFinishedExams).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allFinishedExams, HttpStatus.OK);
    }


    /**
     * Get finished exam by user id
     *
     * @return FinishedExam
     */
    @GetMapping("/object/{id}")
    public FinishedExam getFinishedExamObjectByUserId(@PathVariable int id) {

        FinishedExam finishedExam = null;

            Iterable<FinishedExam> allFinishedExams = finishedExamRepository.findAll();
            for (FinishedExam exam : allFinishedExams) {
                if (id == exam.getUser().getId()) {
                    finishedExam = exam;
                }
            }
            return finishedExam;


    }


    /**
     * Get exam file
     *
     * @param id = id of finishedExam
     * @return byte[]
     */
    @GetMapping("{id}")
    public byte[] getFinishedExamById(@PathVariable int id) {
        byte[] file;
        FinishedExam finishedExam = new FinishedExam();
        try {
            finishedExam = finishedExamRepository.findById(id).get();
            file = IOUtils.toByteArray(new FileInputStream(storageService.load(finishedExam.getFinishedExam()).toFile()));
        } catch (NoSuchElementException e) {
            // Element is not found in the database.
            log.error("Finished exam with id: " + id + " not found.", this.getClass());
            return null;
        } catch (StorageFileNotFoundException e) {
            // File not found in the root folder.
            log.error("Cannot find finished exam file: " + finishedExam.getFinishedExam() + ".", this.getClass());
            return null;
        } catch (FileNotFoundException e) {
            log.error("Cannot find file with filestream: " + finishedExam.getFinishedExam() + ".", this.getClass());
            return null;
        } catch (IOException e) {
            log.error("IoException on: " + finishedExam.getFinishedExam() + ".", this.getClass());
            return null;
        }
        log.info("Sending file " + finishedExam.getFinishedExam(), this.getClass());
        return file;
    }

    /**
     * Create finished Exam
     *
     * @param user_id = id of user that uploads the exam.
     * @param exam_id = id of exam which is being uploaden.
     * @param file    = file of finished exam.
     * @return ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity CreateFinishedExam(@RequestParam("user_id") String user_id, @RequestParam("exam_id") String exam_id, @RequestParam("file") MultipartFile file) {
        storageService.store(file);
        Exam exam;
        User user;
        FinishedExam finishedExamAlreadyExists = getFinishedExamObjectByUserId(Integer.parseInt(user_id));

        if(finishedExamAlreadyExists == null) {


            try {
                exam = examRepository.findById(Integer.parseInt(exam_id)).get();
            } catch (NoSuchElementException e) {
                // Exam is not found in the database.
                log.error("Exam with id: " + exam_id + " Not found", this.getClass());
                return notFound().header(HttpHeaders.CONTENT_DISPOSITION,
                        "Exam with id " + exam_id + " not found").build();
            }
            try {
                // User is not found in the database.
                user = userRepository.findById(Integer.parseInt(user_id)).get();
            } catch (NoSuchElementException e) {
                log.error("user with id: " + user_id + " Not found", this.getClass());
                return notFound().header(HttpHeaders.CONTENT_DISPOSITION,
                        "User with id " + user_id + " not found").build();
            }


            //Create FinishedExam object with factory and generate MD5 hash.
            FinishedExam finishedExam = FinishedExamFactory.createFromFinishedExamInput(
                    exam,
                    user,
                    file.getOriginalFilename(),
                    storageService.generateMD5(file.getOriginalFilename())
            );

            finishedExamRepository.save(finishedExam);
            log.info(" File " + file.getOriginalFilename() + " saved", this.getClass());
            return created(URI.create("/finished/exam/create" + finishedExam.getId())).build();

        }else{
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
    }

    /**
     * Update an already created finishedExam
     *
     * @param id   = Id of finishedExam.
     * @param file = New File to upload.
     * @return ResponseEntity
     */
    @PutMapping("/update/{id}")
    public ResponseEntity UpdateFinishedExamFile(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        FinishedExam finishedExam;
        try {
            finishedExam = finishedExamRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            // FinishedExam is not found in the database.
            log.error("finishedExam with id: " + id + " Not found", this.getClass());
            return notFound().header(HttpHeaders.CONTENT_DISPOSITION,
                    "FinishedExam with id " + id + " not found").build();
        }

        // Delete old file in root location.
        storageService.delete(finishedExam.getFinishedExam());
        // Store new File.
        storageService.store(file);

        finishedExam.setFinishedExam(file.getOriginalFilename());

        finishedExamRepository.save(finishedExam);
        log.info(" File " + file.getOriginalFilename() + " saved", this.getClass());
        return created(URI.create("/finished/exam/update" + finishedExam.getId())).build();
    }

    /**
     * Get a zip of all finished Exams
     * @return ResponseEntity<Resource>
     */
    @GetMapping("/zip/all")
    public byte[] getFinishedExamsZip() {
        // Load all finished Exams from database to find files.
        Iterable<FinishedExam> finishedExams = finishedExamRepository.findAll();
        String[] finishedExamNames = new String[1024];
        for (FinishedExam finishedExam : finishedExams) {
            finishedExamNames[finishedExam.getId() - 1] = finishedExam.getFinishedExam();
        }

        // Save zip with date.
        File zip = zipService.createZipFile(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + "_finished_exams", finishedExamNames);
        Resource resource;
        try {
            resource = new UrlResource(zip.toPath().toUri());
            log.info("Zip created", this.getClass());
            return IOUtils.toByteArray(new FileInputStream(storageService.load(resource.getFile().getName() + ".zip").toFile()));
        } catch (MalformedURLException e) {
            log.error("Cannot convert zip to resource: " + e.getMessage(), this.getClass());
            return null;
        } catch (NullPointerException e) {
            log.error("Zip not found: " + e.getMessage(), this.getClass());
            return null;
        } catch (IOException e) {
            log.error("IoException on: " + e.getMessage() + ".", this.getClass());
            return null;
        }
    }

    /**
     * Exception handler for storageService.
     * @param exc
     * @return
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Delete a finished exam in the database and root location.
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FinishedExam> deleteFinishedExam(@PathVariable("id") int id) {
        FinishedExam finishedExam = new FinishedExam();
        try {
            finishedExam = finishedExamRepository.findById(id).get();
            finishedExamRepository.delete(finishedExam);
            // Delete file from root location;
            storageService.delete(finishedExam.getFinishedExam());
            log.info("Finished Exam with id: " + finishedExam.getId() + " deleted.", this.getClass());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            log.error("Finished Exam with id: " + finishedExam.getId() + " not found.", this.getClass());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}