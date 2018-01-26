package rest.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rest.models.ErrorDetails;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Documentation source
 * http://www.springboottutorial.com/spring-boot-validation-for-rest-services
 * https://gist.github.com/matsev/4519323
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());

        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Validation Failed",
                errors);

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Throwable mostSpecificCause = ex.getMostSpecificCause();
//        ErrorMessage errorMessage;
//        if (mostSpecificCause != null) {
//            String exceptionName = mostSpecificCause.getClass().getName();
//            String message = mostSpecificCause.getMessage();
//            errorMessage = new ErrorMessage(exceptionName, message);
//        } else {
//            errorMessage = new ErrorMessage(ex.getMessage());
//        }
//        return new ResponseEntity(errorMessage, headers, status);
//    }

}
