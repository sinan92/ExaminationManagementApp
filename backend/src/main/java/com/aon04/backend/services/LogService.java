package com.aon04.backend.services;

import com.aon04.backend.factories.LogFactory;
import com.aon04.backend.repository.ILogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogService implements ILogService {
    @Autowired
    public ILogRepository logRepository;

    @Override
    public void init() {
    }

    @Override
    public void info(String msg, Class sender)
    {
        System.out.printf("%s %s [%s] %s - %s%n",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                "INFO",
                Thread.currentThread().getId(),
                sender.getName(),
                msg);
        logRepository.save(LogFactory.createLogEntry(msg, "INFO", sender.getName()));

    }

    @Override
    public void error(String msg, Class sender)
    {
        System.out.printf("%s %s [%s] %s - %s%n",
                LocalDateTime.now(),
                "ERROR",
                Thread.currentThread().getId(),
                sender.getName(),
                msg);
        logRepository.save(LogFactory.createLogEntry(msg, "ERROR", sender.getName()));
    }

    @Override
    public void debug(String msg, Class sender)
    {
        System.out.printf("%s %s [%s] %s - %s%n",
                LocalDateTime.now(),
                "DEBUG",
                Thread.currentThread().getId(),
                sender.getName(),
                msg);
        logRepository.save(LogFactory.createLogEntry(msg, "DEBUG", sender.getName()));
    }




}
