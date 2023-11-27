  package az.processing.auth.controller;

import az.processing.auth.exception.AuthException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


  @ControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(AuthException.class)
    public void handleAuthException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

}
