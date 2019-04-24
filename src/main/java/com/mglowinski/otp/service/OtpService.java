package com.mglowinski.otp.service;

import com.mglowinski.otp.controller.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OtpService {

    private final OtpGenerator otpGenerator;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public OtpService(OtpGenerator otpGenerator,
                      UserService userService,
                      EmailService emailService) {
        this.otpGenerator = otpGenerator;
        this.userService = userService;
        this.emailService = emailService;
    }

    public void generateOtp(String username) {
        int otpValue = otpGenerator.generateOtp(username);

        userService.getEmailByUsername(username)
                .ifPresent(userEmail -> sendEmail(userEmail, otpValue));

        log.info("Otp value is: " + otpValue);
    }

    public boolean validateOtp(String username, int otpNumber) {
        int userOtp = otpGenerator.getUserOtp(username);

        if (userOtp == otpNumber) {
            otpGenerator.clearOtpFromCache(username);
            return true;
        }

        return false;
    }

    private void sendEmail(String to, int otpValue) {
        EmailDto emailDto = new EmailDto(to, "OTP Password", String.valueOf(otpValue));
        emailService.sendSimpleMessage(emailDto);
    }

}
