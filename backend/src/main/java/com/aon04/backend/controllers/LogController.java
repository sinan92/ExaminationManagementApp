package com.aon04.backend.controllers;

import com.aon04.backend.models.Log;
import com.aon04.backend.repository.ILogRepository;
import com.aon04.backend.services.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by toon on 22/5/2018.
 */
@RestController
@RequestMapping("/logs")
public class LogController {

    private ILogService log;

    @Autowired
    private ILogRepository logRepository;

    @Autowired
    public LogController(ILogService logService) {
        this.log = logService;
    }

    /**
     * Get all logged items from database.
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllLogs() {
        Iterable<Log> logs = logRepository.findAll();

        if (((List<Log>) logs).isEmpty()) {
            //log.error("No logs found.", this.getClass());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(logs, HttpStatus.OK);
        }
    }
}