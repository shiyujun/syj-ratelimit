package com.syj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/05
 * @描述
 */

@Configuration
public class ApplicationConfiguration {
    @Bean
    public AnnotationAspect annotationAspect(){
        return new AnnotationAspect();
    }
}
