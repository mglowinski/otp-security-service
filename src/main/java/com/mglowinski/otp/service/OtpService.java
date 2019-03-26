package com.mglowinski.otp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OtpService {

    private final OtpGenerator otpGenerator;

    @Autowired
    public OtpService(OtpGenerator otpGenerator) {
        this.otpGenerator = otpGenerator;
    }

    public void generateOtp(String username) {
        int otpValue = otpGenerator.generateOtp(username);
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
}
