package com.luisdias.rmmservice.support;

import com.luisdias.rmmservice.infra.ApplicationProfiles;
import com.luisdias.rmmservice.support.client.CustomerClient;
import com.luisdias.rmmservice.support.client.DeviceClient;
import com.luisdias.rmmservice.support.client.LoginClient;
import com.luisdias.rmmservice.support.client.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(ApplicationProfiles.INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestContextConfiguration.class})
public abstract class AbstractIT {

    @Autowired
    protected LocalServerResolver localServerResolver;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected LoginClient loginClient;

    @Autowired
    protected CustomerClient customerClient;

    @Autowired
    protected ServiceClient serviceClient;

    @Autowired
    protected DeviceClient deviceClient;
}
