package br.com.zup.bootcamp.controller.advice;

import br.com.zup.bootcamp.controller.model.Violation;
import br.com.zup.bootcamp.controller.model.response.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    /**
     * @param exception Exceção do bean validation
     * @return HttpStatus 400 e um json com as mensagem e os campos que falharam na validação
     * @apiNote Advice Handler que captura a exceção do bean validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorResponse> handler(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();

        ValidationErrorResponse response = new ValidationErrorResponse();
        for (FieldError fieldError : result.getFieldErrors()){
            response.add( new Violation(fieldError.getField(), fieldError.getDefaultMessage()) );
        }

        return ResponseEntity.badRequest().body(response);
    }
}