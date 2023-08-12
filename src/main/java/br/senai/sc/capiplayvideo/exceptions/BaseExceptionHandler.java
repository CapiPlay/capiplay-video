package br.senai.sc.capiplayvideo.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.ResponseEntity.*;

@ControllerAdvice
public class BaseExceptionHandler {

    static record ApiError(String type, String error) { }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError> handleBaseException(BaseException ex) {
        ApiError apiError = new ApiError(ex.getClass().getSimpleName(), ex.getMessage());
        return badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        ApiError apiError = new ApiError(ex.getClass().getSimpleName(), ex.getMessage());
        ex.printStackTrace();
        return internalServerError().body(apiError);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleValidacao(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError != null) {
            ApiError apiError = new ApiError(ex.getClass().getSimpleName(), fieldError.getDefaultMessage());
            return badRequest().body(apiError);
        }
        return badRequest().body("Erro de validação");
    }

}
