package com.blubank.doctorappointment.config;


import com.blubank.doctorappointment.enums.ResponseStatus;
import com.blubank.doctorappointment.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Configuration
@EnableScheduling
public class AppConfig {

    private static final Logger logger = LogManager.getLogger(AppConfig.class);

    @Bean(name = "doctorAppointmentDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource doctorAppointmentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        messageConverters.add(converter);
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, ErrorAttributeOptions includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
                String statusCode=errorAttributes.get("status").toString();

                errorAttributes.remove("timestamp");
                errorAttributes.remove("status");
                errorAttributes.remove("path");
                errorAttributes.remove("error");

                Throwable   error = getError(requestAttributes);
                String  errMessage=super.getMessage(requestAttributes,error);
                errorAttributes.put("error", true);
                logger.error(error!=null ? error.getMessage():null);
                if(statusCode!=null && statusCode.equalsIgnoreCase("404")){
                    errorAttributes.put("message", ResponseStatus.URL_NOT_FOUND.getDescription());
                    errorAttributes.put("result_number", ResponseStatus.URL_NOT_FOUND.getCode());

                }else if(error instanceof BusinessException){
                    BusinessException exception= (BusinessException) error;
                    errorAttributes.put("message", exception.getMessage());
                    errorAttributes.put("result_number", exception.getResult_number());
                }
                else if(error instanceof HttpClientErrorException.BadRequest) {
                    errorAttributes.put("message", ResponseStatus.BAD_REQUEST.getDescription());
                    errorAttributes.put("result_number", ResponseStatus.BAD_REQUEST.getCode());

                }
                else {
                    errorAttributes.put("message",error!=null ? error.getMessage(): ResponseStatus.INTERNAL_ERROR.getDescription());
                    errorAttributes.put("result_number", ResponseStatus.INTERNAL_ERROR.getCode());
                }
                return errorAttributes;

            }
        };
    }






}
