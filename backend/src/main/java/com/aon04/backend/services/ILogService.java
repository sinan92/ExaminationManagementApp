package com.aon04.backend.services;

public interface ILogService {
    void init();

    void debug(String msg, Class sender);
    void error(String msg, Class sender);
    void info(String msg, Class sender);
}
