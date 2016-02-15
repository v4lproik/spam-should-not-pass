package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PlatformResponse handleSQLException(HttpServletRequest request, Exception ex){
        return new PlatformResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.UNKNOWN, ex.getMessage());
    }
}
