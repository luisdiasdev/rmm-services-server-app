package com.luisdias.rmmservice.support;

import com.luisdias.rmmservice.support.client.CustomerClient;
import com.luisdias.rmmservice.support.client.DeviceClient;
import com.luisdias.rmmservice.support.client.LoginClient;
import com.luisdias.rmmservice.support.client.ServiceClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestContextConfiguration {

    @Bean
    public LocalServerResolver localPortHolder() {
        return new LocalServerResolver();
    }

    @Bean
    public TestRestTemplate testRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        var testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        testRestTemplate.getRestTemplate().setErrorHandler(new RestTemplateErrorHandler());
        return testRestTemplate;
    }

    @Bean
    public LoginClient loginClient() {
        return new LoginClient();
    }

    @Bean
    public CustomerClient customerClient() {
        return new CustomerClient();
    }

    @Bean
    public ServiceClient serviceClient() {
        return new ServiceClient();
    }

    @Bean
    public DeviceClient deviceClient() {
        return new DeviceClient();
    }
}
