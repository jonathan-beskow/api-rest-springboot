package br.com.jb.api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceCheckController {
    @GetMapping("/check-resources")
    public String checkResources() {
        return this.getClass().getClassLoader().getResource("META-INF/resources/swagger-ui/index.html").toString();
    }
}
