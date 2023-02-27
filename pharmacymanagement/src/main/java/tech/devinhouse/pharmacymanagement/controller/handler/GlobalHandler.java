package tech.devinhouse.pharmacymanagement.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.devinhouse.pharmacymanagement.exception.BadRequestException;
import tech.devinhouse.pharmacymanagement.exception.NotFoundException;
import tech.devinhouse.pharmacymanagement.exception.ServerSideException;
import tech.devinhouse.pharmacymanagement.padroes.DefaultErrorResponse;

import java.util.List;


@ControllerAdvice
public class GlobalHandler {

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity tratarBadRequestException(BadRequestException badRequestException){
        return new ResponseEntity(
                new DefaultErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        badRequestException.getMessage(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        badRequestException.getLocalizedMessage()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity tratarNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity(
                new DefaultErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        notFoundException.getMessage(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        notFoundException.getLocalizedMessage()
                ), HttpStatus.NOT_FOUND
        );
    }

    @ResponseBody
    @ExceptionHandler(ServerSideException.class)
    public ResponseEntity tratarServerSideException(ServerSideException serverSideException){

        return new ResponseEntity(
                new DefaultErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        serverSideException.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        serverSideException.getLocalizedMessage()
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarMethodArgumentNotValidException(MethodArgumentNotValidException notValidException){

        BindingResult bindingResult = notValidException.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        StringBuilder stringBuilder = new StringBuilder("Os seguintes campos não podem ser nulos: ");
        for (FieldError fieldError:fieldErrorList) {
            stringBuilder.append(" | ");
            stringBuilder.append(fieldError.getField());
        }

        return new ResponseEntity(
                new DefaultErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        stringBuilder.toString(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        notValidException.getLocalizedMessage()
                ), HttpStatus.BAD_REQUEST
        );
    }

}
