package com.aon04.backend.factories;

import com.aon04.backend.models.Log;

/**
 * Created by toon on 22/5/2018.
 */
public class LogFactory {
    public static Log createLogEntry(String msg, String type, String sender) {
        Log log = new Log();
        log.setDescription(msg);
        log.setLevel(type);
        log.setSenderClass(sender);
        return log;
    }
}
