package com.blubank.doctorappointment.exception;


import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.model.response.GeneralResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private  Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error(ex.getMessage());
        return handleBusinessException(new BusinessException(ResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<GeneralResponse> handleBusinessException(BusinessException ex) {
        logger.error(ex.getMessage());
        GeneralResponse resultValue = new GeneralResponse();
        int state = Integer.parseInt(ex.getResult_number().substring(2,5));
        resultValue.setError(true);
        resultValue.setResult_number(Long.valueOf(ex.getResult_number()));
        resultValue.setMessage(ex.getMessage());
        HttpStatus status_code = HttpStatus.resolve(state);

        if (status_code != null){
           // return new ResponseEntity<>(resultValue, status_code);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)    // <-- this here
                    .body(resultValue);
        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)    // <-- this here
                    .body(resultValue);
            //return new ResponseEntity<>(resultValue, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseBody
    public ResponseEntity<GeneralResponse> handleBadRequestException(HttpClientErrorException.BadRequest ex) {
        logger.error(ex.getMessage());
        GeneralResponse resultValue = new GeneralResponse();
        resultValue.setError(true);
        resultValue.setResult_number(ResponseStatus.BAD_REQUEST.getCode());
        resultValue.setMessage(ex.getMessage());
        return new ResponseEntity<>(resultValue, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        logger.error(ex.getMessage());
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        GeneralResponse generalResponse = new GeneralResponse(true, errors,ResponseStatus.METHOD_ARGUMENT_NOT_VALID.getCode());;
        return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage());
        GeneralResponse resultValue = new GeneralResponse();
        resultValue.setError(true);
        resultValue.setResult_number(ResponseStatus.BAD_PARAMETERS.getCode());
        resultValue.setMessage(ex.getMessage());
        return new ResponseEntity<>(resultValue, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        GeneralResponse resultValue = new GeneralResponse();
        resultValue.setError(true);

        if(exception instanceof HttpMediaTypeNotAcceptableException){
            resultValue.setResult_number(ResponseStatus.NOT_ACCEPTABLE.getCode());
            resultValue.setMessage(exception.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)    // <-- this here
                    .body(resultValue);
        }else{
            resultValue.setResult_number(ResponseStatus.BAD_REQUEST.getCode());
            resultValue.setMessage(exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)    // <-- this here
                    .body(resultValue);
        }
    }


    private String getStackTrace(List<StackTraceElement> stackTraceElements) {
        if (stackTraceElements != null) {
            return stackTraceElements.stream()
                    .filter(stackTraceElement -> stackTraceElement.getClassName().contains("com.sanbod"))
                    .limit(3)
                    .map(this::stackTraceToStr)
                    .collect(Collectors.joining("\n"));
        }

        return "";
    }

    private String stackTraceToStr(StackTraceElement stackTraceElement) {
        String className = stackTraceElement
                .getClassName()
                .substring(stackTraceElement
                        .getClassName()
                        .lastIndexOf(".") + 1);

        return "Class: " + className + " " +
                "Method: " + stackTraceElement.getMethodName() + " " +
                "Line: " + stackTraceElement.getLineNumber() + " " +
                "---------------------------------------------------\n";
    }
}
