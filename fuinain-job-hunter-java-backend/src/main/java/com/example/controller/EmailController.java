package com.example.controller;

import com.example.service.EmailService;
import com.example.util.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    @GetMapping("/email")
    @ApiMessage("Send email test")
    public String sendEmail() {
        this.emailService.sendSimpleEmail();
        return "Email sent successfully";
    }

}
