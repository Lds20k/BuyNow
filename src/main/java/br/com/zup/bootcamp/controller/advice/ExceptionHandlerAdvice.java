package br.com.zup.bootcamp.controller.advice;

import br.com.zup.bootcamp.controller.model.Violation;
import br.com.zup.bootcamp.controller.model.response.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * @param exception Exceção do bean validation
     * @return HttpStatus 400 e um json com as mensagem e os campos que falharam na validação
     * @apiNote Advice Handler que captura a exceção do bean validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> argumentNotValid(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();

        ValidationErrorResponse response = new ValidationErrorResponse();
        for (FieldError fieldError : result.getFieldErrors()){
            String field = fieldError.getField();
            response.add( new Violation(field,  field + " " + fieldError.getDefaultMessage()) );
        }

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * @param exception Exceção que é acionada caso não há algum header presente
     * @return HttpStatus 400 e um json com as mensagem e os header que faltam
     * @apiNote Advice Handler que captura a exceção quando houver falta de headers
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ValidationErrorResponse> missingRequestHeader(MissingRequestHeaderException exception){
        Violation violation = new Violation("Request Header", exception.getMessage());

        ValidationErrorResponse response = new ValidationErrorResponse();
        response.add(violation);

        return ResponseEntity.badRequest().body(response);
    }
}
