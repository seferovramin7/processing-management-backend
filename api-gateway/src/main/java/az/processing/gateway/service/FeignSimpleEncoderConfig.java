package az.processing.gateway.service;

import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;

public class FeignSimpleEncoderConfig {
    @Bean
    public Encoder feignEncoder() {
        ObjectFactory<HttpMessageConverters> objectFactory = () ->
                new HttpMessageConverters(new FormHttpMessageConverter());
        return new SpringEncoder(objectFactory);
    }
}
