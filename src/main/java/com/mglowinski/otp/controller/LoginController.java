package com.mglowinski.otp.controller;

import com.mglowinski.otp.service.OtpService;
import com.mglowinski.otp.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    private final OtpService otpService;

    @Autowired
    public LoginController(OtpService otpService) {
        this.otpService = otpService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/otp")
    public ModelAndView otp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("otp");

        String username = SecurityUtils.getCurrentUserLogin();
        otpService.generateOtp(username);

        return modelAndView;
    }

    @PostMapping("/otp")
    public String validateOtp(@RequestParam("otpNumber") int otpNumber) {
        final String SUCCESS = "Entered Otp is valid";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";

        String username = SecurityUtils.getCurrentUserLogin();
        boolean isOtpValid = otpService.validateOtp(username, otpNumber);
        if (isOtpValid) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }

}
