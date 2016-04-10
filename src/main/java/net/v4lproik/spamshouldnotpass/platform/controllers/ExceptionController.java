package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.models.PlatformException;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PlatformResponse handleException(Exception ex){
        return new PlatformResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.UNKNOWN, ex.getMessage());
    }

    @ExceptionHandler(PlatformException.class)
    @ResponseBody
    public PlatformResponse handleException(PlatformException ex){
        return new PlatformResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.NOT_FOUND, ex.getMessage());
    }
}
