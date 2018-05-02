package com.titan.Transformerslab.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LogController {

//	private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        log.info("Request on ping endpoint");
        log.debug("DEBUG: Request on ping endpoint22");
        return "pong";
    }

    @RequestMapping(value = "/loglevel/{loglevel}", method = RequestMethod.POST)
    public String loglevel(@PathVariable("loglevel") String logLevel, @RequestParam(value="package") String packageName) throws Exception {
        log.info("Log level: " + logLevel);
        log.info("Package name: " + packageName);
        String retVal = setLogLevel(logLevel, packageName);
        return retVal;
    }

    public String setLogLevel(String logLevel, String packageName) {
        String retVal;
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
        if (logLevel.equalsIgnoreCase("DEBUG")) {
            loggerContext.getLogger(packageName).setLevel(Level.DEBUG);
            retVal = "ok";
        } else if (logLevel.equalsIgnoreCase("INFO")) {
            loggerContext.getLogger(packageName).setLevel(Level.INFO);
            retVal = "ok";
        } else if (logLevel.equalsIgnoreCase("TRACE")) {
            loggerContext.getLogger(packageName).setLevel(Level.TRACE);
            retVal = "ok";
        } else {
            log.error("Not a known loglevel: " + logLevel);
            retVal = "Error, not a known loglevel: " + logLevel;
        }
        return retVal;
    }

}
