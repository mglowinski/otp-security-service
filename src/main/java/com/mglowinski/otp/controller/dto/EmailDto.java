package com.mglowinski.otp.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDto {

    private String to;
    private String subject;
    private String message;
}
