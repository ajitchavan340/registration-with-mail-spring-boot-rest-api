package com.dev.microservices.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    @PostMapping
    public String reisterUser(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
    @GetMapping("/confirm")
    public String confirmToken(@RequestParam("token") String token){
        return registrationService.confirm(token);
    }
}
