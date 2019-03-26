package com.symbio.dashboard.spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;

@Configuration
@EnableConfigurationProperties
public class PaginationConfiguration extends SpringDataWebConfiguration {

    private static final int MAX_PAGE_SIZE = 1000;

    public PaginationConfiguration(ApplicationContext context,
        ObjectFactory<ConversionService> conversionService) {
        super(context, conversionService);
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageableResolver() {
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver =
            new PageableHandlerMethodArgumentResolver(sortResolver());

        pageableHandlerMethodArgumentResolver.setMaxPageSize(MAX_PAGE_SIZE);

        return pageableHandlerMethodArgumentResolver;
    }

}