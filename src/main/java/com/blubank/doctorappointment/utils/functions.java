package com.blubank.doctorappointment.utils;


import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class functions {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void handleParameterValidationException(Errors errors) throws Exception{
        if (errors.hasErrors()) {
            // Extract ConstraintViolation list from body errors
            List<ConstraintViolation<?>> violationsList = new ArrayList<>();
            for (ObjectError e : errors.getAllErrors()) {
                violationsList.add(e.unwrap(ConstraintViolation.class));
            }
            String exceptionMessage = "";

            // Construct a helpful message for each input violation
            for (ConstraintViolation<?> violation : violationsList) {
                exceptionMessage += violation.getPropertyPath() + " " + violation.getMessage() + " ";
            }

            throw new BusinessException(String.valueOf(ResponseStatus.BAD_PARAMETERS.getCode()), String.format("Request input is invalid:  %s", exceptionMessage));
        }
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, ErrorAttributeOptions includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);

                errorAttributes.remove("timestamp");
                errorAttributes.remove("status");
                errorAttributes.remove("path");
                errorAttributes.remove("error");

                Throwable   error = getError(requestAttributes);
                String  errMessage=super.getMessage(requestAttributes,error);

              if(error instanceof HttpClientErrorException.BadRequest){
                    errorAttributes.put("responseCode", ResponseStatus.BAD_REQUEST.getCode());
                    errorAttributes.put("responseDesc", ResponseStatus.BAD_REQUEST.getDescription());
                }
                else {
                    errorAttributes.put("responseCode", ResponseStatus.INTERNAL_ERROR.getCode());
                    errorAttributes.put("responseDesc",error!=null ? error.getMessage(): ResponseStatus.INTERNAL_ERROR.getDescription());
                }
                return errorAttributes;

            }
        };
    }




}
