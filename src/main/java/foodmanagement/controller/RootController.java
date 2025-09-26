package foodmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")   // This maps the root URL
    public String home() {
        return "API is running!";  // Simple message
    }
}
