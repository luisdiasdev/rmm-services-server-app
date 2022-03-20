package com.luisdias.rmmservice.support;

import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

public class LocalServerResolver implements ApplicationListener<ServletWebServerInitializedEvent> {

    private int serverPort;

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }

    public String getLocalURL() {
        return String.format("http://localhost:%s", serverPort);
    }
}
